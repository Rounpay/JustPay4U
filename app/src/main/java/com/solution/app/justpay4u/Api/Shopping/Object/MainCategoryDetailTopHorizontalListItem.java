package com.solution.app.justpay4u.Api.Shopping.Object;

public class MainCategoryDetailTopHorizontalListItem {

    boolean isSelected;
    private int id;
    private String name, image;

    public MainCategoryDetailTopHorizontalListItem(int id, String name, boolean isSelected) {
        this.id = id;
        this.name = name;
        this.isSelected = isSelected;
    }

    public MainCategoryDetailTopHorizontalListItem(int id, String name, String image, boolean isSelected) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.isSelected = isSelected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
