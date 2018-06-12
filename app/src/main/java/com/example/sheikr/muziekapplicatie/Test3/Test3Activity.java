package com.example.sheikr.muziekapplicatie.Test3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.sheikr.muziekapplicatie.R;
import com.example.sheikr.muziekapplicatie.youtubeLijst.Youtube;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class Test3Activity extends AppCompatActivity {

    DatabaseReference databaseYoutube;
    ValueEventListener valueEventListener;

    RecyclerView recyclerView;

    Vector<YoutubeVideo> youtubeVideos = new Vector<YoutubeVideo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseYoutube = FirebaseDatabase.getInstance().getReference("youtube");

        valueEventListener = databaseYoutube.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot youtubeSnapshot: dataSnapshot.getChildren()){
                    VideoModel videoModel = youtubeSnapshot.getValue(VideoModel.class);

                    youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"+ videoModel.getLink() +"\" frameborder=\"0\"  allowfullscreen></iframe>"));

                    VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);

                    recyclerView.setAdapter(videoAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }
}
