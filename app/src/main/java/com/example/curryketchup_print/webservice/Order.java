package com.example.curryketchup_print.webservice;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order{
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("memberno")
        @Expose
        private String memberno;
        @SerializedName("membername")
        @Expose
        private String membername;
        @SerializedName("specialnotes")
        @Expose
        private String specialnotes;
        @SerializedName("phoneno")
        @Expose
        private String phoneno;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("deliverytype")
        @Expose
        private String deliverytype;
        @SerializedName("deliveryaddress")
        @Expose
        private String deliveryaddress;
        @SerializedName("takeawaytime")
        @Expose
        private String takeawaytime;
        @SerializedName("foodstatus")
        @Expose
        private String foodstatus;
        @SerializedName("kitchecn")
        @Expose
        private String kitchecn;
        @SerializedName("delivery")
        @Expose
        private String delivery;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("transid")
        @Expose
        private String transid;
        @SerializedName("Order_items")
        @Expose
        private List<OrderItem> orderItems = null;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getMemberno() {
            return memberno;
        }

        public void setMemberno(String memberno) {
            this.memberno = memberno;
        }

        public String getMembername() {
            return membername;
        }

        public void setMembername(String membername) {
            this.membername = membername;
        }

        public String getSpecialnotes() {
            return specialnotes;
        }

        public void setSpecialnotes(String specialnotes) {
            this.specialnotes = specialnotes;
        }

        public String getPhoneno() {
            return phoneno;
        }

        public void setPhoneno(String phoneno) {
            this.phoneno = phoneno;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDeliverytype() {
            return deliverytype;
        }

        public void setDeliverytype(String deliverytype) {
            this.deliverytype = deliverytype;
        }

        public String getDeliveryaddress() {
            return deliveryaddress;
        }

        public void setDeliveryaddress(String deliveryaddress) {
            this.deliveryaddress = deliveryaddress;
        }

        public String getTakeawaytime() {
            return takeawaytime;
        }

        public void setTakeawaytime(String takeawaytime) {
            this.takeawaytime = takeawaytime;
        }

        public String getFoodstatus() {
            return foodstatus;
        }

        public void setFoodstatus(String foodstatus) {
            this.foodstatus = foodstatus;
        }

        public String getKitchecn() {
            return kitchecn;
        }

        public void setKitchecn(String kitchecn) {
            this.kitchecn = kitchecn;
        }

        public String getDelivery() {
            return delivery;
        }

        public void setDelivery(String delivery) {
            this.delivery = delivery;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTransid() {
            return transid;
        }

        public void setTransid(String transid) {
            this.transid = transid;
        }

        public List<OrderItem> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
        }

}