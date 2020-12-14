package com.example.e_covid;

public class sakitPost {
    public String id;
    public String matakuliah;
    public String dosen;
    public  String sakit;
    public sakitPost(String Id,String Matakuliah,String Dosen){
        this.id = Id;
        this.matakuliah =  Matakuliah;
        this.dosen = Dosen;
    }

    public String getSakit() {
        return sakit;
    }
}
