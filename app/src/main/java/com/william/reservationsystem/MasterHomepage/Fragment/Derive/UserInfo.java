package com.william.reservationsystem.MasterHomepage.Fragment.Derive;

public class UserInfo {
    private int id;
    private String username;
    private String password;
    private String name;
    private String sex;
    private String age;
    private String phone;
    private String email;
    private String address;

    public UserInfo(int id, String username, String password, String name, String sex, String age, String phone, String email, String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    @Override
    public String toString() {
        String string = null;
        string = "{\"id\": \"" + id +
                "\", \"username\": \"" + username +
                "\", \"password\": \"" + password +
                "\", \"name\": \"" + name +
                "\", \"sex\": \"" + sex +
                "\", \"age\": \"" + age +
                "\", \"phone\": \"" + phone +
                "\", \"email\": \"" + email +
                "\", \"address\": \"" + address + "\"}'";
        return string;
    }
}
