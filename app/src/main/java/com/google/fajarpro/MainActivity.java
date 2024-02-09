package com.google.fajarpro;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.DeviceInfo;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.Tracks;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.CueGroup;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.fajarpro.adapter.TVChannelAdapter;
import com.google.fajarpro.m3u.M3UItem;
import com.google.fajarpro.response.GetChannelResponse;
import com.google.fajarpro.retrofit.RetrofitClient;
import com.google.fajarpro.util.Key;
import com.google.fajarpro.util.SpacesItemDecoration;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends FragmentActivity {

    private static final String API_KEY = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjMwIiwibmFtZSI6IkRlc2EgQWxhbWFuaXMiLCJicmFuY2giOiJDaXJlYm9uIiwiY2l0eSI6IkNpcmVib24iLCJwcm92aW5jZSI6Ikphd2EgQmFyYXQiLCJzdGF0ZSI6IkluZG9uZXNpYSIsImlhdCI6MTY3NDA4ODA0NiwiaXNzIjoiZW5vdmF0dGUifQ.4oa3PryJgK6oSx3t8LmTlpVltwI7eLiQHRI6z50cPXg";
    private static final String BASE_URL = "http://103.30.87.52:8001";
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int STORAGE_PERMISSION_CODE = 23;
    private static final String TAG = "MainActivity";
    private ExoPlayer player;
    private StyledPlayerView playerView;
    private StyledPlayerView playerViewFullscreen;
    private TextView selectedChannel;
    private boolean isFullscreen = false;
    private final HotelProfile profile = new HotelProfile();
    private String selectedUrl = "";
    private String primaryColor = "";
    private String retryUrl = "";
    private int retry = 0;

    public MainActivity() {
    }

    static /* synthetic */ int access$212(MainActivity x0, int x1) {
        int i = x0.retry + x1;
        x0.retry = i;
        return i;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        if (this.isFullscreen) {
            this.playerViewFullscreen.onPause();
        } else {
            this.playerView.onPause();
        }
        releasePlayer();
        super.onPause();
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.selectedChannel = (TextView) findViewById(R.id.channel_selected);
        getHotelProfile();
        updateDateText();
//        if (checkStoragePermissions()) {
//            loadBackgroundImage();
//        } else {
//            requestForStoragePermissions();
//        }
    }



    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.isFullscreen) {
            minimizePlayer();
        } else {
            super.onBackPressed();
        }
    }

    public void onResume() {
        super.onResume();
        initPlayer();
        if (this.selectedUrl.equals("")) {
//            getHotelProfile();
            getChannel();
            return;
        }
        playUrl(this.selectedUrl);
        if (this.isFullscreen) {
            minimizePlayer();
        }
    }

    private void initPlayer() {
        this.playerView = (StyledPlayerView) findViewById(R.id.video_player);
        this.playerViewFullscreen = (StyledPlayerView) findViewById(R.id.video_player_fullscreen);
        DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(getApplicationContext()).forceEnableMediaCodecAsynchronousQueueing();
        ExoPlayer build = new ExoPlayer.Builder(getApplicationContext(), renderersFactory)
                .setLoadControl(new CustomLoadControl.Builder()
                        .setBufferDurationsMs(3000, 8000, 500, 1500)
                        .setPrioritizeTimeOverSizeThresholds(true).build()).build();
        this.player = build;
        build.addListener(new Player.Listener() {
            @Override
            public void onEvents(Player player, Player.Events events) {
                Player.Listener.super.onEvents(player, events);
            }

            @Override
            public void onTimelineChanged(Timeline timeline, int reason) {
                Player.Listener.super.onTimelineChanged(timeline, reason);
            }

            @Override
            public void onMediaItemTransition(@Nullable MediaItem mediaItem, int reason) {
                Player.Listener.super.onMediaItemTransition(mediaItem, reason);
            }

            @Override
                public void onTracksChanged(Tracks tracks) {
                    Player.Listener.super.onTracksChanged(tracks);
                }

            @Override
            public void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
                Player.Listener.super.onMediaMetadataChanged(mediaMetadata);
            }

            @Override
            public void onPlaylistMetadataChanged(MediaMetadata mediaMetadata) {
                Player.Listener.super.onPlaylistMetadataChanged(mediaMetadata);
            }

            @Override
            public void onIsLoadingChanged(boolean isLoading) {
                Player.Listener.super.onIsLoadingChanged(isLoading);
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                Player.Listener.super.onLoadingChanged(isLoading);
            }

            @Override
            public void onAvailableCommandsChanged(Player.Commands availableCommands) {
                Player.Listener.super.onAvailableCommandsChanged(availableCommands);
            }

            @Override
            public void onTrackSelectionParametersChanged(TrackSelectionParameters parameters) {
                Player.Listener.super.onTrackSelectionParametersChanged(parameters);
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Player.Listener.super.onPlayerStateChanged(playWhenReady, playbackState);
            }

            @Override
            public void onPlaybackStateChanged(int playbackState) {
                Player.Listener.super.onPlaybackStateChanged(playbackState);
                Log.d("DEBUG", "onPlaybackStateChanged: " + playbackState);
                if (playbackState == 7 || playbackState == 1 || playbackState == 0) {
                    MainActivity mainActivity = MainActivity.this;
                    mainActivity.retryUrl = mainActivity.selectedUrl;
                    MainActivity.access$212(MainActivity.this, 1);
                    MainActivity mainActivity2 = MainActivity.this;
                    mainActivity2.playUrl(mainActivity2.selectedUrl);
                } else if (playbackState == 3) {
                    MainActivity.this.retryUrl = "";
                    MainActivity.this.retry = 0;
                } else if (playbackState == 4 || playbackState == 10 || playbackState == 11 || playbackState == 9) {
                    MainActivity.this.player.stop();
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.infinitv.tv.MainActivity.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            MainActivity.this.player.prepare();
                        }
                    }, 500L);
                }
            }

            @Override
            public void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {
                Player.Listener.super.onPlayWhenReadyChanged(playWhenReady, reason);
            }

            @Override
            public void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {
                Player.Listener.super.onPlaybackSuppressionReasonChanged(playbackSuppressionReason);
            }

            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                Player.Listener.super.onIsPlayingChanged(isPlaying);
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {
                Player.Listener.super.onRepeatModeChanged(repeatMode);
            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
                Player.Listener.super.onShuffleModeEnabledChanged(shuffleModeEnabled);
            }

            @Override
            public void onPlayerError(PlaybackException error) {
                Player.Listener.super.onPlayerError(error);
                MainActivity mainActivity = MainActivity.this;
                mainActivity.retryUrl = mainActivity.selectedUrl;
                MainActivity.access$212(MainActivity.this, 1);
                MainActivity mainActivity2 = MainActivity.this;
                mainActivity2.playUrl(mainActivity2.selectedUrl);

            }

            @Override
            public void onPlayerErrorChanged(@Nullable PlaybackException error) {
                Player.Listener.super.onPlayerErrorChanged(error);
                if (error != null) {
                    MainActivity mainActivity = MainActivity.this;
                    mainActivity.retryUrl = mainActivity.selectedUrl;
                    MainActivity.access$212(MainActivity.this, 1);
                    MainActivity mainActivity2 = MainActivity.this;
                    mainActivity2.playUrl(mainActivity2.selectedUrl);
                }

            }

            @Override
            public void onPositionDiscontinuity(int reason) {
                Player.Listener.super.onPositionDiscontinuity(reason);
            }

            @Override
            public void onPositionDiscontinuity(Player.PositionInfo oldPosition, Player.PositionInfo newPosition, int reason) {
                Player.Listener.super.onPositionDiscontinuity(oldPosition, newPosition, reason);
            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                Player.Listener.super.onPlaybackParametersChanged(playbackParameters);
            }

            @Override
            public void onSeekBackIncrementChanged(long seekBackIncrementMs) {
                Player.Listener.super.onSeekBackIncrementChanged(seekBackIncrementMs);
            }

            @Override
            public void onSeekForwardIncrementChanged(long seekForwardIncrementMs) {
                Player.Listener.super.onSeekForwardIncrementChanged(seekForwardIncrementMs);
            }

            @Override
            public void onMaxSeekToPreviousPositionChanged(long maxSeekToPreviousPositionMs) {
                Player.Listener.super.onMaxSeekToPreviousPositionChanged(maxSeekToPreviousPositionMs);
            }

            @Override
            public void onAudioSessionIdChanged(int audioSessionId) {
                Player.Listener.super.onAudioSessionIdChanged(audioSessionId);
            }

            @Override
            public void onAudioAttributesChanged(AudioAttributes audioAttributes) {
                Player.Listener.super.onAudioAttributesChanged(audioAttributes);
            }

            @Override
            public void onVolumeChanged(float volume) {
                Player.Listener.super.onVolumeChanged(volume);
            }

            @Override
            public void onSkipSilenceEnabledChanged(boolean skipSilenceEnabled) {
                Player.Listener.super.onSkipSilenceEnabledChanged(skipSilenceEnabled);
            }

            @Override
            public void onDeviceInfoChanged(DeviceInfo deviceInfo) {
                Player.Listener.super.onDeviceInfoChanged(deviceInfo);
            }

            @Override
            public void onDeviceVolumeChanged(int volume, boolean muted) {
                Player.Listener.super.onDeviceVolumeChanged(volume, muted);
            }

            @Override
            public void onVideoSizeChanged(VideoSize videoSize) {
                Player.Listener.super.onVideoSizeChanged(videoSize);
            }

            @Override
            public void onSurfaceSizeChanged(int width, int height) {
                Player.Listener.super.onSurfaceSizeChanged(width, height);
            }

            @Override
            public void onRenderedFirstFrame() {
                Player.Listener.super.onRenderedFirstFrame();
            }

            @Override
            public void onCues(List<Cue> cues) {
                Player.Listener.super.onCues(cues);
            }

            @Override
            public void onCues(CueGroup cueGroup) {
                Player.Listener.super.onCues(cueGroup);
            }

            @Override
            public void onMetadata(Metadata metadata) {
                Player.Listener.super.onMetadata(metadata);
            }
        });
        this.player.setPlayWhenReady(true);
        this.playerViewFullscreen.setPlayer(null);
        this.playerView.setPlayer(this.player);


    }

    private void releasePlayer() {
        ExoPlayer exoPlayer = this.player;
        if (exoPlayer != null) {
            exoPlayer.release();
            this.player = null;
            this.playerView.setPlayer(null);
            this.playerViewFullscreen.setPlayer(null);
        }
    }

    private void fullscreen() {
        this.isFullscreen = true;
        this.playerViewFullscreen.setVisibility(View.VISIBLE);
        this.player.stop();
        this.playerView.setPlayer(null);
        this.playerViewFullscreen.setPlayer(this.player);
    }

    private void minimizePlayer() {
        this.isFullscreen = false;
        this.playerViewFullscreen.setVisibility(View.GONE);
        this.player.stop();
        this.playerViewFullscreen.setPlayer(null);
        this.playerView.setPlayer(this.player);
    }

    private void playChannel(M3UItem channel) {
        if (channel.getItemUrl().equals(this.selectedUrl)) {
            fullscreen();
        } else if (this.player == null) {

        } else {
            this.selectedChannel.setText(channel.getItemName());
            String itemUrl = channel.getItemUrl();
            this.selectedUrl = itemUrl;
            playUrl(itemUrl);
        }
    }

