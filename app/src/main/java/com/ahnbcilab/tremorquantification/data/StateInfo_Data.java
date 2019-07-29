package com.ahnbcilab.tremorquantification.data;

public class StateInfo_Data {
    String drug_Indenol;
    String drug_Primidone;
    String comment;
    String patientId;
    String uid;
    String timestamp;
    int count;

    public StateInfo_Data(String drug_Indenol, String drug_Primidone, String comment, String patientId, String uid, String timestamp, int count) {
        this.drug_Indenol = drug_Indenol;
        this.drug_Primidone = drug_Primidone;
        this.comment = comment;
        this.patientId = patientId;
        this.uid = uid;
        this.timestamp = timestamp;
        this.count = count;
    }

    public String getDrug_Indenol() {
        return drug_Indenol;
    }

    public String getDrug_Primidone() {
        return drug_Primidone;
    }

    public String getComment() {
        return comment;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getUid() {
        return uid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getCount() {
        return count;
    }
}
