package com.ahnbcilab.tremorquantification.tremorquantification;

public class PatientItem {

    String patientType ;
    String clinicID ;
    String patientName ;
    String dateFirst ;
    String dateFinal ;


    public PatientItem(String patientType, String clinicID, String patientName, String dateFirst, String dateFinal){
        this.patientType = patientType ;
        this.clinicID = clinicID ;
        this.patientName = patientName ;
        this.dateFirst = dateFirst ;
        this.dateFinal= dateFinal ;

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

}
