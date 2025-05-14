package com.redhat.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Song implements Serializable {
    private static final long serialVersionUID = 1L; 
    
    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("artist")
    private String artist;

    public Song() {}

    public Song(Long id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}