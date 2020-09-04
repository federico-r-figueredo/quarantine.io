package com.cyberdynesystems.quarantineio;

public class Upload {
    private String imageName;
    private  String imageURL;

    public Upload(){

    }

    public Upload(String imgURL){
        imageName = "nidDocument";
        imageURL = imgURL;
    }

    public String getName(){
        return imageName;
    }

    public String getImageURL(){
        return imageURL;
    }

}
