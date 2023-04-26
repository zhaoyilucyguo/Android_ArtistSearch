package com.example.ArtistSearch.model;

public class Search {
    private String title;
    private String og_type;
    private String id_href;
    private String img_href;

    public Search() {
    }

    public String getTitle() {
        return title;
    }

    public String getOg_type() {
        return og_type;
    }

    public String getId_href() {
        return id_href;
    }

    public String getImg_href() {
        return img_href;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOg_type(String og_type) {
        this.og_type = og_type;
    }

    public void setId_href(String id_href) {
        this.id_href = id_href;
    }

    public void setImg_href(String img_href) {
        this.img_href = img_href;
    }
}
