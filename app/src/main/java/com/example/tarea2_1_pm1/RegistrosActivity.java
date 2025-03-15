package com.example.tarea2_1_pm1;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarea2_1_pm1.Adapter.videoListaAdapter;
import com.example.tarea2_1_pm1.Models.VideoModel;
import com.example.tarea2_1_pm1.repository.VideoRepository;

import java.util.ArrayList;
import java.util.List;

public class RegistrosActivity extends AppCompatActivity {

    VideoRepository videoRepository = new VideoRepository(this);
    RecyclerView listaVideo;
    List<VideoModel> ListVideos;
    videoListaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.registros);

        listaVideo = findViewById(R.id.listView);
        listaVideo.setLayoutManager(new LinearLayoutManager(this));
        ListVideos = videoRepository.getAllSavedVideos();
        adapter = new videoListaAdapter(this,ListVideos);
        listaVideo.setAdapter(adapter);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}