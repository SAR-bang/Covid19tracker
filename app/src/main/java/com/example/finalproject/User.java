package com.example.finalproject;

public class User {

    private String name, emailaddress, dateofbirth;

    public User(String name, String emailaddress, String dateofbirth) {
        this.name = name;
        this.emailaddress = emailaddress;
        this.dateofbirth = dateofbirth;
    }

    public String getName() {
        return name;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }
}
