package com.example.my_project;

public class Users {
    int id;
    String UserName;
    String FullName;
    String EmailAdress;
    String PhoneNumber;
    String Password;

    public Users(int id, String UserName, String FullName, String EmailAdress, String PhoneNumber, String password){
        this.id = id;
        this.UserName = UserName;
        this.FullName = FullName;
        this.EmailAdress = EmailAdress;
        this.PhoneNumber = PhoneNumber;
        this.Password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public void setEmailAdress(String emailAdress) {
        EmailAdress = emailAdress;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        Password = password;
    }


    public int getId() {
        return id;
    }

    public String getUserName() {
        return UserName;
    }

    public String getFullName() {
        return FullName;
    }

    public String getEmailAdress() {
        return EmailAdress;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getPassword() {
        return Password;
    }

}
