package com.william.reservationsystem.model;

public class Master extends MutualInformation {
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
