package com.aziz.youtube_sample.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aziz.youtube_sample.R;
import com.aziz.youtube_sample.SongList;
import com.aziz.youtube_sample.adapter.YoutubeAdapter;
import com.aziz.youtube_sample.util.ServiceListener;
import com.aziz.youtube_sample.util.YoutubeDialogUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.utils.YouTubePlayerTracker;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getLifecycle().addObserver(youtubePlayerView); // don't really need this for now
        RecyclerView videoList = findViewById(R.id.videoList);
        //Fancy Adapter
        YoutubeAdapter adapter = new YoutubeAdapter(SongList.getList(), null);
        videoList.setLayoutManager(new LinearLayoutManager(this));
        videoList.setAdapter(adapter);
    }
}
