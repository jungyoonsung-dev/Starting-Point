package com.jungyoonsung.startingpoint.SchoolSettings;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

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
    public void onBindViewHolder(SchoolSettingsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

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

                            SharedPreferences.Editor editor = thisContext.getSharedPreferences("USER", MODE_PRIVATE).edit();

                            editor.putString("Check", "true");

                            editor.putString("School_Name", SCHUL_NM.get(position) + "   ");

                            String t_s_grade = s_grade;
                            String t_s_class = s_class;
                            String t_s_number = s_number;
                            if (t_s_class.length() == 1) {
                                t_s_class = "0" + t_s_class;
                            }
                            if (t_s_number.length() == 1) {
                                t_s_number = "0" + t_s_number;
                            }

                            editor.putString("Grade_Class_Number", "   " + t_s_grade + t_s_class + t_s_number);
                            editor.putString("s_1_ATPT_OFCDC_SC_CODE", ATPT_OFCDC_SC_CODE.get(position));
                            editor.putString("s_3_SD_SCHUL_CODE", SD_SCHUL_CODE.get(position));
                            editor.putString("s_5_SCHUL_KND_SC_NM", SCHUL_KND_SC_NM.get(position));
                            editor.putString("s_6_grade", s_grade);
                            editor.putString("s_7_class", s_class);
                            editor.apply();


                            SharedPreferences sharedPreferencesUSER = thisContext.getSharedPreferences("USER", MODE_PRIVATE);

                            String ATPT_OFCDC_SC_CODE = sharedPreferencesUSER.getString("s_1_ATPT_OFCDC_SC_CODE", "");
                            String SD_SCHUL_CODE = sharedPreferencesUSER.getString("s_3_SD_SCHUL_CODE", "");
                            String SCHUL_KND_SC_NM = sharedPreferencesUSER.getString("s_5_SCHUL_KND_SC_NM", "");

                            SharedPreferences.Editor editor2 = thisContext.getSharedPreferences("Notification", MODE_PRIVATE).edit();
                            editor2.putString("ATPT_OFCDC_SC_CODE", ATPT_OFCDC_SC_CODE);
                            editor2.putString("SD_SCHUL_CODE", SD_SCHUL_CODE);
                            editor2.putString("SCHUL_KND_SC_NM", SCHUL_KND_SC_NM);
                            editor2.putString("s_grade", s_grade);
                            editor2.putString("s_class", s_class);
                            editor2.apply();

                            ((Activity) thisContext).finish();

                            Intent intent = new Intent(thisContext, MainActivity.class);
                            ((Activity) thisContext).startActivity(intent);
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