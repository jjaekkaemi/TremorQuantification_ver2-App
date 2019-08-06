package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
    String lline_count;
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
        setContentView(R.layout.activity_spiral_result);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent intent = getIntent();
        final double[] result = intent.getDoubleArrayExtra("result");
        String path1 = intent.getStringExtra("path1");
        final String uid = intent.getStringExtra("doc_uid");
        final String PatientName = intent.getStringExtra("PatientName");
        final String Clinic_ID = intent.getStringExtra("Clinic_ID");

        databasepatient = firebaseDatabase.getReference("PatientList");
        databaseclinicID = databasepatient.child(Clinic_ID).child("Line List");
        databaseclinicID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    line_count = (int) dataSnapshot.getChildrenCount();
                    if(line_count < 10){
                        lline_count = "0" + line_count;
                    }
                    else{
                        lline_count = String.valueOf(line_count);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if(path1.equals("main")){

            chartOption(result[2], 2f, "chart1","Amplitude");
            chartOption(result[3], 3f, "chart2","Hz");
            chartOption(result[4], 2f, "chart3","Fitting ratio");

            Button Fbtn = (Button)findViewById(R.id.Gosurvey);
            Fbtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {

                    Date d = new Date();
                    String path1 = "Line_Test";
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                    final String timestamp = sdf.format(d);
                    Spiral spiral = new Spiral(timestamp, line_count);
                    SpiralData spiraldata = new SpiralData(result[2], result[3],result[4],path1);
                    if(line_count == 0){
                        lline_count = "00";
                    }
                    databaseclinicID.child("Task No " + lline_count).setValue(spiral);
                    databaseclinicID.child("Task No " + lline_count).child("Spiral_Result").setValue(spiraldata);

                    databasepatient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                                Long crts_count = childDataSnapshot.child("CRTS List").getChildrenCount();
                                Long updrs_count = childDataSnapshot.child("UPDRS List").getChildrenCount();
                                Long spiral_count = childDataSnapshot.child("Spiral List").getChildrenCount();
                                databasepatient.child(Clinic_ID).child("TaskNo").setValue(crts_count+updrs_count+spiral_count);
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
                    myIntent.putExtra("doc_uid", uid);
                    myIntent.putExtra("task", "LINE TASK");

                    startActivity(myIntent);
                }
            });
        }
        else{
            chartOption(result[2], 2f, "chart1","Amplitude");
            chartOption(result[3], 3f, "chart2","Hz");
            chartOption(result[4], 2f, "chart3","Fitting ratio");

            Button Fbtn = (Button)findViewById(R.id.Gosurvey);
            Fbtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {

                    Date d = new Date();
                    String path1 = "Line_Test";
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                    final String timestamp = sdf.format(d);
                    Spiral spiral = new Spiral(timestamp, line_count);
                    SpiralData spiraldata = new SpiralData(result[2], result[3],result[4],path1);
                    if(line_count == 0){
                        lline_count = "00";
                    }
                    databaseclinicID.child("Task No " + lline_count).setValue(spiral);
                    databaseclinicID.child("Task No " + lline_count).child("Spiral_Result").setValue(spiraldata);

                    databasepatient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                                Long crts_count = childDataSnapshot.child("CRTS List").getChildrenCount();
                                Long updrs_count = childDataSnapshot.child("UPDRS List").getChildrenCount();
                                Long spiral_count = childDataSnapshot.child("Spiral List").getChildrenCount();
                                databasepatient.child(Clinic_ID).child("TaskNo").setValue(crts_count+updrs_count+spiral_count);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    // Start NewActivity.class
                    Intent myIntent = new Intent(LineResultActivity.this,
                            CRTS_TaskActivity.class);
                    myIntent.putExtra("Clinic_ID", Clinic_ID);
                    myIntent.putExtra("PatientName", PatientName);
                    myIntent.putExtra("doc_uid", uid);
                    myIntent.putExtra("path", path1);

                    startActivity(myIntent);
                }
            });
        }

    }


    private BarChart chartOption(double data1, double data2, String chartname, String index){
        int resID = getResources().getIdentifier(chartname, "id", getPackageName());
        BarChart chart = (HorizontalBarChart) findViewById(resID);

        final ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("오늘 검사");
        xVals.add("최근 검사");


        ArrayList<BarEntry> yVals = new ArrayList<>();
        float bs = 1f;
        yVals.add(new BarEntry(bs,(float)data1));
        yVals.add(new BarEntry(2*bs,(float)data2));
        BarDataSet set1;
        set1 = new BarDataSet(yVals,"data set");
        set1.setDrawValues(true);
        BarData data = new BarData(set1);
        chart.setData(data);

        chart.setDrawGridBackground(false);
        chart.getLegend().setEnabled(false);
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xVals));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawTopYLabelEntry(false);
//        if (chartname == "chart2")
//            leftAxis.setAxisMaxValue(12f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(true);
        chart.setTouchEnabled(false);
        xAxis.setLabelCount(2);
        Description description = new Description();
        description.setText(index);
        chart.setDescription(description);
        chart.animateY(2000, Easing.EasingOption.EaseInOutCubic); //애니메이션
        return chart;
    }
}
