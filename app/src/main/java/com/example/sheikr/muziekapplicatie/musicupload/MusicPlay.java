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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicPlay extends MainActivity implements MediaPlayer.OnPreparedListener {

    private DatabaseReference database;

    private RecyclerView recyclerView;

    private MediaPlayer mediaPlayer;

    public String url;
    private List<MusicModel> lijst;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);


        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewMusic);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        lijst = new ArrayList<>();

        database = FirebaseDatabase.getInstance().getReference("music");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    MusicModel muziek  = snapshot.getValue(MusicModel.class);

                    lijst.add(muziek);
                    url = muziek.getNaam();
                }

                MusicAdapter adapter = new MusicAdapter(getApplicationContext(), lijst);

                recyclerView.setAdapter(adapter);

                //mediaPlayer = new MediaPlayer();
                //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                //fetchAudioUrlFromFirebase();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void fetchAudioUrlFromFirebase(){

        final FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl(url);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    final String url = uri.toString();
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.setOnPreparedListener(MusicPlay.this);
                    mediaPlayer.prepareAsync();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }


}
