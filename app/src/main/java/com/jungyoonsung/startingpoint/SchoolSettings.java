package com.jungyoonsung.startingpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SchoolSettings extends AppCompatActivity {

    TextInputEditText school_name;
    FloatingActionButton check;

    private RequestQueue requestQueue;

    List<String> ATPT_OFCDC_SC_NM = new ArrayList<>();
    List<String> ATPT_OFCDC_SC_CODE = new ArrayList<>();
    List<String> SCHUL_NM = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_settings);

        requestQueue = Volley.newRequestQueue(this);

        school_name = (TextInputEditText) findViewById(R.id.school_name);
        check = (FloatingActionButton) findViewById(R.id.check);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://open.neis.go.kr/hub/schoolInfo?KEY=5782ec7c631b48fa8e93c6912fd9f8a7&Type=json&pIndex=1&pSize=100&SCHUL_NM=" + school_name.getText().toString();
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONArray jsonArrayInfo = response.getJSONArray("schoolInfo");
                                    JSONObject response2 = jsonArrayInfo.getJSONObject(1);
                                    JSONArray jsonArrayrow = response2.getJSONArray("row");
                                    for (int i = 0; i < jsonArrayrow.length(); i++) {
                                        JSONObject response3 = jsonArrayrow.getJSONObject(i);

                                        ATPT_OFCDC_SC_CODE.add(response3.getString("ATPT_OFCDC_SC_CODE"));
                                        ATPT_OFCDC_SC_NM.add(response3.getString("ATPT_OFCDC_SC_NM"));
                                        SCHUL_NM.add(response3.getString("SCHUL_NM"));

                                    }
                                    Log.d("TEST", String.valueOf(ATPT_OFCDC_SC_CODE));



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
        });
    }
}
