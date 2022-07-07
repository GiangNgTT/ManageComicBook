package com.example.projectcomic.model;

public class Admin {
    public String username;
    public String pass_word;

    public Admin(String username, String pass_word) {
        this.username = username;
        this.pass_word = pass_word;
    }

//Giang23
    public void setUsername(String username) {

        this.username = username;
    }
    public void setPass_word(String pass_word) {

        this.pass_word = pass_word;
    }
    public String getUsername() {
        return username;
    }
    public String getPass_word() {
        return pass_word;
    }
}
