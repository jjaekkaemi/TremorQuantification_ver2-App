package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CRTS_LineResult extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crts__line_result);

        Intent intent = getIntent();
        final double[] line_result = intent.getDoubleArrayExtra("line_result");
        final String path = intent.getStringExtra("path");
        final String PatientName = intent.getStringExtra("PatientName");
        final String Clinic_ID = intent.getStringExtra("Clinic_ID");
        final String crts_num = intent.getStringExtra("crts_num");

        Button next = (Button) findViewById(R.id.line_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CRTSActivity.class) ;
                intent.putExtra("path", path);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("Clinic_ID", Clinic_ID);
                intent.putExtra("crts_num", crts_num);
                startActivity(intent) ;
            }
        });
    }
}
