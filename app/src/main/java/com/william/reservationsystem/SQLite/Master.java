package com.william.reservationsystem.SQLite;

public class Master {
    private String username;
    private String password;

    // Set up the account of super administrator
    private final String SUPERUSERNAME = "admin";
    private final String SUPERPASSWORD = "abc123";


    public String getSUPERUSERNAME() {
        return SUPERUSERNAME;
    }

    public String getSUPERPASSWORD() {
        return SUPERPASSWORD;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
