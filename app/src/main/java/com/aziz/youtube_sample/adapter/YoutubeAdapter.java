package com.aziz.youtube_sample.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aziz.youtube_sample.R;
import com.aziz.youtube_sample.ui.MainActivity;
import com.aziz.youtube_sample.util.ServiceListener;
import com.aziz.youtube_sample.util.YoutubeDialogUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.utils.YouTubePlayerTracker;

import java.util.List;

public class YoutubeAdapter extends RecyclerViewBaseAdapter<String, YoutubeAdapter.ViewHolder> {

    public YoutubeAdapter(List<String> items, @Nullable ItemSelectedListener<String> itemSelectedListener) {
        super(items, itemSelectedListener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.li_youtube, null, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(getItem(position), position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private YouTubePlayer initializedYouTubePlayer;
        private YouTubePlayerTracker tracker;
        YouTubePlayerView youtubePlayerView;
        ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            youtubePlayerView = itemView.findViewById(R.id.youtube_player_view);
        }

        void setItem(final String videoId, int position) {

            youtubePlayerView.getPlayerUIController().setFullScreenButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initializedYouTubePlayer.pause();
                    //Send the current seconds to the dialog
                    YoutubeDialogUtils.showYoutubeDialog(itemView.getContext(), videoId, (int) tracker.getCurrentSecond(), new ServiceListener<Integer>() {
                        @Override
                        public void success(@Nullable Integer seconds) {
                            //callback received with time of the dialog player
                            initializedYouTubePlayer.seekTo(seconds == null ? 0 : seconds);
                            initializedYouTubePlayer.play();
                        }
                        @Override
                        public void fail(@Nullable Integer obj) {
                            //callback received that things didn't work out in the dialog
                            if (tracker != null) {
                                //so continue where we left off
                                initializedYouTubePlayer.seekTo(tracker.getCurrentSecond());
                                return;
                            }
                            //or restart the video
                            initializedYouTubePlayer.loadVideo(videoId, 0);
                        }
                    });
                }
            });

            youtubePlayerView.initialize(new YouTubePlayerInitListener() {
                @Override
                public void onInitSuccess(@NonNull final YouTubePlayer initializedYouTubePlayer) {
                    ViewHolder.this.initializedYouTubePlayer = initializedYouTubePlayer;
                    //Create a time tracker of this video
                    tracker = new YouTubePlayerTracker();
                    initializedYouTubePlayer.addListener(tracker);
                    initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady() {
                            //load the video, but don't play it yet
                            initializedYouTubePlayer.cueVideo(videoId, 0);
                        }
                    });
                }
            }, true);
        }
    }
}
