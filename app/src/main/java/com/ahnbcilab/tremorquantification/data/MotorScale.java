package com.ahnbcilab.tremorquantification.data;

public class MotorScale {
    String patientId;
    String uid;
    String timestamp;
    int count;
    int motorScale_score;

    public MotorScale(String patientId, String uid, String timestamp, int count, int motorScale_score) {
        this.patientId = patientId;
        this.uid = uid;
        this.timestamp = timestamp;
        this.count = count;
        this.motorScale_score = motorScale_score;
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

    public int getMotorScale_score() {
        return motorScale_score;
    }
}
