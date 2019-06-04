package com.william.reservationsystem.model;

public class DataDailyMenu {
    public static final int PARENT_ITEM = 0;
    public static final int CHILD_ITEM = 1;

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

    public String getChildMenuNumber() {
        return childMenuNumber;
    }

    public void setChildMenuNumber(String childMenuNumber) {
        this.childMenuNumber = childMenuNumber;
    }

    public String getChildDishes_one() {
        return childDishes_one;
    }

    public void setChildDishes_one(String childDishes_one) {
        this.childDishes_one = childDishes_one;
    }

    public String getChildDishes_two() {
        return childDishes_two;
    }

    public void setChildDishes_two(String childDishes_two) {
        this.childDishes_two = childDishes_two;
    }

    public String getcChildDishes_three() {
        return cChildDishes_three;
    }

    public void setcChildDishes_three(String cChildDishes_three) {
        this.cChildDishes_three = cChildDishes_three;
    }

    public String getChildDishes_four() {
        return childDishes_four;
    }

    public void setChildDishes_four(String childDishes_four) {
        this.childDishes_four = childDishes_four;
    }

    public String getChildSoup() {
        return childSoup;
    }

    public void setChildSoup(String childSoup) {
        this.childSoup = childSoup;
    }

    public String getChildCountData() {
        return childCountData;
    }

    public void setChildCountData(String childCountData) {
        this.childCountData = childCountData;
    }

    public String getChildCountDetail() {
        return childCountDetail;
    }

    public void setChildCountDetail(String childCountDetail) {
        this.childCountDetail = childCountDetail;
    }

    private int type;
    private boolean isExpand;
    private DataDailyMenu childMenu;

    private String date;
    private String parentCountData;

    private String childMenuNumber;
    private String childDishes_one;
    private String childDishes_two;
    private String cChildDishes_three;
    private String childDishes_four;
    private String childSoup;
    private String childCountData;
    private String childCountDetail;
}
