package com.jungyoonsung.startingpoint;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.solver.LinearSystem;

import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    TextView text1, text2, text3;
    LinearLayout
            cardview_answerScore07,
            cardview_answerScore06,
            cardview_answerScore05,
            cardview_answerScore04,
            cardview_answerScore03,
            cardview_answerScore02,
            cardview_answerScore01;

    TextView
            t_c_7,
            t_c_6,
            t_c_5,
            t_c_4,
            t_c_3,
            t_c_2,
            t_c_1;


    private RequestQueue requestQueue;

    private List<String> L_question = new ArrayList<>();
    private List<String> L_answer = new ArrayList<>();
    private List<String> L_tip = new ArrayList<>();

    String result;

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);

        cardview_answerScore07 = (LinearLayout) findViewById(R.id.cardview_answerScore07);
        cardview_answerScore06 = (LinearLayout) findViewById(R.id.cardview_answerScore06);
        cardview_answerScore05 = (LinearLayout) findViewById(R.id.cardview_answerScore05);
        cardview_answerScore04 = (LinearLayout) findViewById(R.id.cardview_answerScore04);
        cardview_answerScore03 = (LinearLayout) findViewById(R.id.cardview_answerScore03);
        cardview_answerScore02 = (LinearLayout) findViewById(R.id.cardview_answerScore02);
        cardview_answerScore01 = (LinearLayout) findViewById(R.id.cardview_answerScore01);

        t_c_7 = (TextView) findViewById(R.id.c_t_7);
        t_c_6 = (TextView) findViewById(R.id.c_t_6);
        t_c_5 = (TextView) findViewById(R.id.c_t_5);
        t_c_4 = (TextView) findViewById(R.id.c_t_4);
        t_c_3 = (TextView) findViewById(R.id.c_t_3);
        t_c_2 = (TextView) findViewById(R.id.c_t_2);
        t_c_1 = (TextView) findViewById(R.id.c_t_1);

        requestQueue = Volley.newRequestQueue(this);

        String url = "https://inspct.career.go.kr/openapi/test/questions?apikey=1814f7ddd67754490effc301d6e0f940&q=21";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("RESULT");

                            for (int i = 0; i<jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                String question = jsonObject2.getString("question");

                                String tip1 = jsonObject2.getString("tip1Desc");
                                String tip2 = jsonObject2.getString("tip2Desc");
                                String tip = tip1 + "\n" + tip2;

                                if (i == 0) {
                                    String s_aS7 = jsonObject2.getString("answer07");
                                    String s_aS6 = jsonObject2.getString("answer06");
                                    String s_aS5 = jsonObject2.getString("answer05");
                                    String s_aS4 = jsonObject2.getString("answer04");
                                    String s_aS3 = jsonObject2.getString("answer03");
                                    String s_aS2 = jsonObject2.getString("answer02");
                                    String s_aS1 = jsonObject2.getString("answer01");

                                    L_answer.add(s_aS7);
                                    L_answer.add(s_aS6);
                                    L_answer.add(s_aS5);
                                    L_answer.add(s_aS4);
                                    L_answer.add(s_aS3);
                                    L_answer.add(s_aS2);
                                    L_answer.add(s_aS1);
                                }

                                L_question.add(question);
                                L_tip.add(tip);



                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
        RequestQueue.RequestFinishedListener listener = new RequestQueue.RequestFinishedListener() {
            @Override
            public void onRequestFinished(final Request request) {

                text1.setText(L_question.get(i));
                text2.setText(L_tip.get(i));
                text3.setText(i + 1 + " / " + L_question.size());

                t_c_7.setText(L_answer.get(0));
                t_c_6.setText(L_answer.get(1));
                t_c_5.setText(L_answer.get(2));
                t_c_4.setText(L_answer.get(3));
                t_c_3.setText(L_answer.get(4));
                t_c_2.setText(L_answer.get(5));
                t_c_1.setText(L_answer.get(6));

                cardview_answerScore07.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NextQuestion(7);
                    }
                });
                cardview_answerScore06.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NextQuestion(6);
                    }
                });
            }
        };
        requestQueue.addRequestFinishedListener(listener);
    }

    private void NextQuestion(int i2) {

        if (i+1 == L_question.size()) {
            result = result + "88=" + i2;
            i = 100;
        } else if (i == 100) {

        } else {
            i = i + 1;
            text1.setText(L_question.get(i));
            text2.setText(L_tip.get(i));
            text3.setText(i + 1 + " / " + L_question.size());

            if (TextUtils.isEmpty(result)) {
                result = i + "=" +i2 + " ";
            } else {
                result = result + i + "=" +i2 + " ";
            }
        }

        Log.d("TEST", result);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
