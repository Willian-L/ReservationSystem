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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    private String date;
    private String menu;
    private String user;

    public String getDishes_one() {
        return dishes_one;
    }

    public void setDishes_one(String dishes_one) {
        this.dishes_one = dishes_one;
    }

    public String getDishes_two() {
        return dishes_two;
    }

    public void setDishes_two(String dishes_two) {
        this.dishes_two = dishes_two;
    }

    public String getDishes_three() {
        return dishes_three;
    }

    public void setDishes_three(String dishes_three) {
        this.dishes_three = dishes_three;
    }

    public String getDishes_four() {
        return dishes_four;
    }

    public void setDishes_four(String dishes_four) {
        this.dishes_four = dishes_four;
    }

    public String getSoup() {
        return soup;
    }

    public void setSoup(String soup) {
        this.soup = soup;
    }

    private String dishes_one;
    private String dishes_two;
    private String dishes_three;
    private String dishes_four;
    private String soup;
    private String remark;
}
