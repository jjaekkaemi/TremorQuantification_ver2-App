package com.ahnbcilab.tremorquantification.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ahnbcilab.tremorquantification.data.TaskItem;
import com.ahnbcilab.tremorquantification.tremorquantification.PatientItem;
import com.ahnbcilab.tremorquantification.tremorquantification.PersonalPatient;
import com.ahnbcilab.tremorquantification.tremorquantification.Personal_UPDRS;
import com.ahnbcilab.tremorquantification.tremorquantification.R;
import com.ahnbcilab.tremorquantification.tremorquantification.UPDRS_Fragment;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TaskListViewAdapter extends RecyclerView.Adapter<TaskListViewAdapter.MyViewHolder> implements AlertDialogHelper.AlertDialogListener {
    public ArrayList<TaskItem> taskList = new ArrayList<>();
    public ArrayList<TaskItem> selected_taskList = new ArrayList<>() ;
    public PersonalPatient personalPatient = new PersonalPatient() ;
    ActionMode mActionMode;
    Menu context_menu;
    boolean isMultiSelect = false;
    AlertDialogHelper alertDialogHelper;
    Context mContext;

    public TaskListViewAdapter(Context context, ArrayList<TaskItem> taskList, ArrayList<TaskItem> selected_taskList) {
        this.mContext = context;
        this.taskList = taskList;
        this.selected_taskList = selected_taskList ;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView taskNum;
        TextView taskDate;
        TextView taskTime;
        TextView taskScore;
        TextView totalScore;
        ConstraintLayout ta_listitem;

        public MyViewHolder(View itemView) {
            super(itemView);
            taskNum = (TextView) itemView.findViewById(R.id.taskNum);
            taskDate = (TextView) itemView.findViewById(R.id.taskDate);
            taskTime = (TextView) itemView.findViewById(R.id.taskTime);
            taskScore = (TextView) itemView.findViewById(R.id.taskscore);
            totalScore = (TextView) itemView.findViewById(R.id.totalScore);
            ta_listitem = (ConstraintLayout) itemView.findViewById(R.id.ta_listitem);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.task_list_item, parent, false);
        final MyViewHolder vHolder = new MyViewHolder(itemView) ;
        vHolder.ta_listitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isMultiSelect) multi_select(vHolder.getAdapterPosition()) ;
                else {
                    //intent
                }
            }
        });
        vHolder.ta_listitem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!isMultiSelect) {
                    selected_taskList = new ArrayList<TaskItem>() ;
                    isMultiSelect = true ;

                    if(mActionMode == null) {
                        mActionMode = ((Activity)mContext).startActionMode(mActionModeCallback) ;
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
        TaskItem data = taskList.get(position);
        holder.taskNum.setText(data.getTaskNum());
        holder.taskDate.setText(data.getTaskDate());
        holder.taskTime.setText(data.getTaskTime());
        holder.taskScore.setText(data.getTaskScore());
        holder.totalScore.setText(data.getTotalScore());

        if (selected_taskList.contains(taskList.get(position)))
            holder.ta_listitem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.list_item_selected_state));
        else
            holder.ta_listitem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.list_item_normal_state));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void clear() {
        int size = taskList.size() ;
        taskList.clear() ;
        notifyItemRangeRemoved(0, size);
    }

    public void multi_select(int position) {
        if (mActionMode != null) {
            if (selected_taskList.contains(taskList.get(position))) {
                selected_taskList.remove(taskList.get(position));

            } else
                selected_taskList.add(taskList.get(position));

            if (selected_taskList.size() > 0)
                mActionMode.setTitle("" + selected_taskList.size());
            else
                mActionMode.setTitle("");

            refreshAdapter();

        }
    }

    //어댑터 정비
    public void refreshAdapter() {
        this.selected_taskList = selected_taskList;
        this.taskList = taskList;
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
                            if (selected_taskList.size() > 0) {
                                for (int i = 0; i < selected_taskList.size(); i++) {
                                    int position = Integer.parseInt(selected_taskList.get(i).getTaskNum()) ;
                                    position-- ;
                                    taskList.remove(selected_taskList.get(i));
                                    removeList(position) ;
                                    //Toast.makeText(mContext, personalPatient.Clinic_ID+personalPatient.taskType+String.valueOf(selected_taskList.get(i).getTaskNum()), Toast.LENGTH_SHORT).show();
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
            selected_taskList = new ArrayList<TaskItem>();
            refreshAdapter();
        }
    };

    @Override
    public void onPositiveClick(int from) {
        if (from == 1) {

        } else if (from == 2) {

        }
    }

    @Override
    public void onNegativeClick(int from) {
    }

    @Override
    public void onNeutralClick(int from) {
    }
    public void removeList(int position) {
        String count = "" ;
        if(personalPatient.taskType.equals("CRTS List")) {
            count = "CRTS_count" ;
        }
        else if(personalPatient.taskType.equals("UPDRS List")) {
            count = "UPDRS_count" ;
        }
        else if(personalPatient.taskType.equals("Spiral List")) {
            count = "SPIRAL_count" ;
        }
        DatabaseReference ref ;
        ref = FirebaseDatabase.getInstance().getReference() ;
        final Query deleteQuery = ref.child("PatientList").child(personalPatient.Clinic_ID).child(personalPatient.taskType).orderByChild(count).equalTo(position);

        final int finalPosition1 = position;
        final DatabaseReference finalRef1 = ref;
        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot deleteSnapshot : dataSnapshot.getChildren()) {
                    deleteSnapshot.getRef().removeValue();
                    TaskNo(finalPosition1) ;
                }
            }
            @Override

            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void ListTaskNo(final int position, DataSnapshot deleteSnapshot, final DatabaseReference finalRef1) {
        String count = "" ;
        if(personalPatient.taskType.equals("CRTS List")) {
            count = "CRTS_count" ;
        }
        else if(personalPatient.taskType.equals("UPDRS List")) {
            count = "UPDRS_count" ;
        }
        else if(personalPatient.taskType.equals("Spiral List")) {
            count = "SPIRAL_count" ;
        }
        String Num = String.valueOf(deleteSnapshot.child(personalPatient.taskType).getChildrenCount()) ;
        int nextPosition = position+1 ;
        if(position<Integer.parseInt(Num)) {
            for(int i = nextPosition ; i<=Integer.parseInt(Num) ; i++){
                final int ii = i -1 ;
                Query ref = finalRef1.child("PatientList").child(personalPatient.Clinic_ID).child(personalPatient.taskType).orderByChild(count).equalTo(i) ;
                final String finalCount = count;
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot deleteSnapshot : dataSnapshot.getChildren()) {
                            String key = deleteSnapshot.getKey() ;
                            finalRef1.child("PatientList").child(personalPatient.Clinic_ID).child(personalPatient.taskType).child(key).child(finalCount).setValue(ii) ;
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        }
        else {

        }
    }

    public void TaskNo(final int position) {
        final DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference();
        final Query deleteQuery = ref.child("PatientList").orderByChild("ClinicID").equalTo(personalPatient.Clinic_ID);
        final DatabaseReference finalRef1 = ref;

        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot deleteSnapshot : dataSnapshot.getChildren()) {
                    String CRTSNum = String.valueOf(deleteSnapshot.child("CRTS List").getChildrenCount());
                    String LineNum = String.valueOf(deleteSnapshot.child("Line List").getChildrenCount());
                    String SpiralNum = String.valueOf(deleteSnapshot.child("Spiral List").getChildrenCount());
                    String UPDRSNum = String.valueOf(deleteSnapshot.child("UPDRS List").getChildrenCount());

                    int num = Integer.parseInt(CRTSNum) + Integer.parseInt(LineNum) + Integer.parseInt(SpiralNum) + Integer.parseInt(UPDRSNum);
                    //Toast.makeText(mContext, personalPatient.taskType+num, Toast.LENGTH_SHORT).show();
                    finalRef1.child("PatientList").child(personalPatient.Clinic_ID).child("TaskNo").setValue(num);

                    ListTaskNo(position, deleteSnapshot, ref);
                }
            }

            @Override

            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
