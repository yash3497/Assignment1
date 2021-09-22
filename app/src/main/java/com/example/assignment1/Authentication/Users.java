package com.example.assignment1.Authentication;

public class Users {
    String Email,Password,UserId;

    public Users() {}

    public Users( String email, String password, String userId) {
        Email = email;
        Password = password;
        UserId = userId;
    }


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
