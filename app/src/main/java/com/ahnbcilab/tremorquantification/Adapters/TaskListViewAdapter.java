package com.ahnbcilab.tremorquantification.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ahnbcilab.tremorquantification.data.TaskItem;
import com.ahnbcilab.tremorquantification.tremorquantification.PatientItem;
import com.ahnbcilab.tremorquantification.tremorquantification.R;

import java.util.ArrayList;

public class TaskListViewAdapter extends RecyclerView.Adapter<TaskListViewAdapter.MyViewHolder> {
    public ArrayList<TaskItem> taskList = new ArrayList<>();
    Context mContext;

    public TaskListViewAdapter(Context context, ArrayList<TaskItem> taskList) {
        this.mContext = context;
        this.taskList = taskList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView taskNum;
        TextView taskDate;
        TextView taskTime;
        Button taskEdit;
        ConstraintLayout ta_listitem;

        public MyViewHolder(View itemView) {
            super(itemView);
            taskNum = (TextView) itemView.findViewById(R.id.taskNum);
            taskDate = (TextView) itemView.findViewById(R.id.taskDate);
            taskTime = (TextView) itemView.findViewById(R.id.taskTime);
            taskEdit = (Button) itemView.findViewById(R.id.task_edit);
            ta_listitem = (ConstraintLayout) itemView.findViewById(R.id.ta_listitem);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TaskItem data = taskList.get(position);
        holder.taskNum.setText(data.getTaskNum());
        holder.taskDate.setText(data.getTaskDate());
        holder.taskTime.setText(data.getTaskTime());

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
}
