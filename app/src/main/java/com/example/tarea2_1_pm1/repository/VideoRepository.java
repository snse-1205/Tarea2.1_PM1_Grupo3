package com.example.tarea2_1_pm1.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.tarea2_1_pm1.Models.VideoModel;
import com.example.tarea2_1_pm1.configuracion.SQLiteConexion;
import com.example.tarea2_1_pm1.configuracion.VideosContract;
import com.example.tarea2_1_pm1.functions.Conversions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class VideoRepository {

    Context context;

    public VideoRepository(Context context) {
        this.context = context;
    }

    public void AddVideo(String nombre, Uri videoUri) {
        try {
            Conversions conversions = new Conversions(context);
            String videoPath = conversions.saveVideoToFile(videoUri);

            if (videoPath == null) {
                Log.d("SQLite", "Error al guardar el video");
                Toast.makeText(context, "Error al guardar el video", Toast.LENGTH_SHORT).show();
                return;
            }

            SQLiteConexion conexion = new SQLiteConexion(context);
            SQLiteDatabase db = conexion.getWritableDatabase();
            ContentValues valores = new ContentValues();

            valores.put(VideosContract.COLUMN_NOMBRE, nombre);
            valores.put(VideosContract.COLUMN_VIDEO, videoPath);

            long result = db.insert(VideosContract.TABLE_NAME, null, valores);

            if (result == -1) {
                Toast.makeText(context, "Error al guardar el video", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Video guardado exitosamente en la BD", Toast.LENGTH_SHORT).show();
            }

            db.close();

        } catch (Exception ex) {
            Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show();
            Log.d("SQLite", "Error al guardar video: " + ex);
        }
    }

    public List<VideoModel> getAllSavedVideos() {
        List<VideoModel> videoList = new ArrayList<>();
        SQLiteConexion conexion = new SQLiteConexion(context);
        SQLiteDatabase db = conexion.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(VideosContract.TABLE_NAME,
                    new String[]{VideosContract.COLUMN_ID, VideosContract.COLUMN_NOMBRE, VideosContract.COLUMN_VIDEO},
                    null, null, null, null, VideosContract.COLUMN_ID + " DESC");

            if (cursor != null && cursor.moveToFirst()) {
                videoList.clear();

                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(VideosContract.COLUMN_ID));
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow(VideosContract.COLUMN_NOMBRE));
                    String videoPath = cursor.getString(cursor.getColumnIndexOrThrow(VideosContract.COLUMN_VIDEO));

                    if (videoPath != null && !videoPath.isEmpty()) {
                        Uri videoUri = Uri.fromFile(new File(videoPath));

                        videoList.add(new VideoModel(id, nombre, videoUri));

                        Log.d("SQLite", "ID: " + id + " | Nombre: " + nombre + " | URI: " + videoUri.toString());
                    }
                } while (cursor.moveToNext());
            } else {
                Log.d("SQLite", "No se encontraron videos en la base de datos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SQLite", "Error al obtener los videos guardados: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return videoList;
    }

}
