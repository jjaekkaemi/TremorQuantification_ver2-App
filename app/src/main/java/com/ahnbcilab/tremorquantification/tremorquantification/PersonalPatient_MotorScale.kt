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
import kotlinx.android.synthetic.main.activity_personal_patient__motor_scale.*
import java.util.ArrayList





class PersonalPatient_MotorScale : AppCompatActivity() {

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
    var 걸음걸이_list = ArrayList<Int>()
    var 경직_목_list = ArrayList<Int>()
    var 경직_오른쪽다리_list = ArrayList<Int>()
    var 경직_오른쪽팔_list = ArrayList<Int>()
    var 경직_왼쪽다리_list = ArrayList<Int>()
    var 경직_왼쪽팔_list = ArrayList<Int>()
    var 느린행동_list = ArrayList<Int>()
    var 다리의민첩성_오른쪽다리_list = ArrayList<Int>()
    var 다리의민첩성_왼쪽다리_list = ArrayList<Int>()
    var 말하기_list = ArrayList<Int>()
    var 빠른손놀림_오른쪽손_list = ArrayList<Int>()
    var 빠른손놀림_왼쪽손_list = ArrayList<Int>()
    var 서있는자세_list = ArrayList<Int>()
    var 손가락벌렸다오므리기_오른쪽손_list = ArrayList<Int>()
    var 손가락벌렸다오므리기_왼쪽손_list = ArrayList<Int>()
    var 손운동_오른쪽손_list = ArrayList<Int>()
    var 손운동_왼쪽손_list = ArrayList<Int>()
    var 안정시진정_얼굴과턱_list = ArrayList<Int>()
    var 안정시진정_오른쪽다리_list = ArrayList<Int>()
    var 안정시진정_오른쪽팔_list = ArrayList<Int>()
    var 안정시진정_왼쪽다리_list = ArrayList<Int>()
    var 안정시진정_왼쪽팔_list = ArrayList<Int>()
    var 얼굴표정_list = ArrayList<Int>()
    var 운동또는자세성진정_오른쪽팔_list = ArrayList<Int>()
    var 운동또는자세성진정_왼쪽팔_list = ArrayList<Int>()
    var 의자에서일어서기_list = ArrayList<Int>()
    var 자세안정_list = ArrayList<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_patient__motor_scale)

        val intent = intent
        patientId = intent.getStringExtra("patientId")
        task_no = intent.getStringExtra("task_no")

        personalCount_list = intent.getStringArrayListExtra("count_list")
        val personalCount = personalCount_list.get(Integer.parseInt(task_no))

        val databasePatient = firebaseDatabase.getReference("PatientList")
        val databaseMotorScale = firebaseDatabase.getReference("MotorScale_List")

        databaseMotorScale.orderByChild("patientId").equalTo(patientId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childDataSnapshot in dataSnapshot.children) {
                    val count = Integer.valueOf(childDataSnapshot.child("count").value.toString())
                    count_list.add(count)
                    for (i in count_list.indices) {
                        if(count_list.get(i).toString() == personalCount){
                            res = i
                        }
                    }
                    val timestamp = childDataSnapshot.child("timestamp").getValue() as String
                    timestamp_list.add(timestamp)
                    personal_date.setText("Date : " + timestamp_list.get(res))
                    val motor_score = Integer.valueOf(childDataSnapshot.child("motorScale_score").value.toString())
                    motorScore_list.add(motor_score)
                    MotorScale_score.setText("Part 3 Score : " + motorScore_list.get(res))

                    val 걸음걸이 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("걸음걸이").value.toString())
                    걸음걸이_list.add(걸음걸이)
                    q1text.setText("걸음걸이 : ")
                    q1score.setText(걸음걸이_list.get(res).toString())

                    val 경직_목 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("경직_목").value.toString())
                    경직_목_list.add(경직_목)
                    q2text.setText("경직_목 : ")
                    q2score.setText(경직_목_list.get(res).toString())

                    val 경직_오른쪽다리 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("경직_오른쪽다리").value.toString())
                    경직_오른쪽다리_list.add(경직_오른쪽다리)
                    q3text.setText("경직_오른쪽다리 : ")
                    q3score.setText(경직_오른쪽다리_list.get(res).toString())

                    val 경직_오른쪽팔 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("경직_오른쪽팔").value.toString())
                    경직_오른쪽팔_list.add(경직_오른쪽팔)
                    q4text.setText("경직_오른쪽팔 : ")
                    q4score.setText(경직_오른쪽팔_list.get(res).toString())

                    val 경직_왼쪽다리 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("경직_왼쪽다리").value.toString())
                    경직_왼쪽다리_list.add(경직_왼쪽다리)
                    q5text.setText("경직_왼쪽다리 : ")
                    q5score.setText(경직_왼쪽다리_list.get(res).toString())

                    val 경직_왼쪽팔 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("경직_왼쪽팔").value.toString())
                    경직_왼쪽팔_list.add(경직_왼쪽팔)
                    q6text.setText("경직_왼쪽팔 : ")
                    q6score.setText(경직_왼쪽팔_list.get(res).toString())

                    val 느린행동 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("느린행동").value.toString())
                    느린행동_list.add(느린행동)
                    q7text.setText("느린행동 : ")
                    q7score.setText(느린행동_list.get(res).toString())

                    val 다리의민첩성_오른쪽다리 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("다리의민첩성_오른쪽다리").value.toString())
                    다리의민첩성_오른쪽다리_list.add(다리의민첩성_오른쪽다리)
                    q8text.setText("다리의민첩성_오른쪽다리 : ")
                    q8score.setText(다리의민첩성_오른쪽다리_list.get(res).toString())

                    val 다리의민첩성_왼쪽다리 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("다리의민첩성_왼쪽다리").value.toString())
                    다리의민첩성_왼쪽다리_list.add(다리의민첩성_왼쪽다리)
                    q9text.setText("다리의민첩성_왼쪽다리 : ")
                    q9score.setText(다리의민첩성_왼쪽다리_list.get(res).toString())

                    val 말하기 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("말하기").value.toString())
                    말하기_list.add(말하기)
                    q10text.setText("말하기 : ")
                    q10score.setText(말하기_list.get(res).toString())

                    val 빠른손놀림_오른쪽손 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("빠른손놀림_오른쪽손").value.toString())
                    빠른손놀림_오른쪽손_list.add(빠른손놀림_오른쪽손)
                    q11text.setText("빠른손놀림_오른쪽손 : ")
                    q11score.setText(빠른손놀림_오른쪽손_list.get(res).toString())

                    val 빠른손놀림_왼쪽손 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("빠른손놀림_왼쪽손").value.toString())
                    빠른손놀림_왼쪽손_list.add(빠른손놀림_왼쪽손)
                    q12text.setText("빠른손놀림_왼쪽손 : ")
                    q12score.setText(빠른손놀림_왼쪽손_list.get(res).toString())

                    val 서있는자세 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("서있는자세").value.toString())
                    서있는자세_list.add(서있는자세)
                    q13text.setText("서있는자세 : ")
                    q13score.setText(서있는자세_list.get(res).toString())

                    val 손가락벌렸다오므리기_오른쪽손 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("손가락벌렸다오므리기_오른쪽손").value.toString())
                    손가락벌렸다오므리기_오른쪽손_list.add(손가락벌렸다오므리기_오른쪽손)
                    q14text.setText("손가락벌렸다오므리기_오른쪽손 : ")
                    q14score.setText(손가락벌렸다오므리기_오른쪽손_list.get(res).toString())

                    val 손가락벌렸다오므리기_왼쪽손 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("손가락벌렸다오므리기_왼쪽손").value.toString())
                    손가락벌렸다오므리기_왼쪽손_list.add(손가락벌렸다오므리기_왼쪽손)
                    q15text.setText("손가락벌렸다오므리기_왼쪽손 : ")
                    q15score.setText(손가락벌렸다오므리기_왼쪽손_list.get(res).toString())

                    val 손운동_오른쪽손 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("손운동_오른쪽손").value.toString())
                    손운동_오른쪽손_list.add(손운동_오른쪽손)
                    q16text.setText("손운동_오른쪽손 : ")
                    q16score.setText(손운동_오른쪽손_list.get(res).toString())

                    val 손운동_왼쪽손 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("손운동_왼쪽손").value.toString())
                    손운동_왼쪽손_list.add(손운동_왼쪽손)
                    q17text.setText("손운동_왼쪽손 : ")
                    q17score.setText(손운동_왼쪽손_list.get(res).toString())

                    val 안정시진정_얼굴과턱 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("안정시진정_얼굴과턱").value.toString())
                    안정시진정_얼굴과턱_list.add(안정시진정_얼굴과턱)
                    q18text.setText("안정시진정_얼굴과턱 : ")
                    q18score.setText(안정시진정_얼굴과턱_list.get(res).toString())

                    val 안정시진정_오른쪽다리 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("안정시진정_오른쪽다리").value.toString())
                    안정시진정_오른쪽다리_list.add(안정시진정_오른쪽다리)
                    q19text.setText("안정시진정_오른쪽다리 : ")
                    q19score.setText(안정시진정_오른쪽다리_list.get(res).toString())

                    val 안정시진정_오른쪽팔 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("안정시진정_오른쪽팔").value.toString())
                    안정시진정_오른쪽팔_list.add(안정시진정_오른쪽팔)
                    q20text.setText("안정시진정_오른쪽팔 : ")
                    q20score.setText(안정시진정_오른쪽팔_list.get(res).toString())

                    val 안정시진정_왼쪽다리 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("안정시진정_왼쪽다리").value.toString())
                    안정시진정_왼쪽다리_list.add(안정시진정_왼쪽다리)
                    q21text.setText("안정시진정_왼쪽다리 : ")
                    q21score.setText(안정시진정_왼쪽다리_list.get(res).toString())

                    val 안정시진정_왼쪽팔 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("안정시진정_왼쪽팔").value.toString())
                    안정시진정_왼쪽팔_list.add(안정시진정_왼쪽팔)
                    q22text.setText("안정시진정_왼쪽팔 : ")
                    q22score.setText(안정시진정_왼쪽팔_list.get(res).toString())

                    val 얼굴표정 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("얼굴표정").value.toString())
                    얼굴표정_list.add(얼굴표정)
                    q23text.setText("얼굴표정 : ")
                    q23score.setText(얼굴표정_list.get(res).toString())

                    val 운동또는자세성진정_오른쪽팔 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("운동또는자세성진정_오른쪽팔").value.toString())
                    운동또는자세성진정_오른쪽팔_list.add(운동또는자세성진정_오른쪽팔)
                    q24text.setText("운동또는자세성진정_오른쪽팔 : ")
                    q24score.setText(운동또는자세성진정_오른쪽팔_list.get(res).toString())

                    val 운동또는자세성진정_왼쪽팔 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("운동또는자세성진정_왼쪽팔").value.toString())
                    운동또는자세성진정_왼쪽팔_list.add(운동또는자세성진정_왼쪽팔)
                    q25text.setText("운동또는자세성진정_왼쪽팔 : ")
                    q25score.setText(운동또는자세성진정_왼쪽팔_list.get(res).toString())

                    val 의자에서일어서기 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("의자에서일어서기").value.toString())
                    의자에서일어서기_list.add(의자에서일어서기)
                    q26text.setText("의자에서일어서기 : ")
                    q26score.setText(의자에서일어서기_list.get(res).toString())

                    val 자세안정 = Integer.valueOf(childDataSnapshot.child("MotorScale_task").child("자세안정").value.toString())
                    자세안정_list.add(자세안정)
                    q27text.setText("자세안정 : ")
                    q27score.setText(자세안정_list.get(res).toString())

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
        intent = Intent(this, PersonalPatient2::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.putExtra("patientId", patientId)
        startActivity(intent)

    }

}
