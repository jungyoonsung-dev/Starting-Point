package com.jungyoonsung.startingpoint.Tab;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.jungyoonsung.startingpoint.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TabSchool extends Fragment {

    Context thisContext;

    FirebaseAuth auth;
    FirebaseDatabase database;

    private RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabschool, container, false);
        thisContext = container.getContext();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        requestQueue = Volley.newRequestQueue(thisContext);

        String url = "http://www.schoolinfo.go.kr/openApi.do?apiKey=5e2d50c3906a42b6aa25f0deaba5a268&apiType=08&pbanYr=2019&schulKndCode=04";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(thisContext, "asdsadsad", Toast.LENGTH_SHORT).show();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(request);

        return view;
    }
}
