package com.example.tarea2_1_pm1.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.tarea2_1_pm1.configuracion.SQLiteConexion;
import com.example.tarea2_1_pm1.configuracion.VideosContract;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class VideoRepository {

    Context context;

    public VideoRepository(Context context) {
        this.context = context;
    }

    public void AddVideo(String nombre, Uri videoUri) {
        try {
            byte[] videoBytes = convertirVideoABytes(videoUri);
            if (videoBytes == null || videoBytes.length == 0) {
                Log.d("SQLite", "El arreglo de bytes está vacío");
                Toast.makeText(context, "Error al convertir el video a bytes", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d("SQLite", "Tamaño del video en bytes: " + videoBytes.length); // Confirmar que los bytes se leyeron correctamente


            SQLiteConexion conexion = new SQLiteConexion(context);
            SQLiteDatabase db = conexion.getWritableDatabase();
            ContentValues valores = new ContentValues();

            valores.put(VideosContract.COLUMN_NOMBRE, nombre);
            valores.put(VideosContract.COLUMN_VIDEO, videoBytes);

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

    private byte[] convertirVideoABytes(Uri videoUri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(videoUri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            return byteArrayOutputStream.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
