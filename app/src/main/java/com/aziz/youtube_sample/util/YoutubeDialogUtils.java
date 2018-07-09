package com.aziz.youtube_sample.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.aziz.youtube_sample.R;
import com.aziz.youtube_sample.util.ServiceListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.utils.YouTubePlayerTracker;

public class YoutubeDialogUtils {


    private static YouTubePlayer dialogPLayer;
    private static YouTubePlayerTracker dialogTracker;

    //You don't wanna know what's going on in here
    //JK, Its a dialog view with full screen, with a callback to the original list item
    public static void showYoutubeDialog(Context mBaseActivity, final String id, final int seconds, final ServiceListener<Integer> listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mBaseActivity, R.style.FULL_SCREEN);
        final AlertDialog dialog = builder.create();
        LayoutInflater inflater = (LayoutInflater) mBaseActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.dialog_youtube, null, false);
        dialog.setView(root);
        dialog.show();

        //JK, get the seconds from video list item and start playing
        YouTubePlayerView youtubeView = root.findViewById(R.id.youtube_view);
        youtubeView.initialize(new YouTubePlayerInitListener() {
            @Override
            public void onInitSuccess(@NonNull YouTubePlayer youTubePlayer) {
                dialogPLayer = youTubePlayer;
                AbstractYouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        super.onReady();
                        //add a time tracker for dialog youtube player
                        dialogPLayer.loadVideo(id, seconds);
                        dialogTracker = new YouTubePlayerTracker();
                        dialogPLayer.addListener(dialogTracker);
                    }
                };
                youTubePlayer.addListener(listener);
            }
        }, true);
        //when user clicks fullscreen button to get out of the full screen instead of back
        youtubeView.getPlayerUIController().setFullScreenButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //when the dialog is dismissing, get the current time of the dialog player and send it back using callback
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //we probably couldn't reach the tracker yet but the user dismissed it
                if (dialogTracker == null) {
                    listener.fail(0);
                    return;
                }
                //return current video time in callback
                listener.success((int) dialogTracker.getCurrentSecond());
            }
        });

    }
}
