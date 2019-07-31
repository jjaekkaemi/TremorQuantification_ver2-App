package com.ahnbcilab.tremorquantification.tremorquantification

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.*
import android.widget.*
import com.ahnbcilab.tremorquantification.Adapters.PatientCardAdapter
import com.ahnbcilab.tremorquantification.controller.DBController
import com.ahnbcilab.tremorquantification.data.DoctorData
import com.ahnbcilab.tremorquantification.data.PatientData
import com.ahnbcilab.tremorquantification.tremorquantification.PatientModel.getData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_survey_list.*
import kotlinx.android.synthetic.main.add_patient_dialog.*
import org.apache.commons.math3.random.RandomDataGenerator
import java.util.*
import java.io.Serializable
import kotlin.collections.ArrayList
import android.widget.TextView
import com.ahnbcilab.tremorquantification.functions.Authentication
import com.ahnbcilab.tremorquantification.tremorquantification.R.id.*
import android.widget.AdapterView.AdapterContextMenuInfo
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import java.sql.Types.NULL


class SurveyListActivity : AppCompatActivity() , Observer {
    val user = FirebaseAuth.getInstance().currentUser
    private var mPatientListAdapter: PatientCardAdapter? = null
    private lateinit var adapter: PatientAdapter
    internal var patient_data = ArrayList<String>()
    private var uid:String? = null
    private var email:String? = null
    var path : String = ""
    private var patientcode: String? = null
    var p_id : String = ""
    val data: ArrayList<Patient> = ArrayList<Patient>()
    private var lastTimeBackPressed: Long = 0




    override fun onStart() {
        super.onStart()
        mPatientListAdapter?.clear()


        if(data != null) {
            val data = PatientModel.getData(user?.uid.toString())
            mPatientListAdapter?.clear()
            mPatientListAdapter?.addAll(data)
            mPatientListAdapter?.notifyDataSetChanged()
        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_list)

        adapter = PatientAdapter(this)

        uid = user!!.uid

//////////////////////////////////////////////////////////////////////////////////////////////////
        PatientModel
        PatientModel.addObserver(this)

        val dataList: ListView = findViewById(R.id.patientSurList)

        mPatientListAdapter = PatientCardAdapter(this, R.layout.patient_list_item, data)
        dataList.adapter = mPatientListAdapter
        registerForContextMenu(dataList);
        //setContentView(R.layout.activity_survey_list)
////////////////////////////////////////////////////////////////////////////////////////////////
        // list에서 +버튼 누르는 것
        SuraddBtn.setOnClickListener {
            val dialog = CustomAddDialog(this)
            dialog.show()
        }

        patientSurList.setOnItemClickListener { parent, view, position, id ->
            run {
                val patientName: TextView? = view.findViewById(R.id.clinicIDItem)
                val intent = Intent(this, PersonalPatient2::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.putExtra("ClinicID", patientName!!.text)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun update(p0: Observable?, p1: Any?) {
        mPatientListAdapter?.clear()

        val data = PatientModel.getData(user!!.uid)
        if(data != null) {
            mPatientListAdapter?.clear()
            mPatientListAdapter?.addAll(data)
            mPatientListAdapter?.notifyDataSetChanged()
        }
    }

    override fun onResume(){
        super.onResume()
        PatientModel.addObserver(this)
    }

    override fun onPause(){
        super.onPause()
        PatientModel.deleteObserver(this)
    }

    override fun onStop(){
        super.onStop()
        PatientModel.deleteObserver(this)
    }

    override fun onDestroy() {
        adapter.db.closeDB()
        super.onDestroy()
    }

    inner class PatientAdapter(context: Context) : BaseAdapter() {
        private val mInflator: LayoutInflater = LayoutInflater.from(context)
        val db = DBController.PatientDataDbHelper(context)
        private var listItem: Array<PatientData> = db.select(null, false, null, null, DBController.PatientDataDB.COLUMN_PATIENT_NAME)

        private inner class ViewHolder {
            lateinit var clinic_id: TextView
            lateinit var name: TextView
        }

        fun add(data: PatientData) {
            db.insert(data)
            listItem = db.select(null, false, null, null, DBController.PatientDataDB.COLUMN_PATIENT_NAME)
        }

        override fun getCount(): Int {
            return this.listItem.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return this.listItem[position]
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View
            val holder: ViewHolder
            if (convertView == null) {
                view = mInflator.inflate(R.layout.patient_list_item, parent, false)

                holder = ViewHolder()
                holder.clinic_id = view.findViewById(R.id.clinicIDItem) as TextView
                holder.name = view.findViewById(R.id.patientNameItem) as TextView
                view.tag = holder
            }
            else {
                view = convertView
                holder = convertView.tag as ViewHolder
            }

            val patient = getItem(position) as PatientData
            val name = holder.name
            val clinic_id = holder.clinic_id

            name.text = patient.toMap().getValue("ClinicName").toString()
            clinic_id.text = patient.toMap().getValue("ClinicID").toString()
            //id.text = patient.id.toString()

            return view
        }

    }




    inner class CustomAddDialog(context: Context) : Dialog(context) {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            setContentView(R.layout.add_patient_dialog)

            patienAddCancel.setOnClickListener {
                dismiss()
            }

            patienAddButton.setOnClickListener {
                when {
                    //addDialogBirth.text.isBlank() -> addDialogBirthLayout.error = "Enter the birth"
                    //sex == null -> addDialogNameLayout.error = "Enter the sex"
                    else -> {

                        val newData = PatientData(
                                addClinicID.text.toString(),
                                addPatientName.text.toString(),
                                uid.toString(),
                                0)

                        adapter.add(newData)

                        patient_data.add(addClinicID.text.toString())
                        patient_data.add(addPatientName.text.toString())
                        patient_data.add(uid.toString())
                        patient_data.add("0")

                        FirebaseDatabase.getInstance().reference.child("PatientList").child(addClinicID.text.toString()).setValue(newData).addOnCompleteListener {
                            if (it.isSuccessful){
                                Toast.makeText(applicationContext, "value uploaded successfully!", Toast.LENGTH_LONG).show()
                            }else{
                                println("Something went wrong when uploading value")
                            }
                        }

                        dismiss()

                    }
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_logout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == R.id.logout) {
            Authentication.signOut(FirebaseAuth.getInstance())
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show()
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            ActivityCompat.finishAffinity(this)
            return
        }
        lastTimeBackPressed = System.currentTimeMillis()
        Toast.makeText(this, "'뒤로' 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
    }



}





