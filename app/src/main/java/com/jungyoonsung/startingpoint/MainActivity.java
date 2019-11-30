package com.jungyoonsung.startingpoint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jungyoonsung.startingpoint.Fragment.Fragment_Academic_Calendar;
import com.jungyoonsung.startingpoint.Fragment.Fragment_Lunch;
import com.jungyoonsung.startingpoint.Fragment.Fragment_Schedule;
import com.jungyoonsung.startingpoint.Notification.Schedule_Lunch_Receiver;
import com.jungyoonsung.startingpoint.Notification.DeviceBootReceiver;
import com.jungyoonsung.startingpoint.SchoolSettings.SchoolSettings;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    private FirebaseAuth.AuthStateListener mAuthListener;
    public static final int RC_SIGN_IN = 1;

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.GoogleBuilder().build(),
            new AuthUI.IdpConfig.FacebookBuilder().build()
    );

    TextView textView_name, textView_school, textView_grade_class_number;

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;

    TextView t_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        textView_name = (TextView) findViewById(R.id.textView_name);
        textView_school = (TextView) findViewById(R.id.textView_school);
        textView_grade_class_number = (TextView) findViewById(R.id.textView_grade_class_number);

        viewPager = findViewById(R.id.view_pager);
        circleIndicator = findViewById(R.id.circle);

        t_settings = (TextView) findViewById(R.id.t_settings);
        t_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater myInflater = LayoutInflater.from(MainActivity.this);

                final View mView = myInflater.inflate(R.layout.dialog_settings, null);

                LinearLayout facebook_linearLayout = (LinearLayout) mView.findViewById(R.id.facebook_linearLayout);
                LinearLayout instagram_linearaLayout = (LinearLayout) mView.findViewById(R.id.instagram_linearLayout);
                LinearLayout github_linearLayout = (LinearLayout) mView.findViewById(R.id.github_linearLayout);
                LinearLayout mail_linearLayout = (LinearLayout) mView.findViewById(R.id.mail_linearLayout);

                facebook_linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/jungyoonsung.dev"));
                        startActivity(intent);
                    }
                });

                instagram_linearaLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/jungyoonsung.dev"));
                        startActivity(intent);
                    }
                });

                github_linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/jungyoonsung-dev"));
                        startActivity(intent);
                    }
                });

                mail_linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] mail = {"jungyoonsung.dev@gmail.com"};

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("plain/Text");
                        intent.putExtra(Intent.EXTRA_EMAIL, mail);
                        intent.setType("message/rfc822");
                        startActivity(intent);
                    }
                });

                TextView t_open_source_license = (TextView) mView.findViewById(R.id.t_open_source_license);
                t_open_source_license.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Toast.makeText(MainActivity.this, "CircleIndicator\nhttps://github.com/ongakuer/CircleIndicator\nApache License, Version 2.0", Toast.LENGTH_SHORT).show();

                    }
                });

                TextView t_information = (TextView) mView.findViewById(R.id.t_information);
                t_information.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Toast.makeText(MainActivity.this,
                                "학교기본정보\n기관 : 나이스 교육정보 개방 포털\nhttps://open.neis.go.kr/portal/data/service/selectServicePage.do?page=1&rows=10&sortColumn=&sortDirection=&infId=OPEN17020190531110010104913&infSeq=1\n\n\n" +
                                "급식식단\n기관 : 나이스 교육정보 개방 포털\nhttps://open.neis.go.kr/portal/data/service/selectServicePage.do?page=1&rows=10&sortColumn=&sortDirection=&infId=OPEN17320190722180924242823&infSeq=1\n\n\n" +
                                "초등학교시간표\n기관 : 나이스 교육정보 개방 포털\nhttps://open.neis.go.kr/portal/data/service/selectServicePage.do?page=1&rows=10&sortColumn=&sortDirection=&infId=OPEN15920190423094641415608&infSeq=1\n\n\n" +
                                "중학교시간표\n기관 : 나이스 교육정보 개방 포털\nhttps://open.neis.go.kr/portal/data/service/selectServicePage.do?page=1&rows=10&sortColumn=&sortDirection=&infId=OPEN15120190408165334348844&infSeq=1\n\n\n" +
                                "고등학교시간표\n기관 : 나이스 교육정보 개방 포털\nhttps://open.neis.go.kr/portal/data/service/selectServicePage.do?page=1&rows=10&sortColumn=&sortDirection=&infId=OPEN15220190408172102028818&infSeq=1\n\n\n" +
                                "학사일정\n기관 : 나이스 교육정보 개방 포털\nhttps://open.neis.go.kr/portal/data/service/selectServicePage.do?page=1&rows=10&sortColumn=&sortDirection=&infId=OPEN17220190722175038389180&infSeq=1", Toast.LENGTH_SHORT).show();
                    }
                });

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
            }
        });

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

                                viewPager.setAdapter(new pagerAdapter(getSupportFragmentManager()));
                                viewPager.setCurrentItem(1);

                                circleIndicator.setViewPager(viewPager);

                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                calendar.set(Calendar.HOUR_OF_DAY, 6);
                                calendar.set(Calendar.MINUTE, 0);
                                calendar.set(Calendar.SECOND, 0);

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

                                diaryNotification(calendar);
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

    void diaryNotification(Calendar calendar) {

        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(this, Schedule_Lunch_Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    private class pagerAdapter extends FragmentStatePagerAdapter {
        public pagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new Fragment_Lunch();
                case 1:
                    return new Fragment_Schedule();
                case 2:
                    return new Fragment_Academic_Calendar();
                default:
                    return null;
            }
        }
        @Override
        public int getCount()
        {
            return 3;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (!(resultCode == RESULT_OK)) {
            finish();
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
