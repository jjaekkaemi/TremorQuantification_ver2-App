package com.ahnbcilab.tremorquantification.tremorquantification

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.View
import com.ahnbcilab.tremorquantification.data.CurrentUserData.Companion.user
import com.ahnbcilab.tremorquantification.tremorquantification.R.drawable.view
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.jjoe64.graphview.series.PointsGraphSeries
import kotlinx.android.synthetic.main.activity_personal_patient2.*
import java.util.ArrayList
import com.jjoe64.graphview.helper.StaticLabelsFormatter
import android.R.attr.data
import android.text.method.TextKeyListener.clear
import android.widget.*


class PersonalPatient2 : AppCompatActivity() {

    private var adapter: ArrayAdapter<String>? = null
    var type : String? = null
    var patientId: String? = null
    var cnt : Int = 0
    var motor_count : Int = 0
    var crts_count : Int = 0
    var count_list = ArrayList<String>()
    var motorscale_list = ArrayList<String>()
    var crt_partA_list = ArrayList<String>()
    var crt_partB_list = ArrayList<String>()
    var crt_partC_list = ArrayList<String>()
    var task_name = ArrayList<String>()
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    var mtimestamp_list = ArrayList<String>()
    var m2timestamp_list = ArrayList<String>()
    var Atimestamp_list = ArrayList<String>()
    var A2timestamp_list = ArrayList<String>()
    var Btimestamp_list = ArrayList<String>()
    var B2timestamp_list = ArrayList<String>()
    var Ctimestamp_list = ArrayList<String>()
    var C2timestamp_list = ArrayList<String>()



    override fun onStart() {
        super.onStart()
        adapter?.clear()
        adapter?.notifyDataSetChanged()

    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_patient2)

        val intent = intent
        patientId = intent.getStringExtra("patientId")

