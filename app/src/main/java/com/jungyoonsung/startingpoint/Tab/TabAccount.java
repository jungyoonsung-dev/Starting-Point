package com.jungyoonsung.startingpoint.Tab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jungyoonsung.startingpoint.MainActivity;
import com.jungyoonsung.startingpoint.R;
import com.jungyoonsung.startingpoint.SchoolSettings.SchoolSettings;

import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;

public class TabAccount extends Fragment {

    Context thisContext;

    CardView tabaccount_cardView;

    TextView t_open_source_license;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabaccount, container, false);
        thisContext = container.getContext();

        tabaccount_cardView = (CardView) view.findViewById(R.id.tabaccount_cardView);

        t_open_source_license = (TextView) view.findViewById(R.id.t_open_source_license);

        TextView t_edit = (TextView) view.findViewById(R.id.t_edit);
        t_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((Activity) thisContext).finish();
                Intent intent = new Intent(thisContext, SchoolSettings.class);
                startActivity(intent);
            }
        });

        TextView t_background = (TextView) view.findViewById(R.id.t_background);
        t_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ColorPicker colorPicker = new ColorPicker((Activity) thisContext);
                ArrayList<String> colors = new ArrayList<>();

                colors.add("#000000");
                colors.add("#111111");
                colors.add("#222222");
                colors.add("#333333");
                colors.add("#444444");
                colors.add("#555555");
                colors.add("#666666");
                colors.add("#777777");
                colors.add("#888888");
                colors.add("#999999");
                colors.add("#AAAAAA");
                colors.add("#BBBBBB");
                colors.add("#CCCCCC");
                colors.add("#EEEEEE");
                colors.add("#FFFFFF");


                colorPicker.setColors(colors)
                        .setTitle("배경색")
                        .setColumns(5)
                        .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                            @Override
                            public void onChooseColor(int position, int color) {
                                SharedPreferences.Editor editor = thisContext.getSharedPreferences("Background", thisContext.MODE_PRIVATE).edit();
                                editor.putInt("Position", position);
                                editor.putInt("Color", color);
                                editor.apply();

                                LinearLayout mainLineraLayout = (LinearLayout) MainActivity.activity_main_LinearLayout;
                                mainLineraLayout.setBackgroundColor(color);

                                TextView textView_school = (TextView) MainActivity.textView_school;
                                TextView textView_grade_class_number = (TextView) MainActivity.textView_grade_class_number;

                                BottomNavigationView navView_1 = (BottomNavigationView) MainActivity.navView_1;
                                BottomNavigationView navView_2 = (BottomNavigationView) MainActivity.navView_2;

                                MainActivity mainActivity = (MainActivity) MainActivity.MainContext;

                                if (position < 10) {
                                    textView_school.setTextColor(Color.parseColor("#EEEEEE"));

                                    textView_grade_class_number.setTextColor(Color.parseColor("#EEEEEE"));

                                    navView_1.setVisibility(View.VISIBLE);
                                    navView_2.setVisibility(View.GONE);

                                    NavController navController = Navigation.findNavController(mainActivity, R.id.nav_host_fragment);
                                    NavigationUI.setupWithNavController(navView_1, navController);
                                } else {
                                    textView_school.setTextColor(Color.parseColor("#111111"));
                                    textView_grade_class_number.setTextColor(Color.parseColor("#111111"));

                                    navView_1.setVisibility(View.GONE);
                                    navView_2.setVisibility(View.VISIBLE);

                                    NavController navController = Navigation.findNavController(mainActivity, R.id.nav_host_fragment);
                                    NavigationUI.setupWithNavController(navView_2, navController);
                                }
                            }

                            @Override
                            public void onCancel() {
                            }
                        }).show();

            }
        });

        t_open_source_license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/jungyoonsung-dev/StartingPoint#readme"));
                startActivity(intent);
            }
        });

        return view;
    }
}