package com.ahnbcilab.tremorquantification.tremorquantification

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.ahnbcilab.tremorquantification.data.PatientData
import com.ahnbcilab.tremorquantification.data.StateInfo_Data
import com.ahnbcilab.tremorquantification.data.SurveyData
import com.ahnbcilab.tremorquantification.tremorquantification.R.drawable.view
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_state_info.*
import kotlinx.android.synthetic.main.patient_list_item.*
import java.text.SimpleDateFormat
import java.util.*

class StateInfoActivity : AppCompatActivity() {
    var patientId : String = ""
    var uid : String = ""
    var desc : String = ""
    var sex: Int = 0
    var age: Int = 0
    var count: Int = 0
    var scount : String = ""
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    lateinit var submitBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_info)

        val intent = intent
        patientId = intent.getStringExtra("patientId")
        uid = intent.getStringExtra("doc_uid")
        val databaseStateInfo = firebaseDatabase.getReference("StateInfo_List")

        val databasePatient = firebaseDatabase.getReference("PatientList")
        databasePatient.orderByChild("patientId").equalTo(patientId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childDataSnapshot in dataSnapshot.children) {
                    age = Integer.valueOf(childDataSnapshot.child("age").value.toString())
                    sex = Integer.valueOf(childDataSnapshot.child("sex").value.toString())
                    desc = childDataSnapshot.child("description").getValue() as String

                    val PName = findViewById(R.id.state_PName) as TextView
                    val PAge = findViewById(R.id.state_PAge) as TextView
                    val PSex = findViewById(R.id.state_PSex) as TextView
                    val PDesc = findViewById(R.id.state_Pdiscrip) as TextView
                    val date = findViewById(R.id.state_todate) as TextView

                    PName.setText(patientId)
                    PAge.setText("Age : " + age)
                    if(sex == 1){
                        PSex.setText("Sex : Male")
                    }
                    else{
                        PSex.setText("Sex : Female")
                    }
                    PDesc.setText("Period of treatment : " + desc)

                    val sdf = SimpleDateFormat("yyyy.MM.dd")
                    val timestamp = sdf.format(Date())
                    date.setText(timestamp)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        submitBtn = findViewById(R.id.stateinfo_submit)

        databaseStateInfo.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                count = dataSnapshot.childrenCount.toInt()
                if(count < 10){
                    scount = "0" + count
                }
                else{
                    scount = count.toString()
                }


            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        submitBtn.setOnClickListener(){
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
            val timestamp = sdf.format(Date())
            val drug1 = state_drugedit1.text.toString()
            val drug2 = state_drugedit2.text.toString()
            val comment = state_commentedit.text.toString()
            val StateInfo = StateInfo_Data(drug1, drug2, comment, patientId, uid, timestamp, count) as StateInfo_Data
            Log.v("알림!", count.toString())

            databaseStateInfo.child("Task No "+scount).setValue(StateInfo).addOnCompleteListener {
                Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
            }

            val intent = Intent(this, SurveyListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
            finish()
        }


    }
}
