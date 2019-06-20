package com.william.reservationsystem.model;

import java.sql.Date;

public class Bookings {
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getHava_soup() {
        return hava_soup;
    }

    public void setHava_soup(String hava_soup) {
        this.hava_soup = hava_soup;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    private String date;
    private String menu;
    private String user;
    private String hava_soup;
    private String remark;
}
