package com.jungyoonsung.startingpoint.Tab;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.jungyoonsung.startingpoint.R;

public class TabSchool extends Fragment {

    Context thisContext;

    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabschool, container, false);
        thisContext = container.getContext();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();



        return view;
    }
}
