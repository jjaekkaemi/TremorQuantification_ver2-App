package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ahnbcilab.tremorquantification.data.SpiralData;
import com.ahnbcilab.tremorquantification.data.SurveyData;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SpiralResultActivity extends AppCompatActivity {
    int c14 = 0;
    int crts_count;
    int res ;
    int spiral_count;
    String sspiral_count;
    double preamp;
    ArrayList<Double> preampList = new ArrayList<Double>();
    double prehz;
    ArrayList<Double> prehzList = new ArrayList<Double>();
    double prefit;
    ArrayList<Double> prefitList = new ArrayList<Double>();
    ArrayList<Integer> count_list = new ArrayList<Integer>();


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseCRTS;
    DatabaseReference databaseSpiral;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiral_result);
        final RadioButton c14_0 = findViewById(R.id.crts14_0);
        final RadioButton c14_1 = findViewById(R.id.crts14_1);
        final RadioButton c14_2 = findViewById(R.id.crts14_2);
        final RadioButton c14_3 = findViewById(R.id.crts14_3);
        final RadioButton c14_4 = findViewById(R.id.crts14_4);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent intent = getIntent();
        final double[] result = intent.getDoubleArrayExtra("result");
        String path1 = intent.getStringExtra("path1");
        crts_count = intent.getIntExtra("crts_count", -1);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();
        final String PatientId = intent.getStringExtra("PatientId");

        databaseSpiral = firebaseDatabase.getReference("SpiralList");
        databaseSpiral.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    spiral_count = (int) dataSnapshot.getChildrenCount();
                    if(spiral_count < 10){
                        sspiral_count = "0" + spiral_count;
                    }
                    else{
                        sspiral_count = String.valueOf(spiral_count);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if(path1.equals("maintask")){

            chartOption(result[2], 2f, "chart1","Amplitude");
            chartOption(result[3], 3f, "chart2","Hz");
            chartOption(result[4], 2f, "chart3","Fitting ratio");

            Button Fbtn = (Button)findViewById(R.id.Gosurvey);
            Fbtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {

                    Date d = new Date();
                    String path1 = "Spiral_Test";
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                    final String timestamp = sdf.format(d);
                    SurveyData survey = new SurveyData(PatientId, timestamp, uid, spiral_count);
                    SpiralData spiral = new SpiralData(result[2], result[3],result[4],path1);
                    if(spiral_count == 0){
                        sspiral_count = "00";
                    }
                    databaseSpiral.child("Task No " + sspiral_count).setValue(survey);
                    databaseSpiral.child("Task No " + sspiral_count).child("Spiral_Result").setValue(spiral);

                    // Start NewActivity.class
                    Intent myIntent = new Intent(SpiralResultActivity.this,
                            SurveyListActivity.class);
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
                    Date d2 = new Date();
                    String path1 = "CRTS_Test";
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                    final String timestamp2 = sdf2.format(d2);
                    SurveyData survey2 = new SurveyData(PatientId, timestamp2, uid, spiral_count);
                    SpiralData spiral2 = new SpiralData(result[2], result[3], result[4],path1);
                    databaseSpiral.child("Task No " + sspiral_count).setValue(survey2);
                    databaseSpiral.child("Task No " + sspiral_count).child("Spiral_Result").setValue(spiral2);

                    if (c14_0.isChecked()) {
                        c14 = 0;
                    } else if (c14_1.isChecked()) {
                        c14 = 1;
                    } else if (c14_2.isChecked()) {
                        c14 = 2;
                    } else if (c14_3.isChecked()) {
                        c14 = 3;
                    } else if (c14_4.isChecked()) {
                        c14 = 4;
                    } else {
                        c14 = -1;
                    }


                    // Start NewActivity.class
                    Intent myIntent = new Intent(getApplication(),
                            CRTS_TaskActivity.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    myIntent.putExtra("path", "spiralTask");
                    myIntent.putExtra("patientId", PatientId);
                    myIntent.putExtra("doc_uid", uid);
                    myIntent.putExtra("c14", c14);
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
