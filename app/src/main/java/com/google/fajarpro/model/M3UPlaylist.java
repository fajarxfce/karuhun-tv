package com.google.fajarpro.model;
/*
Author  : Nurul Fajar
Email   : cirebonredhat@gmail.com
*/


import java.util.ArrayList;

public class M3UPlaylist {
    ArrayList<M3UItem> playlistItems;
    private String playlistName;
    private String playlistParams;

    public String getPlaylistName() {
        return this.playlistName;
    }

    public String getPlaylistParams() {
        return this.playlistParams;
    }

    public ArrayList<M3UItem> getPlaylistItems() {
        return this.playlistItems;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public void setPlaylistParams(String playlistParams) {
        this.playlistParams = playlistParams;
    }

    public void setPlaylistItems(ArrayList<M3UItem> playlistItems) {
        this.playlistItems = playlistItems;
    }

    public String getSingleParameter(String paramName) {
        String[] paramsArray = this.playlistParams.split(" ", 0);
        for (String parameter : paramsArray) {
            if (parameter.contains(paramName)) {
                return parameter.substring(parameter.indexOf(paramName) + paramName.length()).replace("=", "");
            }
        }
        return "";
    }
}
