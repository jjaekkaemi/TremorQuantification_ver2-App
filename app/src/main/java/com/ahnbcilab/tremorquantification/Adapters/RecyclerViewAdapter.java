package com.ahnbcilab.tremorquantification.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.MediaRouteButton;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.preference.TwoStatePreference;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.ahnbcilab.tremorquantification.data.TaskItem;
import com.ahnbcilab.tremorquantification.tremorquantification.LoginActivity;
import com.ahnbcilab.tremorquantification.tremorquantification.PatientItem;
import com.ahnbcilab.tremorquantification.tremorquantification.PatientListActivity;
import com.ahnbcilab.tremorquantification.tremorquantification.PersonalPatient;
import com.ahnbcilab.tremorquantification.tremorquantification.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Activity activity;
    public ArrayList<PatientItem> patientList = new ArrayList<>();
    public ArrayList<PatientItem> selected_patientList = new ArrayList<>();
    Context mContext;

    ActionMode mActionMode;
    Menu context_menu;
    boolean isMultiSelect = false;
    boolean checkboxIsVisible = false ;
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
        CheckBox checkBox ;
        ConstraintLayout cl_listitem;

        public MyViewHolder(View itemView) {
            super(itemView);
            patientType = (TextView) itemView.findViewById(R.id.typeViewItem);
            clinicID = (TextView) itemView.findViewById(R.id.clinicIDItem);
            patientName = (TextView) itemView.findViewById(R.id.patientNameItem);
            dateFirst = (TextView) itemView.findViewById(R.id.dateFirstItem);
            dateFinal = (TextView) itemView.findViewById(R.id.dateFinalItem);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox) ;
            cl_listitem = (ConstraintLayout) itemView.findViewById(R.id.cl_listitem);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_list_item, parent, false);

        final RecyclerViewAdapter.MyViewHolder vHolder = new RecyclerViewAdapter.MyViewHolder(itemView) ;
        vHolder.cl_listitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isMultiSelect) multi_select(vHolder.getAdapterPosition()) ;
                else {
                    //LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    Intent intent = new Intent(mContext, PersonalPatient.class);
                    intent.putExtra("ClinicID", patientList.get(vHolder.getAdapterPosition()).getClinicID());
                    intent.putExtra("PatientName", patientList.get(vHolder.getAdapterPosition()).getPatientName());
                    intent.putExtra("doc_uid",  FirebaseAuth.getInstance().getCurrentUser().getUid());
                    intent.putExtra("task", "none");
                    mContext.startActivity(intent);
                }
            }
        });
        vHolder.cl_listitem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!isMultiSelect) {
                    selected_patientList = new ArrayList<PatientItem>() ;
                    isMultiSelect = true ;
                    checkboxIsVisible=true ;
                    if(mActionMode == null) {
                        mActionMode = ((Activity)mContext).startActionMode(mActionModeCallback) ;
                        //vHolder.checkBox.setVisibility(View.VISIBLE) ;
                    }
                }
                multi_select(vHolder.getAdapterPosition()) ;
                return false;
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PatientItem data = patientList.get(position);
        holder.patientType.setText(data.getPatientType());
        holder.clinicID.setText(data.getClinicID());
        holder.patientName.setText(data.getPatientName());
        holder.dateFirst.setText(data.getDateFirst());
        holder.dateFinal.setText(data.getDateFinal());
        holder.checkBox.setChecked(data.isDeleteBox());
        holder.checkBox.setVisibility(checkboxIsVisible?View.VISIBLE:View.GONE);

//        if (selected_patientList.contains(patientList.get(position)))
//            holder.cl_listitem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.list_item_selected_state));
//        else
//            holder.cl_listitem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.list_item_normal_state));

        if(data.getPatientType() == "P"){
            holder.patientType.setText("");
            holder.patientType.setBackground(ContextCompat.getDrawable(mContext, R.drawable.parkinson));

        }
        else if(data.getPatientType() == "ET"){
            holder.patientType.setText("");
            holder.patientType.setBackground(ContextCompat.getDrawable(mContext, R.drawable.essential_tremor));
        }
        else{
            holder.patientType.setText("ㅡ");
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

    public void multi_select(int position) {
        if (mActionMode != null) {
            if (selected_patientList.contains(patientList.get(position))) {
                selected_patientList.remove(patientList.get(position));
                patientList.get(position).setDeleteBox(false);

            } else{
                selected_patientList.add(patientList.get(position));
                patientList.get(position).setDeleteBox(true);
            }
            if (selected_patientList.size() > 0)
                mActionMode.setTitle("" + selected_patientList.size());
            else
                mActionMode.setTitle("");

            refreshAdapter();

        }
    }

    //어댑터 정비
    public void refreshAdapter() {
        this.selected_patientList = selected_patientList;
        this.patientList = patientList;
        this.notifyDataSetChanged();
    }

    //삭제할 때 모드 변환하는 거
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_multi_select, menu);
            context_menu = menu;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext) ;
                    alertDialog.setTitle("") ;
                    alertDialog.setMessage("삭제 하시겠습니까?") ;
                    alertDialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (selected_patientList.size() > 0) {
                                for (int i = 0; i < selected_patientList.size(); i++) {
                                    patientList.remove(selected_patientList.get(i));
                                    removeList(selected_patientList.get(i).getClinicID()) ;
//                                    Toast.makeText(mContext, personalPatient.Clinic_ID+personalPatient.taskType+String.valueOf(selected_taskList.get(i).getTaskNum()), Toast.LENGTH_SHORT).show();
                                }
                                notifyDataSetChanged();
                                if (mActionMode != null) {
                                    mActionMode.finish();
                                }
                            }
                        }
                    }) ;
                    alertDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }) ;
                    AlertDialog dialog = alertDialog.create() ;
                    dialog.show() ;
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            isMultiSelect = false;
            checkboxIsVisible = false ;
            for(int i = 0 ; i<patientList.size(); i++){
                patientList.get(i).setDeleteBox(false);
            }
            selected_patientList = new ArrayList<PatientItem>();
            refreshAdapter();
        }
    };

    public void removeList(String Clinicid) {
        DatabaseReference ref ;
        ref = FirebaseDatabase.getInstance().getReference() ;
        final Query deleteQuery = ref.child("PatientList").orderByChild("ClinicID").equalTo(Clinicid);
        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot deleteSnapshot : dataSnapshot.getChildren()) {
                    deleteSnapshot.getRef().removeValue();
                }
            }
            @Override

            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}