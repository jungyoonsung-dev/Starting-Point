package com.jungyoonsung.startingpoint.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

public class Fragment_Lunch extends Fragment {

    Context thisContext;

    private RequestQueue requestQueue;
    private List<String> MMEAL_SC_CODE = new ArrayList<>();
    private List<String> DDISH_NM = new ArrayList<>();
    private List<String> DDISH_NM2 = new ArrayList<>();

    CardView cardView;

    LinearLayout
            item_fragment_lunch_linearlayout_1,
            item_fragment_lunch_linearlayout_2;

    TextView
            item_fragment_lunch_linearlayout_1_main,
            item_fragment_lunch_linearlayout_1_textView;

    TextView
            lunch_1,
            lunch_2,
            lunch_3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_fragment_lunch, container, false);

        thisContext = container.getContext();

        cardView = (CardView) view.findViewById(R.id.cardView);

        item_fragment_lunch_linearlayout_1 = (LinearLayout) view.findViewById(R.id.item_fragment_lunch_linearlayout_1);
        item_fragment_lunch_linearlayout_2 = (LinearLayout) view.findViewById(R.id.item_fragment_lunch_linearlayout_2);

        item_fragment_lunch_linearlayout_1_main = (TextView) view.findViewById(R.id.item_fragment_lunch_linearlayout_1_main);
        item_fragment_lunch_linearlayout_1_textView = (TextView) view.findViewById(R.id.item_fragment_lunch_linearlayout_1_textView);

        lunch_1 = (TextView) view.findViewById(R.id.lunch_1);
        lunch_2 = (TextView) view.findViewById(R.id.lunch_2);
        lunch_3 = (TextView) view.findViewById(R.id.lunch_3);

        item_fragment_lunch_linearlayout_1.setVisibility(View.VISIBLE);
        item_fragment_lunch_linearlayout_2.setVisibility(View.GONE);
        item_fragment_lunch_linearlayout_1_main.setVisibility(View.VISIBLE);
        item_fragment_lunch_linearlayout_1_textView.setVisibility(View.GONE);

        SharedPreferences sharedPreferencesBackground = thisContext.getSharedPreferences("Background", MODE_PRIVATE);
        int position = sharedPreferencesBackground.getInt("Position", 0);
        if (position < 10) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(thisContext, android.R.color.white));

            item_fragment_lunch_linearlayout_1_main.setTextColor(ContextCompat.getColor(thisContext, android.R.color.black));
            item_fragment_lunch_linearlayout_1_textView.setTextColor(ContextCompat.getColor(thisContext, android.R.color.black));

            lunch_1.setTextColor(ContextCompat.getColor(thisContext, android.R.color.black));
            lunch_2.setTextColor(ContextCompat.getColor(thisContext, android.R.color.black));
            lunch_2.setTextColor(ContextCompat.getColor(thisContext, android.R.color.black));
        } else {
            cardView.setCardBackgroundColor(ContextCompat.getColor(thisContext, android.R.color.black));

            item_fragment_lunch_linearlayout_1_main.setTextColor(ContextCompat.getColor(thisContext, android.R.color.white));
            item_fragment_lunch_linearlayout_1_textView.setTextColor(ContextCompat.getColor(thisContext, android.R.color.white));

            lunch_1.setTextColor(ContextCompat.getColor(thisContext, android.R.color.white));
            lunch_2.setTextColor(ContextCompat.getColor(thisContext, android.R.color.white));
            lunch_2.setTextColor(ContextCompat.getColor(thisContext, android.R.color.white));
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

        String url = "https://open.neis.go.kr/hub/mealServiceDietInfo?KEY=4a5bfd1dc5414d198250eb542fd2d21c&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&MLSV_YMD=" + alldate;

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArrayInfo = response.getJSONArray("mealServiceDietInfo");
                            JSONObject response2 = jsonArrayInfo.getJSONObject(1);
                            JSONArray jsonArrayrow = response2.getJSONArray("row");
                            for (int i = 0; i < jsonArrayrow.length(); i++) {
                                JSONObject response3 = jsonArrayrow.getJSONObject(i);

                                MMEAL_SC_CODE.add(response3.getString("MMEAL_SC_CODE"));

                                String lunch = response3.getString("DDISH_NM");
                                lunch = lunch.replaceAll("<br/>", "\n");
                                DDISH_NM.add(lunch);
                            }

                            if (MMEAL_SC_CODE.size() == 1) {

                                item_fragment_lunch_linearlayout_1.setVisibility(View.VISIBLE);
                                item_fragment_lunch_linearlayout_2.setVisibility(View.GONE);

                                item_fragment_lunch_linearlayout_1_main.setVisibility(View.GONE);
                                item_fragment_lunch_linearlayout_1_textView.setVisibility(View.VISIBLE);

                                item_fragment_lunch_linearlayout_1_textView.setText(DDISH_NM.get(0));

                            } else if (MMEAL_SC_CODE.size() == 3) {

                                item_fragment_lunch_linearlayout_1.setVisibility(View.GONE);
                                item_fragment_lunch_linearlayout_2.setVisibility(View.VISIBLE);

                                lunch_1.setText(DDISH_NM.get(0));
                                lunch_2.setText(DDISH_NM.get(1));
                                lunch_3.setText(DDISH_NM.get(2));
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
//
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
//                String url = "https://open.neis.go.kr/hub/mealServiceDietInfo?KEY=e1e844f2228848cf8b6f521e7f60de86&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&MLSV_YMD=" + alldate;
//
//                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                        new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//
//                                try {
//                                    JSONArray jsonArrayInfo = response.getJSONArray("mealServiceDietInfo");
//                                    JSONObject response2 = jsonArrayInfo.getJSONObject(1);
//                                    JSONArray jsonArrayrow = response2.getJSONArray("row");
//                                    for (int i = 0; i < jsonArrayrow.length(); i++) {
//                                        JSONObject response3 = jsonArrayrow.getJSONObject(i);
//
//                                        MMEAL_SC_CODE.add(response3.getString("MMEAL_SC_CODE"));
//
//                                        String lunch = response3.getString("DDISH_NM");
//                                        lunch = lunch.replaceAll("<br/>", "\n");
//                                        DDISH_NM.add(lunch);
//                                    }
//
//                                    if (MMEAL_SC_CODE.size() == 1) {
//
//                                        item_fragment_lunch_linearlayout_1.setVisibility(View.VISIBLE);
//                                        item_fragment_lunch_linearlayout_2.setVisibility(View.GONE);
//
//                                        item_fragment_lunch_linearlayout_1_main.setVisibility(View.GONE);
//                                        item_fragment_lunch_linearlayout_1_textView.setVisibility(View.VISIBLE);
//
//                                        item_fragment_lunch_linearlayout_1_textView.setText(DDISH_NM.get(0));
//
//                                    } else if (MMEAL_SC_CODE.size() == 3) {
//
//                                        item_fragment_lunch_linearlayout_1.setVisibility(View.GONE);
//                                        item_fragment_lunch_linearlayout_2.setVisibility(View.VISIBLE);
//
//                                        lunch_1.setText(DDISH_NM.get(0));
//                                        lunch_2.setText(DDISH_NM.get(1));
//                                        lunch_3.setText(DDISH_NM.get(2));
//                                    }
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

//        item_fragment_lunch_linearlayout_1_textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(thisContext, DDISH_NM.get(0), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        lunch_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(thisContext, DDISH_NM.get(0), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        lunch_2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(thisContext, DDISH_NM.get(1), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        lunch_3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(thisContext, DDISH_NM.get(2), Toast.LENGTH_SHORT).show();
//            }
//        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(thisContext);
                LayoutInflater myInflater = LayoutInflater.from(thisContext);

                final View mView = myInflater.inflate(R.layout.dialog_lunch_calendar, null);

                final CalendarView dialog_lunch_calendar = (CalendarView) mView.findViewById(R.id.dialog_lunch_calendar);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                Window window = dialog.getWindow();
                window.setGravity(Gravity.CENTER);

                dialog.show();

                dialog_lunch_calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view,
                                                    final int i_year,
                                                    final int i_month,
                                                    final int i_dayOfMonth) {

                        DDISH_NM2.clear();

                        final AlertDialog.Builder mBuilder2 = new AlertDialog.Builder(thisContext);
                        LayoutInflater myInflater2 = LayoutInflater.from(thisContext);

                        final View mView2 = myInflater2.inflate(R.layout.dialog_lunch_text, null);

                        final TextView dialog_lunch_text_1 = (TextView) mView2.findViewById(R.id.dialog_lunch_text_1);
                        final TextView dialog_lunch_text_2 = (TextView) mView2.findViewById(R.id.dialog_lunch_text_2);

                        dialog_lunch_text_1.setVisibility(View.VISIBLE);
                        dialog_lunch_text_2.setVisibility(View.GONE);

                        final RequestQueue requestQueue2;
                        requestQueue2 = Volley.newRequestQueue(thisContext);

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

                        SharedPreferences sharedPreferencesUSER = thisContext.getSharedPreferences("USER", MODE_PRIVATE);
                        String ATPT_OFCDC_SC_CODE = sharedPreferencesUSER.getString("s_1_ATPT_OFCDC_SC_CODE", "");
                        String SD_SCHUL_CODE = sharedPreferencesUSER.getString("s_3_SD_SCHUL_CODE", "");

                        String url = "https://open.neis.go.kr/hub/mealServiceDietInfo?KEY=4a5bfd1dc5414d198250eb542fd2d21c&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&MLSV_YMD=" + alldate;

                        final JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        try {
                                            JSONArray jsonArrayInfo = response.getJSONArray("mealServiceDietInfo");
                                            JSONObject response2 = jsonArrayInfo.getJSONObject(1);
                                            JSONArray jsonArrayrow = response2.getJSONArray("row");
                                            for (int i = 0; i < jsonArrayrow.length(); i++) {
                                                JSONObject response3 = jsonArrayrow.getJSONObject(i);

                                                String lunch = response3.getString("DDISH_NM");
                                                lunch = lunch.replaceAll("<br/>", "\n");
                                                DDISH_NM2.add(lunch);
                                            }
                                            if (DDISH_NM2.size() == 1) {
                                                dialog_lunch_text_1.setVisibility(View.GONE);
                                                dialog_lunch_text_2.setVisibility(View.VISIBLE);

                                                dialog_lunch_text_2.setText(DDISH_NM2.get(0));
                                            } else if (DDISH_NM2.size() == 3) {
                                                dialog_lunch_text_1.setVisibility(View.GONE);
                                                dialog_lunch_text_2.setVisibility(View.VISIBLE);

                                                dialog_lunch_text_2.setText(
                                                        "조식 :\n" + DDISH_NM2.get(0) + "\n\n" +
                                                                "중식 :\n" + DDISH_NM2.get(1) + "\n\n" +
                                                                "석식 :\n" + DDISH_NM2.get(2) + "\n\n");
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

}
