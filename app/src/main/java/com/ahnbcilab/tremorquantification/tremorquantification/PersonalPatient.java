package com.ahnbcilab.tremorquantification.tremorquantification;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ahnbcilab.tremorquantification.Adapters.RecyclerViewAdapter;
import com.ahnbcilab.tremorquantification.data.PatientData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class PersonalPatient extends AppCompatActivity implements View.OnClickListener{
    TextView fb1,fb2,fb3,fb4,fb5;
    TextView edit, person_delete, personal_diseaseType, personal_date;
    FragmentManager fm;
    FragmentTransaction tran;
    UPDRS_Fragment frag1;
    CRTS_Fragment frag2;
    SpiralTask_Fragment frag3;
    LineTask_Fragment frag4;
    Gear_Fragment frag5;
    public static String Clinic_ID;
    public static String taskType ;
    String PatientName;
    String task;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databasePatientList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_patient);

        // intent 값 받아오기
        Intent intent = getIntent();
        Clinic_ID = intent.getExtras().getString("ClinicID");
        PatientName = intent.getExtras().getString("PatientName");
        task = intent.getExtras().getString("task");


        TextView c = (TextView)findViewById(R.id.patientclinicID);
        c.setText(Clinic_ID);
        TextView p = (TextView)findViewById(R.id.patientClinicName);
        p.setText(PatientName);
        fb1 = (TextView) findViewById(R.id.bt1);
        fb2 = (TextView) findViewById(R.id.bt2);
        fb3 = (TextView) findViewById(R.id.bt3);
        fb4 = (TextView) findViewById(R.id.bt4);
        fb5 = (TextView) findViewById(R.id.bt5);

        edit = (TextView) findViewById(R.id.edit);
        person_delete = (TextView) findViewById(R.id.person_delete);
        personal_diseaseType = (TextView) findViewById(R.id.diseasetype) ;
        personal_date = (TextView) findViewById(R.id.date);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(Clinic_ID);
            }
        });
        person_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(PersonalPatient.this);
                dialogBuilder.setMessage("삭제 하시겠습니까?");
                dialogBuilder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                Query deleteQuery = ref.child("PatientList").orderByChild("ClinicID").equalTo(Clinic_ID);

                                deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot deleteSnapshot : dataSnapshot.getChildren()) {
                                            deleteSnapshot.getRef().removeValue();
                                            Intent intent = new Intent(PersonalPatient.this, PatientListActivity.class);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        });
                dialogBuilder.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                dialogBuilder.create().show();
            }
        });
        databasePatientList = firebaseDatabase.getReference("PatientList");
        databasePatientList.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot mData : dataSnapshot.getChildren()) {
                    String disease = String.valueOf(mData.child("DiseaseType").getValue());
                    if (disease.equals("P")) {
                        personal_diseaseType.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.parkinson));
                    } else if (disease.equals("ET")) {
                        personal_diseaseType.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.essential_tremor));
                    } else {
                        personal_diseaseType.setBackgroundResource(0);
                        personal_diseaseType.setText("ㅡ");
                    }
                    String firstDate = String.valueOf(mData.child("FirstDate").getValue()) ;
                    String finalDate = String.valueOf(mData.child("FinalDate").getValue()) ;
                    if(!firstDate.equals("null")&&!finalDate.equals("null")) {
                        String date = firstDate+ " - "+finalDate ;
                        personal_date.setText(date);
                    }
                    else{
                        personal_date.setText("") ;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
            fb1.setText("UPDRS");
            fb1.setBackgroundResource(0);
            fb2.setText("");
            fb2.setBackgroundResource(R.drawable.crts_underline);
            fb3.setText("Spiral Task");
            fb3.setBackgroundResource(0);
            fb4.setText("Line Task");
            fb4.setBackgroundResource(0);
            fb5.setText("Gear");
            fb5.setBackgroundResource(0);
            setFrag(1);
        }
        else if(task.equals("SPIRAL TASK")){
            fb1.setText("UPDRS");
            fb1.setBackgroundResource(0);
            fb2.setText("CRTS");
            fb2.setBackgroundResource(0);
            fb3.setText("");
            fb3.setBackgroundResource(R.drawable.spiral_underline);
            fb4.setText("Line Task");
            fb4.setBackgroundResource(0);
            fb5.setText("Gear");
            fb5.setBackgroundResource(0);
            setFrag(2);
        }
        else if(task.equals("GEAR")){
            fb1.setText("UPDRS");
            fb1.setBackgroundResource(0);
            fb2.setText("CRTS");
            fb2.setBackgroundResource(0);
            fb3.setText("Spiral Task");
            fb3.setBackgroundResource(0);
            fb4.setText("Line Task");
            fb4.setBackgroundResource(0);
            fb5.setText("");
            fb5.setBackgroundResource(R.drawable.gear_underline);
            setFrag(4);
        }
        else if(task.equals("LINE TASK")){
            fb1.setText("UPDRS");
            fb1.setBackgroundResource(0);
            fb2.setText("CRTS");
            fb2.setBackgroundResource(0);
            fb3.setText("Spiral Task");
            fb3.setBackgroundResource(0);
            fb4.setText("");
            fb4.setBackgroundResource(R.drawable.line_underline);
            fb5.setText("Gear");
            fb5.setBackgroundResource(0);
            setFrag(3);
        }
        else{
            fb1.setText("");
            fb1.setBackgroundResource(R.drawable.updrs_underline);
            fb2.setText("CRTS");
            fb2.setBackgroundResource(0);
            fb3.setText("Spiral Task");
            fb3.setBackgroundResource(0);
            fb4.setText("Line Task");
            fb4.setBackgroundResource(0);
            fb5.setText("Gear");
            fb5.setBackgroundResource(0);
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
                fb1.setText("");
                fb1.setBackgroundResource(R.drawable.updrs_underline);
                fb2.setText("CRTS");
                fb2.setBackgroundResource(0);
                fb3.setText("Spiral Task");
                fb3.setBackgroundResource(0);
                fb4.setText("Line Task");
                fb4.setBackgroundResource(0);
                fb5.setText("Gear");
                fb5.setBackgroundResource(0);
                setFrag(0);
                break;
            case R.id.bt2:
                fb1.setText("UPDRS");
                fb1.setBackgroundResource(0);
                fb2.setText("");
                fb2.setBackgroundResource(R.drawable.crts_underline);
                fb3.setText("Spiral Task");
                fb3.setBackgroundResource(0);
                fb4.setText("Line Task");
                fb4.setBackgroundResource(0);
                fb5.setText("Gear");
                fb5.setBackgroundResource(0);
                setFrag(1);
                break;
            case R.id.bt3:
                fb1.setText("UPDRS");
                fb1.setBackgroundResource(0);
                fb2.setText("CRTS");
                fb2.setBackgroundResource(0);
                fb3.setText("");
                fb3.setBackgroundResource(R.drawable.spiral_underline);
                fb4.setText("Line Task");
                fb4.setBackgroundResource(0);
                fb5.setText("Gear");
                fb5.setBackgroundResource(0);
                setFrag(2);
                break;

            case R.id.bt4:
                fb1.setText("UPDRS");
                fb1.setBackgroundResource(0);
                fb2.setText("CRTS");
                fb2.setBackgroundResource(0);
                fb3.setText("Spiral Task");
                fb3.setBackgroundResource(0);
                fb4.setText("");
                fb4.setBackgroundResource(R.drawable.line_underline);
                fb5.setText("Gear");
                fb5.setBackgroundResource(0);
                setFrag(3);
                break;

            case R.id.bt5:
                fb1.setText("UPDRS");
                fb1.setBackgroundResource(0);
                fb2.setText("CRTS");
                fb2.setBackgroundResource(0);
                fb3.setText("Spiral Task");
                fb3.setBackgroundResource(0);
                fb4.setText("Line Task");
                fb4.setBackgroundResource(0);
                fb5.setText("");
                fb5.setBackgroundResource(R.drawable.gear_underline);
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
                frag1.setArguments(bundle1);
                tran.replace(R.id.main_frame, frag1);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                taskType = "UPDRS List" ;
                break;
            case 1:
                Bundle bundle2 = new Bundle();
                bundle2.putString("Clinic_ID", Clinic_ID);
                bundle2.putString("PatientName", PatientName);
                frag2.setArguments(bundle2);
                tran.replace(R.id.main_frame, frag2);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                taskType = "CRTS List" ;
                break;
            case 2:
                Bundle bundle3 = new Bundle();
                bundle3.putString("Clinic_ID", Clinic_ID);
                bundle3.putString("PatientName", PatientName);
                bundle3.putString("path", "main");
                frag3.setArguments(bundle3);
                tran.replace(R.id.main_frame, frag3);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                taskType = "Spiral List" ;
                break;
            case 3:
                Bundle bundle4 = new Bundle();
                bundle4.putString("Clinic_ID", Clinic_ID);
                bundle4.putString("PatientName", PatientName);
                bundle4.putString("path", "main");
                frag4.setArguments(bundle4);
                tran.replace(R.id.main_frame, frag4);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                taskType = "Line List" ;
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

    public void show(final String clinical_ID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_patient_dialog, null);
        builder.setView(view);
        final Button submit = (Button) view.findViewById(R.id.patientEditButton);
        final Button cancel = (Button) view.findViewById(R.id.patientEditCancel);
        final EditText patientName = (EditText) view.findViewById(R.id.editPatientName);
        final RadioButton p = (RadioButton) view.findViewById(R.id.parkinson);
        final RadioButton e = (RadioButton) view.findViewById(R.id.essential_tremor);
        final TextView p_name = (TextView)findViewById(R.id.patientClinicName);
        final TextView diseaseType = (TextView) findViewById(R.id.diseasetype) ;

        databasePatientList = firebaseDatabase.getReference("PatientList");

        final AlertDialog dialog = builder.create();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String patient_Name = patientName.getText().toString();
                Log.v("editPatient", "PatientName : " + patient_Name);
                String patient_disease = "null";
                if(p.isChecked()){
                    patient_disease = "parkinson";
                }
                else if(e.isChecked()){
                    patient_disease = "tremor";
                }
                else{

                }

                final String finalPatient_disease = patient_disease;

                databasePatientList.orderByChild("ClinicID").equalTo(clinical_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (!(patient_Name.equals(""))) {
                                databasePatientList.child(clinical_ID).child("ClinicName").setValue(patient_Name);
                                p_name.setText(patient_Name);
                            }

                            if ((!finalPatient_disease.equals("null"))) {
                                if (finalPatient_disease.equals("parkinson")) {
                                    databasePatientList.child(clinical_ID).child("DiseaseType").setValue("P");
                                    diseaseType.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.parkinson));
                                } else {
                                    databasePatientList.child(clinical_ID).child("DiseaseType").setValue("ET");
                                    diseaseType.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.essential_tremor));
                                }
                            }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                dialog.dismiss();
            }

        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

