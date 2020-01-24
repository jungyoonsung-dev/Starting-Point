package com.jungyoonsung.startingpoint.Tab;

import android.app.Activity;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.jungyoonsung.startingpoint.MainActivity;
import com.jungyoonsung.startingpoint.Notification.Receiver;
import com.jungyoonsung.startingpoint.Notification.Schedule_Lunch_Receiver;
import com.jungyoonsung.startingpoint.R;
import com.jungyoonsung.startingpoint.SchoolSettings.SchoolSettings;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

import petrov.kristiyan.colorpicker.ColorPicker;

public class TabAccount extends Fragment {

    Context thisContext;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabaccount, container, false);
        thisContext = container.getContext();

        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.timePicker);

        SharedPreferences sharedPreferences_check = thisContext.getSharedPreferences("Notification", thisContext.MODE_PRIVATE);
        int i_hour = sharedPreferences_check.getInt("HOUR", -1);
        int i_min = sharedPreferences_check.getInt("MIN", -1);

        if (i_hour != -1 && i_min != -1) {
            timePicker.setHour(i_hour);
            timePicker.setMinute(i_min);
        }

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                SharedPreferences.Editor editor = thisContext.getSharedPreferences("Notification", thisContext.MODE_PRIVATE).edit();
                editor.putInt("HOUR", timePicker.getHour());
                editor.putInt("MIN", timePicker.getMinute());
                editor.apply();


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

                Notification_ALARM(calendar);
            }
        });

        final CheckBox c_l_n, c_s_n;

        c_l_n = (CheckBox) view.findViewById(R.id.c_l_n);
        c_s_n = (CheckBox) view.findViewById(R.id.c_s_n);

        SharedPreferences sharedPreferences = thisContext.getSharedPreferences("Notification", thisContext.MODE_PRIVATE);
        String f_lunch = sharedPreferences.getString("CLunch", "");
        String f_schedule = sharedPreferences.getString("CSchedule", "");

        if (TextUtils.isEmpty(f_lunch)) {
            SharedPreferences.Editor editor = thisContext.getSharedPreferences("Notification", thisContext.MODE_PRIVATE).edit();
            editor.putString("CLunch", "true");
            editor.apply();
        }

        if (TextUtils.isEmpty(f_schedule)) {
            SharedPreferences.Editor editor = thisContext.getSharedPreferences("Notification", thisContext.MODE_PRIVATE).edit();
            editor.putString("CSchedule", "true");
            editor.apply();
        }

        String lunch = sharedPreferences.getString("CLunch", "");
        String schedule = sharedPreferences.getString("CSchedule", "");

        if (lunch.equals("true")) {
            c_l_n.setChecked(true);
        } else {
            c_l_n.setChecked(false);
        }

        if (schedule.equals("true")) {
            c_s_n.setChecked(true);
        } else {
            c_s_n.setChecked(false);
        }

        c_l_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c_l_n.isChecked()) {
                    Log.d("TEST", "true");
                    SharedPreferences.Editor editor = thisContext.getSharedPreferences("Notification", thisContext.MODE_PRIVATE).edit();
                    editor.putString("CLunch", "true");
                    editor.apply();
                } else {
                    Log.d("TEST", "false");
                    SharedPreferences.Editor editor = thisContext.getSharedPreferences("Notification", thisContext.MODE_PRIVATE).edit();
                    editor.putString("CLunch", "false");
                    editor.apply();
                }
            }
        });

        c_s_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c_l_n.isChecked()) {
                    Log.d("TEST", "true");
                    SharedPreferences.Editor editor = thisContext.getSharedPreferences("Notification", thisContext.MODE_PRIVATE).edit();
                    editor.putString("CSchedule", "true");
                    editor.apply();
                } else {
                    Log.d("TEST", "false");
                    SharedPreferences.Editor editor = thisContext.getSharedPreferences("Notification", thisContext.MODE_PRIVATE).edit();
                    editor.putString("CSchedule", "false");
                    editor.apply();
                }
            }
        });


        TextView t_edit = (TextView) view.findViewById(R.id.t_edit);
        t_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((Activity) thisContext).finish();
                Intent intent = new Intent(thisContext, SchoolSettings.class);
                startActivity(intent);
            }
        });

        TextView t_background = (TextView) view.findViewById(R.id.t_background);
        t_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openColorPicker();
