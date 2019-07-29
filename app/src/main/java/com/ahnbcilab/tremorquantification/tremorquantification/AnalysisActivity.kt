package com.ahnbcilab.tremorquantification.tremorquantification

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ahnbcilab.tremorquantification.functions.main

class AnalysisActivity : AppCompatActivity() {
    private val filename: String by lazy { intent.extras.getString("filename") }
    var path1 : String = ""
    var PatientId : String = ""
    var crts_count : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analysis)

        val intent = intent
        path1 = intent.getStringExtra("path1")
        PatientId = intent.getStringExtra("PatientId")
        crts_count = intent.getIntExtra("crts_count", -1)


        val dialog = ProgressDialog(this)
        dialog.setMessage("Analysing...")
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", DialogInterface.OnClickListener {dialog, which -> run {
            dialog.dismiss()
            val cancel_Intent = Intent(this, SpiralTestListActivity::class.java)
            cancel_Intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(cancel_Intent)
        }})
        dialog.show()

        var result = main.main("${this.filesDir.path}/testData/$filename")

        dialog.dismiss()

        val intent1 = Intent(this, SpiralResultActivity::class.java)
        intent1.putExtra("result", result)
        intent1.putExtra("path1", path1)
        intent1.putExtra("PatientId", PatientId)
        intent1.putExtra("crts_count", crts_count)
        startActivity(intent1)

    }


}
