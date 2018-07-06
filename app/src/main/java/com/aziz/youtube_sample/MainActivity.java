package com.aziz.youtube_sample;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.utils.YouTubePlayerTracker;

public class MainActivity extends AppCompatActivity {
    private YouTubePlayer initializedYouTubePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final YouTubePlayerView youtubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youtubePlayerView);
        youtubePlayerView.getPlayerUIController().setFullScreenButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializedYouTubePlayer.pause();
                Toast.makeText(MainActivity.this, "Yo", Toast.LENGTH_SHORT).show();
            }
        });
        YouTubePlayerTracker tracker = new YouTubePlayerTracker();
        initializedYouTubePlayer.addListener(tracker);

        youtubePlayerView.initialize(new YouTubePlayerInitListener() {

            @Override
            public void onInitSuccess(@NonNull final YouTubePlayer initializedYouTubePlayer) {
                MainActivity.this.initializedYouTubePlayer = initializedYouTubePlayer;

                initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        String videoId = "6JYIGclVQdw";
                        initializedYouTubePlayer.loadVideo(videoId, 0);
                    }
                });
            }
        }, true);
    }



    public void showYoutubeDialog(Context mBaseActivity, final String id, final int seconds) {

        YouTubePlayer youTubePlayer = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(mBaseActivity, R.style.FULL_SCREEN);
        AlertDialog dialog = builder.create();
        View root = getLayoutInflater().inflate(R.layout.dialog_youtube,null,false);
        builder.setView(root);

        YouTubePlayerView youtubeView = root.findViewById(R.id.youtube_player_view);
        youtubeView.enterFullScreen();
        youtubeView.initialize(new YouTubePlayerInitListener() {
            @Override
            public void onInitSuccess(YouTubePlayer youTubePlayer) {
                youTubePlayer = youTubePlayer;

                final YouTubePlayer finalYouTubePlayer = youTubePlayer;
                AbstractYouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        super.onReady();
                        finalYouTubePlayer.loadVideo(id, seconds);
                    }

                };
                youTubePlayer.addListener(listener);
            }
        }, true);

        dialog.show();

    }
}
