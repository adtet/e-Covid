package com.example.e_covid;

public class izinPost {
    public String id;
    public String matakuliah;
    public String dosen;
    public String izin;
    public izinPost(String Id,String Matakuliah,String Dosen){
        this.id = Id;
        this.matakuliah = Matakuliah;
        this.dosen = Dosen;
    }

    public String getIzin() {
        return izin;
    }
}
