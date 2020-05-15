package com.jungyoonsung.startingpoint;

import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        // Optional Parameters to pass as POST request
        JSONObject js = new JSONObject();
        try {
            js.put("apikey", "1814f7ddd67754490effc301d6e0f940");
            js.put("qestrnSeq", "21");
            js.put("trgetSe", "100207");
            js.put("name", "");
            js.put("gender", "100323");
            js.put("school", "");
            js.put("grade", "1");
            js.put("email", "");
            js.put("startDtm", ts);
            js.put("answers", "1=5 2=5 3=3 4=3 5=1 6=1 7=4 8=4 9=4 10=5 11=4 12=7 13=7 14=7 15=5 16=5 17=4 18=3 19=3 20=3 21=3 22=4 23=3 24=1 25=1 26=1 27=2 28=3 29=2 30=1 31=7 32=4 33=7 34=7 35=5 36=5 37=7 38=3 39=7 40=4 41=1 42=2 43=4 44=1 45=3 46=2 47=2 48=2 49=5 50=7 51=6 52=6 53=5 54=4 55=3 56=4 57=3 58=2 59=7 60=1 61=5 62=3 63=4 64=3 65=2 66=4 67=2 68=3 69=4 70=1 71=4 72=3 73=4 74=4 75=6 76=4 77=5 78=5 79=7 80=5 81=6 82=5 83=2 84=1 85=4 86=5 87=5 88=4");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Make request for JSONObject
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, "https://inspct.career.go.kr/openapi/test/report", js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TEST", response.toString() + " i am queen");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        // Adding request to request queue
        Volley.newRequestQueue(this).add(jsonObjReq);








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
                cardview_answerScore05.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NextQuestion(5);
                    }
                });
                cardview_answerScore04.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NextQuestion(4);
                    }
                });
                cardview_answerScore03.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NextQuestion(3);
                    }
                });
                cardview_answerScore02.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NextQuestion(2);
                    }
                });
                cardview_answerScore01.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NextQuestion(1);
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
