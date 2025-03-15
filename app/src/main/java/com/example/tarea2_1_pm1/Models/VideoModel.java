package com.example.tarea2_1_pm1.Models;

import android.net.Uri;

import java.sql.Blob;

public class VideoModel {
    private int id;
    private String nombre;
    private Uri video;

    public VideoModel() {
    }

    public VideoModel(int id, String nombre, Uri video) {
        this.id = id;
        this.nombre = nombre;
        this.video = video;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Uri getVideo() {
        return video;
    }

    public void setVideo(Uri video) {
        this.video = video;
    }
}
