package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WritingResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_result);

        Intent intent = getIntent();
        final double[] spiral_result = intent.getDoubleArrayExtra("spiral_result");
        final double[] line_result = intent.getDoubleArrayExtra("line_result");
        final String path = intent.getStringExtra("path1");
        final String PatientName = intent.getStringExtra("PatientName");
        final String Clinic_ID = intent.getStringExtra("Clinic_ID");
        final String crts_num = intent.getStringExtra("crts_num");

        Button next = (Button) findViewById(R.id.writing_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CRTS_SpiralResult.class) ;
                intent.putExtra("spiral_result", spiral_result);
                intent.putExtra("line_result", line_result);
                intent.putExtra("path", path);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("Clinic_ID", Clinic_ID);
                intent.putExtra("crts_num", crts_num);
                startActivity(intent) ;
            }
        });
    }
}