//    private void playChannel(M3UItem channel) {
//        if (channel.getItemUrl().equals(this.selectedUrl)) {
//            fullscreen();
//        } else if (this.player == null) {
//
//        } else {
//            this.selectedChannel.setText(channel.getItemName());
//            String itemUrl = channel.getItemUrl();
//            this.selectedUrl = itemUrl;
//            playUrl(itemUrl);
//        }
//    }

    public void playUrl(String url) {
        this.player.stop();
        if (this.retryUrl.equals(url) && this.retry > 10) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.infinitv.tv.MainActivity.2
                @Override // java.lang.Runnable
                public void run() {
                    MainActivity.this.retry = 0;
                    MainActivity.this.player.prepare();
                }
            }, 500L);
            return;
        }
        this.player.setMediaItem(MediaItem.fromUri(url));
        this.player.prepare();
    }

    private void loadBackgroundImage() {
        File imgFile = new File("/storage/emulated/0/tv/drawer-bg.jpg");
        Uri uri = Uri.fromFile(imgFile);
        if (imgFile.exists()) {
            Log.d(TAG, "loadBackgroundImage: image file exist");
            ImageView imageIV = findViewById(R.id.main_background);
            Picasso.get().load(uri).into(imageIV);
        }else{
            imgFile.exists();
        }
    }

    private void loadLogo(String url) {
        ImageView logo = (ImageView) findViewById(R.id.logo);
        Picasso.get().load(R.drawable.hotel_default_logo).into(logo);
    }

    private void getHotelProfile() {
        this.profile.getProfile(getApplicationContext(), API_KEY, new HotelProfile.Listener() { // from class: com.infinitv.tv.MainActivity$$ExternalSyntheticLambda1
            @Override // com.infinitv.p007tv.HotelProfile.Listener
            public final void onGetProfile(String str, String str2) {
                MainActivity.this.m367lambda$getHotelProfile$0$cominfinitvtvMainActivity(str, str2);
            }
        });
    }

    public /* synthetic */ void m367lambda$getHotelProfile$0$cominfinitvtvMainActivity(String logoWhite, String primaryColor) {
        loadLogo("http://103.30.87.52:8001/files/" + logoWhite);
        this.primaryColor = primaryColor;
        getChannel();
    }

