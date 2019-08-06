package com.ahnbcilab.tremorquantification.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.ahnbcilab.tremorquantification.tremorquantification.LoginActivity;
import com.ahnbcilab.tremorquantification.tremorquantification.PatientItem;
import com.ahnbcilab.tremorquantification.tremorquantification.PatientListActivity;
import com.ahnbcilab.tremorquantification.tremorquantification.PersonalPatient;
import com.ahnbcilab.tremorquantification.tremorquantification.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Activity activity;
    public ArrayList<PatientItem> patientList = new ArrayList<>();
    public ArrayList<PatientItem> selected_patientList = new ArrayList<>();
    Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<PatientItem> patientList, ArrayList<PatientItem> selected_patientList) {
        this.mContext = context;
        this.patientList = patientList;
        this.selected_patientList = selected_patientList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView patientType;
        TextView clinicID;
        TextView patientName;
        TextView dateFirst;
        TextView dateFinal;
        ConstraintLayout cl_listitem;

        public MyViewHolder(View itemView) {
            super(itemView);
            patientType = (TextView) itemView.findViewById(R.id.typeViewItem);
            clinicID = (TextView) itemView.findViewById(R.id.clinicIDItem);
            patientName = (TextView) itemView.findViewById(R.id.patientNameItem);
            dateFirst = (TextView) itemView.findViewById(R.id.dateFirstItem);
            dateFinal = (TextView) itemView.findViewById(R.id.dateFinalItem);
            cl_listitem = (ConstraintLayout) itemView.findViewById(R.id.cl_listitem);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PatientItem data = patientList.get(position);
        holder.patientType.setText(data.getPatientType());
        holder.clinicID.setText(data.getClinicID());
        holder.patientName.setText(data.getPatientName());
        holder.dateFirst.setText(data.getDateFirst());
        holder.dateFinal.setText(data.getDateFinal());

        if (selected_patientList.contains(patientList.get(position)))
            holder.cl_listitem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.list_item_selected_state));
        else
            holder.cl_listitem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.list_item_normal_state));

        if(data.getPatientType() == "P"){
            holder.patientType.setText("");
            holder.patientType.setBackground(ContextCompat.getDrawable(mContext, R.drawable.parkinson));

        }
        else if(data.getPatientType() == "ET"){
            holder.patientType.setText("");
            holder.patientType.setBackground(ContextCompat.getDrawable(mContext, R.drawable.essential_tremor));
        }
        else{

        }
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public void clear() {
        int size = patientList.size() ;
        patientList.clear() ;
        notifyItemRangeRemoved(0, size);
    }
}
