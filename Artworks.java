package com.example.ArtistSearch.model;

public class Artworks {
    private String id ;
    private String title ;
    private String href ;

    public Artworks() {
    }

    public Artworks(String id, String title, String href) {
        this.id = id;
        this.title = title;
        this.href = href;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getHref() {
        return href;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
