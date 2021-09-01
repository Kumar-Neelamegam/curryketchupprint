package com.example.curryketchup_print.webservice;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItem {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("itemname")
    @Expose
    private String itemname;
    @SerializedName("itemprice")
    @Expose
    private String itemprice;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("addons")
    @Expose
    private String addons;
    @SerializedName("itemcategory")
    @Expose
    private String itemcategory;

    public String getItemcategory() {
        return itemcategory;
    }

    public void setItemcategory(String itemcategory) {
        this.itemcategory = itemcategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemprice() {
        return itemprice;
    }

    public void setItemprice(String itemprice) {
        this.itemprice = itemprice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAddons() {
        return addons;
    }

    public void setAddons(String addons) {
        this.addons = addons;
    }

}