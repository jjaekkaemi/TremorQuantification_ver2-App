package com.ahnbcilab.tremorquantification.tremorquantification

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.os.CountDownTimer
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.ahnbcilab.tremorquantification.data.CurrentUserData
import com.ahnbcilab.tremorquantification.data.PathTraceData
import com.ahnbcilab.tremorquantification.functions.Drawable
import com.ahnbcilab.tremorquantification.functions.fitting
import com.ahnbcilab.tremorquantification.functions.main
import com.ahnbcilab.tremorquantification.tremorquantification.R.drawable.view
import kotlinx.android.synthetic.main.activity_line_test.*
import kotlinx.android.synthetic.main.activity_writing.*
import java.io.File
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.*

class WritingActivity : AppCompatActivity() {

    var filename: String = ""
    var path1: String = ""
    var Clinic_ID: String = ""
    var PatientName: String = ""
    var path: String = ""

    private var currentX: Float = 0.toFloat()
    private var currentY: Float = 0.toFloat()

    private val pathTrace: MutableList<PathTraceData> = mutableListOf()
    private val timer = object : CountDownTimer(Long.MAX_VALUE, 1000 / 60) {
        override fun onTick(millisUntilFinished: Long) {
            pathTrace.add(PathTraceData(currentX, currentY, (Long.MAX_VALUE - millisUntilFinished).toInt()))
        }

        override fun onFinish() {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_writing)

        val intent = intent
        Clinic_ID = intent.getStringExtra("ClinicID")
        PatientName = intent.getStringExtra("PatientName")
        var crts_num = intent.getStringExtra("crts_num")


        filename = SimpleDateFormat("yyyyMMdd_HH_mm").format(Calendar.getInstance().time)


        val layout = writingcanvasLayout
        val view = DrawView(this)
        layout.addView(view)

        // 그림 그릴 때, 다시 그리는 버튼
        writingagain.setOnClickListener {
            timer.cancel()
            view.clearLayout()
            val intent = Intent(this, WritingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            intent.putExtra("ClinicID", Clinic_ID)
            intent.putExtra("PatientName", PatientName)
            intent.putExtra("crts_num", crts_num)
            startActivity(intent)
        }

        // 그림 그리고 나서, 다음으로 넘어가는 버튼
        writingfinish.setOnClickListener {
            timer.cancel()
            //view.saveAsJPG(view, this.filesDir.path + "/spiralTest", "${patientId}_$filename.jpg")
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            var prevData: PathTraceData? = null

            if (pathTrace.size > 2) {
                prevData = pathTrace[pathTrace.size - 1]
                for (i in (pathTrace.size - 2) downTo 0) {
                    if (prevData.isSamePosition(pathTrace[i]))
                        pathTrace.removeAt(i)
                    else
                        break
                }
            }

            val metaData = "${CurrentUserData.user?.uid},$Clinic_ID,$filename"
            val path = File("${this.filesDir.path}/testData") // raw save to file dir(data/com.bcilab....)
            if (!path.exists()) path.mkdirs()
            val file = File(path, "${Clinic_ID}_$filename.csv")
            try {
                PrintWriter(file).use { out ->
                    out.println(metaData)
                    for (item in pathTrace)
                        out.println(item.joinToString(","))
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Error on writing file", Toast.LENGTH_LONG).show()
                println(e.message)
            }

            val intent = Intent(this, Spiral::class.java)
            intent.putExtra("Clinic_ID", Clinic_ID);
            intent.putExtra("PatientName", PatientName);
            intent.putExtra("path", "CRTS");
            intent.putExtra("crts_num", crts_num)
            startActivity(intent)
            Toast.makeText(this, "Wait...", Toast.LENGTH_LONG).show()
            finish()
        }
    }


    inner class DrawView(context: Context) : Drawable(context) {
        private var flag = false

        override fun onTouchEvent(event: MotionEvent): Boolean {
            currentX = event.x
            currentY = event.y

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (!flag) {
                        flag = true
                        timer.start()
                    }
                }
            }
            return super.onTouchEvent(event)
        }

        override fun clearLayout() {
            super.clearLayout()
            pathTrace.clear()
            timer.cancel()
        }
    }


}