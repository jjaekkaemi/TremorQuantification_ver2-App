package com.ahnbcilab.tremorquantification.tremorquantification

import com.google.firebase.database.DataSnapshot

class PersonalPatientData(snapshot: DataSnapshot){
    lateinit var Task_No: String
    lateinit var Count_No: String


    init{
        try{
            val data: HashMap<String, Any> = snapshot.value as HashMap<String, Any>
            Task_No = data["task"] as String
            Count_No = data["count"] as String
        } catch(e: Exception){
            e.printStackTrace()
        }
    }
}