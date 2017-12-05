package com.example.hp.popularmovies.Details;

public class Trailers {

    private String mVideoId;
    private String mvideoKey;

    public Trailers(String videoId, String videoKey){
        mVideoId = videoId;
        mvideoKey = videoKey;

    }

    public String getVideoId(){
        return mVideoId;
    }


    public String getVideoKey(){
        return mvideoKey;
    }

}
