package com.ahnbcilab.tremorquantification.Adapters

import android.content.Context
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ArrayAdapter
import com.ahnbcilab.tremorquantification.tremorquantification.Patient
import com.ahnbcilab.tremorquantification.tremorquantification.R
import com.github.mikephil.charting.utils.Utils.init
import com.google.firebase.auth.FirebaseAuth
import android.R.attr.data



class PatientCardAdapter(context: Context, resource: Int, list: ArrayList<Patient>): ArrayAdapter<Patient>(context, resource, list) {
    val user = FirebaseAuth.getInstance().currentUser

    private var mResource: Int = 0
    private var mList: ArrayList<Patient>
    private var mLayoutInflator: LayoutInflater
    private var mContext: Context = context
    private var uid: String?= null



    init{
        this.mResource = resource
        this.mList = list
        this.mLayoutInflator = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View?{
        val returnView: View?
        if(convertView == null){
            returnView = try{
                mLayoutInflator.inflate(mResource, null)
            } catch(e: Exception){
                e.printStackTrace()
                View(context)
            }
            setUI(returnView, position)
            return returnView
        }
        setUI(convertView, position)
        return convertView
    }

    private fun setUI(view: View, position: Int){
        uid = user?.getUid()

        //val patient: Patient? = if(mList[position].uid == uid) getItem(position) else null
        val patient: Patient? = if(count> position) getItem(position) else null
        val patientName: TextView? = view.findViewById(R.id.patientName)
        patientName?.text = patient?.id ?: ""

        val patientDes: TextView? = view.findViewById(R.id.patientDescription)
        patientDes?.text = patient?.description ?: ""

        val DoctorUid: TextView? = view.findViewById(R.id.Doctor_Uid)
        DoctorUid?.text = patient?.uid ?: ""

        //val patientAge: TextView? = view.findViewById(R.id.patient_card_age)
        //patientAge?.text = patient?.age ?: ""
    }
}