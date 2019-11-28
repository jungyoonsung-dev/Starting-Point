package com.jungyoonsung.startingpoint.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class AlarmReceiver extends BroadcastReceiver {

    private RequestQueue requestQueue;
    private List<String> period = new ArrayList<>();

    @Override
    public void onReceive(final Context context, Intent intent) {


        requestQueue = Volley.newRequestQueue(context);

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

        String url = null;

        if (SCHUL_KND_SC_NM.equals("초등학교")) {
            Log.d("TEST", "1");
            url = "https://open.neis.go.kr/hub/elsTimetable?KEY=3c0d4c588de2476a960e8fd2988ce38c&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&ALL_TI_YMD=" + alldate + "&GRADE=" + s_grade + "&CLASS_NM=" + s_class;
        } else if (SCHUL_KND_SC_NM.equals("중학교")) {
            Log.d("TEST", "2");
            url = "https://open.neis.go.kr/hub/misTimetable?KEY=11b3f567023f438296130b6335d20b4c&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&ALL_TI_YMD=" + alldate + "&GRADE=" + s_grade + "&CLASS_NM=" + s_class;
        } else if (SCHUL_KND_SC_NM.equals("고등학교")) {
            Log.d("TEST", "3");
            url = "https://open.neis.go.kr/hub/hisTimetable?KEY=1f0018f4daf247c2b1d3d8b2cf15c257&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&ALL_TI_YMD=" + alldate + "&GRADE=" + s_grade + "&CLRM_NM=" + s_class;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
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
                                    s_period = s_period.substring(1);

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

        requestQueue.add(request);

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

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                    builder.setSmallIcon(R.drawable.ic_launcher_foreground);

                    String channelName ="시간표";
                    int importance = NotificationManager.IMPORTANCE_HIGH;

                    NotificationChannel channel = new NotificationChannel("default", channelName, importance);

                    if (notificationManager != null) {
                        notificationManager.createNotificationChannel(channel);
                    }
                } else builder.setSmallIcon(R.mipmap.ic_launcher);

                builder.setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setWhen(System.currentTimeMillis())
                        .setContentTitle("시간표")
                        .setContentText(Schedule)
                        .setContentIntent(pendingI);

                if (notificationManager != null) {

                    notificationManager.notify(1234, builder.build());

                    Calendar nextNotifyTime = Calendar.getInstance();

                    nextNotifyTime.add(Calendar.DATE, 1);

                    SharedPreferences.Editor editor = context.getSharedPreferences("Notification", MODE_PRIVATE).edit();
                    editor.putLong("nextNotifyTime", nextNotifyTime.getTimeInMillis());
                    editor.apply();
                }
            }
        };

        requestQueue.addRequestFinishedListener(listener);
    }
}
