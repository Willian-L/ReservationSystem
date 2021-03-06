package com.william.reservationsystem.model;

public class DataDailyMenu {
    public static final int PARENT_ITEM = 0;
    public static final int CHILD_ITEM = 1;
    private String CHILD_MENU_ONE = "MENU_ONE";
    private String CHILD_MENU_TWO = "MENU_TWO";

    private int type;
    private boolean isExpand;
    private DataDailyMenu childMenu;
    private boolean TwoView;

    private String parentCountData;
    private String ID;
    private String date;
    private String count;
    private String soupCount;
    private String childOneDis_one;
    private String childOneDis_two;
    private String childOneDis_three;
    private String childOneDis_four;
    private String childOne_Soup;
    private String childOneCount;
    private String childOneSoupCount;
    private String childTwoDis_one;
    private String childTwoDis_two;
    private String childTwoDis_three;
    private String childTwoDis_four;
    private String childTwo_Soup;
    private String childTwoSoupCount;
    private String childTwoCount;

    public boolean isTwoView() {
        return TwoView;
    }

    public void setTwoView(boolean twoView) {
        TwoView = twoView;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public DataDailyMenu getChildMenu() {
        return childMenu;
    }

    public void setChildMenu(DataDailyMenu childMenu) {
        this.childMenu = childMenu;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getParentCountData() {
        return parentCountData;
    }

    public void setParentCountData(String parentCountData) {
        this.parentCountData = parentCountData;
    }

    public String getCHILD_MENU_ONE() {
        return CHILD_MENU_ONE;
    }

    public String getChildOneDis_one() {
        return childOneDis_one;
    }

    public void setChildOneDis_one(String childOneDis_one) {
        this.childOneDis_one = childOneDis_one;
    }

    public String getChildOneDis_two() {
        return childOneDis_two;
    }

    public void setChildOneDis_two(String childOneDis_two) {
        this.childOneDis_two = childOneDis_two;
    }

    public String getChildOneDis_three() {
        return childOneDis_three;
    }

    public void setChildOneDis_three(String childOneDis_three) {
        this.childOneDis_three = childOneDis_three;
    }

    public String getChildOneDis_four() {
        return childOneDis_four;
    }

    public void setChildOneDis_four(String childOneDis_four) {
        this.childOneDis_four = childOneDis_four;
    }

    public String getChildOne_Soup() {
        return childOne_Soup;
    }

    public void setChildOne_Soup(String childOne_Soup) {
        this.childOne_Soup = childOne_Soup;
    }

    public String getChildOneCount() {
        return childOneCount;
    }

    public void setChildOneCount(String childOneCount) {
        this.childOneCount = childOneCount;
    }

    public String getCHILD_MENU_TWO() {
        return CHILD_MENU_TWO;
    }

    public String getChildTwoDis_one() {
        return childTwoDis_one;
    }

    public void setChildTwoDis_one(String childTwoDis_one) {
        this.childTwoDis_one = childTwoDis_one;
    }

    public String getChildTwoDis_two() {
        return childTwoDis_two;
    }

    public void setChildTwoDis_two(String childTwoDis_two) {
        this.childTwoDis_two = childTwoDis_two;
    }

    public String getChildTwoDis_three() {
        return childTwoDis_three;
    }

    public void setChildTwoDis_three(String childTwoDis_three) {
        this.childTwoDis_three = childTwoDis_three;
    }

    public String getChildTwoDis_four() {
        return childTwoDis_four;
    }

    public void setChildTwoDis_four(String childTwoDis_four) {
        this.childTwoDis_four = childTwoDis_four;
    }

    public String getChildTwo_Soup() {
        return childTwo_Soup;
    }

    public void setChildTwo_Soup(String childTwo_Soup) {
        this.childTwo_Soup = childTwo_Soup;
    }

    public String getChildTwoCount() {
        return childTwoCount;
    }

    public void setChildTwoCount(String childTwoCount) {
        this.childTwoCount = childTwoCount;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getChildOneSoupCount() {
        return childOneSoupCount;
    }

    public void setChildOneSoupCount(String childOneSoupCount) {
        this.childOneSoupCount = childOneSoupCount;
    }

    public String getChildTwoSoupCount() {
        return childTwoSoupCount;
    }

    public void setChildTwoSoupCount(String childTwoSoupCount) {
        this.childTwoSoupCount = childTwoSoupCount;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSoupCount() {
        return soupCount;
    }

    public void setSoupCount(String soupCount) {
        this.soupCount = soupCount;
    }
}
