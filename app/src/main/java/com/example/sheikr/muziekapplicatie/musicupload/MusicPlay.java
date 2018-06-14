package com.example.sheikr.muziekapplicatie.musicupload;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sheikr.muziekapplicatie.MainActivity;
import com.example.sheikr.muziekapplicatie.R;
import com.example.sheikr.muziekapplicatie.youtubeLijst.YoutubeListPanel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MusicPlay extends MainActivity {

    DatabaseReference database;
    ListView muziekListView;
    String url, user;
    List<MusicModel> muzieklijst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MusicPlay.this);
        user = sp.getString("gebruiker", null);

        database = FirebaseDatabase.getInstance().getReference("music");

        muziekListView = (ListView) findViewById(R.id.MusicListView);

        muzieklijst = new ArrayList<>();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                muzieklijst.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MusicModel muziek = snapshot.getValue(MusicModel.class);

                    if(user.equals(muziek.getGebruiker())) {

                        muzieklijst.add(muziek);
                        url = muziek.getUrl();
                    }
                }

                MusicAdapter adapter = new MusicAdapter(MusicPlay.this, muzieklijst);
                muziekListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        muziekListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                MusicModel musicModel = muzieklijst.get(position);

                Intent music = new Intent(getApplicationContext(), MusicActivity.class );
                music.putExtra("url", musicModel.getUrl());
                startActivity(music);

                return false;
            }
        });
    }
}