package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ahnbcilab.tremorquantification.data.TaskItem;
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
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CRTS_Result_Activity extends AppCompatActivity {

    String Clinic_ID, PatientName, timestamp;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database_crts;
    DatabaseReference database_patient;

    String crts_score;
    String partA_score, partB_score, partC_score;

    String crts_num;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crts__result_);

        Intent intent = getIntent();
        Clinic_ID = intent.getExtras().getString("ClinicID");
        PatientName = intent.getExtras().getString("PatientName");
        timestamp = intent.getExtras().getString("timestamp");
        crts_num = intent.getExtras().getString("crts_num");
        Log.v("crts_num", "CRTS_Result is " + crts_num);

        database_patient = firebaseDatabase.getReference("PatientList");
        database_crts = database_patient.child(Clinic_ID).child("CRTS List");

        HorizontalBarChart chart = (HorizontalBarChart)findViewById(R.id.crts_barchart);
        BarDataSet set1;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e2 = new BarEntry(1, 89f);
        valueSet1.add(v1e2);
        set1 = new BarDataSet(valueSet1, "today_score");
        set1.setColors(Color.parseColor("#F78B5D"));
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        BarData data = new BarData(dataSets);
        YAxis left = chart.getAxisLeft();
        left.setDrawLabels(false);
        left.setDrawAxisLine(false);
        left.setDrawGridLines(false);
        chart.setDrawGridBackground(false);
        chart.getLegend().setEnabled(false);
        chart.setTouchEnabled(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("");
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
        chart.setData(data);

        Description description = new Description();
        description.setText("");
        chart.setDescription(description);

        // hide legend
        chart.getLegend().setEnabled(false);

        chart.animateY(1000);
        chart.invalidate();



        TextView c_t = (TextView) findViewById(R.id.clinic_ID);
        c_t.setText(Clinic_ID);

        TextView p_t = (TextView) findViewById(R.id.patientName);
        p_t.setText(PatientName);

        String timestamp1 = timestamp.substring(2,4) + "." + timestamp.substring(5,7) + "." + timestamp.substring(8,10);

        TextView t_t = (TextView) findViewById(R.id.today_date);
        t_t.setText(timestamp1);

        TextView home = (TextView) findViewById(R.id.gotohome);

        Button share = (Button) findViewById(R.id.result_share);
        Button detail = (Button) findViewById(R.id.detail_result);

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CRTS_detailResult.class);
                intent.putExtra("ClinicID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("timestamp", timestamp);
                intent.putExtra("crts_num", crts_num);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PersonalPatient.class);
                intent.putExtra("ClinicID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("task", "CRTS");
                startActivity(intent);
            }
        });

        database_patient.orderByChild("ClinicID").equalTo(Clinic_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GraphView graphView = (GraphView)findViewById(R.id.crts_result_graph);
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                series.appendData(new DataPoint(0,0), true, 100);
                for (DataSnapshot mData : dataSnapshot.getChildren()){
                    Long number = mData.child("CRTS List").getChildrenCount() ;
                    //Toast.makeText(view.getContext(), number+"", Toast.LENGTH_SHORT).show();
                    for(int i = 1 ; i<=number; i++) {
                        list(i, mData, graphView, series, crts_num) ;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void list(final int i, final DataSnapshot mData, final GraphView graphView, final LineGraphSeries<DataPoint> series, final String crts_num) {
        Query query = database_patient.child(Clinic_ID).child("CRTS List").orderByChild("CRTS_count").equalTo(i) ;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String key = dataSnapshot1.getKey() ;

                    partA_score = String.valueOf(mData.child("CRTS List").child(key).child("CRTS score").child("partA_score").getValue()) ;
                    partB_score = String.valueOf(mData.child("CRTS List").child(key).child("CRTS score").child("partB_score").getValue()) ;
                    partC_score = String.valueOf(mData.child("CRTS List").child(key).child("CRTS score").child("partC_score").getValue()) ;
                    crts_score = String.valueOf(Integer.parseInt(partA_score) + Integer.parseInt(partB_score) + Integer.parseInt(partC_score));
                    series.appendData(new DataPoint(i,Integer.parseInt(crts_score)), true, 100);
                    //series.setDrawDataPoints(true);
                    graphView.removeAllSeries();
                    graphView.addSeries(series);
                    graphView.getViewport().setScalableY(true);
                    graphView.getViewport().setScrollableY(true);
                    graphView.getViewport().setMinX(0.0);
                    graphView.getViewport().setMaxX(Integer.parseInt(crts_num));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

