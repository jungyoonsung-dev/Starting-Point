package com.jungyoonsung.startingpoint.Notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED")) {

            Intent intentScheduleReceiver = new Intent(context, Schedule_Lunch_Academic_Calendar_Receiver.class);
            PendingIntent pendingIntentScheduleReceiver = PendingIntent.getBroadcast(context, 0, intentScheduleReceiver, 0);

            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            SharedPreferences sharedPreferences = context.getSharedPreferences("Notification", MODE_PRIVATE);
            long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());


            Calendar current_calendar = Calendar.getInstance();
            Calendar nextNotifyTime = new GregorianCalendar();
            nextNotifyTime.setTimeInMillis(sharedPreferences.getLong("nextNotifyTime", millis));

            if (current_calendar.after(nextNotifyTime)) {
                nextNotifyTime.add(Calendar.DATE, 1);
            }

            if (manager != null) {
                manager.setRepeating(AlarmManager.RTC_WAKEUP, nextNotifyTime.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntentScheduleReceiver);
            }
        }
    }
}
