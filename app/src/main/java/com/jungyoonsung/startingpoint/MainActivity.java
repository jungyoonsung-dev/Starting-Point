package com.jungyoonsung.startingpoint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jungyoonsung.startingpoint.Major.MAJORMODEL;
import com.jungyoonsung.startingpoint.Major.TestActivity;
import com.jungyoonsung.startingpoint.Notification.Receiver;
import com.jungyoonsung.startingpoint.Notification.Schedule_Lunch_Academic_Calendar_Receiver;
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
    );

    public static LinearLayout activity_main_LinearLayout;
    public static TextView textView_name, textView_major, textView_school, textView_grade_class_number;

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
        textView_major = (TextView) findViewById(R.id.textView_major);
        textView_major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater myInflater = LayoutInflater.from(MainActivity.this);
                final View mView = myInflater.inflate(R.layout.dialog_major, null);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();

                final EditText dialog_major_edittext = (EditText) mView.findViewById(R.id.dialog_major_edittext);

                TextView dialog_major_textview = (TextView) mView.findViewById(R.id.dialog_major_textview);
                dialog_major_textview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        SharedPreferences sharedPreferencesUSER = getSharedPreferences("USER", MODE_PRIVATE);
                        String s_major = sharedPreferencesUSER.getString("s_5_SCHUL_KND_SC_NM", "");

                        if (s_major.equals("고등학교") || s_major.equals("중학교")) {

                            final AlertDialog.Builder mBuilder2 = new AlertDialog.Builder(MainActivity.this);
                            LayoutInflater myInflater2 = LayoutInflater.from(MainActivity.this);
                            final View mView2 = myInflater2.inflate(R.layout.dialog_gender, null);
                            mBuilder2.setView(mView2);
                            final AlertDialog dialog2 = mBuilder2.create();
                            dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog2.show();

                            TextView t_m = (TextView) mView2.findViewById(R.id.t_m);
                            TextView t_w = (TextView) mView2.findViewById(R.id.t_w);

                            t_m.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(MainActivity.this, TestActivity.class);
                                    intent.putExtra("MF", "M");
                                    startActivity(intent);
                                }
                            });

                            t_w.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(MainActivity.this, TestActivity.class);
                                    intent.putExtra("MF", "F");
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                });

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        String s_major = dialog_major_edittext.getText().toString();

                        if (!(TextUtils.isEmpty(s_major))) {
                            MAJORMODEL majormodel = new MAJORMODEL();
                            majormodel.s_major = s_major;

                            database.getReference().child("Major").child(auth.getCurrentUser().getUid()).setValue(majormodel);
                        }
                    }
                });
            }
        });

        textView_school = (TextView) findViewById(R.id.textView_school);
        textView_grade_class_number = (TextView) findViewById(R.id.textView_grade_class_number);

        navView_1 = (BottomNavigationView) findViewById(R.id.nav_view_1);
        navView_2 = (BottomNavigationView) findViewById(R.id.nav_view_2);

        navView_1.setVisibility(View.VISIBLE);
        navView_2.setVisibility(View.GONE);



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {

                    int permissionCheckWRITE = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (!(permissionCheckWRITE == PackageManager.PERMISSION_GRANTED)) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }

                    database.getReference().child("Major").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String s_major = String.valueOf(dataSnapshot.child("s_major").getValue());

                            if (!(s_major.equals("null"))) {
                                textView_major.setText("   " + s_major);
                            } else {
                                textView_major.setText("   학과");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    SharedPreferences sharedPreferencesUSER = getSharedPreferences("USER", MODE_PRIVATE);
                    String check = sharedPreferencesUSER.getString("Check", "");

                    if (!(TextUtils.isEmpty(check))) {
                        
                        String s_name = sharedPreferencesUSER.getString("Name", "");
                        String s_school_name = sharedPreferencesUSER.getString("School_Name", "");
                        String s_grade_class_number = sharedPreferencesUSER.getString("Grade_Class_Number", "");

                        textView_name.setText(s_name + "   ");
                        textView_school.setText(s_school_name);
                        textView_grade_class_number.setText(s_grade_class_number);

                        SharedPreferences sharedPreferences = getSharedPreferences("Notification", MODE_PRIVATE);
                        String f_lunch = sharedPreferences.getString("CLunch", "");
                        String f_schedule = sharedPreferences.getString("CSchedule", "");
                        String f_academic_calendar = sharedPreferences.getString("CAcademic_Calendar", "");

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

                        if (TextUtils.isEmpty(f_academic_calendar)) {
                            SharedPreferences.Editor editor = getSharedPreferences("Notification", MODE_PRIVATE).edit();
                            editor.putString("CAcademic_Calendar", "true");
                            editor.apply();
                        }

                        SharedPreferences sharedPreferencesBackground = getSharedPreferences("Background", MODE_PRIVATE);
                        int position = sharedPreferencesBackground.getInt("Position", 0);
                        int color = sharedPreferencesBackground.getInt("Color", 0);

                        if (color == 0) {
                            activity_main_LinearLayout.setBackgroundResource(android.R.color.black);

                            textView_name.setTextColor(Color.parseColor("#FFFFFF"));
                            textView_school.setTextColor(Color.parseColor("#EEEEEE"));
                            textView_grade_class_number.setTextColor(Color.parseColor("#EEEEEE"));

                            navView_1.setVisibility(View.VISIBLE);
                            navView_2.setVisibility(View.GONE);

                            NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
                            NavigationUI.setupWithNavController(navView_1, navController);
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

                        String ATPT_OFCDC_SC_CODE = sharedPreferencesUSER.getString("s_1_ATPT_OFCDC_SC_CODE", "");
                        String SD_SCHUL_CODE = sharedPreferencesUSER.getString("s_3_SD_SCHUL_CODE", "");
                        String SCHUL_KND_SC_NM = sharedPreferencesUSER.getString("s_5_SCHUL_KND_SC_NM", "");
                        String s_grade = sharedPreferencesUSER.getString("s_6_grade", "");
                        String s_class = sharedPreferencesUSER.getString("s_7_class", "");

                        SharedPreferences.Editor editor = getSharedPreferences("Notification", MODE_PRIVATE).edit();
                        editor.putString("ATPT_OFCDC_SC_CODE", ATPT_OFCDC_SC_CODE);
                        editor.putString("SD_SCHUL_CODE", SD_SCHUL_CODE);
                        editor.putString("SCHUL_KND_SC_NM", SCHUL_KND_SC_NM);
                        editor.putString("s_grade", s_grade);
                        editor.putString("s_class", s_class);
                        editor.putLong("nextNotifyTime", (long)calendar.getTimeInMillis());
                        editor.apply();

                        Notification_ALARM(calendar);

                    } else {

                        database.getReference().child("Profile").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() == null) {
                                    Intent intent = new Intent(MainActivity.this, SchoolSettings.class);
                                    startActivity(intent);
                                    finish();
                                } else {

                                    SharedPreferences.Editor editor = getSharedPreferences("USER", MODE_PRIVATE).edit();

                                    editor.putString("Check", "true");

                                    editor.putString("Name", auth.getCurrentUser().getDisplayName());
                                    editor.putString("School_Name", String.valueOf(dataSnapshot.child("s_4_SCHUL_NM").getValue()) + " ");

                                    String t_s_grade = String.valueOf(dataSnapshot.child("s_6_grade").getValue());
                                    String t_s_class = String.valueOf(dataSnapshot.child("s_7_class").getValue());
                                    String t_s_number = String.valueOf(dataSnapshot.child("s_8_number").getValue());
                                    if (t_s_class.length() == 1) {
                                        t_s_class = "0" + t_s_class;
                                    }
                                    if (t_s_number.length() == 1) {
                                        t_s_number = "0" + t_s_number;
                                    }
                                    editor.putString("Grade_Class_Number", t_s_grade + t_s_class + t_s_number);
                                    editor.putString("s_1_ATPT_OFCDC_SC_CODE", String.valueOf(dataSnapshot.child("s_1_ATPT_OFCDC_SC_CODE").getValue()));
                                    editor.putString("s_3_SD_SCHUL_CODE", String.valueOf(dataSnapshot.child("s_3_SD_SCHUL_CODE").getValue()));
                                    editor.putString("s_5_SCHUL_KND_SC_NM", String.valueOf(dataSnapshot.child("s_5_SCHUL_KND_SC_NM").getValue()));
                                    editor.putString("s_6_grade", String.valueOf(dataSnapshot.child("s_6_grade").getValue()));
                                    editor.putString("s_7_class", String.valueOf(dataSnapshot.child("s_7_class").getValue()));
                                    editor.apply();


//                                    textView_name.setText(auth.getCurrentUser().getDisplayName());
//                                    textView_school.setText(String.valueOf(dataSnapshot.child("s_4_SCHUL_NM").getValue()) + "   ");

//                                    textView_grade_class_number.setText("   " + t_s_grade + t_s_class + t_s_number);

                                    SharedPreferences sharedPreferencesUSER = getSharedPreferences("USER", MODE_PRIVATE);

                                    String s_name = sharedPreferencesUSER.getString("Name", "");
                                    String s_school_name = sharedPreferencesUSER.getString("School_Name", "");
                                    String s_grade_class_number = sharedPreferencesUSER.getString("Grade_Class_Number", "");

                                    textView_name.setText(s_name);
                                    textView_school.setText(s_school_name);
                                    textView_grade_class_number.setText(s_grade_class_number);

                                    SharedPreferences sharedPreferences = getSharedPreferences("Notification", MODE_PRIVATE);
                                    String f_lunch = sharedPreferences.getString("CLunch", "");
                                    String f_schedule = sharedPreferences.getString("CSchedule", "");
                                    String f_academic_calendar = sharedPreferences.getString("CAcademic_Calendar", "");

                                    if (TextUtils.isEmpty(f_lunch)) {
                                        SharedPreferences.Editor editor2 = getSharedPreferences("Notification", MODE_PRIVATE).edit();
                                        editor2.putString("CLunch", "true");
                                        editor2.apply();
                                    }

                                    if (TextUtils.isEmpty(f_schedule)) {
                                        SharedPreferences.Editor editor2 = getSharedPreferences("Notification", MODE_PRIVATE).edit();
                                        editor2.putString("CSchedule", "true");
                                        editor2.apply();
                                    }

                                    if (TextUtils.isEmpty(f_academic_calendar)) {
                                        SharedPreferences.Editor editor2 = getSharedPreferences("Notification", MODE_PRIVATE).edit();
                                        editor2.putString("CAcademic_Calendar", "true");
                                        editor2.apply();
                                    }

                                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(0,0);

                                    SharedPreferences sharedPreferencesBackground = getSharedPreferences("Background", MODE_PRIVATE);
                                    int position = sharedPreferencesBackground.getInt("Position", 0);
                                    int color = sharedPreferencesBackground.getInt("Color", 0);

                                    if (color == 0) {
                                        activity_main_LinearLayout.setBackgroundResource(android.R.color.black);

                                        textView_name.setTextColor(Color.parseColor("#FFFFFF"));
                                        textView_school.setTextColor(Color.parseColor("#EEEEEE"));
                                        textView_grade_class_number.setTextColor(Color.parseColor("#EEEEEE"));

                                        navView_1.setVisibility(View.VISIBLE);
                                        navView_2.setVisibility(View.GONE);

                                        NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
                                        NavigationUI.setupWithNavController(navView_1, navController);
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

                                    String ATPT_OFCDC_SC_CODE = sharedPreferencesUSER.getString("s_1_ATPT_OFCDC_SC_CODE", "");
                                    String SD_SCHUL_CODE = sharedPreferencesUSER.getString("s_3_SD_SCHUL_CODE", "");
                                    String SCHUL_KND_SC_NM = sharedPreferencesUSER.getString("s_5_SCHUL_KND_SC_NM", "");
                                    String s_grade = sharedPreferencesUSER.getString("s_6_grade", "");
                                    String s_class = sharedPreferencesUSER.getString("s_7_class", "");

                                    SharedPreferences.Editor editor2 = getSharedPreferences("Notification", MODE_PRIVATE).edit();
                                    editor2.putString("ATPT_OFCDC_SC_CODE", ATPT_OFCDC_SC_CODE);
                                    editor2.putString("SD_SCHUL_CODE", SD_SCHUL_CODE);
                                    editor2.putString("SCHUL_KND_SC_NM", SCHUL_KND_SC_NM);
                                    editor2.putString("s_grade", s_grade);
                                    editor2.putString("s_class", s_class);
                                    editor2.putLong("nextNotifyTime", (long)calendar.getTimeInMillis());
                                    editor2.apply();

                                    Notification_ALARM(calendar);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
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
        Intent alarmIntent = new Intent(this, Schedule_Lunch_Academic_Calendar_Receiver.class);
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
                editor.putString("CAcademic_Calendar", "true");
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