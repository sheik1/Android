package com.example.sheikr.muziekapplicatie;

//https://github.com/vpaliyX/SoundCloud-API

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sheikr.muziekapplicatie.visualizer.VisualizerActivity;
import com.example.sheikr.muziekapplicatie.youtubeplayer.youtubeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.button2);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), VisualizerActivity.class );
            startActivity(intent);


        });
    }
}
