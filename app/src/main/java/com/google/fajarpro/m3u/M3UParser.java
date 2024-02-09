package com.google.fajarpro.m3u;
/*
Author  : Nurul Fajar
Email   : cirebonredhat@gmail.com
*/


import android.util.Log;

import java.util.ArrayList;

public class M3UParser {
    private static final String EXT_INF = "#EXTINF:";
    private static final String EXT_LOGO = "tvg-logo";
    private static final String EXT_M3U = "#EXTM3U";
    private static final String EXT_PLAYLIST_NAME = "#PLAYLIST";
    private static final String EXT_URL = "http://";

    public M3UPlaylist parse(String stream) {
        M3UPlaylist m3UPlaylist = new M3UPlaylist();
        ArrayList<M3UItem> playlistItems = new ArrayList<>();
        String[] linesArray = stream.split(EXT_INF);
        for (String currLine : linesArray) {
            if (currLine.contains(EXT_M3U)) {
                if (currLine.contains(EXT_PLAYLIST_NAME)) {
                    String fileParams = currLine.substring(EXT_M3U.length(), currLine.indexOf(EXT_PLAYLIST_NAME));
                    String playListName = currLine.substring(currLine.indexOf(EXT_PLAYLIST_NAME) + EXT_PLAYLIST_NAME.length()).replace(":", "");
                    m3UPlaylist.setPlaylistName(playListName);
                    m3UPlaylist.setPlaylistParams(fileParams);
                } else {
                    m3UPlaylist.setPlaylistName("Untitled Playlist");
                    m3UPlaylist.setPlaylistParams("No Params");
                }
            } else {
                M3UItem playlistItem = new M3UItem();
                String[] dataArray = currLine.split(",");
                if (dataArray[0].contains(EXT_LOGO)) {
                    String duration = dataArray[0].substring(0, dataArray[0].indexOf(EXT_LOGO)).replace(":", "").replace("\n", "");
                    String icon = dataArray[0].substring(dataArray[0].indexOf(EXT_LOGO) + EXT_LOGO.length()).replace("=", "").replace("\"", "").replace("\n", "");
                    playlistItem.setItemDuration(duration);
                    playlistItem.setItemIcon(icon);
                } else {
                    String duration2 = dataArray[0].replace(":", "").replace("\n", "");
                    playlistItem.setItemDuration(duration2);
                    playlistItem.setItemIcon("");
                }
                try {
                    String url = dataArray[1].substring(dataArray[1].indexOf(EXT_URL)).replace("\n", "").replace("\r", "");
                    String name = dataArray[1].substring(0, dataArray[1].indexOf(EXT_URL)).replace("\n", "");
                    playlistItem.setItemName(name);
                    playlistItem.setItemUrl(url);
                } catch (Exception e) {
                    Log.e("Google", "Error: " + e.fillInStackTrace());
                }
                playlistItems.add(playlistItem);
            }
        }
        m3UPlaylist.setPlaylistItems(playlistItems);
        return m3UPlaylist;
    }
}
