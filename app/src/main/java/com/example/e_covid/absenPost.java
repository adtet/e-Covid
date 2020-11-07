package com.example.e_covid;

public class absenPost {
    public String id;
    public String link;
    public String matakuliah;
    public absenPost (String id,String matakuliah){
        this.id = id;
        this.matakuliah = matakuliah;
    }

    public String getLink() {
        return link;
    }
}
