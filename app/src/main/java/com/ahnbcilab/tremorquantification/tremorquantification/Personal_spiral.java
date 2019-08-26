package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Personal_spiral extends AppCompatActivity {

    String Clinic_ID;
    String PatientName;
    String taskDate;
    String taskTime;
    String taskScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_spiral);

        Intent intent = getIntent();
        Clinic_ID = intent.getExtras().getString("ClinicID");
        PatientName = intent.getExtras().getString("PatientName");
        taskDate = intent.getExtras().getString("taskDate");
        taskTime = intent.getExtras().getString("taskTime");
        taskScore = intent.getExtras().getString("taskScore");

        String timestamp = taskDate + " " + taskTime;
        String title = taskDate + "     " + taskTime.substring(0,5);

        TextView t_s = (TextView) findViewById(R.id.timestamp);
        TextView c_id = (TextView) findViewById(R.id.clinicalID);
        TextView p_name = (TextView) findViewById(R.id.patientName);

        t_s.setText(title);
        c_id.setText(Clinic_ID);
        p_name.setText(PatientName);

        Button backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PersonalPatient.class);
                intent.putExtra("ClinicID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("task", "SPIRAL TASK");
                startActivity(intent);
            }
        });



    }
}
