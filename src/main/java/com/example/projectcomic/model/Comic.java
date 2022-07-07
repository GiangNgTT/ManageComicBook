package com.example.projectcomic.model;

public class Comic {
    public int id;
    public String nameC;
    public String image;
    public String author;
    public int quantity;
    public double price;

    public Comic(int id, String nameC, String image, String author, int quantity, double price) {
        this.id = id;
        this.nameC = nameC;
        this.image= image;
        this.author = author;
        this.quantity = quantity;
        this.price = price;
    }
    public Comic(String nameC,  String image, String author, int quantity, double price) {
        this.nameC = nameC;
        this.image= image;
        this.author = author;
        this.quantity = quantity;
        this.price = price;
    }


    public void setId(int name) {
        this.id = id;
    }
    public void setNameC(String nameC) {

        this.nameC = nameC;
    }
    public void setImage(String image) {

        this.image = image;
    }
    public void setAuthor(String author) {

        this.author = author;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public void setPrice(double price) {
        this.price = price;
    }



    public int getId() {
        return id;
    }
    public String getNameC() {
        return nameC;
    }
    public String getImage() {
        return image;
    }
    public String getAuthor() {
        return author;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getPrice() {
        return price;
    }


}
