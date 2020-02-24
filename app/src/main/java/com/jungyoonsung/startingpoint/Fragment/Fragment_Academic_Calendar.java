package com.jungyoonsung.startingpoint.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

public class Fragment_Academic_Calendar extends Fragment {

    FirebaseAuth auth;
    FirebaseDatabase database;

    Context thisContext;

    private RequestQueue requestQueue;
    private List<String> academic_calendar = new ArrayList<>();
    private List<String> academic_calendar2 = new ArrayList<>();

    CardView cardView;

    TextView
            item_fragment_academic_calendar_textView,
            item_fragment_academic_calendar;

    private AdView adView1, adView2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_fragment_academic_calendar, container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        thisContext = container.getContext();

        cardView = (CardView) view.findViewById(R.id.cardView);

        item_fragment_academic_calendar_textView = (TextView) view.findViewById(R.id.item_fragment_academic_calendar_textView);
        item_fragment_academic_calendar = (TextView) view.findViewById(R.id.item_fragment_academic_calendar);

        item_fragment_academic_calendar_textView.setVisibility(View.VISIBLE);
        item_fragment_academic_calendar.setVisibility(View.GONE);

        SharedPreferences sharedPreferencesBackground = thisContext.getSharedPreferences("Background", MODE_PRIVATE);
        int position = sharedPreferencesBackground.getInt("Position", 0);
        if (position < 10) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(thisContext, android.R.color.white));

            item_fragment_academic_calendar_textView.setTextColor(ContextCompat.getColor(thisContext, android.R.color.black));
            item_fragment_academic_calendar.setTextColor(ContextCompat.getColor(thisContext, android.R.color.black));
        } else {
            cardView.setCardBackgroundColor(ContextCompat.getColor(thisContext, android.R.color.black));

            item_fragment_academic_calendar_textView.setTextColor(ContextCompat.getColor(thisContext, android.R.color.white));
            item_fragment_academic_calendar.setTextColor(ContextCompat.getColor(thisContext, android.R.color.white));
        }

        requestQueue = Volley.newRequestQueue(thisContext);

        Date date = Calendar.getInstance().getTime();

        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());

        String year = yearFormat.format(date);
        String month = monthFormat.format(date);
        String day = dayFormat.format(date);

        String alldate = year + month + day;

        SharedPreferences sharedPreferencesUSER = thisContext.getSharedPreferences("USER", MODE_PRIVATE);

        String ATPT_OFCDC_SC_CODE = sharedPreferencesUSER.getString("s_1_ATPT_OFCDC_SC_CODE", "");
        String SD_SCHUL_CODE = sharedPreferencesUSER.getString("s_3_SD_SCHUL_CODE", "");

        String url = "https://open.neis.go.kr/hub/SchoolSchedule?KEY=f7d04890d151476bb2a4e2281962853b&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&AA_YMD=" + alldate;

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArrayInfo = response.getJSONArray("SchoolSchedule");
                            JSONObject response2 = jsonArrayInfo.getJSONObject(1);
                            JSONArray jsonArrayrow = response2.getJSONArray("row");
                            for (int i = 0; i < jsonArrayrow.length(); i++) {
                                JSONObject response3 = jsonArrayrow.getJSONObject(i);

                                String s_period = response3.getString("EVENT_NM");

                                academic_calendar.add(s_period);
                            }

                            if (academic_calendar.size() == 1) {

                                c_1();
                                item_fragment_academic_calendar.setText(academic_calendar.get(0));

                            } else if (academic_calendar.size() == 2) {

                                c_1();
                                item_fragment_academic_calendar.setText(academic_calendar.get(0) + ", " + academic_calendar.get(1));

                            } else if (academic_calendar.size() == 3) {

                                c_1();
                                item_fragment_academic_calendar.setText(academic_calendar.get(0) + ", " + academic_calendar.get(1) + ", " + academic_calendar.get(2));

                            } else if (academic_calendar.size() == 4) {

                                c_1();
                                item_fragment_academic_calendar.setText(academic_calendar.get(0) + ", " + academic_calendar.get(1) + ", " + academic_calendar.get(2) + ", " + academic_calendar.get(3));

                            } else if (academic_calendar.size() == 5) {

                                c_1();
                                item_fragment_academic_calendar.setText(academic_calendar.get(0) + ", " + academic_calendar.get(1) + ", " + academic_calendar.get(2) + ", " + academic_calendar.get(3) + ", " + academic_calendar.get(4));

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(request);

//        database.getReference().child("Profile").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                Date date = Calendar.getInstance().getTime();
//
//                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
//                SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
//                SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
//
//                String year = yearFormat.format(date);
//                String month = monthFormat.format(date);
//                String day = dayFormat.format(date);
//
//                String alldate = year + month + day;
//
//                String ATPT_OFCDC_SC_CODE = String.valueOf(dataSnapshot.child("s_1_ATPT_OFCDC_SC_CODE").getValue());
//                String SD_SCHUL_CODE = String.valueOf(dataSnapshot.child("s_3_SD_SCHUL_CODE").getValue());
//
//                String url = "https://open.neis.go.kr/hub/SchoolSchedule?KEY=f7d04890d151476bb2a4e2281962853b&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&AA_YMD=" + alldate;
//
//                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                        new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//
//                                try {
//                                    JSONArray jsonArrayInfo = response.getJSONArray("SchoolSchedule");
//                                    JSONObject response2 = jsonArrayInfo.getJSONObject(1);
//                                    JSONArray jsonArrayrow = response2.getJSONArray("row");
//                                    for (int i = 0; i < jsonArrayrow.length(); i++) {
//                                        JSONObject response3 = jsonArrayrow.getJSONObject(i);
//
//                                        String s_period = response3.getString("EVENT_NM");
//
//                                        academic_calendar.add(s_period);
//                                    }
//
//                                    if (academic_calendar.size() == 1) {
//
//                                        c_1();
//                                        item_fragment_academic_calendar.setText(academic_calendar.get(0));
//
//                                    } else if (academic_calendar.size() == 2) {
//
//                                        c_1();
//                                        item_fragment_academic_calendar.setText(academic_calendar.get(0) + ", " + academic_calendar.get(1));
//
//                                    } else if (academic_calendar.size() == 3) {
//
//                                        c_1();
//                                        item_fragment_academic_calendar.setText(academic_calendar.get(0) + ", " + academic_calendar.get(1) + ", " + academic_calendar.get(2));
//
//                                    } else if (academic_calendar.size() == 4) {
//
//                                        c_1();
//                                        item_fragment_academic_calendar.setText(academic_calendar.get(0) + ", " + academic_calendar.get(1) + ", " + academic_calendar.get(2) + ", " + academic_calendar.get(3));
//
//                                    } else if (academic_calendar.size() == 5) {
//
//                                        c_1();
//                                        item_fragment_academic_calendar.setText(academic_calendar.get(0) + ", " + academic_calendar.get(1) + ", " + academic_calendar.get(2) + ", " + academic_calendar.get(3) + ", " + academic_calendar.get(4));
//
//                                    }
//
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                    }
//                });
//
//                requestQueue.add(request);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(thisContext);
                LayoutInflater myInflater = LayoutInflater.from(thisContext);

                final View mView = myInflater.inflate(R.layout.dialog_academic_calendar_calendar, null);

                final CalendarView dialog_academic_calendar_calendar = (CalendarView) mView.findViewById(R.id.dialog_academic_calendar_calendar);

                MobileAds.initialize(thisContext,
                        getString(R.string.app_admob_id));

                adView1 = (AdView) mView.findViewById(R.id.adView1);
                AdRequest adRequest = new AdRequest.Builder().build();
                adView1.loadAd(adRequest);

                adView1.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        // Code to be executed when an ad finishes loading.
                        adView1.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Code to be executed when an ad request fails.
                        adView1.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAdOpened() {
                        // Code to be executed when an ad opens an overlay that
                        // covers the screen.
                    }

                    @Override
                    public void onAdLeftApplication() {
                        // Code to be executed when the user has left the app.
                    }

                    @Override
                    public void onAdClosed() {
                        // Code to be executed when when the user is about to return
                        // to the app after tapping on an ad.
                    }
                });

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                Window window = dialog.getWindow();
                window.setGravity(Gravity.CENTER);

                dialog.show();

                dialog_academic_calendar_calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view,
                                                    final int i_year,
                                                    final int i_month,
                                                    final int i_dayOfMonth) {

                        academic_calendar2.clear();

                        final AlertDialog.Builder mBuilder2 = new AlertDialog.Builder(thisContext);
                        LayoutInflater myInflater2 = LayoutInflater.from(thisContext);

                        final View mView2 = myInflater2.inflate(R.layout.dialog_academic_calendar_text, null);

                        final TextView dialog_lunch_text_1 = (TextView) mView2.findViewById(R.id.dialog_academic_calendar_text_1);
                        final TextView dialog_lunch_text_2 = (TextView) mView2.findViewById(R.id.dialog_academic_calendar_text_2);

                        MobileAds.initialize(thisContext,
                                getString(R.string.app_admob_id));

                        adView2 = (AdView) mView2.findViewById(R.id.adView2);
                        AdRequest adRequest = new AdRequest.Builder().build();
                        adView2.loadAd(adRequest);

                        adView2.setAdListener(new AdListener() {
                            @Override
                            public void onAdLoaded() {
                                // Code to be executed when an ad finishes loading.
                                adView2.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAdFailedToLoad(int errorCode) {
                                // Code to be executed when an ad request fails.
                                adView2.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAdOpened() {
                                // Code to be executed when an ad opens an overlay that
                                // covers the screen.
                            }

                            @Override
                            public void onAdLeftApplication() {
                                // Code to be executed when the user has left the app.
                            }

                            @Override
                            public void onAdClosed() {
                                // Code to be executed when when the user is about to return
                                // to the app after tapping on an ad.
                            }
                        });

                        dialog_lunch_text_1.setVisibility(View.VISIBLE);
                        dialog_lunch_text_2.setVisibility(View.GONE);

                        final RequestQueue requestQueue2;
                        requestQueue2 = Volley.newRequestQueue(thisContext);

                        database.getReference().child("Profile").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                String year = String.valueOf(i_year);

                                int i_month_2 = i_month + 1;

                                String month = String.valueOf(i_month_2);
                                if (month.length() == 1) {
                                    month = "0" + month;
                                }

                                String day = String.valueOf(i_dayOfMonth);
                                if (day.length() == 1) {
                                    day = "0" + day;
                                }

                                String alldate = year + month + day;

                                String ATPT_OFCDC_SC_CODE = String.valueOf(dataSnapshot.child("s_1_ATPT_OFCDC_SC_CODE").getValue());
                                String SD_SCHUL_CODE = String.valueOf(dataSnapshot.child("s_3_SD_SCHUL_CODE").getValue());

                                String url = "https://open.neis.go.kr/hub/SchoolSchedule?KEY=f7d04890d151476bb2a4e2281962853b&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&AA_YMD=" + alldate;

                                final JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url, null,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {

                                                try {
                                                    JSONArray jsonArrayInfo = response.getJSONArray("SchoolSchedule");
                                                    JSONObject response2 = jsonArrayInfo.getJSONObject(1);
                                                    JSONArray jsonArrayrow = response2.getJSONArray("row");
                                                    for (int i = 0; i < jsonArrayrow.length(); i++) {
                                                        JSONObject response3 = jsonArrayrow.getJSONObject(i);

                                                        String s_period = response3.getString("EVENT_NM");

                                                        academic_calendar2.add(s_period);
                                                    }

                                                    dialog_lunch_text_1.setVisibility(View.VISIBLE);
                                                    dialog_lunch_text_2.setVisibility(View.GONE);

                                                    String result = null;

                                                    for (int i = 0; i < academic_calendar2.size(); i++) {
                                                        int number = i + 1;
                                                        if (TextUtils.isEmpty(result)) {
                                                            result = number + ". " +  academic_calendar2.get(i);
                                                        } else {
                                                            result = result + "\n" + number + ". " + academic_calendar2.get(i);
                                                        }
                                                    }

                                                    if (TextUtils.isEmpty(result)) {
                                                        dialog_lunch_text_1.setVisibility(View.VISIBLE);
                                                        dialog_lunch_text_2.setVisibility(View.GONE);
                                                    } else {
                                                        dialog_lunch_text_1.setVisibility(View.GONE);
                                                        dialog_lunch_text_2.setVisibility(View.VISIBLE);

                                                        dialog_lunch_text_2.setText(result);
                                                    }

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }
                                });
                                requestQueue2.add(request2);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        mBuilder2.setView(mView2);
                        final AlertDialog dialog2 = mBuilder2.create();
                        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog2.show();
                    }
                });
            }
        });

        return view;
    }

    private void c_1() {
        item_fragment_academic_calendar_textView.setVisibility(View.GONE);
        item_fragment_academic_calendar.setVisibility(View.VISIBLE);
    }

}
