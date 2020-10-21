package com.example.e_covid;

public class jadwalGet {
    public String jamstart;
    public String menitstart;
    public String jamend;
    public String menitend;
    public String matakuliah;
    public String dosen;
    public jadwalGet(String Jamstart,String Menitstart,String Jamend,String Menitend,String Matakuliah,String Dosen){
        this.jamstart = Jamstart;
        this.menitstart = Menitstart;
        this.jamend = Jamend;
        this.menitend = Menitend;
        this.matakuliah = Matakuliah;
        this.dosen = Dosen;
    }

    public String getJamstart() {
        return jamstart;
    }

    public String getMenitstart() {
        return menitstart;
    }

    public String getMenitend() {
        return menitend;
    }

    public String getJamend() {
        return jamend;
    }

    public String getMatakuliah() {
        return matakuliah;
    }

    public String getDosen() {
        return dosen;
    }
}
