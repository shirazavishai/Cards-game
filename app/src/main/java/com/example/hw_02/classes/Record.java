package com.example.hw_02.classes;

public class Record {
    private int score;
    private String name;
    private String date;
    private Double lng;
    private Double lat;

    public Record() {
    }

    public Record(String name, String date, int score) {
        this.name = name;
        this.date = date;
        this.score = score;
    }

    public Record(String name, String date, int score, Double lng, Double lat) {
        this.name = name;
        this.date = date;
        this.score = score;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
