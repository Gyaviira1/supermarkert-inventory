package com.example.firestore;

public class titles {
    private String title,description;

    public titles(){

    }

    public titles(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
