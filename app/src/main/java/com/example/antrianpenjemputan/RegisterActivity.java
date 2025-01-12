package com.example.antrianpenjemputan;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextIdSiswa, editTextNamaSiswa, editTextKelas, editTextKategori, editTextPassword;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextIdSiswa = findViewById(R.id.editTextIdSiswa);
        editTextNamaSiswa = findViewById(R.id.editTextNamaSiswa);
        editTextKelas = findViewById(R.id.editTextKelas);
        editTextKategori = findViewById(R.id.editTextKategori);
        editTextPassword = findViewById(R.id.editTextPassword);

        dbHelper = new DatabaseHelper(this);
    }

    public void register(View view) {
        String idSiswa = editTextIdSiswa.getText().toString();
        String namaSiswa = editTextNamaSiswa.getText().toString();
        String kelas = editTextKelas.getText().toString();
        String kategori = editTextKategori.getText().toString();
        String password = editTextPassword.getText().toString();

        if (idSiswa.isEmpty() || namaSiswa.isEmpty() || kelas.isEmpty() || kategori.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Harap lengkapi semua field", Toast.LENGTH_SHORT).show();
        } else {
            try {
                int kategoriInt = Integer.parseInt(kategori);

                if (kategoriInt < 1 || kategoriInt > 2) {
                    Toast.makeText(this, "Kategori harus 1 atau 2", Toast.LENGTH_SHORT).show();
                    return;
                }

                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_ID_SISWA, idSiswa);
                values.put(DatabaseHelper.COLUMN_NAMA_SISWA, namaSiswa);
                values.put(DatabaseHelper.COLUMN_KELAS, kelas);
                values.put(DatabaseHelper.COLUMN_KATEGORI, kategoriInt);
                values.put(DatabaseHelper.COLUMN_PASSWORD, password);

                long result = db.insert(DatabaseHelper.TABLE_SISWA, null, values);

                if (result == -1) {
                    Toast.makeText(this, "Registrasi Gagal", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                    // Arahkan ke LoginActivity setelah berhasil registrasi
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Menutup RegisterActivity
                }

                db.close();

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Kategori harus berupa angka", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Fungsi untuk membuka halaman login
    public void openLogin(View view) {
        // Arahkan ke LoginActivity
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Menutup RegisterActivity
    }
}
