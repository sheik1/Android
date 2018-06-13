package com.example.sheikr.muziekapplicatie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.sheikr.muziekapplicatie.drumpad.DrumpadActivity;
import com.example.sheikr.muziekapplicatie.equalizer.EqualizerActivity;
import com.example.sheikr.muziekapplicatie.musicPlayer.PlayListActivity;
import com.example.sheikr.muziekapplicatie.musicupload.MusicPlay;
import com.example.sheikr.muziekapplicatie.musicupload.MusicUpload;
import com.example.sheikr.muziekapplicatie.visualizer.VisualizerActivity;
import com.example.sheikr.muziekapplicatie.youtubeLijst.YoutubeListPanel;
import com.example.sheikr.muziekapplicatie.youtubeplayer.YoutubeActivity;
import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("hello1", "hello111111111111111");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.draw_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

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
                Intent upload = new Intent(getApplicationContext(), MusicPlay.class );
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


