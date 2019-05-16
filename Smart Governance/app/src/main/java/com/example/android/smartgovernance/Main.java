package com.example.android.smartgovernance;

/**
 * Created by Riddhi on 03-Apr-18.
 */

public class Main{
    private String name,title, phn, mail;

    public Main() {
    }

    public Main(String name, String title, String phn,String mail) {
        this.title = title;
        this.name=name;
        this.phn=phn;
        this.mail=mail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail =mail;
    }
    public String getPhn() {
        return phn;
    }

    public void setPhn(String phn) {
        this.phn =phn;
    }
}