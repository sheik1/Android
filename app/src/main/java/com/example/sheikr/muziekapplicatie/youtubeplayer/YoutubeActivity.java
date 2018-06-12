package com.example.sheikr.muziekapplicatie.youtubeplayer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.sheikr.muziekapplicatie.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class YoutubeActivity extends AppCompatActivity {

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

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseYoutube = FirebaseDatabase.getInstance().getReference("youtube");

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
}
