package com.ahnbcilab.tremorquantification.tremorquantification

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.ahnbcilab.tremorquantification.data.PatientData
import com.ahnbcilab.tremorquantification.tremorquantification.R.id.*
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_personal_patient__crts.*
import java.util.ArrayList


class PersonalPatient_CRTS : AppCompatActivity() {

    //initialize
    var patientId: String? = null
    var task_no: String? = null
    var res: Int = 0
    var personalCount_list = ArrayList<String>()

    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private var name: String?=null
    private var sex: Int? = null
    internal var Name: TextView? = null
    private var desc: String?=null
    private var age: Int?=null
    private var sex_final: String?=null
    var count_list = ArrayList<Int>()
    var timestamp_list = ArrayList<String>()
    var motorScore_list = ArrayList<Int>()
    var CRTS_partA_list = ArrayList<Int>()
    var CRTS_partB_list = ArrayList<Int>()
    var CRTS_partC_list = ArrayList<Int>()
    var 개인위생_list = ArrayList<Int>()
    var 글쓰기_list = ArrayList<Int>()
    var 기립성_안정시_list = ArrayList<Int>()
    var 기립성_운동시_list = ArrayList<Int>()
    var 기립성_자세시_list = ArrayList<Int>()
    var 말하기_list = ArrayList<Int>()
    var 머리_안정시_list = ArrayList<Int>()
    var 머리_운동시_list = ArrayList<Int>()
    var 머리_자세시_list = ArrayList<Int>()
    var 목소리_안정시_list = ArrayList<Int>()
    var 목소리_운동시_list = ArrayList<Int>()
    var 목소리_자세시_list = ArrayList<Int>()
    var 물따르기_우측_list = ArrayList<Int>()
    var 물따르기_좌측_list = ArrayList<Int>()
    var 물을_입에_갖다대기_list = ArrayList<Int>()
    var 사회활동_list = ArrayList<Int>()
    var 얼굴_안정시_list = ArrayList<Int>()
    var 얼굴_운동시_list = ArrayList<Int>()
    var 얼굴_자세시_list = ArrayList<Int>()
    var 옷입기_list = ArrayList<Int>()
    var 우측상지_안정시_list = ArrayList<Int>()
    var 우측상지_운동시_list = ArrayList<Int>()
    var 우측상지_자세시_list = ArrayList<Int>()
    var 우측하지_안정시_list = ArrayList<Int>()
    var 우측하지_운동시_list = ArrayList<Int>()
    var 우측하지_자세시_list = ArrayList<Int>()
    var 음식먹기_list = ArrayList<Int>()
    var 일하기_list = ArrayList<Int>()
    var 좌측상지_안정시_list = ArrayList<Int>()
    var 좌측상지_운동시_list = ArrayList<Int>()
    var 좌측상지_자세시_list = ArrayList<Int>()
    var 좌측하지_안정시_list = ArrayList<Int>()
    var 좌측하지_운동시_list = ArrayList<Int>()
    var 좌측하지_자세시_list = ArrayList<Int>()
    var 체간_안정시_list = ArrayList<Int>()
    var 체간_운동시_list = ArrayList<Int>()
    var 체간_자세시_list = ArrayList<Int>()
    var 혀_안정시_list = ArrayList<Int>()
    var 혀_운동시_list = ArrayList<Int>()
    var 혀_자세시_list = ArrayList<Int>()



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_patient__crts)

        val intent = intent
        patientId = intent.getStringExtra("patientId")
        task_no = intent.getStringExtra("task_no")

        personalCount_list = intent.getStringArrayListExtra("count_list")
        val personalCount = personalCount_list.get(Integer.parseInt(task_no))

        val databaseCRTS = firebaseDatabase.getReference("CRTS_List")
        val databasePatient = firebaseDatabase.getReference("PatientList")

        databaseCRTS.orderByChild("patientId").equalTo(patientId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childDataSnapshot in dataSnapshot.children) {
                    val count = Integer.valueOf(childDataSnapshot.child("count").value.toString())
                    count_list.add(count)
                    for (i in count_list.indices) {
                        if(count_list.get(i).toString() == personalCount){
                            res = i
                        }
                    }
                    Log.v("count!!", res.toString())
                    val timestamp = childDataSnapshot.child("timestamp").getValue() as String
                    timestamp_list.add(timestamp)
                    personal_date.setText(timestamp_list.get(res))
                    val crts_partA = Integer.valueOf(childDataSnapshot.child("CRTS_Score").child("crts_PartA_score").value.toString())
                    CRTS_partA_list.add(crts_partA)
                    val crts_partB = Integer.valueOf(childDataSnapshot.child("CRTS_Score").child("crts_PartB_score").value.toString())
                    CRTS_partB_list.add(crts_partB)
                    val crts_partC = Integer.valueOf(childDataSnapshot.child("CRTS_Score").child("crts_PartC_score").value.toString())
                    CRTS_partC_list.add(crts_partC)
                    CRT_score.setText("Part_A : " + CRTS_partA_list.get(res) + "     Part_B : " + CRTS_partB_list.get(res) + "     Part_C : " + CRTS_partC_list.get(res))

                    val 개인위생 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("개인위생").value.toString())
                    개인위생_list.add(개인위생)
                    c36text.setText("개인위생 : ")
                    c36score.setText(개인위생_list.get(res).toString())


                    val 글쓰기 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("글쓰기").value.toString())
                    글쓰기_list.add(글쓰기)
                    c38text.setText("글쓰기 : ")
                    c38score.setText(글쓰기_list.get(res).toString())

                    val 기립성_안정시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("기립성_안정시").value.toString())
                    기립성_안정시_list.add(기립성_안정시)
                    c28text.setText("기립성 안정시 : ")
                    c28score.setText(기립성_안정시_list.get(res).toString())

                    val 기립성_운동시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("기립성_운동시").value.toString())
                    기립성_운동시_list.add(기립성_운동시)
                    c30text.setText("기립성 운동시 : ")
                    c30score.setText(기립성_운동시_list.get(res).toString())

                    val 기립성_자세시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("기립성_자세시").value.toString())
                    기립성_자세시_list.add(기립성_자세시)
                    c29text.setText("기립성 자세시 : ")
                    c29score.setText(기립성_자세시_list.get(res).toString())

                    val 말하기 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("말하기").value.toString())
                    말하기_list.add(말하기)
                    c33text.setText("말하기 : ")
                    c33score.setText(말하기_list.get(res).toString())

                    val 머리_안정시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("머리_안정시").value.toString())
                    머리_안정시_list.add(머리_안정시)
                    c10text.setText("머리 안정시 : ")
                    c10score.setText(머리_안정시_list.get(res).toString())

                    val 머리_운동시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("머리_운동시").value.toString())
                    머리_운동시_list.add(머리_운동시)
                    c12text.setText("머리 운동시 : ")
                    c12score.setText(머리_운동시_list.get(res).toString())

                    val 머리_자세시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("머리_자세시").value.toString())
                    머리_자세시_list.add(머리_자세시)
                    c11text.setText("머리 자세시 : ")
                    c11score.setText(머리_자세시_list.get(res).toString())

                    val 목소리_안정시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("목소리_안정시").value.toString())
                    목소리_안정시_list.add(목소리_안정시)
                    c7text.setText("목소리 안정시 : ")
                    c7score.setText(목소리_안정시_list.get(res).toString())

                    val 목소리_운동시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("목소리_운동시").value.toString())
                    목소리_운동시_list.add(목소리_운동시)
                    c9text.setText("목소리 운동시 : ")
                    c9score.setText(목소리_운동시_list.get(res).toString())

                    val 목소리_자세시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("목소리_자세시").value.toString())
                    목소리_자세시_list.add(목소리_자세시)
                    c8text.setText("목소리 자세시 : ")
                    c8score.setText(목소리_자세시_list.get(res).toString())

                    val 물따르기_우측 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("물따르기_우측").value.toString())
                    물따르기_우측_list.add(물따르기_우측)
                    c31text.setText("물따르기 우측 : ")
                    c31score.setText(물따르기_우측_list.get(res).toString())

                    val 물따르기_좌측 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("물따르기_좌측").value.toString())
                    물따르기_좌측_list.add(물따르기_좌측)
                    c32text.setText("물따르기 좌측 : ")
                    c32score.setText(물따르기_좌측_list.get(res).toString())

                    val 물을_입에_갖다대기 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("물을_입에_갖다대기").value.toString())
                    물을_입에_갖다대기_list.add(물을_입에_갖다대기)
                    c35text.setText("물을 입에 갖다대기 : ")
                    c35score.setText(물을_입에_갖다대기_list.get(res).toString())

                    val 사회활동 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("사회활동").value.toString())
                    사회활동_list.add(사회활동)
                    c40text.setText("사회활동 : ")
                    c40score.setText(사회활동_list.get(res).toString())

                    val 얼굴_안정시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("얼굴_안정시").value.toString())
                    얼굴_안정시_list.add(얼굴_안정시)
                    c1text.setText("얼굴 안정시 : ")
                    c1score.setText(얼굴_안정시_list.get(res).toString())

                    val 얼굴_운동시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("얼굴_운동시").value.toString())
                    얼굴_운동시_list.add(얼굴_운동시)
                    c3text.setText("얼굴 운동시 : ")
                    c3score.setText(얼굴_운동시_list.get(res).toString())

                    val 얼굴_자세시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("얼굴_자세시").value.toString())
                    얼굴_자세시_list.add(얼굴_자세시)
                    c2text.setText("얼굴 자세시 : ")
                    c2score.setText(얼굴_자세시_list.get(res).toString())

                    val 옷입기 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("옷입기").value.toString())
                    옷입기_list.add(옷입기)
                    c37text.setText("옷입기 : ")
                    c37score.setText(옷입기_list.get(res).toString())

                    val 우측상지_안정시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("우측상지_안정시").value.toString())
                    우측상지_안정시_list.add(우측상지_안정시)
                    c13text.setText("우측상지 안정시 : ")
                    c13score.setText(우측상지_안정시_list.get(res).toString())

                    val 우측상지_운동시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("우측상지_운동시").value.toString())
                    우측상지_운동시_list.add(우측상지_운동시)
                    c15text.setText("우측상지 운동시 : ")
                    c15score.setText(우측상지_운동시_list.get(res).toString())

                    val 우측상지_자세시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("우측상지_자세시").value.toString())
                    우측상지_자세시_list.add(우측상지_자세시)
                    c14text.setText("우측상지 자세시 : ")
                    c14score.setText(우측상지_자세시_list.get(res).toString())

                    val 우측하지_안정시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("우측하지_안정시").value.toString())
                    우측하지_안정시_list.add(우측하지_안정시)
                    c22text.setText("우측하지 안정시 : ")
                    c22score.setText(우측하지_안정시_list.get(res).toString())

                    val 우측하지_운동시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("우측하지_운동시").value.toString())
                    우측하지_운동시_list.add(우측하지_운동시)
                    c24text.setText("우측하지 운동시 : ")
                    c24score.setText(우측하지_운동시_list.get(res).toString())

                    val 우측하지_자세시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("우측하지_자세시").value.toString())
                    우측하지_자세시_list.add(우측하지_자세시)
                    c23text.setText("우측하지 자세시 : ")
                    c23score.setText(우측하지_자세시_list.get(res).toString())

                    val 음식먹기 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("음식먹기").value.toString())
                    음식먹기_list.add(음식먹기)
                    c34text.setText("음식먹기 : ")
                    c34score.setText(음식먹기_list.get(res).toString())

                    val 일하기 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("일하기").value.toString())
                    일하기_list.add(일하기)
                    c39text.setText("일하기 : ")
                    c39score.setText(일하기_list.get(res).toString())

                    val 좌측상지_안정시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("좌측상지_안정시").value.toString())
                    좌측상지_안정시_list.add(좌측상지_안정시)
                    c16text.setText("좌측상지 안정시 : ")
                    c16score.setText(좌측상지_안정시_list.get(res).toString())


                    val 좌측상지_운동시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("좌측상지_운동시").value.toString())
                    좌측상지_운동시_list.add(좌측상지_운동시)
                    c18text.setText("좌측상지 운동시 : ")
                    c18score.setText(좌측상지_운동시_list.get(res).toString())

                    val 좌측상지_자세시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("좌측상지_자세시").value.toString())
                    좌측상지_자세시_list.add(좌측상지_자세시)
                    c17text.setText("좌측상지 자세시 : ")
                    c17score.setText(좌측상지_자세시_list.get(res).toString())

                    val 좌측하지_안정시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("좌측하지_안정시").value.toString())
                    좌측하지_안정시_list.add(좌측하지_안정시)
                    c25text.setText("좌측하지 안정시 : ")
                    c25score.setText(좌측하지_안정시_list.get(res).toString())

                    val 좌측하지_운동시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("좌측하지_운동시").value.toString())
                    좌측하지_운동시_list.add(좌측하지_운동시)
                    c27text.setText("좌측상지 운동시 : ")
                    c27score.setText(좌측하지_운동시_list.get(res).toString())

                    val 좌측하지_자세시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("좌측하지_자세시").value.toString())
                    좌측하지_자세시_list.add(좌측하지_자세시)
                    c26text.setText("좌측하지 자세시 : ")
                    c26score.setText(좌측하지_자세시_list.get(res).toString())

                    val 체간_안정시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("체간_안정시").value.toString())
                    체간_안정시_list.add(체간_안정시)
                    c19text.setText("체간 안정시 : ")
                    c19score.setText(체간_안정시_list.get(res).toString())

                    val 체간_운동시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("체간_운동시").value.toString())
                    체간_운동시_list.add(체간_운동시)
                    c21text.setText("체간 운동시 : ")
                    c21score.setText(체간_운동시_list.get(res).toString())

                    val 체간_자세시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("체간_자세시").value.toString())
                    체간_자세시_list.add(체간_자세시)
                    c20text.setText("체간 자세시 : ")
                    c20score.setText(체간_자세시_list.get(res).toString())

                    val 혀_안정시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("혀_안정시").value.toString())
                    혀_안정시_list.add(혀_안정시)
                    c4text.setText("혀 안정시 : ")
                    c4score.setText(혀_안정시_list.get(res).toString())

                    val 혀_운동시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("혀_운동시").value.toString())
                    혀_운동시_list.add(혀_운동시)
                    c6text.setText("혀 운동시 : ")
                    c6score.setText(혀_운동시_list.get(res).toString())

                    val 혀_자세시 = Integer.valueOf(childDataSnapshot.child("CRTS_task").child("혀_자세시").value.toString())
                    혀_자세시_list.add(혀_자세시)
                    c5text.setText("혀 자세시 : ")
                    c5score.setText(혀_자세시_list.get(res).toString())


                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        databasePatient.orderByChild("patientId").equalTo(patientId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childDataSnapshot in dataSnapshot.children) {

                    name = childDataSnapshot.child("name").value as String
                    sex = Integer.valueOf(childDataSnapshot.child("sex").value.toString())
                    desc = childDataSnapshot.child("description").getValue() as String
                    //age = Integer.valueOf(childDataSnapshot.child("age").value.toString())
                    age = 0

                    if(sex == 1){
                        sex_final = "Male"

                    }
                    else if(sex == 2) {
                        sex_final = "Female"
                    }
                    else{
                        sex_final = "-"
                    }

                    PName1.setText("Name : "+name)
                    Pdiscrip1.setText("description : "+desc.toString())
                    PSex1.setText("Sex : "+ sex_final)
                    PAge1.setText("Age : "+age.toString())

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })


    }



    override fun onBackPressed() {
        Log.v("알림", "백 버튼 눌림2")
        onStop()
        intent = Intent(this, SurveyListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)

    }
}