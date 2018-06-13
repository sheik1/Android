package com.example.sheikr.muziekapplicatie.musicupload;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sheikr.muziekapplicatie.MainActivity;
import com.example.sheikr.muziekapplicatie.R;
import com.example.sheikr.muziekapplicatie.UserLogin;
import com.example.sheikr.muziekapplicatie.drumpad.DrumpadActivity;
import com.example.sheikr.muziekapplicatie.equalizer.EqualizerActivity;
import com.example.sheikr.muziekapplicatie.musicPlayer.PlayListActivity;
import com.example.sheikr.muziekapplicatie.visualizer.VisualizerActivity;
import com.example.sheikr.muziekapplicatie.youtubeLijst.YoutubeListPanel;
import com.example.sheikr.muziekapplicatie.youtubeplayer.YoutubeActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class MusicActivity extends MainActivity implements MediaPlayer.OnPreparedListener{

    private DrawerLayout drawer;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MusicActivity.this);
        user = sp.getString("gebruiker", null);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.draw_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Button playButton = (Button) findViewById(R.id.play_button);

        NavigationView navview = (NavigationView) findViewById(R.id.nav_view);
        View headerView =  navview.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.user_email_nav);
        navUsername.setText(user);

        SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String url =(mSharedPreference.getString("key", ""));

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    Uri myUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/musicapp-82f20.appspot.com/o/music%2Fdf296993-4046-4ce9-bbd4-1e266d49c429?alt=media&token=d953e887-77b9-410e-82aa-dcb9931dd935");
                    System.out.println(myUri);
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(v.getContext(), myUri);
                    mediaPlayer.prepare();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                        @Override
                        public void onPrepared(MediaPlayer player){
                            player.start();
                        }
                    });
                }catch(IOException e){
                    e.printStackTrace();
                }


            }
        });

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }



    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_firebase_music:
                Intent music = new Intent(getApplicationContext(), MusicActivity.class );
                startActivity(music);
                break;
            case R.id.nav_youtube:
                Intent youtube = new Intent(getApplicationContext(), YoutubeActivity.class );
                startActivity(youtube);
                break;
            case R.id.nav_youtube_list:
                Intent youtubelist = new Intent(getApplicationContext(), YoutubeListPanel.class );
                startActivity(youtubelist);
                break;
            case R.id.nav_streamboxr:
                Intent stream = new Intent(getApplicationContext(), PlayListActivity.class );
                startActivity(stream);
                break;
            case R.id.nav_visualizer:
                Intent viz = new Intent(getApplicationContext(), VisualizerActivity.class );
                startActivity(viz);
                break;
            case R.id.nav_equalizer:
                Intent equa = new Intent(getApplicationContext(), EqualizerActivity.class );
                startActivity(equa);
                break;
            case R.id.nav_drumpad:
                Intent drum = new Intent(getApplicationContext(), DrumpadActivity.class );
                startActivity(drum);
                break;
            case R.id.nav_upload:
                Intent upload = new Intent(getApplicationContext(), MusicUpload.class );
                startActivity(upload);
                break;
            case R.id.nav_signout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), UserLogin.class );
                startActivity(intent);
                break;

            default:
                break;
        }
        return true;
    }


}
