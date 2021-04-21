package com.example.firestore;
public class Model {
    String id,description,product_code,product_name,cate,date;
  Double purchase_price,sale_price,qty;
    Model(String id, String description, String product_code, String product_name,
          String cate, Double purchase_price, Double sale_price, Double qty, String date){

    }

    public Model(String id, String description, String product_code, String product_name, String cate,
                 String date, Double purchase_price, Double sale_price, Double qty) {
        this.id = id;
        this.description = description;
        this.product_code = product_code;
        this.product_name = product_name;
        this.cate = cate;
        this.date = date;
        this.purchase_price = purchase_price;
        this.sale_price = sale_price;
        this.qty = qty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(Double purchase_price) {
        this.purchase_price = purchase_price;
    }

    public Double getSale_price() {
        return sale_price;
    }

    public void setSale_price(Double sale_price) {
        this.sale_price = sale_price;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }
}
