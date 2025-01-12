package com.example.antrianpenjemputan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textViewNamaSiswa, textViewKelasSiswa;
    private String jenisKendaraan; // Akan di-set berdasarkan pilihan pengguna
    private String jamMulai, jamSelesai;
    private String kategoriSiswa; // Akan dihitung berdasarkan kelas siswa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi TextView
        textViewNamaSiswa = findViewById(R.id.textViewNamaSiswa);
        textViewKelasSiswa = findViewById(R.id.textViewKelasSiswa);

        // Ambil data siswa dari Intent (data yang dikirim dari LoginActivity atau RegisterActivity)
        Intent intent = getIntent();
        String namaSiswa = intent.getStringExtra("NAMA_SISWA");
        String kelasSiswa = intent.getStringExtra("KELAS_SISWA");

        if (namaSiswa != null && kelasSiswa != null) {
            // Set data siswa ke TextView
            textViewNamaSiswa.setText("Nama Siswa: " + namaSiswa);
            textViewKelasSiswa.setText("Kelas: " + kelasSiswa);
        } else {
            Toast.makeText(this, "Data Siswa Tidak Ditemukan!", Toast.LENGTH_SHORT).show();
        }
    }

    // Fungsi untuk memilih kloter mobil
    public void pilihMobil(View view) {
        jenisKendaraan = "mobil";
        tentukanJamPenjemputan();
    }

    // Fungsi untuk memilih kloter motor
    public void pilihMotor(View view) {
        jenisKendaraan = "motor";
        tentukanJamPenjemputan();
    }

    private void tentukanJamPenjemputan() {
        // Tentukan kategori siswa berdasarkan kelas yang ditampilkan
        if (textViewKelasSiswa.getText().toString().contains("1") ||
                textViewKelasSiswa.getText().toString().contains("2") ||
                textViewKelasSiswa.getText().toString().contains("3")) {
            kategoriSiswa = "1"; // Kategori 1: Kelas 1-3
        } else {
            kategoriSiswa = "2"; // Kategori 2: Kelas 4-6
        }

        // Tentukan jam penjemputan berdasarkan kategori dan kendaraan
        if (kategoriSiswa.equals("1")) {
            // Kategori 1: Kelas 1-3
            if (jenisKendaraan.equals("mobil")) {
                jamMulai = "12:45";
                jamSelesai = "13:15";
            } else {
                jamMulai = "13:15";
                jamSelesai = "13:35";
            }
        } else {
            // Kategori 2: Kelas 4-6
            if (jenisKendaraan.equals("mobil")) {
                jamMulai = "13:45";
                jamSelesai = "14:15";
            } else {
                jamMulai = "14:15";
                jamSelesai = "14:35";
            }
        }

        // Tampilkan jam penjemputan
        Toast.makeText(this, "Jam mulai: " + jamMulai + ", Jam selesai: " + jamSelesai, Toast.LENGTH_SHORT).show();

        // Arahkan ke PenjemputanActivity dan kirim kategori, jam mulai, dan jam selesai
        Intent intent = new Intent(MainActivity.this, PenjemputanActivity.class);
        intent.putExtra("KATEGORI_SISWA", kategoriSiswa);
        intent.putExtra("JAM_MULAI", jamMulai);
        intent.putExtra("JAM_SELESAI", jamSelesai);
        startActivity(intent);
    }

    // Fungsi Logout, akan mengarahkan kembali ke halaman login
    public void logout(View view) {
        // Buat intent untuk menuju LoginActivity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);

        // Menambahkan flag FLAG_ACTIVITY_CLEAR_TOP agar menutup semua aktivitas yang ada di atas LoginActivity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Mulai LoginActivity
        startActivity(intent);
        finish(); // Menutup MainActivity agar tidak bisa kembali ke halaman ini
    }
}
