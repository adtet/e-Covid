package com.example.e_covid;

public class getCekdata {
    public String day;
    public String date;
    public String matakuliah;
    public String dosen;
    public String time;
    public String info;
    public String id;
    public getCekdata(String Id){
        this.id = Id;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getMatakuliah() {
        return matakuliah;
    }

    public String getDosen() {
        return dosen;
    }

    public String getTime() {
        return time;
    }

    public String getInfo() {
        return info;
    }
}
