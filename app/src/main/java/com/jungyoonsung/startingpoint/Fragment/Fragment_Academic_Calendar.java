package com.jungyoonsung.startingpoint.Fragment;

import android.content.Context;
import android.os.Bundle;
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

public class Fragment_Academic_Calendar extends Fragment {

    FirebaseAuth auth;
    FirebaseDatabase database;

    Context thisContext;

    private RequestQueue requestQueue;
    private List<String> academic_calendar = new ArrayList<>();

    TextView
            item_fragment_academic_calendar_textView,
            item_fragment_academic_calendar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_fragment_academic_calendar, container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        thisContext = container.getContext();


        item_fragment_academic_calendar_textView = (TextView) view.findViewById(R.id.item_fragment_academic_calendar_textView);
        item_fragment_academic_calendar = (TextView) view.findViewById(R.id.item_fragment_academic_calendar);

        item_fragment_academic_calendar_textView.setVisibility(View.VISIBLE);
        item_fragment_academic_calendar.setVisibility(View.GONE);

        requestQueue = Volley.newRequestQueue(thisContext);

        database.getReference().child("Profile").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Date date = Calendar.getInstance().getTime();

                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
                SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());

                String year = yearFormat.format(date);
                String month = monthFormat.format(date);
                String day = dayFormat.format(date);

                String alldate = year + month + day;

                String ATPT_OFCDC_SC_CODE = String.valueOf(dataSnapshot.child("s_1_ATPT_OFCDC_SC_CODE").getValue());
                String SD_SCHUL_CODE = String.valueOf(dataSnapshot.child("s_3_SD_SCHUL_CODE").getValue());

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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void c_1() {
        item_fragment_academic_calendar_textView.setVisibility(View.GONE);
        item_fragment_academic_calendar.setVisibility(View.VISIBLE);
    }

}
