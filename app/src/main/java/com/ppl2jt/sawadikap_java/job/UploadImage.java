package com.ppl2jt.sawadikap_java.job;

public class UploadImage {
    private String name;
    private String imageUrl;

    public UploadImage() {
        // Empty Constructor Needed
    }

    public UploadImage(String name, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No name";
        }

        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
