package com.example.whatsapp.Model;

public class User {
    String profilepic;
    String userName;
    String mail;
    String password;
    String lastMsg;
    String about = "";

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    String userId;

    public  User(){}

    //SignUp Constructor
    public User(String userName, String mail, String password) {
        this.userName = userName;
        this.mail = mail;
        this.password = password;
    }

    public User(String profilepic, String userName, String mail, String password, String lastMsg) {
        this.profilepic = profilepic;
        this.userName = userName;
        this.mail = mail;
        this.password = password;
        this.lastMsg = lastMsg;
    }

    public void setProfilePic(String profilepic) {
        this.profilepic = profilepic;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAbout() {
        return about;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public String getProfilePic() {
        return profilepic;
    }

    public String getUserName() {
        return userName;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getLastMsg() {
        return lastMsg;
    }
}
