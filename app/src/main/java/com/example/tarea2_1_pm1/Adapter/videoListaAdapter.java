package com.example.tarea2_1_pm1.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarea2_1_pm1.MainActivity;
import com.example.tarea2_1_pm1.Models.VideoModel;
import com.example.tarea2_1_pm1.R;
import com.example.tarea2_1_pm1.repository.VideoRepository;

import java.util.List;

public class videoListaAdapter extends RecyclerView.Adapter<videoListaAdapter.ViewHolder> {

    private Context context;
    private List<VideoModel> videos;

    public videoListaAdapter(Context context, List<VideoModel> videos) {
        this.context = context;
        this.videos = videos;
    }

    @NonNull
    @Override
    public videoListaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_video,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VideoModel video = videos.get(position);
        holder.textView.setText(video.getNombre());
        holder.videoView.setVideoURI(video.getVideo());
        holder.videoView.start();
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        VideoView videoView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView2);
            videoView = itemView.findViewById(R.id.videoView1);
        }
    }
}
