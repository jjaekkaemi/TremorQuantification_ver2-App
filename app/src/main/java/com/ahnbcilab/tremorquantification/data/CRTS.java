package com.ahnbcilab.tremorquantification.data;

public class CRTS {
    String patientId;
    String uid;
    String timestamp;
    int count;

    public CRTS(String patientId, String uid, String timestamp, int count) {
        this.patientId = patientId;
        this.uid = uid;
        this.timestamp = timestamp;
        this.count = count;
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
