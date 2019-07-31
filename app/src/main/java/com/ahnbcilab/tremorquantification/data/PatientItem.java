package com.ahnbcilab.tremorquantification.data;

public class PatientItem {

    String patientType ;
    String clinicID ;
    String patientName ;
    String dateFirst ;
    String dateFinal ;
    boolean deleteBox ;

    public PatientItem(String patientType, String clinicID, String patientName, String dateFirst, String dateFinal, boolean deleteBox){
        this.patientType = patientType ;
        this.clinicID = clinicID ;
        this.patientName = patientName ;
        this.dateFirst = dateFirst ;
        this.dateFinal= dateFinal ;
        this.deleteBox = deleteBox ;
    }

    public String getPatientType() {
        return patientType;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }

    public String getClinicID() {
        return clinicID;
    }

    public void setClinicID(String clinicID) {
        this.clinicID = clinicID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDateFirst() {
        return dateFirst;
    }

    public void setDateFirst(String dateFirst) {
        this.dateFirst = dateFirst;
    }

    public String getDateFinal() {
        return dateFinal;
    }

    public void setDateFinal(String dateFinal) {
        this.dateFinal = dateFinal;
    }

    public boolean isDeleteBox() {
        return deleteBox;
    }

    public void setDeleteBox(boolean deleteBox) {
        this.deleteBox = deleteBox;
    }
}