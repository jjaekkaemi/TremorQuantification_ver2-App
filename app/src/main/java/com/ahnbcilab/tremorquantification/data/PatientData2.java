package com.ahnbcilab.tremorquantification.data;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class PatientData2 {
        String ClinicID;
        String ClinicName;
        int TaskNo;
        String UserID;


        public PatientData2(int TaskNo, String ClinicName,  String UserID, String ClinicID) {
                this.ClinicID = ClinicID ;
                this.ClinicName = ClinicName;
                this.TaskNo = TaskNo;
                this.UserID = UserID;

        }

        @Exclude
        public Map<String, Object> toMap() {
                HashMap<String, Object> result = new HashMap<>();
                result.put("ClinicID", ClinicID);
                result.put("ClinicName", ClinicName);
                result.put("TaskNo", TaskNo);
                result.put("UserID", UserID);
                return result;
        }
}