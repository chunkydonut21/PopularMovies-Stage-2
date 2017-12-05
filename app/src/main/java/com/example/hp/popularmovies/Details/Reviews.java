package com.example.hp.popularmovies.Details;


public class Reviews {

    private String mAuthorName;
    private String mContent;

    public Reviews(String author, String content){
        mAuthorName = author;
        mContent = content;

    }

    public String getAuthorName(){
        return mAuthorName;
    }


    public String getContent(){
        return mContent;
    }

}
