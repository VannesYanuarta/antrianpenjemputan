package com.example.antrianpenjemputan;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Random;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "antrianPenjemputan.db";
    private static final int DATABASE_VERSION = 1;

    // Tabel siswa
    public static final String TABLE_SISWA = "siswa";
    public static final String COLUMN_ID_SISWA = "id_siswa";
    public static final String COLUMN_NAMA_SISWA = "nama_siswa";
    public static final String COLUMN_KELAS = "kelas";
    public static final String COLUMN_KATEGORI = "kategori";
    public static final String COLUMN_PASSWORD = "password";

    // Tabel penjemputan
    public static final String TABLE_PENJEMPUTAN = "penjemputan";
    public static final String COLUMN_ID_PENJEMPUTAN = "id_penjemputan";
    public static final String COLUMN_JENIS_KENDARAAN = "jenis_kendaraan";
    public static final String COLUMN_KLOTER = "kloter";
    public static final String COLUMN_JAM_MULAI = "jam_mulai";
    public static final String COLUMN_JAM_SELESAI = "jam_selesai";

    private static final String CREATE_TABLE_SISWA =
            "CREATE TABLE " + TABLE_SISWA + " (" +
                    COLUMN_ID_SISWA + " INTEGER PRIMARY KEY NOT NULL, " +
                    COLUMN_NAMA_SISWA + " TEXT NOT NULL, " +
                    COLUMN_KELAS + " TEXT NOT NULL, " +
                    COLUMN_KATEGORI + " INTEGER NOT NULL, " +
                    COLUMN_PASSWORD + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_PENJEMPUTAN =
            "CREATE TABLE " + TABLE_PENJEMPUTAN + " (" +
                    COLUMN_ID_PENJEMPUTAN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ID_SISWA + " INTEGER NOT NULL, " +
                    COLUMN_JENIS_KENDARAAN + " TEXT NOT NULL, " +
                    COLUMN_KLOTER + " INTEGER NOT NULL, " +
                    COLUMN_JAM_MULAI + " TEXT NOT NULL, " +
                    COLUMN_JAM_SELESAI + " TEXT NOT NULL, " +
                    "FOREIGN KEY(" + COLUMN_ID_SISWA + ") REFERENCES " + TABLE_SISWA + "(" + COLUMN_ID_SISWA + "));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SISWA);
        db.execSQL(CREATE_TABLE_PENJEMPUTAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SISWA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PENJEMPUTAN);
        onCreate(db);
    }

    // Fungsi untuk menambahkan penjemputan dengan ID penjemputan sesuai format kategori + angka acak
    public void addPenjemputan(int idSiswa, String jenisKendaraan, String kategoriSiswa, int kloter, String jamMulai, String jamSelesai) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Generate ID Penjemputan sesuai format kategori + angka acak
        String idPenjemputan = generateIdPenjemputan(kategoriSiswa);

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_PENJEMPUTAN, idPenjemputan);
        values.put(COLUMN_ID_SISWA, idSiswa);
        values.put(COLUMN_JENIS_KENDARAAN, jenisKendaraan);
        values.put(COLUMN_KLOTER, kloter);
        values.put(COLUMN_JAM_MULAI, jamMulai);
        values.put(COLUMN_JAM_SELESAI, jamSelesai);

        // Insert data ke tabel penjemputan
        db.insert(TABLE_PENJEMPUTAN, null, values);
        db.close();
    }

    // Fungsi untuk generate ID Penjemputan berdasarkan kategori dan angka acak
    private String generateIdPenjemputan(String kategoriSiswa) {
        // Generate angka acak setelah kategori
        int randomNumber = (int) (Math.random() * 1000); // Angka acak antara 0 dan 999

        // Format ID Penjemputan: <kategoriSiswa><randomNumber>
        return kategoriSiswa + String.format("%03d", randomNumber); // Format 3 digit untuk angka acak
    }
}

