package com.jungyoonsung.startingpoint.Notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jungyoonsung.startingpoint.MainActivity;
import com.jungyoonsung.startingpoint.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class Schedule_Lunch_Receiver extends BroadcastReceiver {

    private RequestQueue requestQueue;
    private RequestQueue requestQueueLunch;
    private List<String> period = new ArrayList<>();

    private List<String> lunch = new ArrayList<>();

    @Override
    public void onReceive(final Context context, Intent intent) {



        requestQueue = Volley.newRequestQueue(context);
        requestQueueLunch = Volley.newRequestQueue(context);

        Date date = Calendar.getInstance().getTime();

        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        String year = yearFormat.format(date);
        String month = monthFormat.format(date);
        String day = dayFormat.format(date);

        String alldate = year + month + day;


        final SharedPreferences sharedPreferences = context.getSharedPreferences("Notification", MODE_PRIVATE);
        String ATPT_OFCDC_SC_CODE = sharedPreferences.getString("ATPT_OFCDC_SC_CODE", "");
        String SD_SCHUL_CODE = sharedPreferences.getString("SD_SCHUL_CODE", "");
        final String SCHUL_KND_SC_NM = sharedPreferences.getString("SCHUL_KND_SC_NM", "");
        String s_grade = sharedPreferences.getString("s_grade", "");
        String s_class = sharedPreferences.getString("s_class", "");

        String url_schedule = null;
        String url_lunch = "https://open.neis.go.kr/hub/mealServiceDietInfo?KEY=7038ed79d5144336975a34e3c1a184cc&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&MLSV_YMD=" + alldate;

        Log.d("TEST", url_lunch);

        if (SCHUL_KND_SC_NM.equals("초등학교")) {
            Log.d("TEST", "1");
            url_schedule = "https://open.neis.go.kr/hub/elsTimetable?KEY=28d1c579be6f424a82296bef1d143291&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&ALL_TI_YMD=" + alldate + "&GRADE=" + s_grade + "&CLASS_NM=" + s_class;
        } else if (SCHUL_KND_SC_NM.equals("중학교")) {
            Log.d("TEST", "2");
            url_schedule = "https://open.neis.go.kr/hub/misTimetable?KEY=5e224b3241764004ba6db97bf213528d&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&ALL_TI_YMD=" + alldate + "&GRADE=" + s_grade + "&CLASS_NM=" + s_class;
        } else if (SCHUL_KND_SC_NM.equals("고등학교")) {
            Log.d("TEST", "3");
            url_schedule = "https://open.neis.go.kr/hub/hisTimetable?KEY=112df38f4e454ee98c21632e2fc8130f&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&ALL_TI_YMD=" + alldate + "&GRADE=" + s_grade + "&CLRM_NM=" + s_class;
        }

        JsonObjectRequest request_schedule = new JsonObjectRequest(Request.Method.GET, url_schedule, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (SCHUL_KND_SC_NM.equals("초등학교")) {
                            try {
                                JSONArray jsonArrayInfo = response.getJSONArray("elsTimetable");
                                JSONObject response2 = jsonArrayInfo.getJSONObject(1);
                                JSONArray jsonArrayrow = response2.getJSONArray("row");
                                for (int i = 0; i < jsonArrayrow.length(); i++) {
                                    JSONObject response3 = jsonArrayrow.getJSONObject(i);

                                    String s_period = response3.getString("ITRT_CNTNT");

                                    period.add(s_period);
                                }

                                String schedule = null;

                                for (int i = 0; i < period.size(); i++) {

                                    if (i == 0) {
                                        schedule = period.get(i) + ", ";
                                    } else if (i == period.size() - 1) {
                                        schedule = schedule + period.get(i) + " ";
                                    } else  {
                                        schedule = schedule + period.get(i) + ", ";
                                    }
                                }

                                SharedPreferences.Editor editor = context.getSharedPreferences("Notification", MODE_PRIVATE).edit();
                                editor.putString("Schedule", schedule);
                                editor.apply();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else if (SCHUL_KND_SC_NM.equals("중학교")) {
                            try {
                                JSONArray jsonArrayInfo = response.getJSONArray("misTimetable");
                                JSONObject response2 = jsonArrayInfo.getJSONObject(1);
                                JSONArray jsonArrayrow = response2.getJSONArray("row");
                                for (int i = 0; i < jsonArrayrow.length(); i++) {
                                    JSONObject response3 = jsonArrayrow.getJSONObject(i);

                                    String s_period = response3.getString("ITRT_CNTNT");
                                    s_period = s_period.replaceFirst("-", "");

                                    period.add(s_period);
                                }

                                String schedule = null;

                                for (int i = 0; i < period.size(); i++) {

                                    if (i == 0) {
                                        schedule = period.get(i) + ", ";
                                    } else if (i == period.size() - 1) {
                                        schedule = schedule + period.get(i) + " ";
                                    } else  {
                                        schedule = schedule + period.get(i) + ", ";
                                    }
                                }

                                SharedPreferences.Editor editor = context.getSharedPreferences("Notification", MODE_PRIVATE).edit();
                                editor.putString("Schedule", schedule);
                                editor.apply();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else if (SCHUL_KND_SC_NM.equals("고등학교")) {
                            try {
                                JSONArray jsonArrayInfo = response.getJSONArray("hisTimetable");
                                JSONObject response2 = jsonArrayInfo.getJSONObject(1);
                                JSONArray jsonArrayrow = response2.getJSONArray("row");
                                for (int i = 0; i < jsonArrayrow.length(); i++) {
                                    JSONObject response3 = jsonArrayrow.getJSONObject(i);

                                    String s_period = response3.getString("ITRT_CNTNT");

                                    period.add(s_period);
                                }

                                String schedule = null;

                                for (int i = 0; i < period.size(); i++) {

                                    if (i == 0) {
                                        schedule = period.get(i) + ", ";
                                    } else if (i == period.size() - 1) {
                                        schedule = schedule + period.get(i) + " ";
                                    } else  {
                                        schedule = schedule + period.get(i) + ", ";
                                    }
                                }

                                SharedPreferences.Editor editor = context.getSharedPreferences("Notification", MODE_PRIVATE).edit();
                                editor.putString("Schedule", schedule);
                                editor.apply();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        JsonObjectRequest request_lunch = new JsonObjectRequest(Request.Method.GET, url_lunch, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArrayInfo = response.getJSONArray("mealServiceDietInfo");
                            JSONObject response2 = jsonArrayInfo.getJSONObject(1);
                            JSONArray jsonArrayrow = response2.getJSONArray("row");
                            for (int i = 0; i < jsonArrayrow.length(); i++) {
                                JSONObject response3 = jsonArrayrow.getJSONObject(i);

                                String lunch_1 = response3.getString("MMEAL_SC_NM");

                                String lunch_2 = response3.getString("DDISH_NM");
                                lunch_2 = lunch_2.replaceAll("<br/>", "  ");

                                String s_lunch = lunch_1 + " : " + lunch_2;

                                lunch.add(s_lunch);
                            }

                            String s_lunch = null;

                            for (int i = 0; i < lunch.size(); i++) {
                                Log.d("TEST", lunch.get(i));
                                if (i == 0) {
                                    s_lunch = lunch.get(i) + "\n";
                                } else if (i == 1) {
                                    s_lunch = s_lunch + lunch.get(i) + "\n";
                                } else {
                                    s_lunch = s_lunch + lunch.get(i);
                                }
                            }

                            SharedPreferences.Editor editor = context.getSharedPreferences("Notification", MODE_PRIVATE).edit();
                            editor.putString("Lunch", s_lunch);
                            editor.apply();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(request_schedule);
        requestQueueLunch.add(request_lunch);

        RequestQueue.RequestFinishedListener listener = new RequestQueue.RequestFinishedListener() {
            @Override
            public void onRequestFinished(Request request) {

                String Schedule = sharedPreferences.getString("Schedule", "");
                Log.d("TEST", Schedule);

                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                Intent notificationIntent = new Intent(context, MainActivity.class);

                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                final PendingIntent pendingI = PendingIntent.getActivity(context, 0,
                        notificationIntent, 0);

                NotificationCompat.Builder builder_Schedule = new NotificationCompat.Builder(context, "Schedule");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                    builder_Schedule.setSmallIcon(R.drawable.ic_icon);

                    String channelName ="시간표";
                    int importance = NotificationManager.IMPORTANCE_HIGH;

                    NotificationChannel channel = new NotificationChannel("Schedule", channelName, importance);

                    if (notificationManager != null) {
                        notificationManager.createNotificationChannel(channel);
                    }
                } else {
                    builder_Schedule.setSmallIcon(R.mipmap.icon);
                }

                builder_Schedule.setAutoCancel(true)
                        .setWhen(System.currentTimeMillis())
                        .setContentTitle("시간표")
                        .setContentText(Schedule)
                        .setContentIntent(pendingI);

                if (notificationManager != null) {

                    Calendar calendar = Calendar.getInstance();
                    int number = calendar.get(Calendar.DAY_OF_WEEK);

                    SharedPreferences sharedPreferences = context.getSharedPreferences("Notification", MODE_PRIVATE);
                    String schedule = sharedPreferences.getString("CSchedule", "");

                    if (schedule.equals("true")) {
                        if (!(number == 1 || number == 7)) {
                            notificationManager.notify(1111, builder_Schedule.build());
                        }
                    }

                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.add(Calendar.DATE, 1);
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

                    PackageManager pm = context.getPackageManager();
                    ComponentName receiver = new ComponentName(context, DeviceBootReceiver.class);
                    Intent alarmIntent = new Intent(context, Schedule_Lunch_Receiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                            calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

                    pm.setComponentEnabledSetting(receiver,
                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP);

//                    Calendar nextNotifyTime = Calendar.getInstance();
//
//                    nextNotifyTime.add(Calendar.DATE, 1);
//
//                    SharedPreferences.Editor editor = context.getSharedPreferences("Notification", MODE_PRIVATE).edit();
//                    editor.putLong("nextNotifyTime", nextNotifyTime.getTimeInMillis());
//                    editor.apply();
                }
            }
        };

        RequestQueue.RequestFinishedListener listener_lunch = new RequestQueue.RequestFinishedListener() {
            @Override
            public void onRequestFinished(Request request) {
                String Lunch = sharedPreferences.getString("Lunch", "");
                Log.d("TEST", Lunch);

                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                Intent notificationIntent = new Intent(context, MainActivity.class);

                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                final PendingIntent pendingI = PendingIntent.getActivity(context, 0,
                        notificationIntent, 0);

                NotificationCompat.Builder builder_Lunch = new NotificationCompat.Builder(context, "Lunch");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                    builder_Lunch.setSmallIcon(R.drawable.ic_icon);

                    int importance = NotificationManager.IMPORTANCE_HIGH;

                    String channelNameLunch ="급식식단";

                    NotificationChannel channelLunch = new NotificationChannel("Lunch", channelNameLunch, importance);

                    if (notificationManager != null) {
                        notificationManager.createNotificationChannel(channelLunch);
                    }
                } else {
                    builder_Lunch.setSmallIcon(R.mipmap.icon);
                }

                builder_Lunch.setAutoCancel(true)
                        .setWhen(System.currentTimeMillis())
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(Lunch))
                        .setContentTitle("급식식단")
                        .setContentText(Lunch)
                        .setContentIntent(pendingI);

                if (notificationManager != null) {

                    Calendar calendar = Calendar.getInstance();
                    int number = calendar.get(Calendar.DAY_OF_WEEK);

                    SharedPreferences sharedPreferences = context.getSharedPreferences("Notification", MODE_PRIVATE);
                    String lunch = sharedPreferences.getString("CLunch", "");

                    if (lunch.equals("true")) {
                        if (!(number == 1 || number == 7)) {
                            notificationManager.notify(2222, builder_Lunch.build());
                        }
                    }

//                    Calendar nextNotifyTime = Calendar.getInstance();
//
//                    nextNotifyTime.add(Calendar.DATE, 1);
//
//                    SharedPreferences.Editor editor = context.getSharedPreferences("Notification", MODE_PRIVATE).edit();
//                    editor.putLong("nextNotifyTime", nextNotifyTime.getTimeInMillis());
//                    editor.apply();
                }
            }
        };

        requestQueue.addRequestFinishedListener(listener);
        requestQueueLunch.addRequestFinishedListener(listener_lunch);
    }
}
