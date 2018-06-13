package com.example.sheikr.muziekapplicatie.musicupload;

public class MusicModel {

    public String gebruiker;
    public String naam;
    public String url;

    public MusicModel() {
    }

    public MusicModel(String gebruiker, String naam, String url) {
        this.gebruiker = gebruiker;
        this.naam = naam;
        this.url = url;
    }

    public String getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(String gebruiker) {
        this.gebruiker = gebruiker;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
