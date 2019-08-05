package com.ahnbcilab.tremorquantification.tremorquantification

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.ahnbcilab.tremorquantification.data.*
import com.ahnbcilab.tremorquantification.tremorquantification.R.style.survey
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_crts__task.*
import kotlinx.android.synthetic.main.activity_personal_patient2.*
import java.text.SimpleDateFormat
import java.util.*
import android.content.SharedPreferences
import android.R.id.edit
import android.app.Activity
import android.content.Context
import android.R.id.edit
import android.widget.RadioButton
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.util.concurrent.TimeUnit


class CRTS_TaskActivity : AppCompatActivity() {
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    var crts_partA_score : Int = 0
    var crts_partB_score : Int = 0
    var crts_partC_score : Int = 0
    var bool : Boolean = true
    var Clinic_ID : String = ""
    var patientName : String = ""
    var uid : String = ""
    var path : String = ""
    var path1 : String = ""
    var count_list = ArrayList<Int>()
    var crts_count : Int = 0
    var scrts_count : String = ""
    var c1_1 : Int = 0
    var c1_2 : Int = 0
    var c1_3 : Int = 0
    var c2_1 : Int = 0
    var c2_2 : Int = 0
    var c2_3 : Int = 0
    var c3_1 : Int = 0
    var c3_2 : Int = 0
    var c3_3 : Int = 0
    var c4_1 : Int = 0
    var c4_2 : Int = 0
    var c4_3 : Int = 0
    var c5_1 : Int = 0
    var c5_2 : Int = 0
    var c5_3 : Int = 0
    var c6_1 : Int = 0
    var c6_2 : Int = 0
    var c6_3 : Int = 0
    var c7_1 : Int = 0
    var c7_2 : Int = 0
    var c7_3 : Int = 0
    var c8_1 : Int = 0
    var c8_2 : Int = 0
    var c8_3 : Int = 0
    var c9_1 : Int = 0
    var c9_2 : Int = 0
    var c9_3 : Int = 0
    var c10_1 : Int = 0
    var c10_2 : Int = 0
    var c10_3 : Int = 0
    var c14 : Int = 0
    var c15_1 : Int = 0
    var c15_2 : Int = 0
    var c16 : Int = 0
    var c17 : Int = 0
    var c18 : Int = 0
    var c19 : Int = 0
    var c20 : Int = 0
    var c21 : Int = 0
    var c22 : Int = 0
    var c23 : Int = 0
    var sc1_1 : Int = 0
    var sc1_2 : Int = 0
    var sc1_3 : Int = 0
    var sc2_1 : Int = 0
    var sc2_2 : Int = 0
    var sc2_3 : Int = 0
    var sc3_1 : Int = 0
    var sc3_2 : Int = 0
    var sc3_3 : Int = 0
    var sc4_1 : Int = 0
    var sc4_2 : Int = 0
    var sc4_3 : Int = 0
    var sc5_1 : Int = 0
    var sc5_2 : Int = 0
    var sc5_3 : Int = 0
    var sc6_1 : Int = 0
    var sc6_2 : Int = 0
    var sc6_3 : Int = 0
    var sc7_1 : Int = 0
    var sc7_2 : Int = 0
    var sc7_3 : Int = 0
    var sc8_1 : Int = 0
    var sc8_2 : Int = 0
    var sc8_3 : Int = 0
    var sc9_1 : Int = 0
    var sc9_2 : Int = 0
    var sc9_3 : Int = 0
    var sc10_1 : Int = 0
    var sc10_2 : Int = 0
    var sc10_3 : Int = 0
    var sc14 : Int = 0
    var sc15_1 : Int = 0
    var sc15_2 : Int = 0
    var sc16 : Int = 0
    var sc17 : Int = 0
    var sc18 : Int = 0
    var sc19 : Int = 0
    var sc20 : Int = 0
    var sc21 : Int = 0
    var sc22 : Int = 0
    var sc23 : Int = 0
    var taskno : Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crts__task)

        val intent = intent
        Clinic_ID = intent.getStringExtra("Clinic_ID")
        patientName = intent.getStringExtra("PatientName")
        path1 = intent.getStringExtra("path")
        uid = intent.getStringExtra("doc_uid")

        val databasepatient = firebaseDatabase.getReference("PatientList")
        val databaseclinicID = databasepatient.child(Clinic_ID).child("CRTS List")

        databaseclinicID.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                taskno = dataSnapshot.childrenCount.toInt()
                // TODO: show the count in the UI
                if(taskno < 10){
                    scrts_count = "0" + taskno
                }
                else{
                    scrts_count = taskno.toString()
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        spiralButton.setOnClickListener(){
            val intent = Intent(this, WrittenConsentActivity::class.java)
            path = "subtask"

            intent.putExtra("path", path)
            intent.putExtra("patientId", Clinic_ID)
            intent.putExtra("crts_count", crts_count)
            startActivity(intent)
        }

        crts_submit.setOnClickListener() {
            if (crts1_1_0.isChecked) {
                c1_1 = 0
            } else if (crts1_1_1.isChecked) {
                c1_1 = 1
            } else if (crts1_1_2.isChecked) {
                c1_1 = 2
            } else if (crts1_1_3.isChecked) {
                c1_1 = 3
            } else if (crts1_1_4.isChecked) {
                c1_1 = 4
            } else {
                c1_1 = -1
                bool = false
            }
            sc1_1 = c1_1
            if(sc1_1 == -1){
                sc1_1 = 0
            }

            if (crts1_2_0.isChecked) {
                c1_2 = 0
            } else if (crts1_2_1.isChecked) {
                c1_2 = 1
            } else if (crts1_2_2.isChecked) {
                c1_2 = 2
            } else if (crts1_2_3.isChecked) {
                c1_2 = 3
            } else if (crts1_2_4.isChecked) {
                c1_2 = 4
            } else {
                c1_2 = -1
                bool = false
            }
            sc1_2 = c1_2
            if(sc1_2 == -1){
                sc1_2 = 0
            }

            if (crts1_3_0.isChecked) {
                c1_3 = 0
            } else if (crts1_3_1.isChecked) {
                c1_3 = 1
            } else if (crts1_3_2.isChecked) {
                c1_3 = 2
            } else if (crts1_3_3.isChecked) {
                c1_3 = 3
            } else if (crts1_3_4.isChecked) {
                c1_3 = 4
            } else {
                c1_3 = -1
                bool = false
            }
            sc1_3 = c1_3
            if(sc1_3 == -1){
                sc1_3 = 0
            }

            if (crts2_1_0.isChecked) {
                c2_1 = 0
            } else if (crts2_1_1.isChecked) {
                c2_1 = 1
            } else if (crts2_1_2.isChecked) {
                c2_1 = 2
            } else if (crts2_1_3.isChecked) {
                c2_1 = 3
            } else if (crts2_1_4.isChecked) {
                c2_1 = 4
            } else {
                c2_1 = -1
                bool = false
            }
            sc2_1 = c2_1
            if(sc2_1 == -1){
                sc2_1 = 0
            }

            if (crts2_2_0.isChecked) {
                c2_2 = 0
            } else if (crts2_2_1.isChecked) {
                c2_2 = 1
            } else if (crts2_2_2.isChecked) {
                c2_2 = 2
            } else if (crts2_2_3.isChecked) {
                c2_2 = 3
            } else if (crts2_2_4.isChecked) {
                c2_2 = 4
            } else {
                c2_2 = -1
                bool = false
            }
            sc2_2 = c2_2
            if(sc2_2 == -1){
                sc2_2 = 0
            }

            if (crts2_3_0.isChecked) {
                c2_3 = 0
            } else if (crts2_3_1.isChecked) {
                c2_3 = 1
            } else if (crts2_3_2.isChecked) {
                c2_3 = 2
            } else if (crts2_3_3.isChecked) {
                c2_3 = 3
            } else if (crts2_3_4.isChecked) {
                c2_3 = 4
            } else {
                c2_3 = -1
                bool = false
            }
            sc2_3 = c2_3
            if(sc2_3 == -1){
                sc2_3 = 0
            }

            if (crts3_1_0.isChecked) {
                c3_1 = 0
            } else if (crts3_1_1.isChecked) {
                c3_1 = 1
            } else if (crts3_1_2.isChecked) {
                c3_1 = 2
            } else if (crts3_1_3.isChecked) {
                c3_1 = 3
            } else if (crts3_1_4.isChecked) {
                c3_1 = 4
            } else {
                c3_1 = -1
                bool = false
            }
            sc3_1 = c3_1
            if(sc3_1 == -1){
                sc3_1 = 0
            }

            if (crts3_2_0.isChecked) {
                c3_2 = 0
            } else if (crts3_2_1.isChecked) {
                c3_2 = 1
            } else if (crts3_2_2.isChecked) {
                c3_2 = 2
            } else if (crts3_2_3.isChecked) {
                c3_2 = 3
            } else if (crts3_2_4.isChecked) {
                c3_2 = 4
            } else {
                c3_2 = -1
                bool = false
            }
            sc3_2 = c3_2
            if(sc3_2 == -1){
                sc3_2 = 0
            }

            if (crts3_3_0.isChecked) {
                c3_3 = 0
            } else if (crts3_3_1.isChecked) {
                c3_3 = 1
            } else if (crts3_3_2.isChecked) {
                c3_3 = 2
            } else if (crts3_3_3.isChecked) {
                c3_3 = 3
            } else if (crts3_3_4.isChecked) {
                c3_3 = 4
            } else {
                c3_3 = -1
                bool = false
            }
            sc3_3 = c3_3
            if(sc3_3 == -1){
                sc3_3 = 0
            }

            if (crts4_1_0.isChecked) {
                c4_1 = 0
            } else if (crts4_1_1.isChecked) {
                c4_1 = 1
            } else if (crts4_1_2.isChecked) {
                c4_1 = 2
            } else if (crts4_1_3.isChecked) {
                c4_1 = 3
            } else if (crts4_1_4.isChecked) {
                c4_1 = 4
            } else {
                c4_1 = -1
                bool = false
            }
            sc4_1 = c4_1
            if(sc4_1 == -1){
                sc4_1 = 0
            }

            if (crts4_2_0.isChecked) {
                c4_2 = 0
            } else if (crts4_2_1.isChecked) {
                c4_2 = 1
            } else if (crts4_2_2.isChecked) {
                c4_2 = 2
            } else if (crts4_2_3.isChecked) {
                c4_2 = 3
            } else if (crts4_2_4.isChecked) {
                c4_2 = 4
            } else {
                c4_2 = -1
                bool = false
            }
            sc4_2 = c4_2
            if(sc4_2 == -1){
                sc4_2 = 0
            }

            if (crts4_3_0.isChecked) {
                c4_3 = 0
            } else if (crts4_3_1.isChecked) {
                c4_3 = 1
            } else if (crts4_3_2.isChecked) {
                c4_3 = 2
            } else if (crts4_3_3.isChecked) {
                c4_3 = 3
            } else if (crts4_3_4.isChecked) {
                c4_3 = 4
            } else {
                c4_3 = -1
                bool = false
            }
            sc4_3 = c4_3
            if(sc4_3 == -1){
                sc4_3 = 0
            }

            if (crts5_1_0.isChecked) {
                c5_1 = 0
            } else if (crts5_1_1.isChecked) {
                c5_1 = 1
            } else if (crts5_1_2.isChecked) {
                c5_1 = 2
            } else if (crts5_1_3.isChecked) {
                c5_1 = 3
            } else if (crts5_1_4.isChecked) {
                c5_1 = 4
            } else {
                c5_1 = -1
                bool = false
            }
            sc5_1 = c5_1
            if(sc5_1 == -1){
                sc5_1 = 0
            }

            if (crts5_2_0.isChecked) {
                c5_2 = 0
            } else if (crts5_2_1.isChecked) {
                c5_2 = 1
            } else if (crts5_2_2.isChecked) {
                c5_2 = 2
            } else if (crts5_2_3.isChecked) {
                c5_2 = 3
            } else if (crts5_2_4.isChecked) {
                c5_2 = 4
            } else {
                c5_2 = -1
                bool = false
            }
            sc5_2 = c5_2
            if(sc5_2 == -1){
                sc5_2 = 0
            }

            if (crts5_3_0.isChecked) {
                c5_3 = 0
            } else if (crts5_3_1.isChecked) {
                c5_3 = 1
            } else if (crts5_3_2.isChecked) {
                c5_3 = 2
            } else if (crts5_3_3.isChecked) {
                c5_3 = 3
            } else if (crts5_3_4.isChecked) {
                c5_3 = 4
            } else {
                c5_3 = -1
                bool = false
            }
            sc5_3 = c5_3
            if(sc5_3 == -1){
                sc5_3 = 0
            }

            if (crts6_1_0.isChecked) {
                c6_1 = 0
            } else if (crts6_1_1.isChecked) {
                c6_1 = 1
            } else if (crts6_1_2.isChecked) {
                c6_1 = 2
            } else if (crts6_1_3.isChecked) {
                c6_1 = 3
            } else if (crts6_1_4.isChecked) {
                c6_1 = 4
            } else {
                c6_1 = -1
                bool = false
            }
            sc6_1 = c6_1
            if(sc6_1 == -1){
                sc6_1 = 0
            }

            if (crts6_2_0.isChecked) {
                c6_2 = 0
            } else if (crts6_2_1.isChecked) {
                c6_2 = 1
            } else if (crts6_2_2.isChecked) {
                c6_2 = 2
            } else if (crts6_2_3.isChecked) {
                c6_2 = 3
            } else if (crts6_2_4.isChecked) {
                c6_2 = 4
            } else {
                c6_2 = -1
                bool = false
            }
            sc6_2 = c6_2
            if(sc6_2 == -1){
                sc6_2 = 0
            }

            if (crts6_3_0.isChecked) {
                c6_3 = 0
            } else if (crts6_3_1.isChecked) {
                c6_3 = 1
            } else if (crts6_3_2.isChecked) {
                c6_3 = 2
            } else if (crts6_3_3.isChecked) {
                c6_3 = 3
            } else if (crts6_3_4.isChecked) {
                c6_3 = 4
            } else {
                c6_3 = -1
                bool = false
            }
            sc6_3 = c6_3
            if(sc6_3 == -1){
                sc6_3 = 0
            }


            if (crts7_1_0.isChecked) {
                c7_1 = 0
            } else if (crts7_1_1.isChecked) {
                c7_1 = 1
            } else if (crts7_1_2.isChecked) {
                c7_1 = 2
            } else if (crts7_1_3.isChecked) {
                c7_1 = 3
            } else if (crts7_1_4.isChecked) {
                c7_1 = 4
            } else {
                c7_1 = -1
                bool = false
            }
            sc7_1 = c7_1
            if(sc7_1 == -1){
                sc7_1 = 0
            }

            if (crts7_2_0.isChecked) {
                c7_2 = 0
            } else if (crts7_2_1.isChecked) {
                c7_2 = 1
            } else if (crts7_2_2.isChecked) {
                c7_2 = 2
            } else if (crts7_2_3.isChecked) {
                c7_2 = 3
            } else if (crts7_2_4.isChecked) {
                c7_2 = 4
            } else {
                c7_2 = -1
                bool = false
            }
            sc7_2 = c7_2
            if(sc7_2 == -1){
                sc7_2 = 0
            }

            if (crts7_3_0.isChecked) {
                c7_3 = 0
            } else if (crts7_3_1.isChecked) {
                c7_3 = 1
            } else if (crts7_3_2.isChecked) {
                c7_3 = 2
            } else if (crts7_3_3.isChecked) {
                c7_3 = 3
            } else if (crts7_3_4.isChecked) {
                c7_3 = 4
            } else {
                c7_3 = -1
                bool = false
            }
            sc7_3 = c7_3
            if(sc7_3 == -1){
                sc7_3 = 0
            }

            if (crts8_1_0.isChecked) {
                c8_1 = 0
            } else if (crts8_1_1.isChecked) {
                c8_1 = 1
            } else if (crts8_1_2.isChecked) {
                c8_1 = 2
            } else if (crts8_1_3.isChecked) {
                c8_1 = 3
            } else if (crts8_1_4.isChecked) {
                c8_1 = 4
            } else {
                c8_1 = -1
                bool = false
            }
            sc8_1 = c8_1
            if(sc8_1 == -1){
                sc8_1 = 0
            }

            if (crts8_2_0.isChecked) {
                c8_2 = 0
            } else if (crts8_2_1.isChecked) {
                c8_2 = 1
            } else if (crts8_2_2.isChecked) {
                c8_2 = 2
            } else if (crts8_2_3.isChecked) {
                c8_2 = 3
            } else if (crts8_2_4.isChecked) {
                c8_2 = 4
            } else {
                c8_2 = -1
                bool = false
            }
            sc8_2 = c8_2
            if(sc8_2 == -1){
                sc8_2 = 0
            }

            if (crts8_3_0.isChecked) {
                c8_3 = 0
            } else if (crts8_3_1.isChecked) {
                c8_3 = 1
            } else if (crts8_3_2.isChecked) {
                c8_3 = 2
            } else if (crts8_3_3.isChecked) {
                c8_3 = 3
            } else if (crts8_3_4.isChecked) {
                c8_3 = 4
            } else {
                c8_3 = -1
                bool = false
            }
            sc8_3 = c8_3
            if(sc8_3 == -1){
                sc8_3 = 0
            }

            if (crts9_1_0.isChecked) {
                c9_1 = 0
            } else if (crts9_1_1.isChecked) {
                c9_1 = 1
            } else if (crts9_1_2.isChecked) {
                c9_1 = 2
            } else if (crts9_1_3.isChecked) {
                c9_1 = 3
            } else if (crts9_1_4.isChecked) {
                c9_1 = 4
            } else {
                c9_1 = -1
                bool = false
            }
            sc9_1 = c9_1
            if(sc9_1 == -1){
                sc9_1 = 0
            }

            if (crts9_2_0.isChecked) {
                c9_2 = 0
            } else if (crts9_2_1.isChecked) {
                c9_2 = 1
            } else if (crts9_2_2.isChecked) {
                c9_2 = 2
            } else if (crts9_2_3.isChecked) {
                c9_2 = 3
            } else if (crts9_2_4.isChecked) {
                c9_2 = 4
            } else {
                c9_2 = -1
                bool = false
            }
            sc9_2 = c9_2
            if(sc9_2 == -1){
                sc9_2 = 0
            }

            if (crts9_3_0.isChecked) {
                c9_3 = 0
            } else if (crts9_3_1.isChecked) {
                c9_3 = 1
            } else if (crts9_3_2.isChecked) {
                c9_3 = 2
            } else if (crts9_3_3.isChecked) {
                c9_3 = 3
            } else if (crts9_3_4.isChecked) {
                c9_3 = 4
            } else {
                c9_3 = -1
                bool = false
            }
            sc9_3 = c9_3
            if(sc9_3 == -1){
                sc9_3 = 0
            }

            if (crts10_1_0.isChecked) {
                c10_1 = 0
            } else if (crts10_1_1.isChecked) {
                c10_1 = 1
            } else if (crts10_1_2.isChecked) {
                c10_1 = 2
            } else if (crts10_1_3.isChecked) {
                c10_1 = 3
            } else if (crts10_1_4.isChecked) {
                c10_1 = 4
            } else {
                c10_1 = -1
                bool = false
            }
            sc10_1 = c10_1
            if(sc10_1 == -1){
                sc10_1 = 0
            }

            if (crts10_2_0.isChecked) {
                c10_2 = 0
            } else if (crts10_2_1.isChecked) {
                c10_2 = 1
            } else if (crts10_2_2.isChecked) {
                c10_2 = 2
            } else if (crts10_2_3.isChecked) {
                c10_2 = 3
            } else if (crts10_2_4.isChecked) {
                c10_2 = 4
            } else {
                c10_2 = -1
                bool = false
            }
            sc10_2 = c10_2
            if(sc10_2 == -1){
                sc10_2 = 0
            }

            if (crts10_3_0.isChecked) {
                c10_3 = 0
            } else if (crts10_3_1.isChecked) {
                c10_3 = 1
            } else if (crts10_3_2.isChecked) {
                c10_3 = 2
            } else if (crts10_3_3.isChecked) {
                c10_3 = 3
            } else if (crts10_3_4.isChecked) {
                c10_3 = 4
            } else {
                c10_3 = -1
                bool = false
            }
            sc10_3 = c10_3
            if(sc10_3 == -1){
                sc10_3 = 0
            }

            if (crts15_1_0.isChecked) {
                c15_1 = 0
            } else if (crts15_1_1.isChecked) {
                c15_1 = 1
            } else if (crts15_1_2.isChecked) {
                c15_1 = 2
            } else if (crts15_1_3.isChecked) {
                c15_1 = 3
            } else if (crts15_1_4.isChecked) {
                c15_1 = 4
            } else {
                c15_1 = -1
                bool = false
            }
            sc15_1 = c15_1
            if(sc15_1 == -1){
                sc15_1 = 0
            }

            if (crts15_2_0.isChecked) {
                c15_2 = 0
            } else if (crts15_2_1.isChecked) {
                c15_2 = 1
            } else if (crts15_2_2.isChecked) {
                c15_2 = 2
            } else if (crts15_2_3.isChecked) {
                c15_2 = 3
            } else if (crts15_2_4.isChecked) {
                c15_2 = 4
            } else {
                c15_2 = -1
                bool = false
            }
            sc15_2 = c15_2
            if(sc15_2 == -1){
                sc15_2 = 0
            }

            if (crts16_0.isChecked) {
                c16 = 0
            } else if (crts16_1.isChecked) {
                c16 = 1
            } else if (crts16_2.isChecked) {
                c16 = 2
            } else if (crts16_3.isChecked) {
                c16 = 3
            } else if (crts16_4.isChecked) {
                c16 = 4
            } else {
                c16 = -1
                bool = false
            }
            sc16 = c16
            if(sc16 == -1){
                sc16 = 0
            }

            if (crts17_0.isChecked) {
                c17 = 0
            } else if (crts17_1.isChecked) {
                c17 = 1
            } else if (crts17_2.isChecked) {
                c17 = 2
            } else if (crts17_3.isChecked) {
                c17 = 3
            } else if (crts17_4.isChecked) {
                c17 = 4
            } else {
                c17 = -1
                bool = false
            }
            sc17 = c17
            if(sc17 == -1){
                sc17 = 0
            }

            if (crts18_0.isChecked) {
                c18 = 0
            } else if (crts18_1.isChecked) {
                c18 = 1
            } else if (crts18_2.isChecked) {
                c18 = 2
            } else if (crts18_3.isChecked) {
                c18 = 3
            } else if (crts18_4.isChecked) {
                c18 = 4
            } else {
                c18 = -1
                bool = false
            }
            sc18 = c18
            if(sc18 == -1){
                sc18 = 0
            }

            if (crts19_0.isChecked) {
                c19 = 0
            } else if (crts19_1.isChecked) {
                c19 = 1
            } else if (crts19_2.isChecked) {
                c19 = 2
            } else if (crts19_3.isChecked) {
                c19 = 3
            } else if (crts19_4.isChecked) {
                c19 = 4
            } else {
                c19 = -1
                bool = false
            }
            sc19 = c19
            if(sc19 == -1){
                sc19 = 0
            }

            if (crts20_0.isChecked) {
                c20 = 0
            } else if (crts20_1.isChecked) {
                c20 = 1
            } else if (crts20_2.isChecked) {
                c20 = 2
            } else if (crts20_3.isChecked) {
                c20 = 3
            } else if (crts20_4.isChecked) {
                c20 = 4
            } else {
                c20 = -1
                bool = false
            }
            sc20 = c20
            if(sc20 == -1){
                sc20 = 0
            }

            if (crts21_0.isChecked) {
                c21 = 0
            } else if (crts21_1.isChecked) {
                c21 = 1
            } else if (crts21_2.isChecked) {
                c21 = 2
            } else if (crts21_3.isChecked) {
                c21 = 3
            } else if (crts21_4.isChecked) {
                c21 = 4
            } else {
                c21 = -1
                bool = false
            }
            sc21 = c21
            if(sc21 == -1){
                sc21 = 0
            }

            if (crts22_0.isChecked) {
                c22 = 0
            } else if (crts22_1.isChecked) {
                c22 = 1
            } else if (crts22_2.isChecked) {
                c22 = 2
            } else if (crts22_3.isChecked) {
                c22 = 3
            } else if (crts22_4.isChecked) {
                c22 = 4
            } else {
                c22 = -1
                bool = false
            }
            sc22 = c22
            if(sc22 == -1){
                sc22 = 0
            }

            if (crts23_0.isChecked) {
                c23 = 0
            } else if (crts23_1.isChecked) {
                c23 = 1
            } else if (crts23_2.isChecked) {
                c23 = 2
            } else if (crts23_3.isChecked) {
                c23 = 3
            } else if (crts23_4.isChecked) {
                c23 = 4
            } else {
                c23 = -1
                bool = false
            }
            sc23 = c23
            if(sc23 == -1){
                sc23 = 0
            }

            if (bool == false) {
                val dlg = AlertDialog.Builder(this)
                dlg.setTitle("미체크 문항")
                        .setMessage("체크되지 않은 문항이 있습니다\n" +
                                "다음 화면으로 넘어가시겠습니까?")
                        .setPositiveButton("네") { dialogInterface, i ->

                            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
                            val timestamp = sdf.format(Date())
                            val c14 = 0
                            val my_crts = CRTS_Data(c1_1, c1_2, c1_3, c2_1, c2_2, c2_3, c3_1, c3_2, c3_3, c4_1, c4_2, c4_3, c5_1, c5_2, c5_3, c6_1, c6_2, c6_3, c7_1, c7_2, c7_3,
                                    c8_1, c8_2, c8_3, c9_1, c9_2, c9_3, c10_1, c10_2, c10_3, c14, c15_1, c15_2, c16, c17, c18, c19, c20, c21, c22, c23)

                            crts_partA_score = sc1_1 + sc1_2 + sc1_3 + sc2_1 + sc2_2 + sc2_3 + sc3_1 + sc3_2 + sc3_3 + sc4_1 + sc4_2 + sc4_3 + sc5_1 + sc5_2 + sc5_3 + sc6_1 + sc6_2 + sc6_3 + sc7_1 + sc7_2 + sc7_3 +
                                    sc8_1 + sc8_2 + sc8_3 + sc9_1 + sc9_2 + sc9_3 + sc10_1 + sc10_2 + sc10_3
                            crts_partB_score = sc14+ sc15_1 + sc15_2
                            crts_partC_score = sc16 + sc17 + sc18 + sc19 + sc20 + sc21 + sc22 + sc23

                            val crts_score = CRTS_Score(crts_partA_score, crts_partB_score, crts_partC_score)
                            var m_crts = CRTS(timestamp, crts_count)

                            databaseclinicID.child("Task No "+scrts_count).setValue(m_crts).addOnCompleteListener {
                                Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
                            }

                            databaseclinicID.child("Task No "+scrts_count).child("CRTS score").setValue(crts_score).addOnCompleteListener {
                                Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
                            }

                            databaseclinicID.child("Task No "+scrts_count).child("CRTS task").setValue(my_crts).addOnCompleteListener {
                                Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
                            }

                            databasepatient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    for (childDataSnapshot in dataSnapshot.children) {
                                        val crts_count = childDataSnapshot.child("CRTS List").childrenCount
                                        val updrs_count = childDataSnapshot.child("UPDRS List").childrenCount
                                        databasepatient.child(Clinic_ID).child("TaskNo").setValue(crts_count+updrs_count)
                                    }
                                }

                                override fun onCancelled(databaseError: DatabaseError) {

                                }
                            })

                            val intent = Intent(this, PersonalPatient::class.java)
                            intent.putExtra("ClinicID", Clinic_ID)
                            intent.putExtra("PatientName", patientName)
                            intent.putExtra("doc_uid", uid)
                            intent.putExtra("task", "CRTS")
                            startActivity(intent)
                            finish()

                        }

                    .setNegativeButton("아니요") { dialogInterface, i ->
                        bool = true;
                    }
                    .show()
        }
        else {
                val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
                val timestamp = sdf.format(Date())
                val c14 = 0
                val my_crts = CRTS_Data(c1_1, c1_2, c1_3, c2_1, c2_2, c2_3, c3_1, c3_2, c3_3, c4_1, c4_2, c4_3, c5_1, c5_2, c5_3, c6_1, c6_2, c6_3, c7_1, c7_2, c7_3,
                        c8_1, c8_2, c8_3, c9_1, c9_2, c9_3, c10_1, c10_2, c10_3, c14, c15_1, c15_2, c16, c17, c18, c19, c20, c21, c22, c23)

                crts_partA_score = sc1_1 + sc1_2 + sc1_3 + sc2_1 + sc2_2 + sc2_3 + sc3_1 + sc3_2 + sc3_3 + sc4_1 + sc4_2 + sc4_3 + sc5_1 + sc5_2 + sc5_3 + sc6_1 + sc6_2 + sc6_3 + sc7_1 + sc7_2 + sc7_3 +
                        sc8_1 + sc8_2 + sc8_3 + sc9_1 + sc9_2 + sc9_3 + sc10_1 + sc10_2 + sc10_3
                crts_partB_score = sc14+ sc15_1 + sc15_2
                crts_partC_score = sc16 + sc17 + sc18 + sc19 + sc20 + sc21 + sc22 + sc23

                val crts_score = CRTS_Score(crts_partA_score, crts_partB_score, crts_partC_score)
                var m_crts = CRTS(timestamp, crts_count)

                databaseclinicID.child("Task No "+scrts_count).setValue(m_crts).addOnCompleteListener {
                    Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
                }

                databaseclinicID.child("Task No "+scrts_count).child("CRTS score").setValue(crts_score).addOnCompleteListener {
                    Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
                }

                databaseclinicID.child("Task No "+scrts_count).child("CRTS task").setValue(my_crts).addOnCompleteListener {
                    Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
                }



                databasepatient.orderByChild("ClinicID").equalTo(Clinic_ID).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (childDataSnapshot in dataSnapshot.children) {
                            val crts_count = childDataSnapshot.child("CRTS List").childrenCount
                            val updrs_count = childDataSnapshot.child("UPDRS List").childrenCount
                            databasepatient.child(Clinic_ID).child("TaskNo").setValue(crts_count+updrs_count)
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                })

                val intent = Intent(this, PersonalPatient::class.java)
                intent.putExtra("ClinicID", Clinic_ID)
                intent.putExtra("PatientName", patientName)
                intent.putExtra("doc_uid", uid)
                intent.putExtra("task", "CRTS")
                startActivity(intent)
                finish()
            }

        }
    }


}
