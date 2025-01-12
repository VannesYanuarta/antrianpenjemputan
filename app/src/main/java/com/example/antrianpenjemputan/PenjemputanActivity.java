package com.example.antrianpenjemputan;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class PenjemputanActivity extends AppCompatActivity {

    private TextView textViewJamMulai, textViewJamSelesai, textViewWaktuRealTime, textViewNomorAntrian;
    private String jamMulai, jamSelesai;
    private Handler handler;
    private static final String CHANNEL_ID = "CountdownNotification";

    private Button buttonReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjemputan);

        // Inisialisasi TextView
        textViewJamMulai = findViewById(R.id.textViewJamMulai);
        textViewJamSelesai = findViewById(R.id.textViewJamSelesai);
        textViewWaktuRealTime = findViewById(R.id.textViewWaktuRealTime);
        textViewNomorAntrian = findViewById(R.id.textViewNomorAntrian);

        // Inisialisasi Button untuk reminder
        buttonReminder = findViewById(R.id.buttonReminder);

        // Ambil data dari Intent
        Intent intent = getIntent();
        jamMulai = intent.getStringExtra("JAM_MULAI");
        jamSelesai = intent.getStringExtra("JAM_SELESAI");

        // Validasi format jamMulai
        if (jamMulai == null || !jamMulai.matches("\\d{2}:\\d{2}")) {
            textViewWaktuRealTime.setText("Format Jam Mulai Tidak Valid");
            return;
        }

        // Set jam mulai dan selesai ke TextView
        textViewJamMulai.setText("Jam Mulai: " + jamMulai);
        textViewJamSelesai.setText("Jam Selesai: " + jamSelesai);

        // Menampilkan waktu real-time dan selisih waktu setiap detik
        updateTimeRealTime();

        // Generate dan tampilkan nomor antrian
        String nomorAntrian = generateNomorAntrian(1, 1); // Contoh kategori 1, mobil
        textViewNomorAntrian.setText("Nomor Antrian: " + nomorAntrian);

        // Menangani aksi tombol reminder (notifikasi)
        buttonReminder.setOnClickListener(v -> showReminderToast());
    }

    private void updateTimeRealTime() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Update waktu real-time
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                textViewWaktuRealTime.setText("Waktu Sekarang: " + currentTime);

                // Hitung dan tampilkan selisih waktu
                long waktuSisa = hitungWaktuSisa(jamMulai);
                updateWaktuSelisih(waktuSisa);

                handler.postDelayed(this, 1000); // Update setiap detik
            }
        }, 1000); // Mulai dari detik pertama
    }

    private long hitungWaktuSisa(String jamMulai) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Date targetTime = format.parse(jamMulai);

            Calendar now = Calendar.getInstance();
            Calendar target = Calendar.getInstance();

            if (targetTime != null) {
                // Set jam dan menit target
                target.setTime(targetTime);
                target.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
                target.set(Calendar.MONTH, now.get(Calendar.MONTH));
                target.set(Calendar.YEAR, now.get(Calendar.YEAR));

                // Cek apakah target sudah lewat hari ini
                if (target.before(now)) {
                    target.add(Calendar.DAY_OF_MONTH, 1); // Tambahkan satu hari
                }

                return target.getTimeInMillis() - now.getTimeInMillis();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void updateWaktuSelisih(long waktuSisa) {
        if (waktuSisa > 0) {
            long hours = waktuSisa / (1000 * 60 * 60);
            long minutes = (waktuSisa % (1000 * 60 * 60)) / (1000 * 60);
            long seconds = (waktuSisa % (1000 * 60)) / 1000;

            textViewWaktuRealTime.setText(String.format(Locale.getDefault(),
                    "Sisa Waktu Penjemputan: %02d:%02d:%02d", hours, minutes, seconds));
        } else {
            textViewWaktuRealTime.setText("Waktu Penjemputan Sudah Lewat.");
        }
    }

    private void showReminderToast() {
        Toast.makeText(this, "Reminder aktif. Jangan sampai kelupaan ya!", Toast.LENGTH_SHORT).show();
    }

    private String generateNomorAntrian(int idKategori, int jenisKendaraan) {
        String kategori = String.format("%02d", idKategori);
        String kendaraan = (jenisKendaraan == 1) ? "1" : "2";
        Random random = new Random();
        int randomDigits = random.nextInt(1000);
        String randomPart = String.format("%03d", randomDigits);
        return kategori + kendaraan + randomPart;
    }
}
