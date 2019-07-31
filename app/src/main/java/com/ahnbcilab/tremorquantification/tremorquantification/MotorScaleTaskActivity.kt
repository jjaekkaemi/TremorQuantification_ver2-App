package com.ahnbcilab.tremorquantification.tremorquantification

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.ahnbcilab.tremorquantification.data.*
import com.ahnbcilab.tremorquantification.tremorquantification.R.style.survey
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_motor_scale_task.*
import java.text.SimpleDateFormat
import java.util.*
import android.app.Activity
import android.content.SharedPreferences
import android.widget.RadioButton





class MotorScaleTaskActivity : AppCompatActivity() {
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    var motorScale_score : Int = 0
    var patientId : String = ""
    var path : String = ""
    var uid : String = ""
    var bool : Boolean = true
    var count : Int = 0
    var motor_count : Int = 0
    var smotor_count : String = ""
    var a : Int = 0
    var b : Int = 0
    var c : Int = 0
    var d : Int = 0
    var e : Int = 0
    var f : Int = 0
    var g : Int = 0
    var h : Int = 0
    var i1 : Int = 0
    var j : Int = 0
    var k : Int = 0
    var l : Int = 0
    var m : Int = 0
    var n : Int = 0
    var n1 : Int = 0
    var o : Int = 0
    var p : Int = 0
    var q : Int = 0
    var r : Int = 0
    var s : Int = 0
    var t : Int = 0
    var u : Int = 0
    var v : Int = 0
    var w : Int = 0
    var x : Int = 0
    var y : Int = 0
    var z : Int = 0
    var sa : Int = 0
    var sb : Int = 0
    var sc : Int = 0
    var sd : Int = 0
    var se : Int = 0
    var sf : Int = 0
    var sg : Int = 0
    var sh : Int = 0
    var si1 : Int = 0
    var sj : Int = 0
    var sk : Int = 0
    var sl : Int = 0
    var sm : Int = 0
    var sn : Int = 0
    var sn1 : Int = 0
    var so : Int = 0
    var sp : Int = 0
    var sq : Int = 0
    var sr : Int = 0
    var ss : Int = 0
    var st : Int = 0
    var su : Int = 0
    var sv : Int = 0
    var sw : Int = 0
    var sx : Int = 0
    var sy : Int = 0
    var sz : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motor_scale_task)

        val intent = intent
        patientId = intent.getStringExtra("patientId")
        uid = intent.getStringExtra("doc_uid")

        val databaseMotorScale = firebaseDatabase.getReference("MotorScale_List")

        databaseMotorScale.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                motor_count = dataSnapshot.childrenCount.toInt()
                if(motor_count < 10){
                    smotor_count = "0" + motor_count
                }
                else{
                    smotor_count = motor_count.toString()
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        Motorscale_submit.setOnClickListener(){
            if(CBpart3_1_0.isChecked){
                a = 0
            }
            else if(CBpart3_1_1.isChecked){
                a = 1
            }
            else if(CBpart3_1_2.isChecked){
                a = 2
            }
            else if(CBpart3_1_3.isChecked){
                a = 3
            }
            else if(CBpart3_1_4.isChecked){
                a = 4
            }
            else {
                a = -1
                bool = false
            }
            sa = a
            if(sa == -1){
                sa = 0
            }

            if(CBpart3_2_0.isChecked){
                b = 0
            }
            else if(CBpart3_2_1.isChecked){
                b = 1
            }
            else if(CBpart3_2_2.isChecked){
                b = 2
            }
            else if(CBpart3_2_3.isChecked){
                b = 3
            }
            else if(CBpart3_2_4.isChecked){
                b = 4
            }
            else {
                b = -1
                bool = false
            }
            sb = b
            if(sb == -1){
                sb = 0
            }

            if(CBpart3_3_0.isChecked){
                c = 0
            }
            else if(CBpart3_3_1.isChecked){
                c = 1
            }
            else if(CBpart3_3_2.isChecked){
                c = 2
            }
            else if(CBpart3_3_3.isChecked){
                c = 3
            }
            else if(CBpart3_3_4.isChecked){
                c = 4
            }
            else {
                c = -1
                bool = false
            }
            sc = c
            if(sc == -1){
                sc = 0
            }

            if(CBpart3_4_0.isChecked){
                d = 0
            }
            else if(CBpart3_4_1.isChecked){
                d = 1
            }
            else if(CBpart3_4_2.isChecked){
                d = 2
            }
            else if(CBpart3_4_3.isChecked){
                d = 3
            }
            else if(CBpart3_4_4.isChecked){
                d = 4
            }
            else {
                d = -1
                bool = false
            }
            sd = d
            if(sd == -1){
                sd = 0
            }

            if(CBpart3_5_0.isChecked){
                e = 0
            }
            else if(CBpart3_5_1.isChecked){
                e = 1
            }
            else if(CBpart3_5_2.isChecked){
                e = 2
            }
            else if(CBpart3_5_3.isChecked){
                e = 3
            }
            else if(CBpart3_5_4.isChecked){
                e = 4
            }
            else {
                e = -1
                bool = false
            }
            se = e
            if(se == -1){
                se = 0
            }

            if(CBpart3_6_0.isChecked){
                f = 0
            }
            else if(CBpart3_6_1.isChecked){
                f = 1
            }
            else if(CBpart3_6_2.isChecked){
                f = 2
            }
            else if(CBpart3_6_3.isChecked){
                f = 3
            }
            else if(CBpart3_6_4.isChecked){
                f = 4
            }
            else {
                f = -1
                bool = false
            }
            sf = f
            if(sf == -1){
                sf = 0
            }

            if(CBpart3_7_0.isChecked){
                g = 0
            }
            else if(CBpart3_7_1.isChecked){
                g = 1
            }
            else if(CBpart3_7_2.isChecked){
                g = 2
            }
            else if(CBpart3_7_3.isChecked){
                g = 3
            }
            else if(CBpart3_7_4.isChecked){
                g = 4
            }
            else {
                g = -1
                bool = false
            }
            sg = g
            if(sg == -1){
                sg = 0
            }


            if(CBpart3_8_0.isChecked){
                h = 0
            }
            else if(CBpart3_8_1.isChecked){
                h = 1
            }
            else if(CBpart3_8_2.isChecked){
                h = 2
            }
            else if(CBpart3_8_3.isChecked){
                h = 3
            }
            else if(CBpart3_8_4.isChecked){
                h = 4
            }
            else {
                h = -1
                bool = false
            }
            sh = h
            if(sh == -1){
                sh = 0
            }

            if(CBpart3_9_0.isChecked){
                i1 = 0
            }
            else if(CBpart3_9_1.isChecked){
                i1 = 1
            }
            else if(CBpart3_9_2.isChecked){
                i1 = 2
            }
            else if(CBpart3_9_3.isChecked){
                i1 = 3
            }
            else if(CBpart3_9_4.isChecked){
                i1 = 4
            }
            else {
                i1 = -1
                bool = false
            }
            si1 = i1
            if(si1 == -1){
                si1 = 0
            }

            if(CBpart3_10_0.isChecked){
                j = 0
            }
            else if(CBpart3_10_1.isChecked){
                j = 1
            }
            else if(CBpart3_10_2.isChecked){
                j = 2
            }
            else if(CBpart3_10_3.isChecked){
                j = 3
            }
            else if(CBpart3_10_4.isChecked){
                j = 4
            }
            else {
                j = -1
                bool = false
            }
            sj = j
            if(sj == -1){
                sj = 0
            }

            if(CBpart3_11_0.isChecked){
                k = 0
            }
            else if(CBpart3_11_1.isChecked) {
                k = 1
            }
            else if(CBpart3_11_2.isChecked){
                k = 2
            }
            else if(CBpart3_11_3.isChecked){
                k = 3
            }
            else if(CBpart3_11_4.isChecked){
                k = 4
            }
            else {
                k = -1
                bool = false
            }
            sk = k
            if(sk == -1){
                sk = 0
            }

            if(CBpart3_12_0.isChecked){
                l = 0
            }
            else if(CBpart3_12_1.isChecked){
                l = 1
            }
            else if(CBpart3_12_2.isChecked){
                l = 2
            }
            else if(CBpart3_12_3.isChecked){
                l = 3
            }
            else if(CBpart3_12_4.isChecked){
                l = 4
            }
            else {
                l = -1
                bool = false
            }
            sl = l
            if(sl == -1){
                sl = 0
            }

            if(CBpart3_13_0.isChecked){
                m = 0
            }
            else if(CBpart3_13_1.isChecked){
                m = 1
            }
            else if(CBpart3_13_2.isChecked){
                m = 2
            }
            else if(CBpart3_13_3.isChecked){
                m = 3
            }
            else if(CBpart3_13_4.isChecked){
                m = 4
            }
            else {
                m == -1
                bool = false
            }
            sm = m
            if(sm == -1){
                sm = 0
            }

            if(CBpart3_14_0.isChecked){
                n = 0
            }
            else if(CBpart3_14_1.isChecked){
                n = 1
            }
            else if(CBpart3_14_2.isChecked){
                n = 2
            }
            else if(CBpart3_14_3.isChecked){
                n = 3
            }
            else if(CBpart3_14_4.isChecked){
                n = 4
            }
            else {
                n = -1
                bool = false
            }
            sn = n
            if(sn == -1){
                sn = 0
            }

            if(CBpart3_15_0.isChecked){
                n1 = 0
            }
            else if(CBpart3_15_1.isChecked){
                n1 = 1
            }
            else if(CBpart3_15_2.isChecked){
                n1 = 2
            }
            else if(CBpart3_15_3.isChecked){
                n1 = 3
            }
            else if(CBpart3_15_4.isChecked){
                n1 = 4
            }
            else {
                n1 = -1
                bool = false
            }
            sn1 = n1
            if(sn1 == -1){
                sn1 = 0
            }

            if(CBpart3_16_0.isChecked){
                o = 0
            }
            else if(CBpart3_16_1.isChecked){
                o = 1
            }
            else if(CBpart3_16_2.isChecked){
                o = 2
            }
            else if(CBpart3_16_3.isChecked){
                o = 3
            }
            else if(CBpart3_16_4.isChecked){
                o = 4
            }
            else {
                o = -1
                bool = false
            }
            so = o
            if(so == -1){
                so = 0
            }

            if(CBpart3_17_0.isChecked){
                p = 0
            }
            else if(CBpart3_17_1.isChecked){
                p = 1
            }
            else if(CBpart3_17_2.isChecked){
                p = 2
            }
            else if(CBpart3_17_3.isChecked){
                p = 3
            }
            else if(CBpart3_17_4.isChecked){
                p = 4
            }
            else {
                p = -1
                bool = false
            }
            sp = p
            if(sp == -1){
                sp = 0
            }

            if(CBpart3_18_0.isChecked){
                q = 0
            }
            else if(CBpart3_18_1.isChecked){
                q = 1
            }
            else if(CBpart3_18_2.isChecked){
                q = 2
            }
            else if(CBpart3_18_3.isChecked){
                q = 3
            }
            else if(CBpart3_18_4.isChecked){
                q = 4
            }
            else {
                q = -1
                bool = false
            }
            sq = q
            if(sq == -1){
                sq = 0
            }

            if(CBpart3_19_0.isChecked){
                r = 0
            }
            else if(CBpart3_19_1.isChecked){
                r = 1
            }
            else if(CBpart3_19_2.isChecked){
                r = 2
            }
            else if(CBpart3_19_3.isChecked){
                r = 3
            }
            else if(CBpart3_19_4.isChecked){
                r = 4
            }
            else {
                r = -1
                bool = false
            }
            sr = r
            if(sr == -1){
                sr = 0
            }

            if(CBpart3_20_0.isChecked){
                s = 0
            }
            else if(CBpart3_20_1.isChecked){
                s = 1
            }
            else if(CBpart3_20_2.isChecked){
                s = 2
            }
            else if(CBpart3_20_3.isChecked){
                s = 3
            }
            else if(CBpart3_20_4.isChecked){
                s = 4
            }
            else {
                s = -1
                bool = false
            }
            ss = s
            if(ss == -1){
                ss = 0
            }

            if(CBpart3_21_0.isChecked){
                t = 0
            }
            else if(CBpart3_21_1.isChecked){
                t = 1
            }
            else if(CBpart3_21_2.isChecked){
                t = 2
            }
            else if(CBpart3_21_3.isChecked){
                t = 3
            }
            else if(CBpart3_21_4.isChecked){
                t = 4
            }
            else {
                t = -1
                bool = false
            }
            st = t
            if(st == -1){
                st = 0
            }

            if(CBpart3_22_0.isChecked){
                u = 0
            }
            else if(CBpart3_22_1.isChecked){
                u = 1
            }
            else if(CBpart3_22_2.isChecked){
                u = 2
            }
            else if(CBpart3_22_3.isChecked){
                u = 3
            }
            else if(CBpart3_22_4.isChecked){
                u = 4
            }
            else {
                u = -1
                bool = false
            }
            su = u
            if(su == -1){
                su = 0
            }

            if(CBpart3_23_0.isChecked){
                v = 0
            }
            else if(CBpart3_23_1.isChecked){
                v = 1
            }
            else if(CBpart3_23_2.isChecked){
                v = 2
            }
            else if(CBpart3_23_3.isChecked){
                v = 3
            }
            else if(CBpart3_23_4.isChecked){
                v = 4
            }
            else {
                v = -1
                bool = false
            }
            sv = v
            if(sv == -1){
                sv = 0
            }

            if(CBpart3_24_0.isChecked){
                w = 0
            }
            else if(CBpart3_24_1.isChecked){
                w = 1
            }
            else if(CBpart3_24_2.isChecked){
                w = 2
            }
            else if(CBpart3_24_3.isChecked){
                w = 3
            }
            else if(CBpart3_24_4.isChecked){
                w = 4
            }
            else {
                sw = -1
                bool = false
            }
            sw = w
            if(sw == -1){
                sw = 0
            }

            if(CBpart3_25_0.isChecked){
                x = 0
            }
            else if(CBpart3_25_1.isChecked){
                x = 1
            }
            else if(CBpart3_25_2.isChecked){
                x = 2
            }
            else if(CBpart3_25_3.isChecked){
                x = 3
            }
            else if(CBpart3_25_4.isChecked){
                x = 4
            }
            else {
                x = -1
                bool = false
            }
            sx = x
            if(sx == -1){
                sx = 0
            }


            if(CBpart3_26_0.isChecked){
                y = 0
            }
            else if(CBpart3_26_1.isChecked){
                y = 1
            }
            else if(CBpart3_26_2.isChecked){
                y = 2
            }
            else if(CBpart3_26_3.isChecked){
                y = 3
            }
            else if(CBpart3_26_4.isChecked){
                y = 4
            }
            else {
                y = -1
                bool = false
            }
            sy = y
            if(sy == -1){
                sy = 0
            }

            if(CBpart3_27_0.isChecked){
                z = 0
            }
            else if(CBpart3_27_1.isChecked){
                z = 1
            }
            else if(CBpart3_27_2.isChecked){
                z = 2
            }
            else if(CBpart3_27_3.isChecked){
                z = 3
            }
            else if(CBpart3_27_4.isChecked){
                z = 4
            }
            else {
                z = -1
                bool = false
            }
            sz = z
            if(sz == -1){
                sz = 0
            }

            if(bool == false){
                val dlg = AlertDialog.Builder(this)
                dlg.setTitle("미체크 문항")
                        .setMessage("체크되지 않은 문항이 있습니다\n" +
                                "다음 화면으로 넘어가시겠습니까?")
                        .setPositiveButton("네") { dialogInterface, i ->
                            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
                            val timestamp = sdf.format(Date())
                            val motorScale = MotorScale_Data(a, b, c, d, e, f, g, h, i1, j, k, l, m, n, n1, o, p, q, r, s, t, u, v, w, x, y, z)

                            motorScale_score = sa+sb+sc+sd+se+sf+sg+sh+si1+sj+sk+sl+sm+sn+sn1+so+sp+sq+sr+ss+st+su+sv+sw+sx+sy+sz

                            val motor = MotorScale(patientId, uid, timestamp, motor_count, motorScale_score)

                            databaseMotorScale.child("Task No "+smotor_count).setValue(motor).addOnCompleteListener {
                                Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
                            }

                            databaseMotorScale.child("Task No "+smotor_count).child("MotorScale_task").setValue(motorScale).addOnCompleteListener {
                                Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
                            }

                            val intent = Intent(this, SurveyListActivity::class.java)
                            startActivity(intent)
                            finish()

                        }
                        .setNegativeButton("아니요") { dialogInterface, i ->
                            bool = true;
                        }
                        .show()
            }
            else{
                val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
                val timestamp = sdf.format(Date())
                val motorScale = MotorScale_Data(a, b, c, d, e, f, g, h, i1, j, k, l, m, n, n1, o, p, q, r, s, t, u, v, w, x, y, z)

                motorScale_score = sa+sb+sc+sd+se+sf+sg+sh+si1+sj+sk+sl+sm+sn+sn1+so+sp+sq+sr+ss+st+su+sv+sw+sx+sy+sz

                val motor = MotorScale(patientId, uid, timestamp, motor_count, motorScale_score)

                databaseMotorScale.child("Task No "+smotor_count).setValue(motor).addOnCompleteListener {
                    Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
                }

                databaseMotorScale.child("Task No "+smotor_count).child("MotorScale_task").setValue(motorScale).addOnCompleteListener {
                    Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
                }

                val intent = Intent(this, SurveyListActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                startActivity(intent)
                finish()


            }

        }

    }
}
