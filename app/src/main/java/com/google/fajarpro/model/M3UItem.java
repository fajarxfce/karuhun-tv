package com.google.fajarpro.model;
/*
Author  : Nurul Fajar
Email   : cirebonredhat@gmail.com
*/


public class M3UItem {
    private String itemDuration;
    private String itemIcon;
    private String itemName;
    private String itemUrl;

    public String getItemDuration() {
        return this.itemDuration;
    }

    public String getItemName() {
        return this.itemName;
    }

    public String getItemUrl() {
        return this.itemUrl;
    }

    public String getItemIcon() {
        return this.itemIcon;
    }

    public void setItemDuration(String itemDuration) {
        this.itemDuration = itemDuration;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public void setItemIcon(String itemIcon) {
        this.itemIcon = itemIcon;
    }
}