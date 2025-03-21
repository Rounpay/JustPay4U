package com.solution.app.justpay4u.Fintech.Info.Model;

/**
 * Created by Vishnu Agarwal on 12,December,2019
 */
public class InfoContactDataItem {

    /* type 1=Mobile;
     type 2= Phone
     type 3= Whatsapp
     type 4= Email*/
    int headerIcon;
    String headerName;
    int type;
    String value;
    int icon;

    public InfoContactDataItem(int headerIcon, String headerName, int type, String value, int icon) {
        this.headerIcon = headerIcon;
        this.headerName = headerName;
        this.type = type;
        this.value = value;
        this.icon = icon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getHeaderIcon() {
        return headerIcon;
    }

    public void setHeaderIcon(int headerIcon) {
        this.headerIcon = headerIcon;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }
}
