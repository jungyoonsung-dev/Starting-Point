package com.jungyoonsung.startingpoint.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class Fragment_Schedule extends Fragment {

    FirebaseAuth auth;
    FirebaseDatabase database;

    Context thisContext;

    private RequestQueue requestQueue;
    private List<String> period = new ArrayList<>();

    LinearLayout
            period_1_linearlayout,
            period_2_linearlayout,
            period_3_linearlayout,
            period_4_linearlayout,
            period_5_linearlayout,
            period_6_linearlayout,
            period_7_linearlayout;

    TextView
            period_1,
            period_2,
            period_3,
            period_4,
            period_5,
            period_6,
            period_7;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_fragment_schedule, container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        thisContext = container.getContext();

        period_1_linearlayout = (LinearLayout) view.findViewById(R.id.period_1_linearlayout);
        period_2_linearlayout = (LinearLayout) view.findViewById(R.id.period_2_linearlayout);
        period_3_linearlayout = (LinearLayout) view.findViewById(R.id.period_3_linearlayout);
        period_4_linearlayout = (LinearLayout) view.findViewById(R.id.period_4_linearlayout);
        period_5_linearlayout = (LinearLayout) view.findViewById(R.id.period_5_linearlayout);
        period_6_linearlayout = (LinearLayout) view.findViewById(R.id.period_6_linearlayout);
        period_7_linearlayout = (LinearLayout) view.findViewById(R.id.period_7_linearlayout);

        period_1 = (TextView) view.findViewById(R.id.period_1);
        period_2 = (TextView) view.findViewById(R.id.period_2);
        period_3 = (TextView) view.findViewById(R.id.period_3);
        period_4 = (TextView) view.findViewById(R.id.period_4);
        period_5 = (TextView) view.findViewById(R.id.period_5);
        period_6 = (TextView) view.findViewById(R.id.period_6);
        period_7 = (TextView) view.findViewById(R.id.period_7);

        requestQueue = Volley.newRequestQueue(thisContext);

        database.getReference().child("Profile").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Date date = Calendar.getInstance().getTime();

                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
                SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
//                SimpleDateFormat day_2_Format = new SimpleDateFormat("EE", Locale.getDefault());

                String year = yearFormat.format(date);
                String month = monthFormat.format(date);
                String day = dayFormat.format(date);
//                String day_2 = day_2_Format.format(date);

                String alldate = year + month + day;

                String ATPT_OFCDC_SC_CODE = String.valueOf(dataSnapshot.child("s_1_ATPT_OFCDC_SC_CODE").getValue());
                String SD_SCHUL_CODE = String.valueOf(dataSnapshot.child("s_3_SD_SCHUL_CODE").getValue());
                String s_grade = String.valueOf(dataSnapshot.child("s_6_grade").getValue());
                String s_class = String.valueOf(dataSnapshot.child("s_7_class").getValue());

                final String SCHUL_KND_SC_NM = String.valueOf(dataSnapshot.child("s_5_SCHUL_KND_SC_NM").getValue());
                String url = null;

                if (SCHUL_KND_SC_NM.equals("초등학교")) {
                    url = "https://open.neis.go.kr/hub/elsTimetable?KEY=3c0d4c588de2476a960e8fd2988ce38c&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&ALL_TI_YMD=" + alldate + "&GRADE=" + s_grade + "&CLASS_NM=" + s_class;
                } else if (SCHUL_KND_SC_NM.equals("중학교")) {
                    url = "https://open.neis.go.kr/hub/misTimetable?KEY=11b3f567023f438296130b6335d20b4c&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&ALL_TI_YMD=" + alldate + "&GRADE=" + s_grade + "&CLASS_NM=" + s_class;
                } else if (SCHUL_KND_SC_NM.equals("고등학교")) {
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

                                        if (period.size() == 1) {

                                            period_1.setText(period.get(0));

                                            period_2_linearlayout.setVisibility(View.GONE);
                                            period_3_linearlayout.setVisibility(View.GONE);
                                            period_4_linearlayout.setVisibility(View.GONE);
                                            period_5_linearlayout.setVisibility(View.GONE);
                                            period_6_linearlayout.setVisibility(View.GONE);
                                            period_7_linearlayout.setVisibility(View.GONE);

                                        } else if (period.size() == 2) {

                                            period_1.setText(period.get(0));
                                            period_2.setText(period.get(1));

                                            period_3_linearlayout.setVisibility(View.GONE);
                                            period_4_linearlayout.setVisibility(View.GONE);
                                            period_5_linearlayout.setVisibility(View.GONE);
                                            period_6_linearlayout.setVisibility(View.GONE);
                                            period_7_linearlayout.setVisibility(View.GONE);

                                        } else if (period.size() == 3) {

                                            period_1.setText(period.get(0));
                                            period_2.setText(period.get(1));
                                            period_3.setText(period.get(2));

                                            period_4_linearlayout.setVisibility(View.GONE);
                                            period_5_linearlayout.setVisibility(View.GONE);
                                            period_6_linearlayout.setVisibility(View.GONE);
                                            period_7_linearlayout.setVisibility(View.GONE);

                                        } else if (period.size() == 4) {

                                            period_1.setText(period.get(0));
                                            period_2.setText(period.get(1));
                                            period_3.setText(period.get(2));
                                            period_4.setText(period.get(3));

                                            period_5_linearlayout.setVisibility(View.GONE);
                                            period_6_linearlayout.setVisibility(View.GONE);
                                            period_7_linearlayout.setVisibility(View.GONE);

                                        } else if (period.size() == 5) {

                                            period_1.setText(period.get(0));
                                            period_2.setText(period.get(1));
                                            period_3.setText(period.get(2));
                                            period_4.setText(period.get(3));
                                            period_5.setText(period.get(4));

                                            period_6_linearlayout.setVisibility(View.GONE);
                                            period_7_linearlayout.setVisibility(View.GONE);

                                        } else if (period.size() == 6) {

                                            period_1.setText(period.get(0));
                                            period_2.setText(period.get(1));
                                            period_3.setText(period.get(2));
                                            period_4.setText(period.get(3));
                                            period_5.setText(period.get(4));
                                            period_6.setText(period.get(5));

                                            period_7_linearlayout.setVisibility(View.GONE);

                                        } else if (period.size() == 7) {

                                            period_1.setText(period.get(0));
                                            period_2.setText(period.get(1));
                                            period_3.setText(period.get(2));
                                            period_4.setText(period.get(3));
                                            period_5.setText(period.get(4));
                                            period_6.setText(period.get(5));
                                            period_7.setText(period.get(6));

                                        } else {
                                            period_1_linearlayout.setVisibility(View.GONE);
                                            period_2_linearlayout.setVisibility(View.GONE);
                                            period_3_linearlayout.setVisibility(View.GONE);
                                            period_4_linearlayout.setVisibility(View.GONE);
                                            period_5_linearlayout.setVisibility(View.GONE);
                                            period_6_linearlayout.setVisibility(View.GONE);
                                            period_7_linearlayout.setVisibility(View.GONE);
                                        }

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

                                        if (period.size() == 1) {

                                            period_1.setText(period.get(0));

                                            period_2_linearlayout.setVisibility(View.GONE);
                                            period_3_linearlayout.setVisibility(View.GONE);
                                            period_4_linearlayout.setVisibility(View.GONE);
                                            period_5_linearlayout.setVisibility(View.GONE);
                                            period_6_linearlayout.setVisibility(View.GONE);
                                            period_7_linearlayout.setVisibility(View.GONE);

                                        } else if (period.size() == 2) {

                                            period_1.setText(period.get(0));
                                            period_2.setText(period.get(1));

                                            period_3_linearlayout.setVisibility(View.GONE);
                                            period_4_linearlayout.setVisibility(View.GONE);
                                            period_5_linearlayout.setVisibility(View.GONE);
                                            period_6_linearlayout.setVisibility(View.GONE);
                                            period_7_linearlayout.setVisibility(View.GONE);

                                        } else if (period.size() == 3) {

                                            period_1.setText(period.get(0));
                                            period_2.setText(period.get(1));
                                            period_3.setText(period.get(2));

                                            period_4_linearlayout.setVisibility(View.GONE);
                                            period_5_linearlayout.setVisibility(View.GONE);
                                            period_6_linearlayout.setVisibility(View.GONE);
                                            period_7_linearlayout.setVisibility(View.GONE);

                                        } else if (period.size() == 4) {

                                            period_1.setText(period.get(0));
                                            period_2.setText(period.get(1));
                                            period_3.setText(period.get(2));
                                            period_4.setText(period.get(3));

                                            period_5_linearlayout.setVisibility(View.GONE);
                                            period_6_linearlayout.setVisibility(View.GONE);
                                            period_7_linearlayout.setVisibility(View.GONE);

                                        } else if (period.size() == 5) {

                                            period_1.setText(period.get(0));
                                            period_2.setText(period.get(1));
                                            period_3.setText(period.get(2));
                                            period_4.setText(period.get(3));
                                            period_5.setText(period.get(4));

                                            period_6_linearlayout.setVisibility(View.GONE);
                                            period_7_linearlayout.setVisibility(View.GONE);

                                        } else if (period.size() == 6) {

                                            period_1.setText(period.get(0));
                                            period_2.setText(period.get(1));
                                            period_3.setText(period.get(2));
                                            period_4.setText(period.get(3));
                                            period_5.setText(period.get(4));
                                            period_6.setText(period.get(5));

                                            period_7_linearlayout.setVisibility(View.GONE);

                                        } else if (period.size() == 7) {

                                            period_1.setText(period.get(0));
                                            period_2.setText(period.get(1));
                                            period_3.setText(period.get(2));
                                            period_4.setText(period.get(3));
                                            period_5.setText(period.get(4));
                                            period_6.setText(period.get(5));
                                            period_7.setText(period.get(6));

                                        } else {
                                            period_1_linearlayout.setVisibility(View.GONE);
                                            period_2_linearlayout.setVisibility(View.GONE);
                                            period_3_linearlayout.setVisibility(View.GONE);
                                            period_4_linearlayout.setVisibility(View.GONE);
                                            period_5_linearlayout.setVisibility(View.GONE);
                                            period_6_linearlayout.setVisibility(View.GONE);
                                            period_7_linearlayout.setVisibility(View.GONE);
                                        }

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

                                        if (period.size() == 1) {

                                            period_1.setText(period.get(0));

                                            period_2_linearlayout.setVisibility(View.GONE);
                                            period_3_linearlayout.setVisibility(View.GONE);
                                            period_4_linearlayout.setVisibility(View.GONE);
                                            period_5_linearlayout.setVisibility(View.GONE);
                                            period_6_linearlayout.setVisibility(View.GONE);
                                            period_7_linearlayout.setVisibility(View.GONE);

                                        } else if (period.size() == 2) {

                                            period_1.setText(period.get(0));
                                            period_2.setText(period.get(1));

                                            period_3_linearlayout.setVisibility(View.GONE);
                                            period_4_linearlayout.setVisibility(View.GONE);
                                            period_5_linearlayout.setVisibility(View.GONE);
                                            period_6_linearlayout.setVisibility(View.GONE);
                                            period_7_linearlayout.setVisibility(View.GONE);

                                        } else if (period.size() == 3) {

                                            period_1.setText(period.get(0));
                                            period_2.setText(period.get(1));
                                            period_3.setText(period.get(2));

                                            period_4_linearlayout.setVisibility(View.GONE);
                                            period_5_linearlayout.setVisibility(View.GONE);
                                            period_6_linearlayout.setVisibility(View.GONE);
                                            period_7_linearlayout.setVisibility(View.GONE);

                                        } else if (period.size() == 4) {

                                            period_1.setText(period.get(0));
                                            period_2.setText(period.get(1));
                                            period_3.setText(period.get(2));
                                            period_4.setText(period.get(3));

                                            period_5_linearlayout.setVisibility(View.GONE);
                                            period_6_linearlayout.setVisibility(View.GONE);
                                            period_7_linearlayout.setVisibility(View.GONE);

                                        } else if (period.size() == 5) {

                                            period_1.setText(period.get(0));
                                            period_2.setText(period.get(1));
                                            period_3.setText(period.get(2));
                                            period_4.setText(period.get(3));
                                            period_5.setText(period.get(4));

                                            period_6_linearlayout.setVisibility(View.GONE);
                                            period_7_linearlayout.setVisibility(View.GONE);

                                        } else if (period.size() == 6) {

                                            period_1.setText(period.get(0));
                                            period_2.setText(period.get(1));
                                            period_3.setText(period.get(2));
                                            period_4.setText(period.get(3));
                                            period_5.setText(period.get(4));
                                            period_6.setText(period.get(5));

                                            period_7_linearlayout.setVisibility(View.GONE);

                                        } else if (period.size() == 7) {

                                            period_1.setText(period.get(0));
                                            period_2.setText(period.get(1));
                                            period_3.setText(period.get(2));
                                            period_4.setText(period.get(3));
                                            period_5.setText(period.get(4));
                                            period_6.setText(period.get(5));
                                            period_7.setText(period.get(6));

                                        } else {
                                            period_1_linearlayout.setVisibility(View.GONE);
                                            period_2_linearlayout.setVisibility(View.GONE);
                                            period_3_linearlayout.setVisibility(View.GONE);
                                            period_4_linearlayout.setVisibility(View.GONE);
                                            period_5_linearlayout.setVisibility(View.GONE);
                                            period_6_linearlayout.setVisibility(View.GONE);
                                            period_7_linearlayout.setVisibility(View.GONE);
                                        }

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

}
