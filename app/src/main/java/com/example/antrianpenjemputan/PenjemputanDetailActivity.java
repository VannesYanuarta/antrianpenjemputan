package com.example.antrianpenjemputan;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class PenjemputanDetailActivity extends AppCompatActivity {

    private TextView textViewWaktuPenjemputan, textViewNomorAntrian, textViewSisaWaktu;
    private Button buttonSetAlarm;
    private String jamMulai = "13:15"; // Contoh waktu mulai penjemputan
    private String nomorAntrian = "A01"; // Contoh nomor antrian
    private long waktuPenjemputanMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjemputan_detail);

        // Inisialisasi komponen dari layout XML
        textViewWaktuPenjemputan = findViewById(R.id.textViewWaktuPenjemputan);
        textViewNomorAntrian = findViewById(R.id.textViewNomorAntrian);
        textViewSisaWaktu = findViewById(R.id.textViewSisaWaktu);
        buttonSetAlarm = findViewById(R.id.buttonSetAlarm);

        // Menampilkan data waktu penjemputan, nomor antrian, dan sisa waktu
        textViewWaktuPenjemputan.setText("Waktu Penjemputan: " + jamMulai);
        textViewNomorAntrian.setText("Nomor Antrian: " + nomorAntrian);

        // Menghitung sisa waktu
        waktuPenjemputanMillis = System.currentTimeMillis() + 5000; // contoh waktu penjemputan 5 detik dari sekarang
        long sisaWaktu = waktuPenjemputanMillis - System.currentTimeMillis();
        textViewSisaWaktu.setText("Sisa Waktu: " + sisaWaktu / 1000 + " detik");

        // Set alarm ketika tombol di klik
        buttonSetAlarm.setOnClickListener(v -> setAlarm());
    }

    // Fungsi untuk mengatur alarm
    private void setAlarm() {
        // Menentukan waktu alarm (misalnya, 5 detik dari sekarang)
        long triggerTime = SystemClock.elapsedRealtime() + (5 * 1000); // 5 detik dari sekarang

        // Membuat AlarmManager dan PendingIntent
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Mengatur alarm
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent);
            Toast.makeText(this, "Alarm telah diatur", Toast.LENGTH_SHORT).show();
        }
    }
}
