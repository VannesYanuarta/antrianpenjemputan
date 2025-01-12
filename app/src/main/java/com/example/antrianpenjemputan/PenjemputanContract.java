package com.example.antrianpenjemputan;

import android.provider.BaseColumns;

public final class PenjemputanContract {
    private PenjemputanContract() {}

    public static class PenjemputanEntry implements BaseColumns {
        public static final String TABLE_PENJEMPUTAN = "penjemputan";
        public static final String COLUMN_ID_PENJEMPUTAN = "id_penjemputan";
        public static final String COLUMN_JENIS_KENDARAAN = "jenis_kendaraan";
        public static final String COLUMN_KLOTER = "kloter";
        public static final String COLUMN_JAM_MULAI = "jam_mulai";
        public static final String COLUMN_JAM_SELESAI = "jam_selesai";
    }
}
