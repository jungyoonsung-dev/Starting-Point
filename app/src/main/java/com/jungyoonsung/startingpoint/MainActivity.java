package com.jungyoonsung.startingpoint;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jungyoonsung.startingpoint.SchoolSettings.SchoolSettings;

public class MainActivity extends AppCompatActivity {

    public static Context MainContext;

    public static LinearLayout activity_main_LinearLayout;
    public static TextView textView_school, textView_grade_class_number;

    public static BottomNavigationView navView_1, navView_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainContext = MainActivity.this;

        activity_main_LinearLayout = (LinearLayout) findViewById(R.id.activity_main_LinearLayout);

        textView_school = (TextView) findViewById(R.id.textView_school);
        textView_grade_class_number = (TextView) findViewById(R.id.textView_grade_class_number);

        navView_1 = (BottomNavigationView) findViewById(R.id.nav_view_1);
        navView_2 = (BottomNavigationView) findViewById(R.id.nav_view_2);

        navView_1.setVisibility(View.VISIBLE);
        navView_2.setVisibility(View.GONE);

        SharedPreferences sharedPreferencesUSER = getSharedPreferences("USER", MODE_PRIVATE);
        String check = sharedPreferencesUSER.getString("Check", "");

        if (!(TextUtils.isEmpty(check))) {

            String s_school_name = sharedPreferencesUSER.getString("School_Name", "");
            String s_grade_class_number = sharedPreferencesUSER.getString("Grade_Class_Number", "");

            textView_school.setText(s_school_name);
            textView_grade_class_number.setText(s_grade_class_number);

            SharedPreferences sharedPreferencesBackground = getSharedPreferences("Background", MODE_PRIVATE);
            int position = sharedPreferencesBackground.getInt("Position", 0);
            int color = sharedPreferencesBackground.getInt("Color", 0);

            if (color == 0) {
                activity_main_LinearLayout.setBackgroundResource(android.R.color.black);

                textView_school.setTextColor(Color.parseColor("#EEEEEE"));
                textView_grade_class_number.setTextColor(Color.parseColor("#EEEEEE"));

                navView_1.setVisibility(View.VISIBLE);
                navView_2.setVisibility(View.GONE);

                NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
                NavigationUI.setupWithNavController(navView_1, navController);
            } else if (color != 0) {
                activity_main_LinearLayout.setBackgroundColor(color);

                if (position < 10) {
                    textView_school.setTextColor(Color.parseColor("#EEEEEE"));
                    textView_grade_class_number.setTextColor(Color.parseColor("#EEEEEE"));

                    navView_1.setVisibility(View.VISIBLE);
                    navView_2.setVisibility(View.GONE);

                    NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
                    NavigationUI.setupWithNavController(navView_1, navController);
                } else {
                    textView_school.setTextColor(Color.parseColor("#111111"));
                    textView_grade_class_number.setTextColor(Color.parseColor("#111111"));

                    navView_1.setVisibility(View.GONE);
                    navView_2.setVisibility(View.VISIBLE);

                    NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
                    NavigationUI.setupWithNavController(navView_2, navController);
                }
            }

        } else {
            startActivity(new Intent(MainActivity.this, SchoolSettings.class));
            finish();
        }
    }
}