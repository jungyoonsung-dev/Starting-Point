package com.jungyoonsung.startingpoint.Tab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.jungyoonsung.startingpoint.R;
import com.jungyoonsung.startingpoint.Major.TestActivity;

public class TabTest extends Fragment {

    Context thisContext;

    LinearLayout cardview;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabtest, container, false);
        thisContext = container.getContext();

        cardview = (LinearLayout) view.findViewById(R.id.cardview);
        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thisContext, TestActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
