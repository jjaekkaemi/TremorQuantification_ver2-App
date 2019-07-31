package com.ahnbcilab.tremorquantification.tremorquantification;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PersonalPatient extends AppCompatActivity implements View.OnClickListener{
    Button fb1,fb2,fb3;
    FragmentManager fm;
    FragmentTransaction tran;
    UPDRS_Fragment frag1;
    CRTS_Fragment frag2;
    SpiralTask_Fragment frag3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_patient);
        fb1 = (Button) findViewById(R.id.bt1);
        fb2 = (Button) findViewById(R.id.bt2);
        fb3 = (Button) findViewById(R.id.bt3);
        fb1.setOnClickListener(this);
        fb2.setOnClickListener(this);
        fb3.setOnClickListener(this);
        frag1 = new UPDRS_Fragment(); //프래그먼트 객채셍성
        frag2 = new CRTS_Fragment(); //프래그먼트 객채셍성
        frag3 = new SpiralTask_Fragment(); //프래그먼트 객채셍성
        setFrag(0); //프래그먼트 교체
    }
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
                setFrag(0);
                break;
            case R.id.bt2:
                fb1.setBackgroundResource(R.drawable.nonselect);
                fb1.setTextColor(Color.parseColor("#FFFFFF"));
                fb2.setBackgroundResource(R.drawable.select);
                fb2.setTextColor(Color.parseColor("#37BFA7"));
                fb3.setBackgroundResource(R.drawable.nonselect);
                fb3.setTextColor(Color.parseColor("#FFFFFF"));
                setFrag(1);
                break;
            case R.id.bt3:
                fb1.setBackgroundResource(R.drawable.nonselect);
                fb1.setTextColor(Color.parseColor("#FFFFFF"));
                fb2.setBackgroundResource(R.drawable.nonselect);
                fb2.setTextColor(Color.parseColor("#FFFFFF"));
                fb3.setBackgroundResource(R.drawable.select);
                fb3.setTextColor(Color.parseColor("#37BFA7"));
                setFrag(2);
                break;
        }
    }
    public void setFrag(int n){    //프래그먼트를 교체하는 작업을 하는 메소드를 만들었습니다
        fm = getFragmentManager();
        tran = fm.beginTransaction();
        switch (n){
            case 0:
                tran.replace(R.id.main_frame, frag1);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;
            case 1:
                tran.replace(R.id.main_frame, frag2);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;
            case 2:
                tran.replace(R.id.main_frame, frag3);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;
        }
    }
}

