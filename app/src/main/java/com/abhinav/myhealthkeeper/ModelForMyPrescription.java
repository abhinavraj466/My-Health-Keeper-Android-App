package com.abhinav.myhealthkeeper;

public class ModelForMyPrescription {

    String docName, docId, date, patId, patName;

    public ModelForMyPrescription() {
    }


    public ModelForMyPrescription(String docName, String docId, String date, String patId, String patName) {
        this.docName = docName;
        this.docId = docId;
        this.date = date;
        this.patId = patId;
        this.patName = patName;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }
}

