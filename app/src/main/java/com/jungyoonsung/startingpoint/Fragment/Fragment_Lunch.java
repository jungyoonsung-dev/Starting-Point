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

public class Fragment_Lunch extends Fragment {

    FirebaseAuth auth;
    FirebaseDatabase database;

    Context thisContext;

    private RequestQueue requestQueue;
    private List<String> MMEAL_SC_CODE = new ArrayList<>();
    private List<String> DDISH_NM = new ArrayList<>();

    LinearLayout
            lunch_1_linearlayout,
            lunch_2_linearlayout,
            lunch_3_linearlayout;

    TextView
            lunch_1,
            lunch_2,
            lunch_3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_fragment_lunch, container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        thisContext = container.getContext();

        lunch_1_linearlayout = (LinearLayout) view.findViewById(R.id.lunch_1_linearlayout);
        lunch_2_linearlayout = (LinearLayout) view.findViewById(R.id.lunch_2_linearlayout);
        lunch_3_linearlayout = (LinearLayout) view.findViewById(R.id.lunch_3_linearlayout);

        lunch_1 = (TextView) view.findViewById(R.id.lunch_1);
        lunch_2 = (TextView) view.findViewById(R.id.lunch_2);
        lunch_3 = (TextView) view.findViewById(R.id.lunch_3);


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

                String url = "https://open.neis.go.kr/hub/mealServiceDietInfo?KEY=bb8beb7f15d64325b567e0954c5cae58&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&MLSV_YMD=" + alldate;

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
                                        lunch_2.setText(DDISH_NM.get(0));

                                        lunch_1_linearlayout.setVisibility(View.GONE);
                                        lunch_3_linearlayout.setVisibility(View.GONE);
                                    } else if (MMEAL_SC_CODE.size() == 3) {
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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

}
