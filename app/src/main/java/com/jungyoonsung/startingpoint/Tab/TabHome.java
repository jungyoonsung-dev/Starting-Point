package com.jungyoonsung.startingpoint.Tab;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.jungyoonsung.startingpoint.Fragment.Fragment_Academic_Calendar;
import com.jungyoonsung.startingpoint.Fragment.Fragment_Lunch;
import com.jungyoonsung.startingpoint.Fragment.Fragment_Schedule;
import com.jungyoonsung.startingpoint.R;

import me.relex.circleindicator.CircleIndicator;

public class TabHome extends Fragment {

    Context thisContext;

    private ViewPager viewPager;
    private CircleIndicator circleIndicator_1, circleIndicator_2;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabhome, container, false);
        thisContext = container.getContext();

        viewPager = view.findViewById(R.id.view_pager);
        circleIndicator_1 = view.findViewById(R.id.circle_1);
        circleIndicator_2 = view.findViewById(R.id.circle_2);

        circleIndicator_1.setVisibility(View.VISIBLE);
        circleIndicator_2.setVisibility(View.GONE);

        viewPager.setAdapter(new pagerAdapter(((FragmentActivity)thisContext).getSupportFragmentManager()));
        viewPager.setCurrentItem(1);

        circleIndicator_1.setViewPager(viewPager);

        SharedPreferences sharedPreferencesBackground = thisContext.getSharedPreferences("Background", thisContext.MODE_PRIVATE);
        int position = sharedPreferencesBackground.getInt("Position", 0);

        if (position < 10) {
            circleIndicator_1.setViewPager(viewPager);

            circleIndicator_1.setVisibility(View.VISIBLE);
            circleIndicator_2.setVisibility(View.GONE);
        } else {
            circleIndicator_2.setViewPager(viewPager);

            circleIndicator_1.setVisibility(View.GONE);
            circleIndicator_2.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private class pagerAdapter extends FragmentStatePagerAdapter {
        public pagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new Fragment_Lunch();
                case 1:
                    return new Fragment_Schedule();
                case 2:
                    return new Fragment_Academic_Calendar();
                default:
                    return null;
            }
        }
        @Override
        public int getCount()
        {
            return 3;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
