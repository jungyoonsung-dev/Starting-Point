package com.jungyoonsung.startingpoint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jungyoonsung.startingpoint.Notification.Receiver;
import com.jungyoonsung.startingpoint.Notification.Schedule_Lunch_Receiver;
import com.jungyoonsung.startingpoint.SchoolSettings.SchoolSettings;
import com.jungyoonsung.startingpoint.Tab.TabAccount;
import com.jungyoonsung.startingpoint.Tab.TabHome;
import com.jungyoonsung.startingpoint.Tab.TabSchool;
import com.jungyoonsung.startingpoint.Tab.TabStudy;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    private FirebaseAuth.AuthStateListener mAuthListener;
    public static final int RC_SIGN_IN = 1;

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.GoogleBuilder().build()
//            new AuthUI.IdpConfig.FacebookBuilder().build()
    );

    TextView textView_name, textView_school, textView_grade_class_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        textView_name = (TextView) findViewById(R.id.textView_name);
        textView_school = (TextView) findViewById(R.id.textView_school);
        textView_grade_class_number = (TextView) findViewById(R.id.textView_grade_class_number);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    database.getReference().child("Profile").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null) {
                                Intent intent = new Intent(MainActivity.this, SchoolSettings.class);
                                startActivity(intent);
                                finish();
                            } else {

                                textView_name.setText(auth.getCurrentUser().getDisplayName());
                                textView_school.setText(String.valueOf(dataSnapshot.child("s_4_SCHUL_NM").getValue()) + "   ");

                                String t_s_grade = String.valueOf(dataSnapshot.child("s_6_grade").getValue());
                                String t_s_class = String.valueOf(dataSnapshot.child("s_7_class").getValue());
                                String t_s_number = String.valueOf(dataSnapshot.child("s_8_number").getValue());

                                if (t_s_class.length() == 1) {
                                    t_s_class = "0" + t_s_class;
                                }

                                if (t_s_number.length() == 1) {
                                    t_s_number = "0" + t_s_number;
                                }

                                textView_grade_class_number.setText("   " + t_s_grade + t_s_class + t_s_number);

                                BottomNavigationView navView = findViewById(R.id.nav_view);
                                AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                                        R.id.navigation_home, R.id.navigation_school, R.id.navigation_study, R.id.navigation_account)
                                        .build();
                                NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
                                NavigationUI.setupActionBarWithNavController(MainActivity.this, navController, appBarConfiguration);
                                NavigationUI.setupWithNavController(navView, navController);

//                                BottomNavigationView main_bottomNavigation = (BottomNavigationView) findViewById(R.id.main_bottomNavigation);
//                                main_bottomNavigation.setOnNavigationItemSelectedListener(navListener);

//                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                                        new TabHome()).commit();

                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(System.currentTimeMillis());

                                SharedPreferences sharedPreferences = getSharedPreferences("Notification", MODE_PRIVATE);
                                int i_hour = sharedPreferences.getInt("HOUR", -1);
                                int i_min = sharedPreferences.getInt("MIN", -1);

                                if (i_hour != -1 && i_min != -1) {
                                    calendar.set(Calendar.HOUR_OF_DAY, i_hour);
                                    calendar.set(Calendar.MINUTE, i_min);
                                    calendar.set(Calendar.SECOND, 0);
                                } else {
                                    calendar.set(Calendar.HOUR_OF_DAY, 6);
                                    calendar.set(Calendar.MINUTE, 0);
                                    calendar.set(Calendar.SECOND, 0);
                                }

                                if (calendar.before(Calendar.getInstance())) {
                                    calendar.add(Calendar.DATE, 1);
                                }


                                String ATPT_OFCDC_SC_CODE = String.valueOf(dataSnapshot.child("s_1_ATPT_OFCDC_SC_CODE").getValue());
                                String SD_SCHUL_CODE = String.valueOf(dataSnapshot.child("s_3_SD_SCHUL_CODE").getValue());
                                String SCHUL_KND_SC_NM = String.valueOf(dataSnapshot.child("s_5_SCHUL_KND_SC_NM").getValue());
                                String s_grade = String.valueOf(dataSnapshot.child("s_6_grade").getValue());
                                String s_class = String.valueOf(dataSnapshot.child("s_7_class").getValue());

                                SharedPreferences.Editor editor = getSharedPreferences("Notification", MODE_PRIVATE).edit();
                                editor.putString("ATPT_OFCDC_SC_CODE", ATPT_OFCDC_SC_CODE);
                                editor.putString("SD_SCHUL_CODE", SD_SCHUL_CODE);
                                editor.putString("SCHUL_KND_SC_NM", SCHUL_KND_SC_NM);
                                editor.putString("s_grade", s_grade);
                                editor.putString("s_class", s_class);
                                editor.putLong("nextNotifyTime", (long)calendar.getTimeInMillis());
                                editor.apply();

                                Notification_ALARM(calendar);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else {

                    startActivityForResult(

                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .setIsSmartLockEnabled(false)
                                    .setLogo(R.mipmap.icon)
                                    .setTheme(R.style.FirebaseUITheme)
                                    .build(),
                            RC_SIGN_IN
                    );
                }
            }
        };

        SharedPreferences sharedPreferences = getSharedPreferences("Notification", MODE_PRIVATE);
        String f_lunch = sharedPreferences.getString("CLunch", "");
        String f_schedule = sharedPreferences.getString("CSchedule", "");

        if (TextUtils.isEmpty(f_lunch)) {
            SharedPreferences.Editor editor = getSharedPreferences("Notification", MODE_PRIVATE).edit();
            editor.putString("CLunch", "true");
            editor.apply();
        }

        if (TextUtils.isEmpty(f_schedule)) {
            SharedPreferences.Editor editor = getSharedPreferences("Notification", MODE_PRIVATE).edit();
            editor.putString("CSchedule", "true");
            editor.apply();
        }
    }

    void Notification_ALARM(Calendar calendar) {

        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this, Receiver.class);
        Intent alarmIntent = new Intent(this, Schedule_Lunch_Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (!(resultCode == RESULT_OK)) {
            finish();
        }

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                SharedPreferences.Editor editor = getSharedPreferences("Notification", MODE_PRIVATE).edit();
                editor.putString("CLunch", "true");
                editor.putString("CSchedule", "true");
                editor.apply();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        auth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        auth.removeAuthStateListener(mAuthListener);
    }
}