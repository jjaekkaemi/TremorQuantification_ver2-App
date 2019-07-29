package com.ahnbcilab.tremorquantification.tremorquantification

import com.google.firebase.database.DataSnapshot

class Patient(snapshot: DataSnapshot){
    lateinit var id: String
    lateinit var name: String
    lateinit var description: String
    lateinit var uid: String


    init{
        try{
            val data: HashMap<String, Any> = snapshot.value as HashMap<String, Any>
            id = snapshot.key ?: ""
            name = data["name"] as String
            description = data["description"] as String
            uid = data["uid"] as String
        } catch(e: Exception){
            e.printStackTrace()
        }
    }
}