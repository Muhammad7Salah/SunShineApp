package com.example.muhammadsalah.nesmaweatherapp;

/**
 * Created by Muhammad Salah on 11/27/2017.
 */

public class Item {
    public String day;
    public String status;
    public int img;
    public String upper;
    public String lower;

    public Item(String day, String status, int img, String upper, String lower) {
        this.day = day;
        this.status = status;
        this.img = img;
        this.upper = upper;
        this.lower = lower;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getUpper() {
        return upper;
    }

    public void setUpper(String upper) {
        this.upper = upper;
    }

    public String getLower() {
        return lower;
    }

    public void setLower(String lower) {
        this.lower = lower;
    }
}