        val personalList: ListView = findViewById(R.id.personal_patient_list)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, task_name)
        personalList.adapter = adapter

        val databaseMotorScale = firebaseDatabase.getReference("MotorScale_List")
        val databaseCRTS = firebaseDatabase.getReference("CRTS_List")


        motor_button.setOnClickListener() {
            mtimestamp_list.clear()
            motorscale_list.clear()
            m2timestamp_list.clear()
            task_name.clear()
            adapter?.notifyDataSetChanged()
            partA_button.setTextColor(Color.BLACK)
            partB_button.setTextColor(Color.BLACK)

            databaseMotorScale.orderByChild("patientId").equalTo(patientId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (childDataSnapshot in dataSnapshot.children) {

                        motor_count = dataSnapshot.childrenCount.toInt()

                        val timestamp = childDataSnapshot.child("timestamp").getValue() as String
                        mtimestamp_list.add(timestamp)

                        val count = Integer.valueOf(childDataSnapshot.child("count").value.toString())
                        count_list.add(count.toString())

                        val motor_scale = Integer.valueOf(childDataSnapshot.child("motorScale_score").value.toString())
                        motorscale_list.add(motor_scale.toString())

                        type = "MotorScale"

                        name_of_patient.setText("UPDRS Part 3 History")
                        name_of_patient.setTextColor(Color.GREEN)

                        for (i in mtimestamp_list.indices) {
                            if (!m2timestamp_list.contains(mtimestamp_list.get(i))) {
                                m2timestamp_list.add(mtimestamp_list.get(i));
                                task_name.add(mtimestamp_list.get(i) + " - UPDRS_PART3")
                                cnt++
                            }
                        }
                        adapter?.notifyDataSetChanged()

                        motor_button.setText("UPDRS_PART3")
                        motor_button.setTextColor(Color.GREEN)
                        var graph: GraphView = findViewById(R.id.chart_space) as GraphView
                        val data = DataPoint(0.0, 0.0)
                        val data_list = arrayOf(data)
                        var series1 = LineGraphSeries(data_list)

                        for (i in m2timestamp_list.indices) {
                            val data1 = DataPoint(i + 1.toDouble(), motorscale_list.get(i).toDouble())
                            series1.appendData(data1, true, 100)
                        }
                        series1.setColor(Color.GREEN)
                        series1.setDrawDataPoints(true)
                        graph.removeAllSeries()
                        graph.addSeries(series1)
                        graph.getViewport().setScalableY(true)
                        graph.getViewport().setScrollableY(true)
                        graph.getViewport().setMinX(0.0)
                        val cnt1 = java.lang.Double.parseDouble(motor_count.toString())
                        graph.getViewport().setMaxX(cnt1)



                    }

                }
                override fun onCancelled(databaseError: DatabaseError) {


                }
            })



        }

        partA_button.setOnClickListener(){
            Atimestamp_list.clear()
            A2timestamp_list.clear()
            task_name.clear()
            adapter?.notifyDataSetChanged()
            partB_button.setTextColor(Color.BLACK)
            motor_button.setTextColor(Color.BLACK)

            databaseCRTS.orderByChild("patientId").equalTo(patientId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (childDataSnapshot in dataSnapshot.children) {

                        crts_count = dataSnapshot.childrenCount.toInt()

                        val timestamp = childDataSnapshot.child("timestamp").getValue() as String
                        Atimestamp_list.add(timestamp)

                        val count = Integer.valueOf(childDataSnapshot.child("count").value.toString())
                        count_list.add(count.toString())

                        name_of_patient.setText("CRTS Part A History")
                        name_of_patient.setTextColor(Color.BLUE)

                        for(i in Atimestamp_list.indices){
                            if (!A2timestamp_list.contains(Atimestamp_list.get(i))) {
                                A2timestamp_list.add(Atimestamp_list.get(i));
                                task_name.add(Atimestamp_list.get(i) + " - CRT_PART_A")
                            }
                        }


                        adapter?.notifyDataSetChanged()

                        val crt_partA = Integer.valueOf(childDataSnapshot.child("CRTS_Score").child("crts_PartA_score").value.toString())
                        crt_partA_list.add(crt_partA.toString())

                        type = "CRTS"
                        partA_button.setText("CRT_PART_A")
                        partA_button.setTextColor(Color.BLUE)
                        var graph : GraphView = findViewById(R.id.chart_space) as GraphView
                        val data = DataPoint(0.0, 0.0)
                        val data_list = arrayOf(data)
                        var series = LineGraphSeries(data_list)

                        for (i in A2timestamp_list.indices) {
                            val data1 = DataPoint(i+1.toDouble(), crt_partA_list.get(i).toDouble())
                            series.appendData(data1, true, 100)

                        }
                        series.setColor(Color.BLUE)
                        series.setDrawDataPoints(true)
                        graph.removeAllSeries()
                        graph.addSeries(series)
                        graph.getViewport().setScalableY(true)
                        graph.getViewport().setScrollableY(true)
                        graph.getViewport().setMinX(0.0)
                        val cnt1 = java.lang.Double.parseDouble(crts_count.toString())
                        graph.getViewport().setMaxX(cnt1)

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {



                }
            })

        }

        partB_button.setOnClickListener(){
            Btimestamp_list.clear()
            B2timestamp_list.clear()
            task_name.clear()
            adapter?.notifyDataSetChanged()
            partA_button.setTextColor(Color.BLACK)
            motor_button.setTextColor(Color.BLACK)


            databaseCRTS.orderByChild("patientId").equalTo(patientId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (childDataSnapshot in dataSnapshot.children) {
                        crts_count = dataSnapshot.childrenCount.toInt()
                        val timestamp = childDataSnapshot.child("timestamp").getValue() as String
                        Btimestamp_list.add(timestamp)

                        val count = Integer.valueOf(childDataSnapshot.child("count").value.toString())
                        count_list.add(count.toString())

                        type = "CRTS"

                        name_of_patient.setText("CRTS Part B History")
                        name_of_patient.setTextColor(Color.RED)

                        for(i in Btimestamp_list.indices){
                            if (!B2timestamp_list.contains(Btimestamp_list.get(i))) {
                                B2timestamp_list.add(Btimestamp_list.get(i));
                                task_name.add(Btimestamp_list.get(i)+ " - CRT_PART_B")
                            }
                        }

                        adapter?.notifyDataSetChanged()

                        val crt_partB = Integer.valueOf(childDataSnapshot.child("CRTS_Score").child("crts_PartB_score").value.toString())
                        crt_partB_list.add(crt_partB.toString())

                        partB_button.setText("CRT_PART_B")
                        partB_button.setTextColor(Color.RED)
                        var graph : GraphView = findViewById(R.id.chart_space) as GraphView
                        val data = DataPoint(0.0, 0.0)
                        val data_list = arrayOf(data)
                        var series = LineGraphSeries(data_list)

                        for (i in B2timestamp_list.indices) {
                            val data1 = DataPoint(i+1.toDouble(), crt_partB_list.get(i).toDouble())
                            series.appendData(data1, true, 100)

                        }
                        graph.removeAllSeries()
                        graph.addSeries(series)
                        graph.getViewport().setScalableY(true);
                        graph.getViewport().setScrollableY(true);

                        series.setColor(Color.RED);
                        series.setDrawDataPoints(true);
                        graph.getViewport().setMinX(0.0)
                        val cnt1 = java.lang.Double.parseDouble(crts_count.toString())
                        graph.getViewport().setMaxX(cnt1)

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })

        }

        partC_button.setOnClickListener(){
            Ctimestamp_list.clear()
            C2timestamp_list.clear()
            task_name.clear()
            adapter?.notifyDataSetChanged()
            partA_button.setTextColor(Color.BLACK)
            partB_button.setTextColor(Color.BLACK)
            motor_button.setTextColor(Color.BLACK)

            databaseCRTS.orderByChild("patientId").equalTo(patientId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (childDataSnapshot in dataSnapshot.children) {
                        crts_count = dataSnapshot.childrenCount.toInt()
                        val timestamp = childDataSnapshot.child("timestamp").getValue() as String
                        Ctimestamp_list.add(timestamp)

                        for(i in Ctimestamp_list.indices){
                            if (!C2timestamp_list.contains(Ctimestamp_list.get(i))) {
                                C2timestamp_list.add(Ctimestamp_list.get(i));
                                task_name.add(Ctimestamp_list.get(i)+ " - CRT_PART_C")
                            }
                        }

                        adapter?.notifyDataSetChanged()

                        val crt_partC = Integer.valueOf(childDataSnapshot.child("CRTS_Score").child("crts_PartC_score").value.toString())
                        crt_partC_list.add(crt_partC.toString())

                        val count = Integer.valueOf(childDataSnapshot.child("count").value.toString())
                        count_list.add(count.toString())

                        type = "CRTS"

                        name_of_patient.setText("CRTS Part C History")
                        name_of_patient.setTextColor(Color.BLACK)

                        partC_button.setText("CRT_PART_C")
                        partC_button.setTextColor(Color.BLACK)
                        var graph : GraphView = findViewById(R.id.chart_space) as GraphView
                        val data = DataPoint(0.0, 0.0)
                        val data_list = arrayOf(data)
                        var series = LineGraphSeries(data_list)

                        for (i in C2timestamp_list.indices) {
                            val data1 = DataPoint(i+1.toDouble(), crt_partC_list.get(i).toDouble())
                            series.appendData(data1, true, 100)

                        }
                        graph.removeAllSeries()
                        graph.addSeries(series)
                        graph.getViewport().setScalableY(true);
                        graph.getViewport().setScrollableY(true);

                        series.setColor(Color.BLACK);
                        series.setDrawDataPoints(true);
                        graph.getViewport().setMinX(0.0)
                        val cnt1 = java.lang.Double.parseDouble(crts_count.toString())
                        graph.getViewport().setMaxX(cnt1)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
        }

        clear_button.setOnClickListener(){
            adapter?.clear()
            partA_button.setText("CRT_PART_A")
            partB_button.setText("CRT_PART_B")
            partC_button.setText("CRT_PART_C")
            motor_button.setText("UPDRS_PART3")
            partA_button.setTextColor(Color.BLACK)
            partB_button.setTextColor(Color.BLACK)
            motor_button.setTextColor(Color.BLACK)
            name_of_patient.setText("")

            var graph = findViewById(R.id.chart_space) as GraphView
            graph.removeAllSeries()
        }

        personalList.setOnItemClickListener { parent, view, position, id ->
            run {
                val number = position.toString()

                if(type == "MotorScale"){
                    val intent = Intent(this, PersonalPatient_MotorScale::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    intent.putExtra("patientId", patientId)
                    intent.putExtra("task_no", number)
                    intent.putExtra("count_list", count_list)
                    startActivity(intent)
                    finish()
                }
                else {
                    val intent = Intent(this, PersonalPatient_CRTS::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    intent.putExtra("patientId", patientId)
                    intent.putExtra("task_no", number)
                    intent.putExtra("count_list", count_list)
                    startActivity(intent)
                    finish()
                }

            }
        }

    }

    override fun onBackPressed() {
        Log.v("알림", "백 버튼 눌림2")
        onStop()
        intent = Intent(this, SurveyListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)

    }
}
