package com.example.antrianpenjemputan;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextIdSiswa, editTextPassword;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextIdSiswa = findViewById(R.id.editTextIdSiswa);
        editTextPassword = findViewById(R.id.editTextPassword);

        dbHelper = new DatabaseHelper(this);
    }

    // Fungsi untuk login
    public void login(View view) {
        String idSiswa = editTextIdSiswa.getText().toString();
        String password = editTextPassword.getText().toString();

        if (idSiswa.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Harap lengkapi semua field", Toast.LENGTH_SHORT).show();
        } else {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM " + DatabaseHelper.TABLE_SISWA +
                    " WHERE " + DatabaseHelper.COLUMN_ID_SISWA + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{idSiswa, password});

            if (cursor.moveToFirst()) {
                // Ambil kolom nama dan kelas dengan pengecekan
                int indexNama = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAMA_SISWA);
                int indexKelas = cursor.getColumnIndex(DatabaseHelper.COLUMN_KELAS);

                if (indexNama != -1 && indexKelas != -1) {
                    String namaSiswa = cursor.getString(indexNama);
                    String kelasSiswa = cursor.getString(indexKelas);

                    // Kirim data nama dan kelas ke MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("NAMA_SISWA", namaSiswa);
                    intent.putExtra("KELAS_SISWA", kelasSiswa);
                    startActivity(intent);
                    finish(); // Menutup LoginActivity setelah login berhasil
                } else {
                    Toast.makeText(this, "Kolom nama atau kelas tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "ID atau Password salah", Toast.LENGTH_SHORT).show();
            }

            cursor.close();
            db.close();
        }
    }

    // Fungsi untuk membuka halaman registrasi
    public void openRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
