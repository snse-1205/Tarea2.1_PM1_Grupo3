    package com.example.tarea2_1_pm1.configuracion;

    public class VideosContract {
        public static String TABLE_NAME ="videos";
        public static String COLUMN_ID="id";
        public static String COLUMN_NOMBRE="nombre";
        public static String COLUMN_VIDEO="video";

        public static final String CREATE_TABLE_VIDEOS="CREATE TABLE "+
                TABLE_NAME+" ("+
                COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COLUMN_NOMBRE+" TEXT,"+
                COLUMN_VIDEO+" BLOB)";

        public static final String DROP_TABLE_VIDEO ="DROP TABLE IF EXISTS "+TABLE_NAME;

    }


