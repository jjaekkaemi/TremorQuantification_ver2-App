package com.ahnbcilab.tremorquantification.tremorquantification;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SpiralTask_Fragment extends Fragment {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database_spiral;
    String Clinic_ID;
    String uid;
    String PatientName;
    String path;
    View view;
    File file;
    String m;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {

        // 이전 Activity value 받아오기
        if(getArguments() != null){
            Clinic_ID = getArguments().getString("Clinic_ID");
            PatientName = getArguments().getString("PatientName");
            uid = getArguments().getString("doc_uid");
            path = getArguments().getString("path");
        }


        // 초기 화면
        view = inflater.inflate(R.layout.non_task_fragment, container, false);
        Button add_task = (Button) view.findViewById(R.id.add_task);

        // Spiral task 추가
        add_task.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), SpiralTestActivity.class);
                intent.putExtra("Clinic_ID",Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("doc_uid", uid);
                intent.putExtra("path", "main");
                startActivity(intent);
            }
        });


        // 환자 별 Spiral_task 개수 file 저장
        file = new File(view.getContext().getFilesDir(), Clinic_ID + "SPIRAL_task_num.txt");
        //writeToFile("0", view.getContext());


        //환자 별 Spiral_task 개수 database에서 받아오기
        database_spiral = firebaseDatabase.getReference("PatientList").child(Clinic_ID).child("Spiral List");
        database_spiral.addValueEventListener(new ValueEventListener() {
            int temp_count = 0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    temp_count = (int) dataSnapshot.getChildrenCount();
                    Log.v("hello!", "" + temp_count);
                    writeToFile(String.valueOf(temp_count), view.getContext());
                    //writeToFile(String.valueOf("0"), view.getContext());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // 환자별 Spiral_task의 개수가 1개 이상이면 view 바꾸기
        if(file.exists()){
            m = readFromFile(view.getContext());
            Log.v("aaaaa", m);
            if(Integer.parseInt(m) > 0){
                view = inflater.inflate(R.layout.task_spiral_fragment, container, false);
                Button add_spiral = (Button) view.findViewById(R.id.spiral_add);

                add_spiral.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(view.getContext(), SpiralTestActivity.class);
                        intent.putExtra("Clinic_ID",Clinic_ID);
                        intent.putExtra("PatientName", PatientName);
                        intent.putExtra("doc_uid", uid);
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
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(Clinic_ID + "SPIRAL_task_num.txt", Context.MODE_PRIVATE));
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
            InputStream inputStream = context.openFileInput(Clinic_ID + "SPIRAL_task_num.txt");
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
