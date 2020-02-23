package com.jungyoonsung.startingpoint.Fragment_Download_Share;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.jungyoonsung.startingpoint.R;

public class Fragment_Downalod_Image extends Fragment {

    Context thisContext;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabschool, container, false);
        thisContext = container.getContext();


        return view;
    }
}
