package com.solution.app.justpay4u.Api.Shopping.Object;

import java.io.Serializable;

/**
 * Created by Asus on 10-08-2018.
 */

public class ProductDetailImageListItem implements Serializable {

    String imageBig, imageMedium, imageSmall;
    boolean isSelected = false;

    public ProductDetailImageListItem(String imageBig, String imageMedium, String imageSmall, boolean isSelected) {
        this.imageBig = imageBig;
        this.imageMedium = imageMedium;
        this.imageSmall = imageSmall;
        this.isSelected = isSelected;
    }

    public String getImageBig() {
        return imageBig;
    }

    public String getImageMedium() {
        return imageMedium;
    }

    public String getImageSmall() {
        return imageSmall;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
