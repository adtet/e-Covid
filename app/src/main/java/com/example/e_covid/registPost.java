package com.example.e_covid;

public class registPost {
    public String nim;
    public String username;
    public String jurusan;
    public String prodi;
    public String kelas;
    public String email;
    public String password;
    public String message;
    public registPost(String Nim,String Username,String Jurusan,String Prodi,String Kelas,String Email,String Password){
        this.nim = Nim;
        this.username = Username;
        this.jurusan = Jurusan;
        this.prodi = Prodi;
        this.kelas = Kelas;
        this.email = Email;
        this.password = Password;
    }

    public String getNim() {
        return nim;
    }

    public String getUsername() {
        return username;
    }

    public String getJurusan() {
        return jurusan;
    }

    public String getProdi() {
        return prodi;
    }

    public String getKelas() {
        return kelas;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMessage() {
        return message;
    }
}
