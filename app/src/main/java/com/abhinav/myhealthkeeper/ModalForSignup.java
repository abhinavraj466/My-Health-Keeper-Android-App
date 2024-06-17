package com.abhinav.myhealthkeeper;

public class ModalForSignup {

    String email, name, password, confirm_password, userId, role;

    public ModalForSignup(String email, String name, String password, String confirm_password, String userId, String role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.confirm_password = confirm_password;
        this.userId = userId;
        this.role = role;
    }

    public ModalForSignup(){}

    // Signup Constuctor

    public ModalForSignup(String email, String name, String password, String role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole(){
        return  role;
    }

    public  void setRole(String role){
        this.role = role;
    }
}
