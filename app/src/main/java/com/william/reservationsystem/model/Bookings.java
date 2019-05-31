package com.william.reservationsystem.model;

import java.sql.Date;

public class Bookings {
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public int getHava_soup() {
        return hava_soup;
    }

    public void setHava_soup(int hava_soup) {
        this.hava_soup = hava_soup;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    private Date date;
    private String menu;
    private String user;
    private int hava_soup;
    private String remark;
}
