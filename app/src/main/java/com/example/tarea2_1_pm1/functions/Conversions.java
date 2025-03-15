package com.example.tarea2_1_pm1.functions;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Conversions {

    private Context context;  // Declarar el contexto

    // Constructor para recibir el contexto
    public Conversions(Context context) {
        this.context = context;
    }

    public String convertirVideoAStringBase64(Uri videoUri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(videoUri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            byte[] videoBytes = byteArrayOutputStream.toByteArray();

            // Convertir a Base64
            return Base64.encodeToString(videoBytes, Base64.DEFAULT);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String saveVideoToFile(Uri videoUri) {
        try {
            // Obtener el nombre del archivo original a partir del URI
            String fileName = getFileName(videoUri);

            // Crear el archivo en el almacenamiento con el nombre original
            File outputFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_MOVIES), fileName);

            // Copiar los bytes del video al archivo
            InputStream inputStream = context.getContentResolver().openInputStream(videoUri);
            FileOutputStream fos = new FileOutputStream(outputFile);
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            fos.close();

            return outputFile.getAbsolutePath();  // Retornar la ruta del archivo
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para obtener el nombre del archivo del URI
    private String getFileName(Uri uri) {
        String fileName = null;
        String[] projection = {MediaStore.MediaColumns.DISPLAY_NAME};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
            fileName = cursor.getString(columnIndex);
            cursor.close();
        }

        // Si no se encuentra el nombre, puede devolver un nombre por defecto o generar uno.
        if (fileName == null) {
            fileName = "video_" + System.currentTimeMillis() + ".mp4";
        }

        return fileName;
    }



    public Uri saveVideoFromBase64(String videoBase64) {
        try {
            byte[] videoBytes = Base64.decode(videoBase64, Base64.DEFAULT);

            // Guardar el video en el almacenamiento
            FileOutputStream fos = null;
            File videoFile = new File(context.getExternalFilesDir(null), "video.mp4");

            fos = new FileOutputStream(videoFile);
            fos.write(videoBytes);
            fos.flush();

            // Devuelve el URI del archivo guardado
            return Uri.fromFile(videoFile);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
