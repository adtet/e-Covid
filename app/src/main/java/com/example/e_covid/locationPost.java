package com.example.e_covid;

public class locationPost {
    public float latitude;
    public float longitude;
    public String pesan;
    public locationPost(float Latitude,float Longitude){
        this.latitude = Latitude;
        this.longitude = Longitude;
    }
    public String getPesan() {
        return pesan;
    }
}
