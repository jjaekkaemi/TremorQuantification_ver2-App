package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ahnbcilab.tremorquantification.data.Spiral;
import com.ahnbcilab.tremorquantification.data.SpiralData;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LineResultActivity extends AppCompatActivity {
    int res ;
    int line_count;
    double preamp;
    ArrayList<Double> preampList = new ArrayList<Double>();
    double prehz;
    ArrayList<Double> prehzList = new ArrayList<Double>();
    double prefit;
    ArrayList<Double> prefitList = new ArrayList<Double>();
    ArrayList<Integer> count_list = new ArrayList<Integer>();


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databasepatient;
    DatabaseReference databaseclinicID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_result);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent intent = getIntent();
        final double[] result = intent.getDoubleArrayExtra("line_result");
        String path1 = intent.getStringExtra("path1");
        final String PatientName = intent.getStringExtra("PatientName");
        final String Clinic_ID = intent.getStringExtra("Clinic_ID");

        databasepatient = firebaseDatabase.getReference("PatientList");
        databaseclinicID = databasepatient.child(Clinic_ID).child("Line List");
        databaseclinicID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    line_count = Integer.parseInt(String.valueOf(dataSnapshot.getChildrenCount()));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        TextView Fbtn = (TextView) findViewById(R.id.gotohome);
        Fbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Date d = new Date();
                String path1 = "Line_Test";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                final String timestamp = sdf.format(d);
                Spiral spiral = new Spiral(timestamp, line_count);
                SpiralData spiraldata = new SpiralData(result[2], result[3],result[4],path1);
                final String key = String.valueOf(databaseclinicID.push().getKey()) ;
                databaseclinicID.child(key).setValue(spiral);
                databaseclinicID.child(key).child("Spiral_Result").setValue(spiraldata);

                databasepatient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            Long crts_count = childDataSnapshot.child("CRTS List").getChildrenCount();
                            Long updrs_count = childDataSnapshot.child("UPDRS List").getChildrenCount();
                            Long spiral_count = childDataSnapshot.child("Spiral List").getChildrenCount();
                            Long line_count = childDataSnapshot.child("Line List").getChildrenCount() ;
                            int taskNo = (int)(crts_count + updrs_count + spiral_count + line_count);
                            databasepatient.child(Clinic_ID).child("TaskNo").setValue(taskNo);

                            if(taskNo==1) {
                                String FirstDate = String.valueOf(childDataSnapshot.child("Line List").child(key).child("timestamp").getValue());
                                int idx = FirstDate.indexOf(" ") ;
                                String firstDate1 = FirstDate.substring(0, idx) ;
                                databasepatient.child(Clinic_ID).child("FirstDate").setValue(firstDate1) ;
                                databasepatient.child(Clinic_ID).child("FinalDate").setValue(firstDate1) ;
                            }
                            else{
                                String FinalDate = String.valueOf(childDataSnapshot.child("Line List").child(key).child("timestamp").getValue());
                                int idx = FinalDate.indexOf(" ") ;
                                String finalDate1 = FinalDate.substring(0, idx);
                                databasepatient.child(Clinic_ID).child("FinalDate").setValue(finalDate1) ;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                // Start NewActivity.class
                Intent myIntent = new Intent(LineResultActivity.this,
                        PersonalPatient.class);
                myIntent.putExtra("ClinicID", Clinic_ID);
                myIntent.putExtra("PatientName", PatientName);
                myIntent.putExtra("task", "LINE TASK");

                startActivity(myIntent);
                finish() ;
            }
        });

    }



}
