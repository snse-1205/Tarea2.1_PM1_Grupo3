package com.example.tarea2_1_pm1.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.tarea2_1_pm1.configuracion.SQLiteConexion;
import com.example.tarea2_1_pm1.configuracion.VideosContract;
import com.example.tarea2_1_pm1.functions.Conversions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

public class VideoRepository {

    Context context;

    public VideoRepository(Context context) {
        this.context = context;
    }

    public void AddVideo(String nombre, Uri videoUri) {
        try {
            // Convertir el video en bytes y guardarlo en el almacenamiento
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
            valores.put(VideosContract.COLUMN_VIDEO, videoPath);  // Guardar solo la ruta del archivo

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


    public Uri getLastSavedVideoUri() {
        Uri videoUri = null;
        SQLiteConexion conexion = new SQLiteConexion(context);
        SQLiteDatabase db = conexion.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(VideosContract.TABLE_NAME,
                    new String[]{VideosContract.COLUMN_ID, VideosContract.COLUMN_VIDEO},
                    null, null, null, null, VideosContract.COLUMN_ID + " DESC", "1");

            if (cursor != null && cursor.moveToFirst()) {
                // Recuperar la ruta del archivo desde la base de datos
                String videoPath = cursor.getString(cursor.getColumnIndex(VideosContract.COLUMN_VIDEO));

                if (videoPath != null && !videoPath.isEmpty()) {
                    videoUri = Uri.fromFile(new File(videoPath));  // Crear un URI a partir de la ruta
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SQLite", "Error al obtener el video guardado: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return videoUri;
    }


}
