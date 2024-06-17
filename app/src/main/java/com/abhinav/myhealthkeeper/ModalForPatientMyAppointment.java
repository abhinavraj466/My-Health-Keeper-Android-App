package com.abhinav.myhealthkeeper;

public class ModalForPatientMyAppointment {

    String name, specialist, date, userId, docId;

    public ModalForPatientMyAppointment() {
    }

    public ModalForPatientMyAppointment(String name, String specialist, String date, String userId) {
        this.name = name;
        this.specialist = specialist;
        this.date = date;
        this.userId = userId;
        this.docId = docId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}