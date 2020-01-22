package com.jungyoonsung.startingpoint.Tab;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.jungyoonsung.startingpoint.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TabStudy extends Fragment {

    Context thisContext;

    TextView
            t_1, t_2, t_3, t_4, t_5, t_6, t_7,
            t_8, t_9, t_10, t_11, t_12, t_13, t_14,
            t_15, t_16, t_17, t_18, t_19, t_20, t_21,
            t_22, t_23, t_24, t_25, t_26, t_27, t_28,
            t_29, t_30, t_31, t_32, t_33, t_34, t_35,
            t_36, t_37, t_38, t_39, t_40, t_41, t_42;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabstudy, container, false);
        thisContext = container.getContext();

        t_1 = (TextView) view.findViewById(R.id.t_1);
        t_2 = (TextView) view.findViewById(R.id.t_2);
        t_3 = (TextView) view.findViewById(R.id.t_3);
        t_4 = (TextView) view.findViewById(R.id.t_4);
        t_5 = (TextView) view.findViewById(R.id.t_5);
        t_6 = (TextView) view.findViewById(R.id.t_6);
        t_7 = (TextView) view.findViewById(R.id.t_7);
        t_8 = (TextView) view.findViewById(R.id.t_8);
        t_9 = (TextView) view.findViewById(R.id.t_9);
        t_10 = (TextView) view.findViewById(R.id.t_10);
        t_11 = (TextView) view.findViewById(R.id.t_11);
        t_12 = (TextView) view.findViewById(R.id.t_12);
        t_13 = (TextView) view.findViewById(R.id.t_13);
        t_14 = (TextView) view.findViewById(R.id.t_14);
        t_15 = (TextView) view.findViewById(R.id.t_15);
        t_16 = (TextView) view.findViewById(R.id.t_16);
        t_17 = (TextView) view.findViewById(R.id.t_17);
        t_18 = (TextView) view.findViewById(R.id.t_18);
        t_19 = (TextView) view.findViewById(R.id.t_19);
        t_20 = (TextView) view.findViewById(R.id.t_20);
        t_21 = (TextView) view.findViewById(R.id.t_21);
        t_22 = (TextView) view.findViewById(R.id.t_22);
        t_23 = (TextView) view.findViewById(R.id.t_23);
        t_24 = (TextView) view.findViewById(R.id.t_24);
        t_25 = (TextView) view.findViewById(R.id.t_25);
        t_26 = (TextView) view.findViewById(R.id.t_26);
        t_27 = (TextView) view.findViewById(R.id.t_27);
        t_28 = (TextView) view.findViewById(R.id.t_28);
        t_29 = (TextView) view.findViewById(R.id.t_29);
        t_30 = (TextView) view.findViewById(R.id.t_30);
        t_31 = (TextView) view.findViewById(R.id.t_31);
        t_32 = (TextView) view.findViewById(R.id.t_32);
        t_33 = (TextView) view.findViewById(R.id.t_33);
        t_34 = (TextView) view.findViewById(R.id.t_34);
        t_35 = (TextView) view.findViewById(R.id.t_35);
        t_36 = (TextView) view.findViewById(R.id.t_36);
        t_37 = (TextView) view.findViewById(R.id.t_37);
        t_38 = (TextView) view.findViewById(R.id.t_38);
        t_39 = (TextView) view.findViewById(R.id.t_39);
        t_40 = (TextView) view.findViewById(R.id.t_40);
        t_41 = (TextView) view.findViewById(R.id.t_41);
        t_42 = (TextView) view.findViewById(R.id.t_42);

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DATE, 1);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        if (week == 1) {
        }
        List<String> Date = new ArrayList<>();
        for (int i = 1; i <= calendar.getMaximum(Calendar.DAY_OF_MONTH); i++) {
            Date.add(String.valueOf(i));
        }
        Log.d("TEST", Date.toString());
//
//        Date currentTime = calendar.getTime();
//        SimpleDateFormat weekdayFormat = new SimpleDateFormat("EE", Locale.getDefault());
//        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
//        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
//        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
//
//        String weekDay = weekdayFormat.format(currentTime);
//        String year = yearFormat.format(currentTime);
//        String month = monthFormat.format(currentTime);
//        String day = dayFormat.format(currentTime);
//
//        Log.d("webnautes", year + "년 " + month + "월 " + day + "일 " + weekDay + "요일");

        Log.d("TEST", String.valueOf(calendar.getMinimum(Calendar.DAY_OF_MONTH)));
        Log.d("TEST", String.valueOf(calendar.getMaximum(Calendar.DAY_OF_MONTH)));
        return view;
    }
}
