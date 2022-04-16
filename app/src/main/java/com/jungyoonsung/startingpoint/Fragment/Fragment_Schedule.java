package com.jungyoonsung.startingpoint.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jungyoonsung.startingpoint.R;
import com.jungyoonsung.startingpoint.Settings.OnSwipeTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class Fragment_Schedule extends Fragment {

    Context thisContext;

    private RequestQueue requestQueue;

    String monday, tuesday, wednesday, thursday, friday;

    private List<String> period_monday = new ArrayList<>();
    private List<String> period_tuesday = new ArrayList<>();
    private List<String> period_wednesday = new ArrayList<>();
    private List<String> period_thursday = new ArrayList<>();
    private List<String> period_friday = new ArrayList<>();
    private List<String> period_monday_c = new ArrayList<>();
    private List<String> period_tuesday_c = new ArrayList<>();
    private List<String> period_wednesday_c = new ArrayList<>();
    private List<String> period_thursday_c = new ArrayList<>();
    private List<String> period_friday_c = new ArrayList<>();

    private List<String> d_period_monday = new ArrayList<>();
    private List<String> d_period_tuesday = new ArrayList<>();
    private List<String> d_period_wednesday = new ArrayList<>();
    private List<String> d_period_thursday = new ArrayList<>();
    private List<String> d_period_friday = new ArrayList<>();
    private List<String> d_period_monday_c = new ArrayList<>();
    private List<String> d_period_tuesday_c = new ArrayList<>();
    private List<String> d_period_wednesday_c = new ArrayList<>();
    private List<String> d_period_thursday_c = new ArrayList<>();
    private List<String> d_period_friday_c = new ArrayList<>();

    CardView cardView;

    TableRow
            tableRow_1,
            tableRow_2,
            tableRow_3,
            tableRow_4,
            tableRow_5,
            tableRow_6,
            tableRow_7,
            tableRow_8;

    TextView
            textView_1_1,
            textView_1_2,
            textView_1_3,
            textView_1_4,
            textView_1_5,
            textView_1_6,
            textView_1_7,
            textView_1_8;

    TextView
            textView_2_1,
            textView_2_2,
            textView_2_3,
            textView_2_4,
            textView_2_5,
            textView_2_6,
            textView_2_7,
            textView_2_8;

    TextView
            textView_3_1,
            textView_3_2,
            textView_3_3,
            textView_3_4,
            textView_3_5,
            textView_3_6,
            textView_3_7,
            textView_3_8;

    TextView
            textView_4_1,
            textView_4_2,
            textView_4_3,
            textView_4_4,
            textView_4_5,
            textView_4_6,
            textView_4_7,
            textView_4_8;

    TextView
            textView_5_1,
            textView_5_2,
            textView_5_3,
            textView_5_4,
            textView_5_5,
            textView_5_6,
            textView_5_7,
            textView_5_8;

    int count;

    String s_image_pdf_school, s_image_pdf_grade, s_image_pdf_class;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_fragment_schedule, container, false);

        thisContext = container.getContext();

        requestQueue = Volley.newRequestQueue(thisContext);

        cardView = (CardView) view.findViewById(R.id.cardView);

        SharedPreferences sharedPreferencesBackground = thisContext.getSharedPreferences("Background", MODE_PRIVATE);
        int position = sharedPreferencesBackground.getInt("Position", 0);
        if (position < 10) {
            cardView.setCardBackgroundColor(Color.parseColor("#292929"));
        } else {
            cardView.setCardBackgroundColor(Color.parseColor("#000000"));
        }

        tableRow_1 = (TableRow) view.findViewById(R.id.tableRow_1);
        tableRow_2 = (TableRow) view.findViewById(R.id.tableRow_2);
        tableRow_3 = (TableRow) view.findViewById(R.id.tableRow_3);
        tableRow_4 = (TableRow) view.findViewById(R.id.tableRow_4);
        tableRow_5 = (TableRow) view.findViewById(R.id.tableRow_5);
        tableRow_6 = (TableRow) view.findViewById(R.id.tableRow_6);
        tableRow_7 = (TableRow) view.findViewById(R.id.tableRow_7);
        tableRow_8 = (TableRow) view.findViewById(R.id.tableRow_8);

        textView_1_1 = (TextView) view.findViewById(R.id.textView_1_1);
        textView_1_2 = (TextView) view.findViewById(R.id.textView_1_2);
        textView_1_3 = (TextView) view.findViewById(R.id.textView_1_3);
        textView_1_4 = (TextView) view.findViewById(R.id.textView_1_4);
        textView_1_5 = (TextView) view.findViewById(R.id.textView_1_5);
        textView_1_6 = (TextView) view.findViewById(R.id.textView_1_6);
        textView_1_7 = (TextView) view.findViewById(R.id.textView_1_7);
        textView_1_8 = (TextView) view.findViewById(R.id.textView_1_8);

        textView_2_1 = (TextView) view.findViewById(R.id.textView_2_1);
        textView_2_2 = (TextView) view.findViewById(R.id.textView_2_2);
        textView_2_3 = (TextView) view.findViewById(R.id.textView_2_3);
        textView_2_4 = (TextView) view.findViewById(R.id.textView_2_4);
        textView_2_5 = (TextView) view.findViewById(R.id.textView_2_5);
        textView_2_6 = (TextView) view.findViewById(R.id.textView_2_6);
        textView_2_7 = (TextView) view.findViewById(R.id.textView_2_7);
        textView_2_8 = (TextView) view.findViewById(R.id.textView_2_8);

        textView_3_1 = (TextView) view.findViewById(R.id.textView_3_1);
        textView_3_2 = (TextView) view.findViewById(R.id.textView_3_2);
        textView_3_3 = (TextView) view.findViewById(R.id.textView_3_3);
        textView_3_4 = (TextView) view.findViewById(R.id.textView_3_4);
        textView_3_5 = (TextView) view.findViewById(R.id.textView_3_5);
        textView_3_6 = (TextView) view.findViewById(R.id.textView_3_6);
        textView_3_7 = (TextView) view.findViewById(R.id.textView_3_7);
        textView_3_8 = (TextView) view.findViewById(R.id.textView_3_8);

        textView_4_1 = (TextView) view.findViewById(R.id.textView_4_1);
        textView_4_2 = (TextView) view.findViewById(R.id.textView_4_2);
        textView_4_3 = (TextView) view.findViewById(R.id.textView_4_3);
        textView_4_4 = (TextView) view.findViewById(R.id.textView_4_4);
        textView_4_5 = (TextView) view.findViewById(R.id.textView_4_5);
        textView_4_6 = (TextView) view.findViewById(R.id.textView_4_6);
        textView_4_7 = (TextView) view.findViewById(R.id.textView_4_7);
        textView_4_8 = (TextView) view.findViewById(R.id.textView_4_8);

        textView_5_1 = (TextView) view.findViewById(R.id.textView_5_1);
        textView_5_2 = (TextView) view.findViewById(R.id.textView_5_2);
        textView_5_3 = (TextView) view.findViewById(R.id.textView_5_3);
        textView_5_4 = (TextView) view.findViewById(R.id.textView_5_4);
        textView_5_5 = (TextView) view.findViewById(R.id.textView_5_5);
        textView_5_6 = (TextView) view.findViewById(R.id.textView_5_6);
        textView_5_7 = (TextView) view.findViewById(R.id.textView_5_7);
        textView_5_8 = (TextView) view.findViewById(R.id.textView_5_8);

        String startDate, endDate;

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

        startDate = dateFormat.format(calendar.getTime());
        monday = dateFormat.format(calendar.getTime());

        calendar.add(Calendar.DAY_OF_WEEK, 1);
        tuesday = dateFormat.format(calendar.getTime());

        calendar.add(Calendar.DAY_OF_WEEK, 1);
        wednesday = dateFormat.format(calendar.getTime());

        calendar.add(Calendar.DAY_OF_WEEK, 1);
        thursday = dateFormat.format(calendar.getTime());

        calendar.add(Calendar.DAY_OF_WEEK, 1);
        endDate = dateFormat.format(calendar.getTime());
        friday = dateFormat.format(calendar.getTime());

        SharedPreferences sharedPreferencesUSER = thisContext.getSharedPreferences("USER", MODE_PRIVATE);

        String ATPT_OFCDC_SC_CODE = sharedPreferencesUSER.getString("s_1_ATPT_OFCDC_SC_CODE", "");
        String SD_SCHUL_CODE = sharedPreferencesUSER.getString("s_3_SD_SCHUL_CODE", "");
        final String SCHUL_KND_SC_NM = sharedPreferencesUSER.getString("s_5_SCHUL_KND_SC_NM", "");
        String s_grade = sharedPreferencesUSER.getString("s_6_grade", "");
        String s_class = sharedPreferencesUSER.getString("s_7_class", "");

        String url = null;

        if (SCHUL_KND_SC_NM.equals("초등학교")) {
            url = "https://open.neis.go.kr/hub/elsTimetable?KEY=3c0d4c588de2476a960e8fd2988ce38c&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&GRADE=" + s_grade + "&CLASS_NM=" + s_class + "&TI_FROM_YMD=" + startDate + "&TI_TO_YMD=" + endDate;
        } else if (SCHUL_KND_SC_NM.equals("중학교")) {
            url = "https://open.neis.go.kr/hub/misTimetable?KEY=11b3f567023f438296130b6335d20b4c&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&GRADE=" + s_grade + "&CLASS_NM=" + s_class + "&TI_FROM_YMD=" + startDate + "&TI_TO_YMD=" + endDate;
        } else if (SCHUL_KND_SC_NM.equals("고등학교")) {
            url = "https://open.neis.go.kr/hub/hisTimetable?KEY=1f0018f4daf247c2b1d3d8b2cf15c257&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&GRADE=" + s_grade + "&CLRM_NM=" + s_class + "&TI_FROM_YMD=" + startDate + "&TI_TO_YMD=" + endDate;
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

                                    String date = response3.getString("ALL_TI_YMD");
                                    String perio = response3.getString("PERIO");
                                    String period = response3.getString("ITRT_CNTNT");

                                    c_4(date, perio, period);
                                }
                                show_period(period_monday, textView_1_1, textView_1_2, textView_1_3, textView_1_4, textView_1_5, textView_1_6, textView_1_7, textView_1_8);
                                show_period(period_tuesday, textView_2_1, textView_2_2, textView_2_3, textView_2_4, textView_2_5, textView_2_6, textView_2_7, textView_2_8);
                                show_period(period_wednesday, textView_3_1, textView_3_2, textView_3_3, textView_3_4, textView_3_5, textView_3_6, textView_3_7, textView_3_8);
                                show_period(period_thursday, textView_4_1, textView_4_2, textView_4_3, textView_4_4, textView_4_5, textView_4_6, textView_4_7, textView_4_8);
                                show_period(period_friday, textView_5_1, textView_5_2, textView_5_3, textView_5_4, textView_5_5, textView_5_6, textView_5_7, textView_5_8);
                                c_2();

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

                                    String date = response3.getString("ALL_TI_YMD");
                                    String perio = response3.getString("PERIO");
                                    String period = response3.getString("ITRT_CNTNT");

                                    period = period.replaceFirst("-", "");

                                    c_4(date, perio, period);
                                }
                                show_period(period_monday, textView_1_1, textView_1_2, textView_1_3, textView_1_4, textView_1_5, textView_1_6, textView_1_7, textView_1_8);
                                show_period(period_tuesday, textView_2_1, textView_2_2, textView_2_3, textView_2_4, textView_2_5, textView_2_6, textView_2_7, textView_2_8);
                                show_period(period_wednesday, textView_3_1, textView_3_2, textView_3_3, textView_3_4, textView_3_5, textView_3_6, textView_3_7, textView_3_8);
                                show_period(period_thursday, textView_4_1, textView_4_2, textView_4_3, textView_4_4, textView_4_5, textView_4_6, textView_4_7, textView_4_8);
                                show_period(period_friday, textView_5_1, textView_5_2, textView_5_3, textView_5_4, textView_5_5, textView_5_6, textView_5_7, textView_5_8);
                                c_2();

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

                                    String date = response3.getString("ALL_TI_YMD");
                                    String perio = response3.getString("PERIO");
                                    String period = response3.getString("ITRT_CNTNT");

                                    c_4(date, perio, period);
                                }
                                show_period(period_monday, textView_1_1, textView_1_2, textView_1_3, textView_1_4, textView_1_5, textView_1_6, textView_1_7, textView_1_8);
                                show_period(period_tuesday, textView_2_1, textView_2_2, textView_2_3, textView_2_4, textView_2_5, textView_2_6, textView_2_7, textView_2_8);
                                show_period(period_wednesday, textView_3_1, textView_3_2, textView_3_3, textView_3_4, textView_3_5, textView_3_6, textView_3_7, textView_3_8);
                                show_period(period_thursday, textView_4_1, textView_4_2, textView_4_3, textView_4_4, textView_4_5, textView_4_6, textView_4_7, textView_4_8);
                                show_period(period_friday, textView_5_1, textView_5_2, textView_5_3, textView_5_4, textView_5_5, textView_5_6, textView_5_7, textView_5_8);
                                c_2();

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

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                count = 0;

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(thisContext);
                LayoutInflater myInflater = LayoutInflater.from(thisContext);

                final View mView = myInflater.inflate(R.layout.dialog_schedule, null);

                final TableRow
                        tableRow_1,
                        tableRow_2,
                        tableRow_3,
                        tableRow_4,
                        tableRow_5,
                        tableRow_6,
                        tableRow_7,
                        tableRow_8;

                tableRow_1 = (TableRow) mView.findViewById(R.id.tableRow_1);
                tableRow_2 = (TableRow) mView.findViewById(R.id.tableRow_2);
                tableRow_3 = (TableRow) mView.findViewById(R.id.tableRow_3);
                tableRow_4 = (TableRow) mView.findViewById(R.id.tableRow_4);
                tableRow_5 = (TableRow) mView.findViewById(R.id.tableRow_5);
                tableRow_6 = (TableRow) mView.findViewById(R.id.tableRow_6);
                tableRow_7 = (TableRow) mView.findViewById(R.id.tableRow_7);
                tableRow_8 = (TableRow) mView.findViewById(R.id.tableRow_8);

                final TextView
                        textView_1_1,
                        textView_1_2,
                        textView_1_3,
                        textView_1_4,
                        textView_1_5,
                        textView_1_6,
                        textView_1_7,
                        textView_1_8;

                final TextView
                        textView_2_1,
                        textView_2_2,
                        textView_2_3,
                        textView_2_4,
                        textView_2_5,
                        textView_2_6,
                        textView_2_7,
                        textView_2_8;

                final TextView
                        textView_3_1,
                        textView_3_2,
                        textView_3_3,
                        textView_3_4,
                        textView_3_5,
                        textView_3_6,
                        textView_3_7,
                        textView_3_8;

                final TextView
                        textView_4_1,
                        textView_4_2,
                        textView_4_3,
                        textView_4_4,
                        textView_4_5,
                        textView_4_6,
                        textView_4_7,
                        textView_4_8;

                final TextView
                        textView_5_1,
                        textView_5_2,
                        textView_5_3,
                        textView_5_4,
                        textView_5_5,
                        textView_5_6,
                        textView_5_7,
                        textView_5_8;

                textView_1_1 = (TextView) mView.findViewById(R.id.textView_1_1);
                textView_1_2 = (TextView) mView.findViewById(R.id.textView_1_2);
                textView_1_3 = (TextView) mView.findViewById(R.id.textView_1_3);
                textView_1_4 = (TextView) mView.findViewById(R.id.textView_1_4);
                textView_1_5 = (TextView) mView.findViewById(R.id.textView_1_5);
                textView_1_6 = (TextView) mView.findViewById(R.id.textView_1_6);
                textView_1_7 = (TextView) mView.findViewById(R.id.textView_1_7);
                textView_1_8 = (TextView) mView.findViewById(R.id.textView_1_8);

                textView_2_1 = (TextView) mView.findViewById(R.id.textView_2_1);
                textView_2_2 = (TextView) mView.findViewById(R.id.textView_2_2);
                textView_2_3 = (TextView) mView.findViewById(R.id.textView_2_3);
                textView_2_4 = (TextView) mView.findViewById(R.id.textView_2_4);
                textView_2_5 = (TextView) mView.findViewById(R.id.textView_2_5);
                textView_2_6 = (TextView) mView.findViewById(R.id.textView_2_6);
                textView_2_7 = (TextView) mView.findViewById(R.id.textView_2_7);
                textView_2_8 = (TextView) mView.findViewById(R.id.textView_2_8);

                textView_3_1 = (TextView) mView.findViewById(R.id.textView_3_1);
                textView_3_2 = (TextView) mView.findViewById(R.id.textView_3_2);
                textView_3_3 = (TextView) mView.findViewById(R.id.textView_3_3);
                textView_3_4 = (TextView) mView.findViewById(R.id.textView_3_4);
                textView_3_5 = (TextView) mView.findViewById(R.id.textView_3_5);
                textView_3_6 = (TextView) mView.findViewById(R.id.textView_3_6);
                textView_3_7 = (TextView) mView.findViewById(R.id.textView_3_7);
                textView_3_8 = (TextView) mView.findViewById(R.id.textView_3_8);

                textView_4_1 = (TextView) mView.findViewById(R.id.textView_4_1);
                textView_4_2 = (TextView) mView.findViewById(R.id.textView_4_2);
                textView_4_3 = (TextView) mView.findViewById(R.id.textView_4_3);
                textView_4_4 = (TextView) mView.findViewById(R.id.textView_4_4);
                textView_4_5 = (TextView) mView.findViewById(R.id.textView_4_5);
                textView_4_6 = (TextView) mView.findViewById(R.id.textView_4_6);
                textView_4_7 = (TextView) mView.findViewById(R.id.textView_4_7);
                textView_4_8 = (TextView) mView.findViewById(R.id.textView_4_8);

                textView_5_1 = (TextView) mView.findViewById(R.id.textView_5_1);
                textView_5_2 = (TextView) mView.findViewById(R.id.textView_5_2);
                textView_5_3 = (TextView) mView.findViewById(R.id.textView_5_3);
                textView_5_4 = (TextView) mView.findViewById(R.id.textView_5_4);
                textView_5_5 = (TextView) mView.findViewById(R.id.textView_5_5);
                textView_5_6 = (TextView) mView.findViewById(R.id.textView_5_6);
                textView_5_7 = (TextView) mView.findViewById(R.id.textView_5_7);
                textView_5_8 = (TextView) mView.findViewById(R.id.textView_5_8);

                final TextView t_date;
                t_date = (TextView) mView.findViewById(R.id.t_date);

                CardView dialog_cardView = (CardView) mView.findViewById(R.id.cardView);

                SharedPreferences sharedPreferencesBackground = thisContext.getSharedPreferences("Background", MODE_PRIVATE);
                int position = sharedPreferencesBackground.getInt("Position", 0);
                if (position < 10) {
                    dialog_cardView.setCardBackgroundColor(Color.parseColor("#292929"));
                } else {
                    dialog_cardView.setCardBackgroundColor(Color.parseColor("#000000"));
                }

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                final RequestQueue requestQueueDialog = Volley.newRequestQueue(thisContext);

                SharedPreferences sharedPreferencesUSER = thisContext.getSharedPreferences("USER", MODE_PRIVATE);

                String ATPT_OFCDC_SC_CODE = sharedPreferencesUSER.getString("s_1_ATPT_OFCDC_SC_CODE", "");
                String SD_SCHUL_CODE = sharedPreferencesUSER.getString("s_3_SD_SCHUL_CODE", "");
                final String SCHUL_KND_SC_NM = sharedPreferencesUSER.getString("s_5_SCHUL_KND_SC_NM", "");
                String s_grade = sharedPreferencesUSER.getString("s_6_grade", "");
                String s_class = sharedPreferencesUSER.getString("s_7_class", "");


                c_1();

                final Calendar calendar = GregorianCalendar.getInstance();
                calendar.setFirstDayOfWeek(Calendar.MONDAY);
                calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
                calendar.add(Calendar.WEEK_OF_MONTH, count);

                String startDate, endDate;
                String s_d_s, s_d_f;

                DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                DateFormat t_dateFormat_s = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
                DateFormat t_dateFormat_f = new SimpleDateFormat("dd", Locale.getDefault());

                startDate = dateFormat.format(calendar.getTime());
                monday = dateFormat.format(calendar.getTime());
                s_d_s = t_dateFormat_s.format(calendar.getTime());

                calendar.add(Calendar.DAY_OF_WEEK, 1);
                tuesday = dateFormat.format(calendar.getTime());

                calendar.add(Calendar.DAY_OF_WEEK, 1);
                wednesday = dateFormat.format(calendar.getTime());

                calendar.add(Calendar.DAY_OF_WEEK, 1);
                thursday = dateFormat.format(calendar.getTime());

                calendar.add(Calendar.DAY_OF_WEEK, 1);
                endDate = dateFormat.format(calendar.getTime());
                friday = dateFormat.format(calendar.getTime());
                s_d_f = t_dateFormat_f.format(calendar.getTime());

                t_date.setText(s_d_s + " ~ " + s_d_f);

                String url = null;

                if (SCHUL_KND_SC_NM.equals("초등학교")) {
                    url = "https://open.neis.go.kr/hub/elsTimetable?KEY=3c0d4c588de2476a960e8fd2988ce38c&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&GRADE=" + s_grade + "&CLASS_NM=" + s_class + "&TI_FROM_YMD=" + startDate + "&TI_TO_YMD=" + endDate;
                } else if (SCHUL_KND_SC_NM.equals("중학교")) {
                    url = "https://open.neis.go.kr/hub/misTimetable?KEY=11b3f567023f438296130b6335d20b4c&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&GRADE=" + s_grade + "&CLASS_NM=" + s_class + "&TI_FROM_YMD=" + startDate + "&TI_TO_YMD=" + endDate;
                } else if (SCHUL_KND_SC_NM.equals("고등학교")) {
                    url = "https://open.neis.go.kr/hub/hisTimetable?KEY=1f0018f4daf247c2b1d3d8b2cf15c257&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&GRADE=" + s_grade + "&CLRM_NM=" + s_class + "&TI_FROM_YMD=" + startDate + "&TI_TO_YMD=" + endDate;
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

                                            String date = response3.getString("ALL_TI_YMD");
                                            String perio = response3.getString("PERIO");
                                            String period = response3.getString("ITRT_CNTNT");

                                            c_3(date, perio, period);
                                        }
                                        show_period(d_period_monday, textView_1_1, textView_1_2, textView_1_3, textView_1_4, textView_1_5, textView_1_6, textView_1_7, textView_1_8);
                                        show_period(d_period_tuesday, textView_2_1, textView_2_2, textView_2_3, textView_2_4, textView_2_5, textView_2_6, textView_2_7, textView_2_8);
                                        show_period(d_period_wednesday, textView_3_1, textView_3_2, textView_3_3, textView_3_4, textView_3_5, textView_3_6, textView_3_7, textView_3_8);
                                        show_period(d_period_thursday, textView_4_1, textView_4_2, textView_4_3, textView_4_4, textView_4_5, textView_4_6, textView_4_7, textView_4_8);
                                        show_period(d_period_friday, textView_5_1, textView_5_2, textView_5_3, textView_5_4, textView_5_5, textView_5_6, textView_5_7, textView_5_8);

                                        c_5(
                                                textView_1_2, textView_1_3, textView_1_4, textView_1_5, textView_1_6, textView_1_7, textView_1_8,
                                                textView_2_2, textView_2_3, textView_2_4, textView_2_5, textView_2_6, textView_2_7, textView_2_8,
                                                textView_3_2, textView_3_3, textView_3_4, textView_3_5, textView_3_6, textView_3_7, textView_3_8,
                                                textView_4_2, textView_4_3, textView_4_4, textView_4_5, textView_4_6, textView_4_7, textView_4_8,
                                                textView_5_2, textView_5_3, textView_5_4, textView_5_5, textView_5_6, textView_5_7, textView_5_8,
                                                tableRow_1, tableRow_2, tableRow_3, tableRow_4, tableRow_5, tableRow_6, tableRow_7, tableRow_8);

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

                                            String date = response3.getString("ALL_TI_YMD");
                                            String perio = response3.getString("PERIO");
                                            String period = response3.getString("ITRT_CNTNT");

                                            period = period.replaceFirst("-", "");

                                            c_3(date, perio, period);
                                        }
                                        show_period(d_period_monday, textView_1_1, textView_1_2, textView_1_3, textView_1_4, textView_1_5, textView_1_6, textView_1_7, textView_1_8);
                                        show_period(d_period_tuesday, textView_2_1, textView_2_2, textView_2_3, textView_2_4, textView_2_5, textView_2_6, textView_2_7, textView_2_8);
                                        show_period(d_period_wednesday, textView_3_1, textView_3_2, textView_3_3, textView_3_4, textView_3_5, textView_3_6, textView_3_7, textView_3_8);
                                        show_period(d_period_thursday, textView_4_1, textView_4_2, textView_4_3, textView_4_4, textView_4_5, textView_4_6, textView_4_7, textView_4_8);
                                        show_period(d_period_friday, textView_5_1, textView_5_2, textView_5_3, textView_5_4, textView_5_5, textView_5_6, textView_5_7, textView_5_8);

                                        c_5(
                                                textView_1_2, textView_1_3, textView_1_4, textView_1_5, textView_1_6, textView_1_7, textView_1_8,
                                                textView_2_2, textView_2_3, textView_2_4, textView_2_5, textView_2_6, textView_2_7, textView_2_8,
                                                textView_3_2, textView_3_3, textView_3_4, textView_3_5, textView_3_6, textView_3_7, textView_3_8,
                                                textView_4_2, textView_4_3, textView_4_4, textView_4_5, textView_4_6, textView_4_7, textView_4_8,
                                                textView_5_2, textView_5_3, textView_5_4, textView_5_5, textView_5_6, textView_5_7, textView_5_8,
                                                tableRow_1, tableRow_2, tableRow_3, tableRow_4, tableRow_5, tableRow_6, tableRow_7, tableRow_8);

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

                                            String date = response3.getString("ALL_TI_YMD");
                                            String perio = response3.getString("PERIO");
                                            String period = response3.getString("ITRT_CNTNT");

                                            c_3(date, perio, period);
                                        }
                                        show_period(d_period_monday, textView_1_1, textView_1_2, textView_1_3, textView_1_4, textView_1_5, textView_1_6, textView_1_7, textView_1_8);
                                        show_period(d_period_tuesday, textView_2_1, textView_2_2, textView_2_3, textView_2_4, textView_2_5, textView_2_6, textView_2_7, textView_2_8);
                                        show_period(d_period_wednesday, textView_3_1, textView_3_2, textView_3_3, textView_3_4, textView_3_5, textView_3_6, textView_3_7, textView_3_8);
                                        show_period(d_period_thursday, textView_4_1, textView_4_2, textView_4_3, textView_4_4, textView_4_5, textView_4_6, textView_4_7, textView_4_8);
                                        show_period(d_period_friday, textView_5_1, textView_5_2, textView_5_3, textView_5_4, textView_5_5, textView_5_6, textView_5_7, textView_5_8);

                                        c_5(
                                                textView_1_2, textView_1_3, textView_1_4, textView_1_5, textView_1_6, textView_1_7, textView_1_8,
                                                textView_2_2, textView_2_3, textView_2_4, textView_2_5, textView_2_6, textView_2_7, textView_2_8,
                                                textView_3_2, textView_3_3, textView_3_4, textView_3_5, textView_3_6, textView_3_7, textView_3_8,
                                                textView_4_2, textView_4_3, textView_4_4, textView_4_5, textView_4_6, textView_4_7, textView_4_8,
                                                textView_5_2, textView_5_3, textView_5_4, textView_5_5, textView_5_6, textView_5_7, textView_5_8,
                                                tableRow_1, tableRow_2, tableRow_3, tableRow_4, tableRow_5, tableRow_6, tableRow_7, tableRow_8);

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

                requestQueueDialog.add(request);

                final RequestQueue.RequestFinishedListener listener = new RequestQueue.RequestFinishedListener() {
                    @Override
                    public void onRequestFinished(final Request request) {
                        dialog.show();
                    }
                };

                requestQueueDialog.addRequestFinishedListener(listener);

                dialog_cardView.setOnTouchListener(new OnSwipeTouchListener(thisContext) {
                    public void onSwipeRight() {
                        count = count - 1;

                        dialog.dismiss();

                        c_1();

                        requestQueueDialog.getCache().clear();

                        SharedPreferences sharedPreferencesUSER = thisContext.getSharedPreferences("USER", MODE_PRIVATE);

                        String ATPT_OFCDC_SC_CODE = sharedPreferencesUSER.getString("s_1_ATPT_OFCDC_SC_CODE", "");
                        String SD_SCHUL_CODE = sharedPreferencesUSER.getString("s_3_SD_SCHUL_CODE", "");
                        final String SCHUL_KND_SC_NM = sharedPreferencesUSER.getString("s_5_SCHUL_KND_SC_NM", "");
                        String s_grade = sharedPreferencesUSER.getString("s_6_grade", "");
                        String s_class = sharedPreferencesUSER.getString("s_7_class", "");

                        final Calendar calendar = GregorianCalendar.getInstance();
                        calendar.setFirstDayOfWeek(Calendar.MONDAY);
                        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
                        calendar.add(Calendar.WEEK_OF_MONTH, count);

                        String startDate, endDate;
                        String s_d_s, s_d_f;

                        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                        DateFormat t_dateFormat_s = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
                        DateFormat t_dateFormat_f = new SimpleDateFormat("dd", Locale.getDefault());

                        startDate = dateFormat.format(calendar.getTime());
                        monday = dateFormat.format(calendar.getTime());
                        s_d_s = t_dateFormat_s.format(calendar.getTime());

                        calendar.add(Calendar.DAY_OF_WEEK, 1);
                        tuesday = dateFormat.format(calendar.getTime());

                        calendar.add(Calendar.DAY_OF_WEEK, 1);
                        wednesday = dateFormat.format(calendar.getTime());

                        calendar.add(Calendar.DAY_OF_WEEK, 1);
                        thursday = dateFormat.format(calendar.getTime());

                        calendar.add(Calendar.DAY_OF_WEEK, 1);
                        endDate = dateFormat.format(calendar.getTime());
                        friday = dateFormat.format(calendar.getTime());
                        s_d_f = t_dateFormat_f.format(calendar.getTime());

                        t_date.setText(s_d_s + " ~ " + s_d_f);

                        String url = null;

                        if (SCHUL_KND_SC_NM.equals("초등학교")) {
                            url = "https://open.neis.go.kr/hub/elsTimetable?KEY=3c0d4c588de2476a960e8fd2988ce38c&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&GRADE=" + s_grade + "&CLASS_NM=" + s_class + "&TI_FROM_YMD=" + startDate + "&TI_TO_YMD=" + endDate;
                        } else if (SCHUL_KND_SC_NM.equals("중학교")) {
                            url = "https://open.neis.go.kr/hub/misTimetable?KEY=11b3f567023f438296130b6335d20b4c&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&GRADE=" + s_grade + "&CLASS_NM=" + s_class + "&TI_FROM_YMD=" + startDate + "&TI_TO_YMD=" + endDate;
                        } else if (SCHUL_KND_SC_NM.equals("고등학교")) {
                            url = "https://open.neis.go.kr/hub/hisTimetable?KEY=1f0018f4daf247c2b1d3d8b2cf15c257&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&GRADE=" + s_grade + "&CLRM_NM=" + s_class + "&TI_FROM_YMD=" + startDate + "&TI_TO_YMD=" + endDate;
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

                                                    String date = response3.getString("ALL_TI_YMD");
                                                    String perio = response3.getString("PERIO");
                                                    String period = response3.getString("ITRT_CNTNT");

                                                    c_3(date, perio, period);
                                                }
                                                show_period(d_period_monday, textView_1_1, textView_1_2, textView_1_3, textView_1_4, textView_1_5, textView_1_6, textView_1_7, textView_1_8);
                                                show_period(d_period_tuesday, textView_2_1, textView_2_2, textView_2_3, textView_2_4, textView_2_5, textView_2_6, textView_2_7, textView_2_8);
                                                show_period(d_period_wednesday, textView_3_1, textView_3_2, textView_3_3, textView_3_4, textView_3_5, textView_3_6, textView_3_7, textView_3_8);
                                                show_period(d_period_thursday, textView_4_1, textView_4_2, textView_4_3, textView_4_4, textView_4_5, textView_4_6, textView_4_7, textView_4_8);
                                                show_period(d_period_friday, textView_5_1, textView_5_2, textView_5_3, textView_5_4, textView_5_5, textView_5_6, textView_5_7, textView_5_8);

                                                c_5(
                                                        textView_1_2, textView_1_3, textView_1_4, textView_1_5, textView_1_6, textView_1_7, textView_1_8,
                                                        textView_2_2, textView_2_3, textView_2_4, textView_2_5, textView_2_6, textView_2_7, textView_2_8,
                                                        textView_3_2, textView_3_3, textView_3_4, textView_3_5, textView_3_6, textView_3_7, textView_3_8,
                                                        textView_4_2, textView_4_3, textView_4_4, textView_4_5, textView_4_6, textView_4_7, textView_4_8,
                                                        textView_5_2, textView_5_3, textView_5_4, textView_5_5, textView_5_6, textView_5_7, textView_5_8,
                                                        tableRow_1, tableRow_2, tableRow_3, tableRow_4, tableRow_5, tableRow_6, tableRow_7, tableRow_8);

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

                                                    String date = response3.getString("ALL_TI_YMD");
                                                    String perio = response3.getString("PERIO");
                                                    String period = response3.getString("ITRT_CNTNT");

                                                    period = period.replaceFirst("-", "");

                                                    c_3(date, perio, period);
                                                }
                                                show_period(d_period_monday, textView_1_1, textView_1_2, textView_1_3, textView_1_4, textView_1_5, textView_1_6, textView_1_7, textView_1_8);
                                                show_period(d_period_tuesday, textView_2_1, textView_2_2, textView_2_3, textView_2_4, textView_2_5, textView_2_6, textView_2_7, textView_2_8);
                                                show_period(d_period_wednesday, textView_3_1, textView_3_2, textView_3_3, textView_3_4, textView_3_5, textView_3_6, textView_3_7, textView_3_8);
                                                show_period(d_period_thursday, textView_4_1, textView_4_2, textView_4_3, textView_4_4, textView_4_5, textView_4_6, textView_4_7, textView_4_8);
                                                show_period(d_period_friday, textView_5_1, textView_5_2, textView_5_3, textView_5_4, textView_5_5, textView_5_6, textView_5_7, textView_5_8);

                                                c_5(
                                                        textView_1_2, textView_1_3, textView_1_4, textView_1_5, textView_1_6, textView_1_7, textView_1_8,
                                                        textView_2_2, textView_2_3, textView_2_4, textView_2_5, textView_2_6, textView_2_7, textView_2_8,
                                                        textView_3_2, textView_3_3, textView_3_4, textView_3_5, textView_3_6, textView_3_7, textView_3_8,
                                                        textView_4_2, textView_4_3, textView_4_4, textView_4_5, textView_4_6, textView_4_7, textView_4_8,
                                                        textView_5_2, textView_5_3, textView_5_4, textView_5_5, textView_5_6, textView_5_7, textView_5_8,
                                                        tableRow_1, tableRow_2, tableRow_3, tableRow_4, tableRow_5, tableRow_6, tableRow_7, tableRow_8);

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

                                                    String date = response3.getString("ALL_TI_YMD");
                                                    String perio = response3.getString("PERIO");
                                                    String period = response3.getString("ITRT_CNTNT");

                                                    c_3(date, perio, period);
                                                }
                                                show_period(d_period_monday, textView_1_1, textView_1_2, textView_1_3, textView_1_4, textView_1_5, textView_1_6, textView_1_7, textView_1_8);
                                                show_period(d_period_tuesday, textView_2_1, textView_2_2, textView_2_3, textView_2_4, textView_2_5, textView_2_6, textView_2_7, textView_2_8);
                                                show_period(d_period_wednesday, textView_3_1, textView_3_2, textView_3_3, textView_3_4, textView_3_5, textView_3_6, textView_3_7, textView_3_8);
                                                show_period(d_period_thursday, textView_4_1, textView_4_2, textView_4_3, textView_4_4, textView_4_5, textView_4_6, textView_4_7, textView_4_8);
                                                show_period(d_period_friday, textView_5_1, textView_5_2, textView_5_3, textView_5_4, textView_5_5, textView_5_6, textView_5_7, textView_5_8);

                                                c_5(
                                                        textView_1_2, textView_1_3, textView_1_4, textView_1_5, textView_1_6, textView_1_7, textView_1_8,
                                                        textView_2_2, textView_2_3, textView_2_4, textView_2_5, textView_2_6, textView_2_7, textView_2_8,
                                                        textView_3_2, textView_3_3, textView_3_4, textView_3_5, textView_3_6, textView_3_7, textView_3_8,
                                                        textView_4_2, textView_4_3, textView_4_4, textView_4_5, textView_4_6, textView_4_7, textView_4_8,
                                                        textView_5_2, textView_5_3, textView_5_4, textView_5_5, textView_5_6, textView_5_7, textView_5_8,
                                                        tableRow_1, tableRow_2, tableRow_3, tableRow_4, tableRow_5, tableRow_6, tableRow_7, tableRow_8);

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

                        requestQueueDialog.add(request);

                        final RequestQueue.RequestFinishedListener listener = new RequestQueue.RequestFinishedListener() {
                            @Override
                            public void onRequestFinished(final Request request) {
                                dialog.show();
                            }
                        };

                        requestQueueDialog.addRequestFinishedListener(listener);
                    }
                    public void onSwipeLeft() {
                        count = count + 1;

                        dialog.dismiss();

                        c_1();

                        requestQueueDialog.getCache().clear();

                        SharedPreferences sharedPreferencesUSER = thisContext.getSharedPreferences("USER", MODE_PRIVATE);

                        String ATPT_OFCDC_SC_CODE = sharedPreferencesUSER.getString("s_1_ATPT_OFCDC_SC_CODE", "");
                        String SD_SCHUL_CODE = sharedPreferencesUSER.getString("s_3_SD_SCHUL_CODE", "");
                        final String SCHUL_KND_SC_NM = sharedPreferencesUSER.getString("s_5_SCHUL_KND_SC_NM", "");
                        String s_grade = sharedPreferencesUSER.getString("s_6_grade", "");
                        String s_class = sharedPreferencesUSER.getString("s_7_class", "");

                        final Calendar calendar = GregorianCalendar.getInstance();
                        calendar.setFirstDayOfWeek(Calendar.MONDAY);
                        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
                        calendar.add(Calendar.WEEK_OF_MONTH, count);

                        String startDate, endDate;
                        String s_d_s, s_d_f;

                        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                        DateFormat t_dateFormat_s = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
                        DateFormat t_dateFormat_f = new SimpleDateFormat("dd", Locale.getDefault());

                        startDate = dateFormat.format(calendar.getTime());
                        monday = dateFormat.format(calendar.getTime());
                        s_d_s = t_dateFormat_s.format(calendar.getTime());

                        calendar.add(Calendar.DAY_OF_WEEK, 1);
                        tuesday = dateFormat.format(calendar.getTime());

                        calendar.add(Calendar.DAY_OF_WEEK, 1);
                        wednesday = dateFormat.format(calendar.getTime());

                        calendar.add(Calendar.DAY_OF_WEEK, 1);
                        thursday = dateFormat.format(calendar.getTime());

                        calendar.add(Calendar.DAY_OF_WEEK, 1);
                        endDate = dateFormat.format(calendar.getTime());
                        friday = dateFormat.format(calendar.getTime());
                        s_d_f = t_dateFormat_f.format(calendar.getTime());

                        t_date.setText(s_d_s + " ~ " + s_d_f);

                        String url = null;

                        if (SCHUL_KND_SC_NM.equals("초등학교")) {
                            url = "https://open.neis.go.kr/hub/elsTimetable?KEY=3c0d4c588de2476a960e8fd2988ce38c&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&GRADE=" + s_grade + "&CLASS_NM=" + s_class + "&TI_FROM_YMD=" + startDate + "&TI_TO_YMD=" + endDate;
                        } else if (SCHUL_KND_SC_NM.equals("중학교")) {
                            url = "https://open.neis.go.kr/hub/misTimetable?KEY=11b3f567023f438296130b6335d20b4c&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&GRADE=" + s_grade + "&CLASS_NM=" + s_class + "&TI_FROM_YMD=" + startDate + "&TI_TO_YMD=" + endDate;
                        } else if (SCHUL_KND_SC_NM.equals("고등학교")) {
                            url = "https://open.neis.go.kr/hub/hisTimetable?KEY=1f0018f4daf247c2b1d3d8b2cf15c257&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&GRADE=" + s_grade + "&CLRM_NM=" + s_class + "&TI_FROM_YMD=" + startDate + "&TI_TO_YMD=" + endDate;
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

                                                    String date = response3.getString("ALL_TI_YMD");
                                                    String perio = response3.getString("PERIO");
                                                    String period = response3.getString("ITRT_CNTNT");

                                                    c_3(date, perio, period);
                                                }
                                                show_period(d_period_monday, textView_1_1, textView_1_2, textView_1_3, textView_1_4, textView_1_5, textView_1_6, textView_1_7, textView_1_8);
                                                show_period(d_period_tuesday, textView_2_1, textView_2_2, textView_2_3, textView_2_4, textView_2_5, textView_2_6, textView_2_7, textView_2_8);
                                                show_period(d_period_wednesday, textView_3_1, textView_3_2, textView_3_3, textView_3_4, textView_3_5, textView_3_6, textView_3_7, textView_3_8);
                                                show_period(d_period_thursday, textView_4_1, textView_4_2, textView_4_3, textView_4_4, textView_4_5, textView_4_6, textView_4_7, textView_4_8);
                                                show_period(d_period_friday, textView_5_1, textView_5_2, textView_5_3, textView_5_4, textView_5_5, textView_5_6, textView_5_7, textView_5_8);

                                                c_5(
                                                        textView_1_2, textView_1_3, textView_1_4, textView_1_5, textView_1_6, textView_1_7, textView_1_8,
                                                        textView_2_2, textView_2_3, textView_2_4, textView_2_5, textView_2_6, textView_2_7, textView_2_8,
                                                        textView_3_2, textView_3_3, textView_3_4, textView_3_5, textView_3_6, textView_3_7, textView_3_8,
                                                        textView_4_2, textView_4_3, textView_4_4, textView_4_5, textView_4_6, textView_4_7, textView_4_8,
                                                        textView_5_2, textView_5_3, textView_5_4, textView_5_5, textView_5_6, textView_5_7, textView_5_8,
                                                        tableRow_1, tableRow_2, tableRow_3, tableRow_4, tableRow_5, tableRow_6, tableRow_7, tableRow_8);

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

                                                    String date = response3.getString("ALL_TI_YMD");
                                                    String perio = response3.getString("PERIO");
                                                    String period = response3.getString("ITRT_CNTNT");

                                                    period = period.replaceFirst("-", "");

                                                    c_3(date, perio, period);
                                                }
                                                show_period(d_period_monday, textView_1_1, textView_1_2, textView_1_3, textView_1_4, textView_1_5, textView_1_6, textView_1_7, textView_1_8);
                                                show_period(d_period_tuesday, textView_2_1, textView_2_2, textView_2_3, textView_2_4, textView_2_5, textView_2_6, textView_2_7, textView_2_8);
                                                show_period(d_period_wednesday, textView_3_1, textView_3_2, textView_3_3, textView_3_4, textView_3_5, textView_3_6, textView_3_7, textView_3_8);
                                                show_period(d_period_thursday, textView_4_1, textView_4_2, textView_4_3, textView_4_4, textView_4_5, textView_4_6, textView_4_7, textView_4_8);
                                                show_period(d_period_friday, textView_5_1, textView_5_2, textView_5_3, textView_5_4, textView_5_5, textView_5_6, textView_5_7, textView_5_8);

                                                c_5(
                                                        textView_1_2, textView_1_3, textView_1_4, textView_1_5, textView_1_6, textView_1_7, textView_1_8,
                                                        textView_2_2, textView_2_3, textView_2_4, textView_2_5, textView_2_6, textView_2_7, textView_2_8,
                                                        textView_3_2, textView_3_3, textView_3_4, textView_3_5, textView_3_6, textView_3_7, textView_3_8,
                                                        textView_4_2, textView_4_3, textView_4_4, textView_4_5, textView_4_6, textView_4_7, textView_4_8,
                                                        textView_5_2, textView_5_3, textView_5_4, textView_5_5, textView_5_6, textView_5_7, textView_5_8,
                                                        tableRow_1, tableRow_2, tableRow_3, tableRow_4, tableRow_5, tableRow_6, tableRow_7, tableRow_8);

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

                                                    String date = response3.getString("ALL_TI_YMD");
                                                    String perio = response3.getString("PERIO");
                                                    String period = response3.getString("ITRT_CNTNT");

                                                    c_3(date, perio, period);
                                                }
                                                show_period(d_period_monday, textView_1_1, textView_1_2, textView_1_3, textView_1_4, textView_1_5, textView_1_6, textView_1_7, textView_1_8);
                                                show_period(d_period_tuesday, textView_2_1, textView_2_2, textView_2_3, textView_2_4, textView_2_5, textView_2_6, textView_2_7, textView_2_8);
                                                show_period(d_period_wednesday, textView_3_1, textView_3_2, textView_3_3, textView_3_4, textView_3_5, textView_3_6, textView_3_7, textView_3_8);
                                                show_period(d_period_thursday, textView_4_1, textView_4_2, textView_4_3, textView_4_4, textView_4_5, textView_4_6, textView_4_7, textView_4_8);
                                                show_period(d_period_friday, textView_5_1, textView_5_2, textView_5_3, textView_5_4, textView_5_5, textView_5_6, textView_5_7, textView_5_8);

                                                c_5(
                                                        textView_1_2, textView_1_3, textView_1_4, textView_1_5, textView_1_6, textView_1_7, textView_1_8,
                                                        textView_2_2, textView_2_3, textView_2_4, textView_2_5, textView_2_6, textView_2_7, textView_2_8,
                                                        textView_3_2, textView_3_3, textView_3_4, textView_3_5, textView_3_6, textView_3_7, textView_3_8,
                                                        textView_4_2, textView_4_3, textView_4_4, textView_4_5, textView_4_6, textView_4_7, textView_4_8,
                                                        textView_5_2, textView_5_3, textView_5_4, textView_5_5, textView_5_6, textView_5_7, textView_5_8,
                                                        tableRow_1, tableRow_2, tableRow_3, tableRow_4, tableRow_5, tableRow_6, tableRow_7, tableRow_8);

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

                        requestQueueDialog.add(request);

                        final RequestQueue.RequestFinishedListener listener = new RequestQueue.RequestFinishedListener() {
                            @Override
                            public void onRequestFinished(final Request request) {
                                dialog.show();
                            }
                        };

                        requestQueueDialog.addRequestFinishedListener(listener);
                    }

                });

            }
        });

        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final Dialog dialog = new Dialog(thisContext);
                dialog.setContentView(R.layout.dialog_download);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                final ImageView image = (ImageView) dialog.findViewById(R.id.image);

                String startDate, endDate;

                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setFirstDayOfWeek(Calendar.MONDAY);
                calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
                DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

                DateFormat dateFormat_s = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
                DateFormat dateFormat_f = new SimpleDateFormat("dd", Locale.getDefault());

                final String s_d_s, s_d_f;

                startDate = dateFormat.format(calendar.getTime());
                monday = dateFormat.format(calendar.getTime());
                s_d_s = dateFormat_s.format(calendar.getTime());

                calendar.add(Calendar.DAY_OF_WEEK, 1);
                tuesday = dateFormat.format(calendar.getTime());

                calendar.add(Calendar.DAY_OF_WEEK, 1);
                wednesday = dateFormat.format(calendar.getTime());

                calendar.add(Calendar.DAY_OF_WEEK, 1);
                thursday = dateFormat.format(calendar.getTime());

                calendar.add(Calendar.DAY_OF_WEEK, 1);
                endDate = dateFormat.format(calendar.getTime());
                friday = dateFormat.format(calendar.getTime());
                s_d_f = dateFormat_f.format(calendar.getTime());

                final SharedPreferences sharedPreferencesUSER = thisContext.getSharedPreferences("USER", MODE_PRIVATE);

                String ATPT_OFCDC_SC_CODE = sharedPreferencesUSER.getString("School_Name", "");
                String SD_SCHUL_CODE = sharedPreferencesUSER.getString("s_3_SD_SCHUL_CODE", "");

                String s_school = sharedPreferencesUSER.getString("School_Name", "");
                String s_grade = sharedPreferencesUSER.getString("s_6_grade", "");
                String s_class = sharedPreferencesUSER.getString("s_7_class", "");

                final String s_grade_class_number = sharedPreferencesUSER.getString("Grade_Class_Number", "");

                s_image_pdf_school = s_school;
                s_image_pdf_grade = s_grade;
                s_image_pdf_class = s_class;

                String url = null;

                if (SCHUL_KND_SC_NM.equals("초등학교")) {
                    url = "https://open.neis.go.kr/hub/elsTimetable?KEY=fe77fbc542fa4baa9b8c39659d7f5f33&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&GRADE=" + s_grade + "&CLASS_NM=" + s_class + "&TI_FROM_YMD=" + startDate + "&TI_TO_YMD=" + endDate;
                } else if (SCHUL_KND_SC_NM.equals("중학교")) {
                    url = "https://open.neis.go.kr/hub/misTimetable?KEY=fe77fbc542fa4baa9b8c39659d7f5f33&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&GRADE=" + s_grade + "&CLASS_NM=" + s_class + "&TI_FROM_YMD=" + startDate + "&TI_TO_YMD=" + endDate;
                } else if (SCHUL_KND_SC_NM.equals("고등학교")) {
                    url = "https://open.neis.go.kr/hub/hisTimetable?KEY=fe77fbc542fa4baa9b8c39659d7f5f33&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + ATPT_OFCDC_SC_CODE + "&SD_SCHUL_CODE=" + SD_SCHUL_CODE + "&GRADE=" + s_grade + "&CLRM_NM=" + s_class + "&TI_FROM_YMD=" + startDate + "&TI_TO_YMD=" + endDate;
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

                                            String date = response3.getString("ALL_TI_YMD");
                                            String perio = response3.getString("PERIO");
                                            String period = response3.getString("ITRT_CNTNT");

                                            c_4(date, perio, period);
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

                                            String date = response3.getString("ALL_TI_YMD");
                                            String perio = response3.getString("PERIO");
                                            String period = response3.getString("ITRT_CNTNT");

                                            period = period.replaceFirst("-", "");

                                            c_4(date, perio, period);
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

                                            String date = response3.getString("ALL_TI_YMD");
                                            String perio = response3.getString("PERIO");
                                            String period = response3.getString("ITRT_CNTNT");

                                            c_4(date, perio, period);
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
                final RequestQueue.RequestFinishedListener listener = new RequestQueue.RequestFinishedListener() {
                    @Override
                    public void onRequestFinished(final Request request) {

                        String monday = null;
                        String tuesday = null;
                        String wednesday = null;
                        String thursday = null;
                        String friday = null;

                        for (int i = 0; i < period_monday.size(); i++) {
                            int count = i + 1;

                            if (count == 1) {
                                monday =  period_monday.get(i) + ", ";
                            } else if (!(count == period_monday.size())) {
                                monday = monday + period_monday.get(i) + ", ";
                            } else {
                                monday = monday + period_monday.get(i);
                            }
                        }
                        for (int i = 0; i < period_tuesday.size(); i++) {
                            int count = i + 1;

                            if (count == 1) {
                                tuesday =  period_tuesday.get(i) + ", ";
                            } else if (!(count == period_tuesday.size())) {
                                tuesday = tuesday + period_tuesday.get(i) + ", ";
                            } else {
                                tuesday = tuesday + period_tuesday.get(i);
                            }
                        }
                        for (int i = 0; i < period_wednesday.size(); i++) {
                            int count = i + 1;

                            if (count == 1) {
                                wednesday =  period_wednesday.get(i) + ", ";
                            } else if (!(count == period_wednesday.size())) {
                                wednesday = wednesday + period_wednesday.get(i) + ", ";
                            } else {
                                wednesday = wednesday + period_wednesday.get(i);
                            }
                        }
                        for (int i = 0; i < period_thursday.size(); i++) {
                            int count = i + 1;

                            if (count == 1) {
                                thursday =  period_thursday.get(i) + ", ";
                            } else if (!(count == period_thursday.size())) {
                                thursday = thursday + period_thursday.get(i) + ", ";
                            } else {
                                thursday = thursday + period_thursday.get(i);
                            }
                        }
                        for (int i = 0; i < period_friday.size(); i++) {
                            int count = i + 1;

                            if (count == 1) {
                                friday =  period_friday.get(i) + ", ";
                            } else if (!(count == period_friday.size())) {
                                friday = friday + period_friday.get(i) + ", ";
                            } else {
                                friday = friday + period_friday.get(i);
                            }
                        }

                        final String finalMonday = monday;
                        final String finalTuesday = tuesday;
                        final String finalWednesday = wednesday;
                        final String finalThursday = thursday;
                        final String finalFriday = friday;

                        final String info = s_image_pdf_school + "•" + sharedPreferencesUSER.getString("s_7_class", "");

                        image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Paint paint = new Paint();
                                paint.setColor(Color.BLACK);
                                paint.setTextSize(50);

                                Bitmap bitmap = Bitmap.createBitmap(2000,1700,Bitmap.Config.ARGB_8888);
                                Canvas canvas = new Canvas(bitmap);

                                canvas.drawColor(Color.BLACK);

                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                paint.setColor(Color.WHITE);

                                paint.setColor(Color.parseColor("#EEEEEE"));
                                canvas.drawText(s_image_pdf_school, 100, 218, paint);
                                float width_s_image_pdf_school_1 = paint.measureText(s_image_pdf_school);

                                paint.setColor(Color.parseColor("#EA0000"));
                                canvas.drawText("   •   ", 100 + width_s_image_pdf_school_1, 218, paint);
                                float width_s_image_pdf_school_2 = paint.measureText("   •   ");

                                paint.setColor(Color.parseColor("#EEEEEE"));
                                canvas.drawText(s_grade_class_number, 100 + width_s_image_pdf_school_1 + width_s_image_pdf_school_2, 218, paint);

                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                paint.setColor(Color.WHITE);
                                canvas.drawText("월 : " + finalMonday, 200, 750, paint);
                                canvas.drawText("화 : " + finalTuesday, 200, 870, paint);
                                canvas.drawText("수 : " + finalWednesday, 200, 990, paint);
                                canvas.drawText("목 : " + finalThursday, 200, 1110, paint);
                                canvas.drawText("금 : " + finalFriday, 200, 1230, paint);

                                float width = paint.measureText(s_d_s + " ~ " + s_d_f);

                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                canvas.drawText(s_d_s + " ~ " + s_d_f, 1900 - width, 1600, paint);


                                String path = Environment.getExternalStorageDirectory().getPath() + "/StartingPointDownload/Image/";
                                File file = new File(path);
                                if (!file.exists()) {
                                    file.mkdirs();
                                }
                                String resultPath = path + "시간표(" + s_d_s+" ~ "+s_d_f + ")" + ".png";

                                File resultFile = new File(file, "시간표(" + s_d_s+" ~ "+s_d_f + ")" + ".png");

                                OutputStream outputStream = null;

                                try {
                                    outputStream = new FileOutputStream(resultFile);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                dialog.dismiss();
                                Toast.makeText(thisContext, "경로 : " + resultPath, Toast.LENGTH_SHORT).show();

                                try {
                                    outputStream.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    outputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                intent.setData(Uri.fromFile(resultFile));
                                thisContext.sendBroadcast(intent);
                            }
                        });
                    }
                };

                requestQueue.addRequestFinishedListener(listener);

                return true;
            }
        });

        return view;
    }

    private void show_period(List<String> period, TextView t_1, TextView t_2, TextView t_3, TextView t_4, TextView t_5, TextView t_6, TextView t_7, TextView t_8) {

        t_1.setText("");
        t_2.setText("");
        t_3.setText("");
        t_4.setText("");
        t_5.setText("");
        t_6.setText("");
        t_7.setText("");
        t_8.setText("");

        SharedPreferences sharedPreferencesBackground = thisContext.getSharedPreferences("Background", MODE_PRIVATE);
        int position = sharedPreferencesBackground.getInt("Position", 0);

        if (period.size() == 0) {

            if (position < 10) {
                t_1.setBackgroundResource(R.color.color1);
                t_2.setBackgroundResource(R.color.color1);
                t_3.setBackgroundResource(R.color.color1);
                t_4.setBackgroundResource(R.color.color1);
                t_5.setBackgroundResource(R.color.color1);
                t_6.setBackgroundResource(R.color.color1);
                t_7.setBackgroundResource(R.color.color1);
                t_8.setBackgroundResource(R.color.color1);
            } else {
                t_1.setBackgroundColor(Color.parseColor("#000000"));
                t_2.setBackgroundColor(Color.parseColor("#000000"));
                t_3.setBackgroundColor(Color.parseColor("#000000"));
                t_4.setBackgroundColor(Color.parseColor("#000000"));
                t_5.setBackgroundColor(Color.parseColor("#000000"));
                t_6.setBackgroundColor(Color.parseColor("#000000"));
                t_7.setBackgroundColor(Color.parseColor("#000000"));
                t_8.setBackgroundColor(Color.parseColor("#000000"));
            }

        } else if (period.size() == 1) {

            if (position < 10) {
                t_2.setBackgroundResource(R.color.color1);
                t_3.setBackgroundResource(R.color.color1);
                t_4.setBackgroundResource(R.color.color1);
                t_5.setBackgroundResource(R.color.color1);
                t_6.setBackgroundResource(R.color.color1);
                t_7.setBackgroundResource(R.color.color1);
                t_8.setBackgroundResource(R.color.color1);
            } else {
                t_2.setBackgroundColor(Color.parseColor("#000000"));
                t_3.setBackgroundColor(Color.parseColor("#000000"));
                t_4.setBackgroundColor(Color.parseColor("#000000"));
                t_5.setBackgroundColor(Color.parseColor("#000000"));
                t_6.setBackgroundColor(Color.parseColor("#000000"));
                t_7.setBackgroundColor(Color.parseColor("#000000"));
                t_8.setBackgroundColor(Color.parseColor("#000000"));
            }

            t_1.setText(period.get(0));
            t_1.setBackgroundResource(R.color.colorPrimary);

        } else if (period.size() == 2) {

            if (position < 10) {
                t_3.setBackgroundResource(R.color.color1);
                t_4.setBackgroundResource(R.color.color1);
                t_5.setBackgroundResource(R.color.color1);
                t_6.setBackgroundResource(R.color.color1);
                t_7.setBackgroundResource(R.color.color1);
                t_8.setBackgroundResource(R.color.color1);
            } else {
                t_3.setBackgroundColor(Color.parseColor("#000000"));
                t_4.setBackgroundColor(Color.parseColor("#000000"));
                t_5.setBackgroundColor(Color.parseColor("#000000"));
                t_6.setBackgroundColor(Color.parseColor("#000000"));
                t_7.setBackgroundColor(Color.parseColor("#000000"));
                t_8.setBackgroundColor(Color.parseColor("#000000"));
            }

            t_1.setText(period.get(0));
            t_2.setText(period.get(1));
            t_1.setBackgroundResource(R.color.colorPrimary);
            t_2.setBackgroundResource(R.color.colorPrimary);

        } else if (period.size() == 3) {

            if (position < 10) {
                t_4.setBackgroundResource(R.color.color1);
                t_5.setBackgroundResource(R.color.color1);
                t_6.setBackgroundResource(R.color.color1);
                t_7.setBackgroundResource(R.color.color1);
                t_8.setBackgroundResource(R.color.color1);
            } else {
                t_4.setBackgroundColor(Color.parseColor("#000000"));
                t_5.setBackgroundColor(Color.parseColor("#000000"));
                t_6.setBackgroundColor(Color.parseColor("#000000"));
                t_7.setBackgroundColor(Color.parseColor("#000000"));
                t_8.setBackgroundColor(Color.parseColor("#000000"));
            }

            t_1.setText(period.get(0));
            t_2.setText(period.get(1));
            t_3.setText(period.get(2));
            t_1.setBackgroundResource(R.color.colorPrimary);
            t_2.setBackgroundResource(R.color.colorPrimary);
            t_3.setBackgroundResource(R.color.colorPrimary);

        } else if (period.size() == 4) {

            if (position < 10) {
                t_5.setBackgroundResource(R.color.color1);
                t_6.setBackgroundResource(R.color.color1);
                t_7.setBackgroundResource(R.color.color1);
                t_8.setBackgroundResource(R.color.color1);
            } else {
                t_5.setBackgroundColor(Color.parseColor("#000000"));
                t_6.setBackgroundColor(Color.parseColor("#000000"));
                t_7.setBackgroundColor(Color.parseColor("#000000"));
                t_8.setBackgroundColor(Color.parseColor("#000000"));
            }

            t_1.setText(period.get(0));
            t_2.setText(period.get(1));
            t_3.setText(period.get(2));
            t_4.setText(period.get(3));
            t_1.setBackgroundResource(R.color.colorPrimary);
            t_2.setBackgroundResource(R.color.colorPrimary);
            t_3.setBackgroundResource(R.color.colorPrimary);
            t_4.setBackgroundResource(R.color.colorPrimary);

        } else if (period.size() == 5) {

            if (position < 10) {
                t_6.setBackgroundResource(R.color.color1);
                t_7.setBackgroundResource(R.color.color1);
                t_8.setBackgroundResource(R.color.color1);
            } else {
                t_6.setBackgroundColor(Color.parseColor("#000000"));
                t_7.setBackgroundColor(Color.parseColor("#000000"));
                t_8.setBackgroundColor(Color.parseColor("#000000"));
            }

            t_1.setText(period.get(0));
            t_2.setText(period.get(1));
            t_3.setText(period.get(2));
            t_4.setText(period.get(3));
            t_5.setText(period.get(4));
            t_1.setBackgroundResource(R.color.colorPrimary);
            t_2.setBackgroundResource(R.color.colorPrimary);
            t_3.setBackgroundResource(R.color.colorPrimary);
            t_4.setBackgroundResource(R.color.colorPrimary);
            t_5.setBackgroundResource(R.color.colorPrimary);

        } else if (period.size() == 6) {

            if (position < 10) {
                t_7.setBackgroundResource(R.color.color1);
                t_8.setBackgroundResource(R.color.color1);
            } else {
                t_7.setBackgroundColor(Color.parseColor("#000000"));
                t_8.setBackgroundColor(Color.parseColor("#000000"));
            }

            t_1.setText(period.get(0));
            t_2.setText(period.get(1));
            t_3.setText(period.get(2));
            t_4.setText(period.get(3));
            t_5.setText(period.get(4));
            t_6.setText(period.get(5));
            t_1.setBackgroundResource(R.color.colorPrimary);
            t_2.setBackgroundResource(R.color.colorPrimary);
            t_3.setBackgroundResource(R.color.colorPrimary);
            t_4.setBackgroundResource(R.color.colorPrimary);
            t_5.setBackgroundResource(R.color.colorPrimary);
            t_6.setBackgroundResource(R.color.colorPrimary);

        } else if (period.size() == 7) {

            if (position < 10) {
                t_8.setBackgroundResource(R.color.color1);
            } else {
                t_8.setBackgroundColor(Color.parseColor("#000000"));
            }

            t_1.setText(period.get(0));
            t_2.setText(period.get(1));
            t_3.setText(period.get(2));
            t_4.setText(period.get(3));
            t_5.setText(period.get(4));
            t_6.setText(period.get(5));
            t_7.setText(period.get(6));
            t_1.setBackgroundResource(R.color.colorPrimary);
            t_2.setBackgroundResource(R.color.colorPrimary);
            t_3.setBackgroundResource(R.color.colorPrimary);
            t_4.setBackgroundResource(R.color.colorPrimary);
            t_5.setBackgroundResource(R.color.colorPrimary);
            t_6.setBackgroundResource(R.color.colorPrimary);
            t_7.setBackgroundResource(R.color.colorPrimary);

        } else if (period.size() == 8) {

            t_1.setText(period.get(0));
            t_2.setText(period.get(1));
            t_3.setText(period.get(2));
            t_4.setText(period.get(3));
            t_5.setText(period.get(4));
            t_6.setText(period.get(5));
            t_7.setText(period.get(6));
            t_8.setText(period.get(7));
            t_1.setBackgroundResource(R.color.colorPrimary);
            t_2.setBackgroundResource(R.color.colorPrimary);
            t_3.setBackgroundResource(R.color.colorPrimary);
            t_4.setBackgroundResource(R.color.colorPrimary);
            t_5.setBackgroundResource(R.color.colorPrimary);
            t_6.setBackgroundResource(R.color.colorPrimary);
            t_7.setBackgroundResource(R.color.colorPrimary);
            t_8.setBackgroundResource(R.color.colorPrimary);

        }
    }

    private void c_1() {
        d_period_monday.clear();
        d_period_tuesday.clear();
        d_period_wednesday.clear();
        d_period_thursday.clear();
        d_period_friday.clear();
        d_period_monday_c.clear();
        d_period_tuesday_c.clear();
        d_period_wednesday_c.clear();
        d_period_thursday_c.clear();
        d_period_friday_c.clear();
    }

    private void c_2() {
        if (
                        TextUtils.isEmpty(textView_1_2.getText().toString()) &&
                        TextUtils.isEmpty(textView_2_2.getText().toString()) &&
                        TextUtils.isEmpty(textView_3_2.getText().toString()) &&
                        TextUtils.isEmpty(textView_4_2.getText().toString()) &&
                        TextUtils.isEmpty(textView_5_2.getText().toString())) {
            tableRow_2.setVisibility(View.GONE);
            tableRow_3.setVisibility(View.GONE);
            tableRow_4.setVisibility(View.GONE);
            tableRow_5.setVisibility(View.GONE);
            tableRow_6.setVisibility(View.GONE);
            tableRow_7.setVisibility(View.GONE);
            tableRow_8.setVisibility(View.GONE);

        } else if (
                        TextUtils.isEmpty(textView_1_3.getText().toString()) &&
                        TextUtils.isEmpty(textView_2_3.getText().toString()) &&
                        TextUtils.isEmpty(textView_3_3.getText().toString()) &&
                        TextUtils.isEmpty(textView_4_3.getText().toString()) &&
                        TextUtils.isEmpty(textView_5_3.getText().toString())) {
            tableRow_3.setVisibility(View.GONE);
            tableRow_4.setVisibility(View.GONE);
            tableRow_5.setVisibility(View.GONE);
            tableRow_6.setVisibility(View.GONE);
            tableRow_7.setVisibility(View.GONE);
            tableRow_8.setVisibility(View.GONE);

        } else if (
                        TextUtils.isEmpty(textView_1_4.getText().toString()) &&
                        TextUtils.isEmpty(textView_2_4.getText().toString()) &&
                        TextUtils.isEmpty(textView_3_4.getText().toString()) &&
                        TextUtils.isEmpty(textView_4_4.getText().toString()) &&
                        TextUtils.isEmpty(textView_5_4.getText().toString())) {
            tableRow_4.setVisibility(View.GONE);
            tableRow_5.setVisibility(View.GONE);
            tableRow_6.setVisibility(View.GONE);
            tableRow_7.setVisibility(View.GONE);
            tableRow_8.setVisibility(View.GONE);

        } else if (
                        TextUtils.isEmpty(textView_1_5.getText().toString()) &&
                        TextUtils.isEmpty(textView_2_5.getText().toString()) &&
                        TextUtils.isEmpty(textView_3_5.getText().toString()) &&
                        TextUtils.isEmpty(textView_4_5.getText().toString()) &&
                        TextUtils.isEmpty(textView_5_5.getText().toString())) {
            tableRow_5.setVisibility(View.GONE);
            tableRow_6.setVisibility(View.GONE);
            tableRow_7.setVisibility(View.GONE);
            tableRow_8.setVisibility(View.GONE);

        } else if (
                        TextUtils.isEmpty(textView_1_6.getText().toString()) &&
                        TextUtils.isEmpty(textView_2_6.getText().toString()) &&
                        TextUtils.isEmpty(textView_3_6.getText().toString()) &&
                        TextUtils.isEmpty(textView_4_6.getText().toString()) &&
                        TextUtils.isEmpty(textView_5_6.getText().toString())) {
            tableRow_6.setVisibility(View.GONE);
            tableRow_7.setVisibility(View.GONE);
            tableRow_8.setVisibility(View.GONE);

        } else if (
                        TextUtils.isEmpty(textView_1_7.getText().toString()) &&
                        TextUtils.isEmpty(textView_2_7.getText().toString()) &&
                        TextUtils.isEmpty(textView_3_7.getText().toString()) &&
                        TextUtils.isEmpty(textView_4_7.getText().toString()) &&
                        TextUtils.isEmpty(textView_5_7.getText().toString())) {
            tableRow_7.setVisibility(View.GONE);
            tableRow_8.setVisibility(View.GONE);
        } else if (
                        TextUtils.isEmpty(textView_1_8.getText().toString()) &&
                        TextUtils.isEmpty(textView_2_8.getText().toString()) &&
                        TextUtils.isEmpty(textView_3_8.getText().toString()) &&
                        TextUtils.isEmpty(textView_4_8.getText().toString()) &&
                        TextUtils.isEmpty(textView_5_8.getText().toString())) {
            tableRow_8.setVisibility(View.GONE);
        } else {
            tableRow_1.setVisibility(View.VISIBLE);
            tableRow_2.setVisibility(View.VISIBLE);
            tableRow_3.setVisibility(View.VISIBLE);
            tableRow_4.setVisibility(View.VISIBLE);
            tableRow_5.setVisibility(View.VISIBLE);
            tableRow_6.setVisibility(View.VISIBLE);
            tableRow_7.setVisibility(View.VISIBLE);
            tableRow_8.setVisibility(View.VISIBLE);
        }
    }

    private void c_3(String date, String perio, String period) {
        if (date.equals(monday)) {

            if (!(d_period_monday_c.contains(perio))) {
                d_period_monday_c.add(perio);
                d_period_monday.add(period);
            }

        } else if (date.equals(tuesday)) {

            if (!(d_period_tuesday_c.contains(perio))) {
                d_period_tuesday_c.add(perio);
                d_period_tuesday.add(period);
            }

        } else if (date.equals(wednesday)) {

            if (!(d_period_wednesday_c.contains(perio))) {
                d_period_wednesday_c.add(perio);
                d_period_wednesday.add(period);
            }

        } else if (date.equals(thursday)) {

            if (!(d_period_thursday_c.contains(perio))) {
                d_period_thursday_c.add(perio);
                d_period_thursday.add(period);
            }

        } else if (date.equals(friday)) {

            if (!(d_period_friday_c.contains(perio))) {
                d_period_friday_c.add(perio);
                d_period_friday.add(period);
            }

        }
    }

    private void c_4(String date, String perio, String period) {
        if (date.equals(monday)) {

            if (!(period_monday_c.contains(perio))) {
                period_monday_c.add(perio);
                period_monday.add(period);
            }

        } else if (date.equals(tuesday)) {

            if (!(period_tuesday_c.contains(perio))) {
                period_tuesday_c.add(perio);
                period_tuesday.add(period);
            }

        } else if (date.equals(wednesday)) {

            if (!(period_wednesday_c.contains(perio))) {
                period_wednesday_c.add(perio);
                period_wednesday.add(period);
            }

        } else if (date.equals(thursday)) {

            if (!(period_thursday_c.contains(perio))) {
                period_thursday_c.add(perio);
                period_thursday.add(period);
            }

        } else if (date.equals(friday)) {

            if (!(period_friday_c.contains(perio))) {
                period_friday_c.add(perio);
                period_friday.add(period);
            }

        }
    }

    private void c_5(
            TextView textView_1_2, TextView textView_1_3, TextView textView_1_4, TextView textView_1_5, TextView textView_1_6, TextView textView_1_7, TextView textView_1_8,
            TextView textView_2_2, TextView textView_2_3, TextView textView_2_4, TextView textView_2_5, TextView textView_2_6, TextView textView_2_7, TextView textView_2_8,
            TextView textView_3_2, TextView textView_3_3, TextView textView_3_4, TextView textView_3_5, TextView textView_3_6, TextView textView_3_7, TextView textView_3_8,
            TextView textView_4_2, TextView textView_4_3, TextView textView_4_4, TextView textView_4_5, TextView textView_4_6, TextView textView_4_7, TextView textView_4_8,
            TextView textView_5_2, TextView textView_5_3, TextView textView_5_4, TextView textView_5_5, TextView textView_5_6, TextView textView_5_7, TextView textView_5_8,

            TableRow tableRow_1, TableRow tableRow_2, TableRow tableRow_3, TableRow tableRow_4,
            TableRow tableRow_5, TableRow tableRow_6, TableRow tableRow_7, TableRow tableRow_8) {
        if (
                        TextUtils.isEmpty(textView_1_2.getText().toString()) &&
                        TextUtils.isEmpty(textView_2_2.getText().toString()) &&
                        TextUtils.isEmpty(textView_3_2.getText().toString()) &&
                        TextUtils.isEmpty(textView_4_2.getText().toString()) &&
                        TextUtils.isEmpty(textView_5_2.getText().toString())) {
            tableRow_1.setVisibility(View.VISIBLE);
            tableRow_2.setVisibility(View.GONE);
            tableRow_3.setVisibility(View.GONE);
            tableRow_4.setVisibility(View.GONE);
            tableRow_5.setVisibility(View.GONE);
            tableRow_6.setVisibility(View.GONE);
            tableRow_7.setVisibility(View.GONE);
            tableRow_8.setVisibility(View.GONE);

        } else if (
                        TextUtils.isEmpty(textView_1_3.getText().toString()) &&
                        TextUtils.isEmpty(textView_2_3.getText().toString()) &&
                        TextUtils.isEmpty(textView_3_3.getText().toString()) &&
                        TextUtils.isEmpty(textView_4_3.getText().toString()) &&
                        TextUtils.isEmpty(textView_5_3.getText().toString())) {
            tableRow_1.setVisibility(View.VISIBLE);
            tableRow_2.setVisibility(View.VISIBLE);
            tableRow_3.setVisibility(View.GONE);
            tableRow_4.setVisibility(View.GONE);
            tableRow_5.setVisibility(View.GONE);
            tableRow_6.setVisibility(View.GONE);
            tableRow_7.setVisibility(View.GONE);
            tableRow_8.setVisibility(View.GONE);

        } else if (
                        TextUtils.isEmpty(textView_1_4.getText().toString()) &&
                        TextUtils.isEmpty(textView_2_4.getText().toString()) &&
                        TextUtils.isEmpty(textView_3_4.getText().toString()) &&
                        TextUtils.isEmpty(textView_4_4.getText().toString()) &&
                        TextUtils.isEmpty(textView_5_4.getText().toString())) {
            tableRow_1.setVisibility(View.VISIBLE);
            tableRow_2.setVisibility(View.VISIBLE);
            tableRow_3.setVisibility(View.VISIBLE);
            tableRow_4.setVisibility(View.GONE);
            tableRow_5.setVisibility(View.GONE);
            tableRow_6.setVisibility(View.GONE);
            tableRow_7.setVisibility(View.GONE);
            tableRow_8.setVisibility(View.GONE);

        } else if (
                        TextUtils.isEmpty(textView_1_5.getText().toString()) &&
                        TextUtils.isEmpty(textView_2_5.getText().toString()) &&
                        TextUtils.isEmpty(textView_3_5.getText().toString()) &&
                        TextUtils.isEmpty(textView_4_5.getText().toString()) &&
                        TextUtils.isEmpty(textView_5_5.getText().toString())) {
            tableRow_1.setVisibility(View.VISIBLE);
            tableRow_2.setVisibility(View.VISIBLE);
            tableRow_3.setVisibility(View.VISIBLE);
            tableRow_4.setVisibility(View.VISIBLE);
            tableRow_5.setVisibility(View.GONE);
            tableRow_6.setVisibility(View.GONE);
            tableRow_7.setVisibility(View.GONE);
            tableRow_8.setVisibility(View.GONE);

        } else if (
                        TextUtils.isEmpty(textView_1_6.getText().toString()) &&
                        TextUtils.isEmpty(textView_2_6.getText().toString()) &&
                        TextUtils.isEmpty(textView_3_6.getText().toString()) &&
                        TextUtils.isEmpty(textView_4_6.getText().toString()) &&
                        TextUtils.isEmpty(textView_5_6.getText().toString())) {
            tableRow_1.setVisibility(View.VISIBLE);
            tableRow_2.setVisibility(View.VISIBLE);
            tableRow_3.setVisibility(View.VISIBLE);
            tableRow_4.setVisibility(View.VISIBLE);
            tableRow_5.setVisibility(View.VISIBLE);
            tableRow_6.setVisibility(View.GONE);
            tableRow_7.setVisibility(View.GONE);
            tableRow_8.setVisibility(View.GONE);

        } else if (
                        TextUtils.isEmpty(textView_1_7.getText().toString()) &&
                        TextUtils.isEmpty(textView_2_7.getText().toString()) &&
                        TextUtils.isEmpty(textView_3_7.getText().toString()) &&
                        TextUtils.isEmpty(textView_4_7.getText().toString()) &&
                        TextUtils.isEmpty(textView_5_7.getText().toString())) {
            tableRow_1.setVisibility(View.VISIBLE);
            tableRow_2.setVisibility(View.VISIBLE);
            tableRow_3.setVisibility(View.VISIBLE);
            tableRow_4.setVisibility(View.VISIBLE);
            tableRow_5.setVisibility(View.VISIBLE);
            tableRow_6.setVisibility(View.VISIBLE);
            tableRow_7.setVisibility(View.GONE);
            tableRow_8.setVisibility(View.GONE);
        } else if (
                        TextUtils.isEmpty(textView_1_8.getText().toString()) &&
                        TextUtils.isEmpty(textView_2_8.getText().toString()) &&
                        TextUtils.isEmpty(textView_3_8.getText().toString()) &&
                        TextUtils.isEmpty(textView_4_8.getText().toString()) &&
                        TextUtils.isEmpty(textView_5_8.getText().toString())) {
            tableRow_1.setVisibility(View.VISIBLE);
            tableRow_2.setVisibility(View.VISIBLE);
            tableRow_3.setVisibility(View.VISIBLE);
            tableRow_4.setVisibility(View.VISIBLE);
            tableRow_5.setVisibility(View.VISIBLE);
            tableRow_6.setVisibility(View.VISIBLE);
            tableRow_7.setVisibility(View.VISIBLE);
            tableRow_8.setVisibility(View.GONE);
        } else {
            tableRow_1.setVisibility(View.VISIBLE);
            tableRow_2.setVisibility(View.VISIBLE);
            tableRow_3.setVisibility(View.VISIBLE);
            tableRow_4.setVisibility(View.VISIBLE);
            tableRow_5.setVisibility(View.VISIBLE);
            tableRow_6.setVisibility(View.VISIBLE);
            tableRow_7.setVisibility(View.VISIBLE);
            tableRow_8.setVisibility(View.VISIBLE);
        }
    }
}
