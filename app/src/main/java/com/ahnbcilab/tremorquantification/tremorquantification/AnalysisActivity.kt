package com.ahnbcilab.tremorquantification.tremorquantification

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ahnbcilab.tremorquantification.functions.main
import com.ahnbcilab.tremorquantification.functions.main1

class AnalysisActivity : AppCompatActivity() {
    private val filename: String by lazy { intent.extras.getString("filename") }
    var path1 : String = ""
    var Clinic_ID : String = ""
    var PatientName : String = ""
    var task: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analysis)

        val intent = intent
        path1 = intent.getStringExtra("path1")
        Clinic_ID = intent.getStringExtra("Clinic_ID")
        PatientName = intent.getStringExtra("PatientName")
        task = intent.getStringExtra("task")
        var spiral_result = intent.getDoubleArrayExtra("spiral_result")
        var crts_num = intent.getStringExtra("crts_num")


        val dialog = ProgressDialog(this)
        dialog.setMessage("Analysing...")
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", DialogInterface.OnClickListener {dialog, which -> run {
            dialog.dismiss()
            val cancel_Intent = Intent(this, PatientListActivity::class.java)
            cancel_Intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(cancel_Intent)
        }})
        dialog.show()


        if(task.equals("SpiralTask")&&path1.equals("main")){
            spiral_result = main.main("${this.filesDir.path}/testData/$filename", applicationContext, Clinic_ID)
            dialog.dismiss()
            val intent1 = Intent(this, SpiralResultActivity::class.java)
            intent1.putExtra("spiral_result", spiral_result)
            intent1.putExtra("path1", path1)
            intent1.putExtra("Clinic_ID", Clinic_ID)
            intent1.putExtra("PatientName", PatientName)
            startActivity(intent1)
        }
        else if(task.equals("SpiralTask")&&path1.equals("CRTS")){

            spiral_result = main.main("${this.filesDir.path}/testData/$filename", applicationContext, Clinic_ID)
            dialog.dismiss()
            val intent1 = Intent(this, Line::class.java)
            intent1.putExtra("spiral_result", spiral_result)
            intent1.putExtra("path1", path1)
            intent1.putExtra("Clinic_ID", Clinic_ID)
            intent1.putExtra("PatientName", PatientName)
            intent1.putExtra("crts_num", crts_num)
            startActivity(intent1)
            finish()

        }
        else if(task.equals("LineTask")&&path1.equals("CRTS")){
            var result1 = main1.main1("${this.filesDir.path}/testData/$filename", applicationContext, Clinic_ID)
            dialog.dismiss()
            val intent1 = Intent(this, WritingResult::class.java)
            intent1.putExtra("line_result", result1)
            intent1.putExtra("path1", path1)
            intent1.putExtra("Clinic_ID", Clinic_ID)
            intent1.putExtra("PatientName", PatientName)
            intent1.putExtra("spiral_result", spiral_result)
            intent1.putExtra("crts_num", crts_num)
            startActivity(intent1)
            finish()
        }
        else{
            var result1 = main1.main1("${this.filesDir.path}/testData/$filename", applicationContext, Clinic_ID)
            dialog.dismiss()
            val intent1 = Intent(this, LineResultActivity::class.java)
            intent1.putExtra("line_result", result1)
            intent1.putExtra("path1", path1)
            intent1.putExtra("Clinic_ID", Clinic_ID)
            intent1.putExtra("PatientName", PatientName)
            startActivity(intent1)
            finish()
        }

    }


}
