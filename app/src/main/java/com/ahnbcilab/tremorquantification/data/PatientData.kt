package com.ahnbcilab.tremorquantification.data;


import java.util.*
import java.io.Serializable

data class PatientData(var name: String, var sex: Int?, var birth: Int, var description: String?) : Serializable{

        var uid : String = ""
        var age = Calendar.getInstance().get(Calendar.YEAR) - birth!!.toInt()/10000 + 1
        var patientId : String = ""

        companion object {
                const val MALE = 1
                const val FEMALE = 2
        }


}