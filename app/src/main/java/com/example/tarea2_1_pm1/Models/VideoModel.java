package com.example.tarea2_1_pm1.Models;

import java.sql.Blob;

public class VideoModel {
    private int id;
    private String nombre;
    private Blob video;

    public VideoModel() {
    }

    public VideoModel(Blob video, String nombre, int id) {
        this.video = video;
        this.nombre = nombre;
        this.id = id;
    }

    public Blob getVideo() {
        return video;
    }

    public void setVideo(Blob video) {
        this.video = video;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
