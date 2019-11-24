package com.jungyoonsung.startingpoint.SchoolSettings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.jungyoonsung.startingpoint.R;

import java.util.List;

public class SchoolSettingsAdapter extends RecyclerView.Adapter<SchoolSettingsAdapter.ViewHolder> {

    private List<String> ATPT_OFCDC_SC_CODE;
    private List<String> ATPT_OFCDC_SC_NM;

    private List<String> SD_SCHUL_CODE;
    private List<String> SCHUL_NM;

    Context thisContext;

    SchoolSettingsAdapter(List<String> ATPT_OFCDC_SC_CODE_list, List<String> ATPT_OFCDC_SC_NM_list, List<String> SD_SCHUL_CODE_list, List<String> SCHUL_NM_list) {
        ATPT_OFCDC_SC_CODE = ATPT_OFCDC_SC_CODE_list;
        ATPT_OFCDC_SC_NM = ATPT_OFCDC_SC_NM_list;

        SD_SCHUL_CODE = SD_SCHUL_CODE_list;
        SCHUL_NM = SCHUL_NM_list;
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
        holder.textView_SCHUL_NM.setText(SCHUL_NM.get(position));
        holder.textView_ATPT_OFCDC_SC_NM.setText(ATPT_OFCDC_SC_NM.get(position));

        holder.cardView_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TEST", ATPT_OFCDC_SC_NM.get(position));
                Log.d("TEST", ATPT_OFCDC_SC_CODE.get(position));
                Log.d("TEST", SCHUL_NM.get(position));
                Log.d("TEST", SD_SCHUL_CODE.get(position));

                Dialog dialog = new Dialog(thisContext);
                dialog.setContentView(R.layout.dialog_schoolsettings);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                final EditText editText_grade = (EditText) dialog.findViewById(R.id.dialog_schoolsettings_edittext_grade);
                editText_grade.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("TEST", editText_grade.getText().toString());
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