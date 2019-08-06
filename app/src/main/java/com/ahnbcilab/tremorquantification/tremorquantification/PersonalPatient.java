package com.ahnbcilab.tremorquantification.tremorquantification;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PersonalPatient extends AppCompatActivity implements View.OnClickListener{
    Button fb1,fb2,fb3,fb4,fb5;
    FragmentManager fm;
    FragmentTransaction tran;
    UPDRS_Fragment frag1;
    CRTS_Fragment frag2;
    SpiralTask_Fragment frag3;
    LineTask_Fragment frag4;
    Gear_Fragment frag5;
    String Clinic_ID;
    String PatientName;
    String uid;
    String task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_patient);

        // intent 값 받아오기
        Intent intent = getIntent();
        Clinic_ID = intent.getExtras().getString("ClinicID");
        PatientName = intent.getExtras().getString("PatientName");
        uid = intent.getExtras().getString("doc_uid");
        task = intent.getExtras().getString("task");


        TextView c = (TextView)findViewById(R.id.patientclinicID);
        c.setText(Clinic_ID);
        TextView p = (TextView)findViewById(R.id.patientClinicName);
        p.setText(PatientName);
        fb1 = (Button) findViewById(R.id.bt1);
        fb2 = (Button) findViewById(R.id.bt2);
        fb3 = (Button) findViewById(R.id.bt3);
        fb4 = (Button) findViewById(R.id.bt4);
        fb5 = (Button) findViewById(R.id.bt5);

        fb1.setOnClickListener(this);
        fb2.setOnClickListener(this);
        fb3.setOnClickListener(this);
        fb4.setOnClickListener(this);
        fb5.setOnClickListener(this);
        frag1 = new UPDRS_Fragment(); //프래그먼트 객채셍성
        frag2 = new CRTS_Fragment(); //프래그먼트 객채셍성
        frag3 = new SpiralTask_Fragment(); //프래그먼트 객채셍성
        frag4 = new LineTask_Fragment();
        frag5 = new Gear_Fragment();
        setFrag(0);



        // 완료한 task에 맞게 레이아웃 변경
        if(task.equals("CRTS")){
            fb1.setBackgroundResource(R.drawable.nonselect);
            fb1.setTextColor(Color.parseColor("#FFFFFF"));
            fb2.setBackgroundResource(R.drawable.select);
            fb2.setTextColor(Color.parseColor("#37BFA7"));
            fb3.setBackgroundResource(R.drawable.nonselect);
            fb3.setTextColor(Color.parseColor("#FFFFFF"));
            fb4.setBackgroundResource(R.drawable.nonselect);
            fb4.setTextColor(Color.parseColor("#FFFFFF"));
            fb5.setBackgroundResource(R.drawable.nonselect);
            fb5.setTextColor(Color.parseColor("#FFFFFF"));
            setFrag(1);
        }
        else if(task.equals("SPIRAL TASK")){
            fb1.setBackgroundResource(R.drawable.nonselect);
            fb1.setTextColor(Color.parseColor("#FFFFFF"));
            fb2.setBackgroundResource(R.drawable.nonselect);
            fb2.setTextColor(Color.parseColor("#FFFFFF"));
            fb3.setBackgroundResource(R.drawable.select);
            fb3.setTextColor(Color.parseColor("#37BFA7"));
            fb4.setBackgroundResource(R.drawable.nonselect);
            fb4.setTextColor(Color.parseColor("#FFFFFF"));
            fb5.setBackgroundResource(R.drawable.nonselect);
            fb5.setTextColor(Color.parseColor("#FFFFFF"));
            setFrag(2);
        }
        else if(task.equals("GEAR")){
            fb1.setBackgroundResource(R.drawable.nonselect);
            fb1.setTextColor(Color.parseColor("#FFFFFF"));
            fb2.setBackgroundResource(R.drawable.nonselect);
            fb2.setTextColor(Color.parseColor("#FFFFFF"));
            fb3.setBackgroundResource(R.drawable.nonselect);
            fb3.setTextColor(Color.parseColor("#FFFFFF"));
            fb4.setBackgroundResource(R.drawable.nonselect);
            fb4.setTextColor(Color.parseColor("#FFFFFF"));
            fb5.setBackgroundResource(R.drawable.select);
            fb5.setTextColor(Color.parseColor("#37BFA7"));
            setFrag(4);
        }
        else if(task.equals("LINE TASK")){
            fb1.setBackgroundResource(R.drawable.nonselect);
            fb1.setTextColor(Color.parseColor("#FFFFFF"));
            fb2.setBackgroundResource(R.drawable.nonselect);
            fb2.setTextColor(Color.parseColor("#FFFFFF"));
            fb3.setBackgroundResource(R.drawable.nonselect);
            fb3.setTextColor(Color.parseColor("#FFFFFF"));
            fb4.setBackgroundResource(R.drawable.select);
            fb4.setTextColor(Color.parseColor("#37BFA7"));
            fb5.setBackgroundResource(R.drawable.nonselect);
            fb5.setTextColor(Color.parseColor("#FFFFFF"));
            setFrag(3);
        }
        else{
            fb1.setBackgroundResource(R.drawable.select);
            fb1.setTextColor(Color.parseColor("#37BFA7"));
            fb2.setBackgroundResource(R.drawable.nonselect);
            fb2.setTextColor(Color.parseColor("#FFFFFF"));
            fb3.setBackgroundResource(R.drawable.nonselect);
            fb3.setTextColor(Color.parseColor("#FFFFFF"));
            fb4.setBackgroundResource(R.drawable.nonselect);
            fb4.setTextColor(Color.parseColor("#FFFFFF"));
            fb5.setBackgroundResource(R.drawable.nonselect);
            fb5.setTextColor(Color.parseColor("#FFFFFF"));
            setFrag(0);
        }


        // Backbutton 클릭
        Button b = (Button)findViewById(R.id.backButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }



    // task별 버튼 클릭
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.bt1:
                fb1.setBackgroundResource(R.drawable.select);
                fb1.setTextColor(Color.parseColor("#37BFA7"));
                fb2.setBackgroundResource(R.drawable.nonselect);
                fb2.setTextColor(Color.parseColor("#FFFFFF"));
                fb3.setBackgroundResource(R.drawable.nonselect);
                fb3.setTextColor(Color.parseColor("#FFFFFF"));
                fb4.setBackgroundResource(R.drawable.nonselect);
                fb4.setTextColor(Color.parseColor("#FFFFFF"));
                fb5.setBackgroundResource(R.drawable.nonselect);
                fb5.setTextColor(Color.parseColor("#FFFFFF"));
                setFrag(0);
                break;
            case R.id.bt2:
                fb1.setBackgroundResource(R.drawable.nonselect);
                fb1.setTextColor(Color.parseColor("#FFFFFF"));
                fb2.setBackgroundResource(R.drawable.select);
                fb2.setTextColor(Color.parseColor("#37BFA7"));
                fb3.setBackgroundResource(R.drawable.nonselect);
                fb3.setTextColor(Color.parseColor("#FFFFFF"));
                fb4.setBackgroundResource(R.drawable.nonselect);
                fb4.setTextColor(Color.parseColor("#FFFFFF"));
                fb5.setBackgroundResource(R.drawable.nonselect);
                fb5.setTextColor(Color.parseColor("#FFFFFF"));
                setFrag(1);
                break;
            case R.id.bt3:
                fb1.setBackgroundResource(R.drawable.nonselect);
                fb1.setTextColor(Color.parseColor("#FFFFFF"));
                fb2.setBackgroundResource(R.drawable.nonselect);
                fb2.setTextColor(Color.parseColor("#FFFFFF"));
                fb3.setBackgroundResource(R.drawable.select);
                fb3.setTextColor(Color.parseColor("#37BFA7"));
                fb4.setBackgroundResource(R.drawable.nonselect);
                fb4.setTextColor(Color.parseColor("#FFFFFF"));
                fb5.setBackgroundResource(R.drawable.nonselect);
                fb5.setTextColor(Color.parseColor("#FFFFFF"));
                setFrag(2);
                break;

            case R.id.bt4:
                fb1.setBackgroundResource(R.drawable.nonselect);
                fb1.setTextColor(Color.parseColor("#FFFFFF"));
                fb2.setBackgroundResource(R.drawable.nonselect);
                fb2.setTextColor(Color.parseColor("#FFFFFF"));
                fb3.setBackgroundResource(R.drawable.nonselect);
                fb3.setTextColor(Color.parseColor("#FFFFFF"));
                fb4.setBackgroundResource(R.drawable.select);
                fb4.setTextColor(Color.parseColor("#37BFA7"));
                fb5.setBackgroundResource(R.drawable.nonselect);
                fb5.setTextColor(Color.parseColor("#FFFFFF"));
                setFrag(3);
                break;

            case R.id.bt5:
                fb1.setBackgroundResource(R.drawable.nonselect);
                fb1.setTextColor(Color.parseColor("#FFFFFF"));
                fb2.setBackgroundResource(R.drawable.nonselect);
                fb2.setTextColor(Color.parseColor("#FFFFFF"));
                fb3.setBackgroundResource(R.drawable.nonselect);
                fb3.setTextColor(Color.parseColor("#FFFFFF"));
                fb4.setBackgroundResource(R.drawable.nonselect);
                fb4.setTextColor(Color.parseColor("#FFFFFF"));
                fb5.setBackgroundResource(R.drawable.select);
                fb5.setTextColor(Color.parseColor("#37BFA7"));
                setFrag(4);
                break;
        }
    }


    // fragment 교체
    public void setFrag(int n){
        fm = getFragmentManager();
        tran = fm.beginTransaction();
        switch (n){
            case 0:
                Bundle bundle1 = new Bundle();
                bundle1.putString("Clinic_ID", Clinic_ID);
                bundle1.putString("PatientName", PatientName);
                bundle1.putString("doc_uid", uid);
                frag1.setArguments(bundle1);
                tran.replace(R.id.main_frame, frag1);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;
            case 1:
                Bundle bundle2 = new Bundle();
                bundle2.putString("Clinic_ID", Clinic_ID);
                bundle2.putString("PatientName", PatientName);
                bundle2.putString("doc_uid", uid);
                frag2.setArguments(bundle2);
                tran.replace(R.id.main_frame, frag2);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;
            case 2:
                Bundle bundle3 = new Bundle();
                bundle3.putString("Clinic_ID", Clinic_ID);
                bundle3.putString("PatientName", PatientName);
                bundle3.putString("doc_uid", uid);
                bundle3.putString("path", "main");
                frag3.setArguments(bundle3);
                tran.replace(R.id.main_frame, frag3);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;
            case 3:
                Bundle bundle4 = new Bundle();
                bundle4.putString("Clinic_ID", Clinic_ID);
                bundle4.putString("PatientName", PatientName);
                bundle4.putString("doc_uid", uid);
                bundle4.putString("path", "main");
                frag4.setArguments(bundle4);
                tran.replace(R.id.main_frame, frag4);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;
            case 4:
                tran.replace(R.id.main_frame, frag5);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;

        }
    }



    // Backbutton 클릭 method
    @Override
    public void onBackPressed(){
        onStop();
        Intent intent = new Intent(getApplicationContext(), PatientListActivity.class) ;
        startActivity(intent) ;
    }
}

