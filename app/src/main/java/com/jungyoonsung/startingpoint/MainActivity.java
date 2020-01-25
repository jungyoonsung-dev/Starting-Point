package com.jungyoonsung.startingpoint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
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

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static Context MainContext;

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    private FirebaseAuth.AuthStateListener mAuthListener;
    public static final int RC_SIGN_IN = 1;

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.GoogleBuilder().build()
//            new AuthUI.IdpConfig.FacebookBuilder().build()
    );

    public static LinearLayout activity_main_LinearLayout;
    public static TextView textView_name, textView_school, textView_grade_class_number;

    public static BottomNavigationView navView_1, navView_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainContext = MainActivity.this;

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        activity_main_LinearLayout = (LinearLayout) findViewById(R.id.activity_main_LinearLayout);

        textView_name = (TextView) findViewById(R.id.textView_name);
        textView_school = (TextView) findViewById(R.id.textView_school);
        textView_grade_class_number = (TextView) findViewById(R.id.textView_grade_class_number);

        navView_1 = (BottomNavigationView) findViewById(R.id.nav_view_1);
        navView_2 = (BottomNavigationView) findViewById(R.id.nav_view_2);

        navView_1.setVisibility(View.VISIBLE);
        navView_2.setVisibility(View.GONE);

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

                                SharedPreferences sharedPreferencesBackground = getSharedPreferences("Background", MODE_PRIVATE);
                                int position = sharedPreferencesBackground.getInt("Position", 0);
                                int color = sharedPreferencesBackground.getInt("Color", 0);

                                if (color == 0) {
                                    activity_main_LinearLayout.setBackgroundResource(android.R.color.black);
                                } else if (color != 0) {
                                    activity_main_LinearLayout.setBackgroundColor(color);

                                    if (position < 10) {
                                        textView_name.setTextColor(Color.parseColor("#FFFFFF"));
                                        textView_school.setTextColor(Color.parseColor("#EEEEEE"));
                                        textView_grade_class_number.setTextColor(Color.parseColor("#EEEEEE"));

                                        navView_1.setVisibility(View.VISIBLE);
                                        navView_2.setVisibility(View.GONE);

                                        NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
                                        NavigationUI.setupWithNavController(navView_1, navController);
                                    } else {
                                        textView_name.setTextColor(Color.parseColor("#000000"));
                                        textView_school.setTextColor(Color.parseColor("#111111"));
                                        textView_grade_class_number.setTextColor(Color.parseColor("#111111"));

                                        navView_1.setVisibility(View.GONE);
                                        navView_2.setVisibility(View.VISIBLE);

                                        NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
                                        NavigationUI.setupWithNavController(navView_2, navController);
                                    }
                                }

                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(System.currentTimeMillis());

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