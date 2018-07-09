package com.aziz.youtube_sample;

import java.util.ArrayList;
import java.util.List;

//List repository
public class SongList {
    private static ArrayList<String> songs;

    //Create a fancy song list from Diversity youtube channel
    private static ArrayList<String> createSongList() {
        songs = new ArrayList<>();
        songs.add("0ODWYEI4zuM");
        songs.add("Y5KWCOeFOQA");
        songs.add("4zc-5hxwHrQ");
        songs.add("oDw3rlAZMs8");
        songs.add("qaub0QpzVec");
        songs.add("2axgtOhLyRA");
        songs.add("bXmjCCwj4xY");
        return songs;
    }

    //return the list
    public static List<String> getList() {
        if (songs == null) {
            songs = createSongList();
        }
        return songs;
    }
}
