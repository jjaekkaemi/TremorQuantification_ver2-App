package com.ahnbcilab.tremorquantification.tremorquantification;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ahnbcilab.tremorquantification.Adapters.AlertDialogHelper;
import com.ahnbcilab.tremorquantification.Adapters.TaskListViewAdapter;
import com.ahnbcilab.tremorquantification.data.TaskItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class LineTask_Fragment extends Fragment {

    Context context ;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database_line;
    DatabaseReference database_patient;
    AlertDialogHelper alertDialogHelper;

    ActionMode mActionMode;
    Menu context_menu;
    boolean isMultiSelect = false;

    String Clinic_ID;
    String PatientName;
    String path;
    View view;
    File file;
    String m;

    String timestamp;
    String hz_score, magnitude_score, distance_score, time_score, velocity_score;

    RecyclerView recyclerView;
    TaskListViewAdapter taskListViewAdapter;
    ArrayList<TaskItem> tasks = new ArrayList<TaskItem>();
    ArrayList<TaskItem> selected_tasks = new ArrayList<>() ;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {

        // 이전 Activity value 받아오기
        if(getArguments() != null){
            Clinic_ID = getArguments().getString("Clinic_ID");
            PatientName = getArguments().getString("PatientName");
            path = getArguments().getString("path");
        }


        // 초기 화면
        view = inflater.inflate(R.layout.non_task_fragment, container, false);
        Button add_task = (Button) view.findViewById(R.id.add_task);

        // Line task 추가
        add_task.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), Line.class);
                intent.putExtra("Clinic_ID",Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("path", "main");
                startActivity(intent);
            }
        });


        // 환자 별 Line_task 개수 file 저장
        file = new File(view.getContext().getFilesDir(), Clinic_ID + "LINE_task_num.txt");
        //writeToFile("0", view.getContext());


        //환자 별 Line_task 개수 database에서 받아오기
        database_patient = firebaseDatabase.getReference("PatientList");
        database_line = database_patient.child(Clinic_ID).child("Line List");
        database_line.addValueEventListener(new ValueEventListener() {
            int temp_count = 0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    temp_count = (int) dataSnapshot.getChildrenCount();
                    writeToFile(String.valueOf(temp_count), view.getContext());
                    //writeToFile(String.valueOf("0"), view.getContext());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // 환자별 Line_task의 개수가 1개 이상이면 view 바꾸기
        if(file.exists()){
            m = readFromFile(view.getContext());
            Log.v("aaaaa", m);
            if(Integer.parseInt(m) > 0){
                view = inflater.inflate(R.layout.task_line_fragment, container, false);

                recyclerView = (RecyclerView) view.findViewById(R.id.personal_line_taskList);
                taskListViewAdapter = new TaskListViewAdapter(getActivity(), tasks, selected_tasks);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(taskListViewAdapter);

                database_patient.orderByChild("ClinicID").equalTo(Clinic_ID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int count = 1;
                        GraphView graphView = (GraphView) view.findViewById(R.id.line_graph);
                        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                        series.appendData(new DataPoint(0,0), true, 100);
                        taskListViewAdapter.clear();
                        for (DataSnapshot mData : dataSnapshot.getChildren()){
                            String msg = mData.child("Line List").getValue().toString();
                            String[] array = msg.split(", |\\}");
                            for(int i=0; i<array.length; i++) {
                                if (array[i].contains("timestamp")) {
                                    timestamp = array[i].substring(array[i].indexOf("=") + 1);
                                    String taskDate = timestamp.substring(0, timestamp.indexOf(" "));
                                    String taskTime = timestamp.substring(timestamp.indexOf(" ") + 1);
                                    tasks.add(new TaskItem(String.valueOf(count), taskDate, taskTime, "12", "/ 100"));
                                    taskListViewAdapter.notifyDataSetChanged();
                                    count++;
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Button add_line = (Button) view.findViewById(R.id.line_add);

                add_line.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(view.getContext(), Line.class);
                        intent.putExtra("Clinic_ID",Clinic_ID);
                        intent.putExtra("PatientName", PatientName);
                        intent.putExtra("path", "main");
                        startActivity(intent);
                    }
                });

            }
        }

        return view;

    }


    // write File
    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(Clinic_ID + "LINE_task_num.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }



    // read File
    private String readFromFile(Context context) {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput(Clinic_ID + "LINE_task_num.txt");
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }


}
