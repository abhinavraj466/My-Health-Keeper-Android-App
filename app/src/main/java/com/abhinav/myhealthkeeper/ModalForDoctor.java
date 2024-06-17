package com.abhinav.myhealthkeeper;

import android.content.Context;

public class ModalForDoctor {

    String name, specialist;




    public ModalForDoctor() {
    }

    public ModalForDoctor(String name, String specialist) {
        this.name = name;
        this.specialist = specialist;
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


}
