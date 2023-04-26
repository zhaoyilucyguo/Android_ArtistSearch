package com.example.ArtistSearch.model;

public class Artist {
    private String id;
    private String name;
    private String biography;
    private String birthday;
    private String deathday;
    private String nationality;
    private String genes_href;

    public Artist() {
    }

    public Artist(String id, String name, String biography, String birthday, String deathday, String nationality, String genes_href) {
        this.id = id;
        this.name = name;
        this.biography = biography;
        this.birthday = birthday;
        this.deathday = deathday;
        this.nationality = nationality;
        this.genes_href = genes_href;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBiography() {
        return biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public String getNationality() {
        return nationality;
    }

    public String getGenes_href() {
        return genes_href;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setGenes_href(String genes_href) {
        this.genes_href = genes_href;
    }
}
