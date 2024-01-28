package com.example.whatsapp;

public class CallsModel {
    int img, call_status_img, call_type_img;
    String name, time;

    public CallsModel(int img, String name, int call_status_img, String time, int call_type_img) {
        this.img = img;
        this.call_status_img = call_status_img;
        this.call_type_img = call_type_img;
        this.name = name;
        this.time = time;
    }
}
