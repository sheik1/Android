package com.example.sheikr.muziekapplicatie.youtubeplayer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.sheikr.muziekapplicatie.MainActivity;
import com.example.sheikr.muziekapplicatie.R;
import com.example.sheikr.muziekapplicatie.UserLogin;
import com.example.sheikr.muziekapplicatie.drumpad.DrumpadActivity;
import com.example.sheikr.muziekapplicatie.equalizer.EqualizerActivity;
import com.example.sheikr.muziekapplicatie.musicPlayer.PlayListActivity;

import com.example.sheikr.muziekapplicatie.musicupload.MusicActivity;
import com.example.sheikr.muziekapplicatie.musicupload.MusicPlay;
import com.example.sheikr.muziekapplicatie.musicupload.MusicUpload;
import com.example.sheikr.muziekapplicatie.visualizer.VisualizerActivity;
import com.example.sheikr.muziekapplicatie.youtubeLijst.YoutubeListPanel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class YoutubeActivity extends MainActivity {

    private DrawerLayout drawer;
    DatabaseReference databaseYoutube;
    ValueEventListener valueEventListener;

    RecyclerView recyclerView;

    Vector<YoutubeVideo> youtubeVideos = new Vector<YoutubeVideo>();

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(YoutubeActivity.this);
        user = sp.getString("gebruiker", null);

        NavigationView navview = (NavigationView) findViewById(R.id.nav_view);
        View headerView =  navview.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.user_email_nav);
        navUsername.setText(user);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseYoutube = FirebaseDatabase.getInstance().getReference("youtube");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.draw_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        valueEventListener = databaseYoutube.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot youtubeSnapshot: dataSnapshot.getChildren()){
                    VideoModel videoModel = youtubeSnapshot.getValue(VideoModel.class);

                    if(user.equals(videoModel.getGebruiker()) ) {

                        youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoModel.getLink() + "\" frameborder=\"0\"  allowfullscreen></iframe>"));

                        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);

                        recyclerView.setAdapter(videoAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
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
        Log.d("MUsicupload", "onNavigationItemSelected: ");
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