//                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(thisContext);
//                LayoutInflater myInflater = LayoutInflater.from(thisContext);
//
//                final View mView = myInflater.inflate(R.layout.dialog_background, null);
//
//                mBuilder.setView(mView);
//                final AlertDialog dialog = mBuilder.create();
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//                Window window = dialog.getWindow();
//                window.setGravity(Gravity.CENTER);
//
//                dialog.show();
            }
        });

        TextView t_log_out = (TextView) view.findViewById(R.id.t_log_out);
        t_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(thisContext)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                                homeIntent.addCategory( Intent.CATEGORY_HOME);
                                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(homeIntent);
                            }
                        });
            }
        });

        TextView t_open_source_license = (TextView) view.findViewById(R.id.t_open_source_license);
        t_open_source_license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(thisContext, "CircleIndicator\nhttps://github.com/ongakuer/CircleIndicator\nApache License, Version 2.0", Toast.LENGTH_SHORT).show();

            }
        });

        TextView t_information = (TextView) view.findViewById(R.id.t_information);
        t_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(thisContext,
                        "학교기본정보\n기관 : 나이스 교육정보 개방 포털\nhttps://open.neis.go.kr/portal/data/service/selectServicePage.do?page=1&rows=10&sortColumn=&sortDirection=&infId=OPEN17020190531110010104913&infSeq=1\n\n\n" +
                                "급식식단\n기관 : 나이스 교육정보 개방 포털\nhttps://open.neis.go.kr/portal/data/service/selectServicePage.do?page=1&rows=10&sortColumn=&sortDirection=&infId=OPEN17320190722180924242823&infSeq=1\n\n\n" +
                                "초등학교시간표\n기관 : 나이스 교육정보 개방 포털\nhttps://open.neis.go.kr/portal/data/service/selectServicePage.do?page=1&rows=10&sortColumn=&sortDirection=&infId=OPEN15020190408160341416743&infSeq=1\n\n\n" +
                                "중학교시간표\n기관 : 나이스 교육정보 개방 포털\nhttps://open.neis.go.kr/portal/data/service/selectServicePage.do?page=1&rows=10&sortColumn=&sortDirection=&infId=OPEN15120190408165334348844&infSeq=1\n\n\n" +
                                "고등학교시간표\n기관 : 나이스 교육정보 개방 포털\nhttps://open.neis.go.kr/portal/data/service/selectServicePage.do?page=1&rows=10&sortColumn=&sortDirection=&infId=OPEN15220190408172102028818&infSeq=1\n\n\n" +
                                "학사일정\n기관 : 나이스 교육정보 개방 포털\nhttps://open.neis.go.kr/portal/data/service/selectServicePage.do?page=1&rows=10&sortColumn=&sortDirection=&infId=OPEN17220190722175038389180&infSeq=1", Toast.LENGTH_SHORT).show();
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

    private void openColorPicker() {
        final ColorPicker colorPicker = new ColorPicker((Activity) thisContext);  // ColorPicker 객체 생성
        ArrayList<String> colors = new ArrayList<>();  // Color 넣어줄 list

        colors.add("#FF000000");
        colors.add("#FF111111");
        colors.add("#FF222222");
        colors.add("#FF333333");
        colors.add("#FF444444");

        colorPicker.setColors(colors)
                .setTitle("배경색")
                .setColumns(5)
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        SharedPreferences.Editor editor = thisContext.getSharedPreferences("Background", thisContext.MODE_PRIVATE).edit();
                        editor.putInt("Color", color);
                        editor.apply();

                        LinearLayout mainLineraLayout = (LinearLayout)MainActivity.activity_main_LinearLayout;
                        mainLineraLayout.setBackgroundColor(color);
                    }

                    @Override
                    public void onCancel() {
                    }
                }).show();
    }
}