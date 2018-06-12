package com.example.sheikr.muziekapplicatie.youtubeplayer;

public class YoutubeVideo {

    String videoUrl;

    public YoutubeVideo() {
    }

    public YoutubeVideo(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
