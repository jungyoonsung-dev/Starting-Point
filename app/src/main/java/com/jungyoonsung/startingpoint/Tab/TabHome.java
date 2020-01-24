package com.jungyoonsung.startingpoint.Tab;

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
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jungyoonsung.startingpoint.Fragment.Fragment_Academic_Calendar;
import com.jungyoonsung.startingpoint.Fragment.Fragment_Lunch;
import com.jungyoonsung.startingpoint.Fragment.Fragment_Schedule;
import com.jungyoonsung.startingpoint.MainActivity;
import com.jungyoonsung.startingpoint.Notification.Receiver;
import com.jungyoonsung.startingpoint.Notification.Schedule_Lunch_Receiver;
import com.jungyoonsung.startingpoint.R;
import com.jungyoonsung.startingpoint.SchoolSettings.SchoolSettings;

import java.util.Calendar;

import me.relex.circleindicator.CircleIndicator;

public class TabHome extends Fragment {

    Context thisContext;

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabhome, container, false);
        thisContext = container.getContext();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        viewPager = view.findViewById(R.id.view_pager);
        circleIndicator = view.findViewById(R.id.circle);

        database.getReference().child("Profile").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                } else {

                    viewPager.setAdapter(new pagerAdapter(((FragmentActivity)thisContext).getSupportFragmentManager()));
                    viewPager.setCurrentItem(1);

                    circleIndicator.setViewPager(viewPager);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());

                    SharedPreferences sharedPreferences = thisContext.getSharedPreferences("Notification", thisContext.MODE_PRIVATE);
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

                    SharedPreferences.Editor editor = thisContext.getSharedPreferences("Notification", thisContext.MODE_PRIVATE).edit();
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

        return view;
    }

    void Notification_ALARM(Calendar calendar) {

        PackageManager pm = thisContext.getPackageManager();
        ComponentName receiver = new ComponentName(thisContext, Receiver.class);
        Intent alarmIntent = new Intent(thisContext, Schedule_Lunch_Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(thisContext, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) thisContext.getSystemService(Context.ALARM_SERVICE);

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
}
