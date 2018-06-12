package com.example.sheikr.muziekapplicatie.youtubeplayer;

public class VideoModel {

    String id;
    String gebruiker;
    String titel;
    String link;

    public VideoModel() {
    }

    public VideoModel(String id, String gebruiker, String titel, String link) {
        this.id = id;
        this.gebruiker = gebruiker;
        this.titel = titel;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(String gebruiker) {
        this.gebruiker = gebruiker;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
