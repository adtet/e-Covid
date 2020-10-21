package com.example.e_covid;

public class loginPost {
    public String email;
    public String password;
    public String id;
    public String kelas;

    public loginPost(String Email,String Password){
        this.email = Email;
        this.password = Password;
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public String getKelas() {
        return kelas;
    }
}
