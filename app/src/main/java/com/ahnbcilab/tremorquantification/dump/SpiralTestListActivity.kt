package com.ahnbcilab.tremorquantification.tremorquantification

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import com.ahnbcilab.tremorquantification.Adapters.PatientCardAdapter
import com.ahnbcilab.tremorquantification.controller.DBController
import com.ahnbcilab.tremorquantification.data.PatientData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_spiral_test_list.*
import kotlinx.android.synthetic.main.add_patient_dialog.*
import java.util.*

class SpiralTestListActivity : AppCompatActivity(), Observer {
    val user = FirebaseAuth.getInstance().currentUser
    private var mPatientListAdapter: PatientCardAdapter? = null
    //private lateinit var mPatientListAdapter: PatientCardAdapter
    private lateinit var adapter: PatientAdapter
    private var patientcode: String? = null
    private var uid:String? = null

    override fun onStart() {
        super.onStart()
        mPatientListAdapter?.clear()

        val data = PatientModel.getData(user!!.uid)
        if(data != null) {
            mPatientListAdapter?.clear()
            mPatientListAdapter?.addAll(data)
            mPatientListAdapter?.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spiral_test_list)

        adapter = PatientAdapter(this)

        val intent = intent
        uid = intent.getStringExtra("doc_uid")
//////////////////////////////////////////////////////////////////////////////////////////////////
        PatientModel
        PatientModel.addObserver(this)

        val dataList: ListView = findViewById(R.id.patient_list)

        val data: ArrayList<Patient> = ArrayList()
        mPatientListAdapter = PatientCardAdapter(this, R.layout.patient_list_item, data)
        dataList.adapter = mPatientListAdapter
////////////////////////////////////////////////////////////////////////////////////////////////
        // list에서 +버튼 누르는 것
        addBtn.setOnClickListener {
            val dialog = CustomAddDialog(this)
            dialog.show()
        }

        patient_list.setOnItemClickListener { parent, view, position, id ->
            run {
                val id = patientcode
                val intent = Intent(this, WrittenConsentActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.putExtra("patientId", id)
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
            lateinit var id: TextView
            lateinit var name: TextView
            lateinit var description: TextView
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
                holder.name = view.findViewById(R.id.patientName) as TextView
                holder.description = view.findViewById(R.id.patientDescription) as TextView
                holder.id = view.findViewById(R.id.PatientId) as TextView
                view.tag = holder
            }
            else {
                view = convertView
                holder = convertView.tag as ViewHolder
            }

            val patient = getItem(position) as PatientData
            val name = holder.name
            val description = holder.description
            val id = holder.id

            name.text = patient.name
            description.text = patient.description
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

            var sex: Int? = null

            addDialogCancel.setOnClickListener {
                dismiss()
            }

            addDialogSexLayout.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.male -> sex = PatientData.MALE
                    R.id.female -> sex = PatientData.FEMALE
                }
            }

            addDialogAdd.setOnClickListener {
                when {
                    addDialogName.text.isBlank() -> addDialogNameLayout.error = "Enter the name"
                    addDialogBirth.text.isBlank() -> addDialogBirthLayout.error = "Enter the birth"
                    sex == null -> addDialogNameLayout.error = "Enter the sex"
                    else -> {

                        //generate patient code function
                        fun random(): String {
                            val generator = Random()
                            val randomStringBuilder = StringBuilder()
                            var tempChar: Char
                            var tempNum: Int
                            for (i in 0..4) {
                                if (i < 3) {
                                    tempChar = (generator.nextInt(26) + 97).toChar()
                                    randomStringBuilder.append(tempChar)
                                } else {
                                    tempNum = generator.nextInt(10)
                                    randomStringBuilder.append(tempNum)
                                }
                            }

                            val code = randomStringBuilder.toString()
                            return code;
                        }
                        patientcode = random();

                        val newData = PatientData(
                                addDialogName.text.toString(),
                                sex!!,
                                addDialogBirth.text.toString().toInt(),
                                addDialogDescription.text.toString())
                        newData.uid = uid.toString()
                        newData.patientId = patientcode as String
                        //newData.patientCode = patientcode.toString()

                        adapter.add(newData)

                        FirebaseDatabase.getInstance().reference.child("PatientList").child(patientcode!!).setValue(newData).addOnCompleteListener {
                            if (it.isSuccessful){
                                Toast.makeText(applicationContext, "value uploaded successfully!", Toast.LENGTH_LONG).show()
                            }else{
                                println("Something went wrong when uploading value")
                            }
                        }

                        dismiss()

                        val intent = Intent(context, WrittenConsentActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                        val item = adapter.getItem(adapter.count - 1) as PatientData
                        intent.putExtra("patientId", patientcode)
                        startActivity(context, intent, null)
                    }
                }
            }

            addDialogName.setOnFocusChangeListener { _, hasFocus ->
                run {
                    if (!hasFocus && !addDialogName.text.isBlank())
                        addDialogName.error = null
                }
            }

            addDialogBirth.setOnFocusChangeListener { _, hasFocus ->
                run {
                    if (!hasFocus && !addDialogBirth.text.isBlank())
                        addDialogName.error = null
                }
            }
        }
    }

}