//    private void getTVChannels() {
//        M3ULoader loader = new M3ULoader();
//        loader.setOnItemClickListener(new M3ULoader.OnPlaylistLoadedListener() {
//            @Override // com.infinitv.p007tv.m3u.M3ULoader.OnPlaylistLoadedListener
//            public final void onPlaylistLoaded(M3UPlaylist m3UPlaylist) {
//                MainActivity.this.m370lambda$getTVChannels$3$cominfinitvtvMainActivity(m3UPlaylist);
//            }
//        });
//        loader.load(getApplicationContext(), API_KEY);
//    }


    private void getChannel(){

        Call<GetChannelResponse> call = RetrofitClient.getInstance().
                getApi().
                getUrl(new Key().getAPI_KEY());
        call.enqueue(new Callback<GetChannelResponse>() {
            @Override
            public void onResponse(Call<GetChannelResponse> call, Response<GetChannelResponse> response) {
                if (response.isSuccessful()) {
//                    progressBar.setVisibility(View.GONE);
                    GetChannelResponse getChannelResponse = response.body();

                    for (int i = 0; i < getChannelResponse.getData().size(); i++) {
                        Log.d(TAG, "onResponse: "+ getChannelResponse.getData().get(i).getName());
                    }

                    if (getChannelResponse.getData().size() > 0){
                        playUrl(getChannelResponse.getData().get(0).getUrl());
                        selectedUrl = getChannelResponse.getData().get(0).getUrl();
                        selectedChannel.setText(getChannelResponse.getData().get(0).getName());
                    }

                    RecyclerView rvChannels = (RecyclerView) findViewById(R.id.channel_list);
                    TVChannelAdapter adapter = new TVChannelAdapter(getChannelResponse);
                    adapter.setPrimaryColor(primaryColor);
                    adapter.setOnItemClickListener(new TVChannelAdapter.OnItemClickListener() {

                        @Override // com.infinitv.p007tv.TVChannelAdapter.OnItemClickListener
                        public final void onItemClick(View view, int i) {
//                            M3UItem channel = (M3UItem) channels.get(i);
                            selectedUrl = getChannelResponse.getData().get(i).getUrl();
                            playUrl(getChannelResponse.getData().get(i).getUrl());
                            selectedChannel.setText(getChannelResponse.getData().get(i).getName());
                        }
                    });
                    adapter.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.infinitv.tv.MainActivity$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnFocusChangeListener
                        public final void onFocusChange(View view, boolean z) {
                            if (isFullscreen) {
                                minimizePlayer();
                            }
                        }
                    });
                    rvChannels.setAdapter(adapter);
                    rvChannels.addItemDecoration(new SpacesItemDecoration(5));
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    rvChannels.setLayoutManager(layoutManager);

                } else {
//                    progressBar.setVisibility(View.GONE);
                    Log.d("TAG", "onResponse: success :" + response.message());
//                    Toast.makeText(getApplicationContext(), "Unauthorized : " + response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetChannelResponse> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: "+t.getMessage());
//                Toast.makeText(getApplicationContext(), "Gagal mendapatkan response : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public /* synthetic */ void m370lambda$getTVChannels$3$cominfinitvtvMainActivity(M3UPlaylist playlist) {
//        final ArrayList<M3UItem> channels = playlist.getPlaylistItems();
////        channels.remove(0);
//        for (int i = 0; i < channels.size(); i++) {
//            Log.d("channeltv", channels.get(i).getItemName()+" : "+channels.get(i).getItemUrl());
//        }
//
//        if (channels.size() > 0) {
//            playChannel(channels.get(10));
//        }
//        RecyclerView rvChannels = (RecyclerView) findViewById(R.id.channel_list);
//        TVChannelAdapter adapter = new TVChannelAdapter(channels);
//        adapter.setPrimaryColor(this.primaryColor);
//        adapter.setOnItemClickListener(new TVChannelAdapter.OnItemClickListener() {
//
//            @Override // com.infinitv.p007tv.TVChannelAdapter.OnItemClickListener
//            public final void onItemClick(View view, int i) {
//                M3UItem channel = (M3UItem) channels.get(i);
//                playChannel(channel);
//            }
//        });
//        adapter.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.infinitv.tv.MainActivity$$ExternalSyntheticLambda0
//            @Override // android.view.View.OnFocusChangeListener
//            public final void onFocusChange(View view, boolean z) {
//                if (isFullscreen) {
//                    minimizePlayer();
//                }
//            }
//        });
//        rvChannels.setAdapter(adapter);
//        rvChannels.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//    }

    private void updateDateText() {
        TextView text = (TextView) findViewById(R.id.text_date);
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy", Locale.US);
        String date = dateFormat.format(Calendar.getInstance().getTime());
        text.setText(date);
    }

    public boolean checkStoragePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //Android is 11 (R) or above
            return Environment.isExternalStorageManager();
        } else {
            //Below android 11
            int write = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int read = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

            return read == PackageManager.PERMISSION_GRANTED && write == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestForStoragePermissions() {
        //Android is 11 (R) or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent);
            }
        } else {
            //Below android 11
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    },
                    STORAGE_PERMISSION_CODE
            );
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200) {
            if (grantResults.length == 0 || grantResults[0] == -1) {
                Toast.makeText(this, "Read External Storage permission denied", Toast.LENGTH_SHORT).show();
            } else {
                loadBackgroundImage();
            }
        }
    }


}

