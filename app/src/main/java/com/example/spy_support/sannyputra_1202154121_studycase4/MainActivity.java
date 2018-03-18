package com.example.spy_support.sannyputra_1202154121_studycase4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Akan memindahkan anda kedalam aktivitas Daftar Mahasiswa
    public void tampilNama(View view) {
        Intent i = new Intent(getApplicationContext(), com.example.spy_support.sannyputra_1202154121_studycase4.MahasiswaActivity.class);
        startActivity(i);
    }

    //Akan memindahkan anda kedalam aktivitas Penampil Gambar URL
    public void tampilGambar(View view) {
        Intent i = new Intent(getApplicationContext(),GambarActivity.class);
        startActivity(i);
    }
}
