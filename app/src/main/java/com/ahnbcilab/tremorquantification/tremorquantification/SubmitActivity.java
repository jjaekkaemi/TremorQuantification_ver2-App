package com.ahnbcilab.tremorquantification.tremorquantification;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import android.widget.Button;

import java.util.ArrayList;

public class SubmitActivity extends AppCompatActivity {
    String doc_uid;
    String patientId;
    int motorScale_score;
    int crts_partA;
    int crts_partB;
    int crts_partC;
    PieChart pieChart;
    PieChart pieChart1;
    PieChart pieChart2;
    PieChart pieChart3;
    PieChart pieChart4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        Intent intent = getIntent();
        doc_uid = intent.getStringExtra("doc_uid");
        patientId = intent.getStringExtra("patientId");
        motorScale_score = intent.getIntExtra("motorscale_score", 0);
        crts_partA = intent.getIntExtra("crt_partA_score", 0);
        crts_partB = intent.getIntExtra("crt_partB_score", 0);
        crts_partC = intent.getIntExtra("crt_partC_score", 0);

        pieChart1 = (PieChart)findViewById(R.id.part3chart);
        pieChart2 = (PieChart)findViewById(R.id.CRTSchart1);
        pieChart3 = (PieChart)findViewById(R.id.CRTSchart2);
        pieChart4 = (PieChart)findViewById(R.id.CRTSchart3);

        for (int i = 0; i < 4 ;i ++) {
            ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

            if(i == 0){
                pieChart = pieChart1;
                yValues.add(new PieEntry(motorScale_score, "score"));
                yValues.add(new PieEntry(10, "loss"));
                yValues.add(new PieEntry(16, "no data"));}
            else if(i == 1){
                pieChart = pieChart2;
                yValues.add(new PieEntry(crts_partA, "score"));
                yValues.add(new PieEntry(6, "loss"));
                yValues.add(new PieEntry(4, "no data"));}
            else if(i == 2){
                pieChart = pieChart3;
                yValues.add(new PieEntry(crts_partB, "score"));
                yValues.add(new PieEntry(1, "loss"));
                yValues.add(new PieEntry(0, "no data"));}
            else if(i == 3){
                pieChart = pieChart4;
                yValues.add(new PieEntry(crts_partC, "score"));
                yValues.add(new PieEntry(3, "loss"));
                yValues.add(new PieEntry(8, "no data"));}
            pieChart.setUsePercentValues(true);
            pieChart.getDescription().setEnabled(false);
            pieChart.getLegend().setEnabled(false);
            pieChart.setExtraOffsets(5, 10, 5, 5);

            pieChart.setDragDecelerationFrictionCoef(0.95f);

            pieChart.setDrawHoleEnabled(false);
            pieChart.setHoleColor(Color.BLACK);
            pieChart.setTransparentCircleRadius(61f);

//            ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();
//
//            yValues.add(new PieEntry(82, "score"));
//            yValues.add(new PieEntry(10, "loss"));
//            yValues.add(new PieEntry(16, "no data"));

            pieChart.animateY(2000, Easing.EasingOption.EaseInOutCubic); //애니메이션

            PieDataSet dataSet = new PieDataSet(yValues, "data");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
            dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

            PieData data = new PieData((dataSet));
            data.setValueTextSize(10f);
            data.setValueTextColor(Color.BLACK);

            pieChart.setData(data);
        }
        TextView textView1 = (TextView)findViewById(R.id.total1);
        textView1.setText("Total: " + motorScale_score);
        TextView textView2 = (TextView)findViewById(R.id.total2);
        textView2.setText("Part A: " + crts_partA + "    Part B: " + crts_partB + "   Part C: " + crts_partC);
//        barChart = (BarChart) findViewById(R.id.CRTSchart);
//        ArrayList<BarEntry> BarEntry = new ArrayList<>();
//        BarEntry.add(new BarEntry(2f, 0));
//        BarEntry.add(new BarEntry(4f, 1));
//        BarEntry.add(new BarEntry(6f, 2));
//        BarDataSet dataSet1 = new BarDataSet(BarEntry, "Projects");
//        ArrayList<String> labels1 = new ArrayList<>();
//        labels1.add("January");
//        labels1.add("February");
//        labels1.add("March");
//        BarData data1 = new BarData(labels1, dataSet1);
//        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        barChart.setData(data1);
        Button btn = (Button)findViewById(R.id.spiraBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(SubmitActivity.this,
                        WrittenConsentActivity.class);
                myIntent.putExtra("patientId", patientId);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(myIntent);
            }
        });

        Button Fbtn = (Button)findViewById(R.id.finish);
        Fbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(SubmitActivity.this,
                        SurveyListActivity.class);
                myIntent.putExtra("doc_uid", doc_uid);
                startActivity(myIntent);
            }
        });

    }



}
