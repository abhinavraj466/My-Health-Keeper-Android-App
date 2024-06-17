package com.abhinav.myhealthkeeper;

public class ModalForDoctorMyAppointment {

   // String name, time, aptMonth, aptDay, aptDayName;

    String name, date, userId, docId, age, address, mobile, gender, parentId;

    public ModalForDoctorMyAppointment() {
    }

    public ModalForDoctorMyAppointment(String name, String date, String userId, String docId, String age, String address, String mobile, String gender, String parentId) {
        this.name = name;
        this.date = date;
        this.userId = userId;
        this.docId = docId;
        this.age = age;
        this.address = address;
        this.mobile = mobile;
        this.gender = gender;
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}