package com.example.e_covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class register extends AppCompatActivity {
    EditText nim,nama,email,pass,confpass;
    AutoCompleteTextView prodi,jurusan,kelas;
    ImageButton register;
    public String url = "http://156.67.221.101:4000/user/";
    private static final String[] prodis = new String[]{"D4-Teknik Telekomunikasi","D3-Teknik Telekomunikasi"};
    private static final String[]jurusans = new String[]{"Elektro"};
    private static final String[]kelass = new String[]{"3nk","3b","3a"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        String[] prodies = getResources().getStringArray(R.array.prodis);
        String[] jurusanes = getResources().getStringArray(R.array.jurusans);
        String[] kelases = getResources().getStringArray(R.array.kelass);
        nim = findViewById(R.id.txtnimregister);
        nama = findViewById(R.id.txtnamaregister);
        prodi = findViewById(R.id.txtprodiregsiter);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,R.layout.custom_list_item,R.id.text_view_list_item,prodies);
        prodi.setAdapter(adapter1);
        ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(this,R.layout.custom_list_item,R.id.text_view_list_item,jurusanes);
        jurusan = findViewById(R.id.txtjurusanregister);
        jurusan.setAdapter(adapter2);
        kelas = findViewById(R.id.txtkelasregister);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,R.layout.custom_list_item,R.id.text_view_list_item,kelases);
        kelas.setAdapter(adapter3);
        email = findViewById(R.id.txtemailregister);
        pass = findViewById(R.id.txtpassregister);
        confpass = findViewById(R.id.txtconfirmpassregister);
        register = findViewById(R.id.btnprosesregister);
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        final JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nim.getText().toString().equals("") && nama.getText().toString().equals("") && prodi.getText().toString().equals("") && jurusan.getText().toString().equals("") && kelas.getText().toString().equals("") && email.getText().toString().equals("") && pass.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"lengkapi data",Toast.LENGTH_LONG).show();
                }
                else{
                    if(pass.getText().toString().equals(confpass.getText().toString())){
                        String a = nim.getText().toString();
                        String b = nama.getText().toString();
                        String d = prodi.getText().toString();
                        String c = jurusan.getText().toString();
                        String e = kelas.getText().toString();
                        String f = email.getText().toString();
                        String g = pass.getText().toString();
                        final registPost registPost = new registPost(a,b,c,d,e,f,g);
                        Call<registPost> call = jsonPlaceHolder.getregistPost(registPost);
                        call.enqueue(new Callback<com.example.e_covid.registPost>() {
                            @Override
                            public void onResponse(Call<com.example.e_covid.registPost> call, Response<com.example.e_covid.registPost> response) {
//                                Toast.makeText(getApplicationContext(),"regist berhasil",Toast.LENGTH_SHORT).show();
                                registPost registPost1 = response.body();
                                String pesan = registPost1.getMessage();
                                Toast.makeText(getApplicationContext(),pesan,Toast.LENGTH_SHORT).show();

                                if(pesan.equals("Regist Berhasil")){
                                    nim.setText("");
                                    nama.setText("");
                                    prodi.setText("");
                                    jurusan.setText("");
                                    kelas.setText("");
                                    email.setText("");
                                    pass.setText("");
                                    confpass.setText("");
                                    onBackPressed();
                                }
                                else{
                                    nim.setText("");
                                    nama.setText("");
                                    prodi.setText("");
                                    jurusan.setText("");
                                    kelas.setText("");
                                    email.setText("");
                                    pass.setText("");
                                    confpass.setText("");
                                }
                            }
                            @Override
                            public void onFailure(Call<com.example.e_covid.registPost> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),"regist Failed",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Check your password",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(register.this,login.class));
        finish();
    }
}