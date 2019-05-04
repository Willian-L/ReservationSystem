package com.william.reservationsystem.SQLite;

public class Master extends Userinfo{
    // Set up the account of super administrator
    private final String SUPERUSERNAME = "admin";
    private final String SUPERPASSWORD = "abc123";

    public String getSUPERUSERNAME() {
        return SUPERUSERNAME;
    }

    public String getSUPERPASSWORD() {
        return SUPERPASSWORD;
    }
}
