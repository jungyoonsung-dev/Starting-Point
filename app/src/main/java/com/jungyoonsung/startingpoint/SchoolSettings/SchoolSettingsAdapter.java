package com.jungyoonsung.startingpoint.SchoolSettings;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.jungyoonsung.startingpoint.MainActivity;
import com.jungyoonsung.startingpoint.R;

import java.util.List;

public class SchoolSettingsAdapter extends RecyclerView.Adapter<SchoolSettingsAdapter.ViewHolder> {

    private List<String> ATPT_OFCDC_SC_CODE;
    private List<String> ATPT_OFCDC_SC_NM;

    private List<String> SD_SCHUL_CODE;
    private List<String> SCHUL_NM;

    private List<String> SCHUL_KND_SC_NM;

    Context thisContext;

    FirebaseAuth auth;
    FirebaseDatabase database;

    SchoolSettingsAdapter(List<String> ATPT_OFCDC_SC_CODE_list, List<String> ATPT_OFCDC_SC_NM_list, List<String> SD_SCHUL_CODE_list, List<String> SCHUL_NM_list, List<String> SCHUL_KND_SC_NM_list) {
        ATPT_OFCDC_SC_CODE = ATPT_OFCDC_SC_CODE_list;
        ATPT_OFCDC_SC_NM = ATPT_OFCDC_SC_NM_list;

        SD_SCHUL_CODE = SD_SCHUL_CODE_list;
        SCHUL_NM = SCHUL_NM_list;

        SCHUL_KND_SC_NM = SCHUL_KND_SC_NM_list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout cardView_linearlayout;
        TextView textView_SCHUL_NM, textView_ATPT_OFCDC_SC_NM;

        ViewHolder(View itemView) {
            super(itemView);

            cardView_linearlayout = (LinearLayout) itemView.findViewById(R.id.cardView_linearlayout);

            textView_SCHUL_NM = (TextView) itemView.findViewById(R.id.SCHUL_NM);
            textView_ATPT_OFCDC_SC_NM = (TextView) itemView.findViewById(R.id.ATPT_OFCDC_SC_NM);
        }
    }

    @Override
    public SchoolSettingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        thisContext = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) thisContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_schoolsettings, parent, false);
        SchoolSettingsAdapter.ViewHolder vh = new SchoolSettingsAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(SchoolSettingsAdapter.ViewHolder holder, final int position) {

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        holder.textView_SCHUL_NM.setText(SCHUL_NM.get(position));
        holder.textView_ATPT_OFCDC_SC_NM.setText(ATPT_OFCDC_SC_NM.get(position));

        holder.cardView_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(thisContext);
                dialog.setContentView(R.layout.dialog_schoolsettings);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                final EditText editText_grade = (EditText) dialog.findViewById(R.id.dialog_schoolsettings_edittext_grade);
                final EditText editText_class = (EditText) dialog.findViewById(R.id.dialog_schoolsettings_edittext_class);
                final EditText editText_number = (EditText) dialog.findViewById(R.id.dialog_schoolsettings_edittext_number);

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        String s_grade = editText_grade.getText().toString();
                        String s_class = editText_class.getText().toString();
                        String s_number = editText_number.getText().toString();

                        if (!(TextUtils.isEmpty(s_grade)) && !(TextUtils.isEmpty(s_class)) && !(TextUtils.isEmpty(s_number))) {

                            SCHOOLSETTINGSMODEL schoolsettingsmodel = new SCHOOLSETTINGSMODEL();
                            schoolsettingsmodel.s_1_ATPT_OFCDC_SC_CODE = ATPT_OFCDC_SC_CODE.get(position);
                            schoolsettingsmodel.s_2_ATPT_OFCDC_SC_NM = ATPT_OFCDC_SC_NM.get(position);
                            schoolsettingsmodel.s_3_SD_SCHUL_CODE = SD_SCHUL_CODE.get(position);
                            schoolsettingsmodel.s_4_SCHUL_NM = SCHUL_NM.get(position);
                            schoolsettingsmodel.s_5_SCHUL_KND_SC_NM = SCHUL_KND_SC_NM.get(position);
                            schoolsettingsmodel.s_6_grade = s_grade;
                            schoolsettingsmodel.s_7_class = s_class;
                            schoolsettingsmodel.s_8_number = s_number;

                            database.getReference().child("Profile").child(auth.getCurrentUser().getUid()).setValue(schoolsettingsmodel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    ((Activity) thisContext).finish();

                                    Intent intent = new Intent(thisContext, MainActivity.class);
                                    ((Activity) thisContext).startActivity(intent);

                                }
                            });
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return SCHUL_NM.size() ;
    }
}