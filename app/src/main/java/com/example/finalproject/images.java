package com.example.finalproject;

public class images {

    private int imageUrl;
    private String title;


    public images(int imageUrl, String title) {
        this.imageUrl = imageUrl;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getImageUrl() {
        return imageUrl;
    }
}
