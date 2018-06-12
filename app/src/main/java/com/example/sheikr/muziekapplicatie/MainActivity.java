package com.example.sheikr.muziekapplicatie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.sheikr.muziekapplicatie.drumpad.DrumpadActivity;
import com.example.sheikr.muziekapplicatie.equalizer.EqualizerActivity;
import com.example.sheikr.muziekapplicatie.musicPlayer.PlayListActivity;
import com.example.sheikr.muziekapplicatie.musicupload.MusicUpload;
import com.example.sheikr.muziekapplicatie.visualizer.VisualizerActivity;
import com.example.sheikr.muziekapplicatie.youtubeplayer.YoutubeActivity;


public class MainActivity extends AppCompatActivity {

    GridView grid;
    String[] muziek = {
            "Music Player",
            "Visualizer",
            "Youtube Player",
            "Youtube Player List",
            "Drumpad",
            "Equalizer",
            "Upload"
    };

    int[] imageId = {
            R.drawable.normal,
            R.drawable.normal,
            R.drawable.normal,
            R.drawable.normal,
            R.drawable.normal,
            R.drawable.normal,
            R.drawable.normal,
            R.drawable.normal
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainGridActivity adapter = new MainGridActivity(MainActivity.this, muziek, imageId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (muziek[+position]){

                    case "Music Player":
                        Intent intent = new Intent(getApplicationContext(), PlayListActivity.class );
                        startActivity(intent);
                        break;

                    case "Youtube Player":
                        Intent intent2 = new Intent(getApplicationContext(), YoutubeActivity.class );
                        startActivity(intent2);
                        break;

                    case "Visualizer":
                        Intent intent3 = new Intent(getApplicationContext(), VisualizerActivity.class );
                        startActivity(intent3);
                        break;

                    case "Drumpad":
                        Intent intent4 = new Intent(getApplicationContext(), DrumpadActivity.class );
                        startActivity(intent4);
                        break;

                    case "Youtube Player List":
                        break;

                    case "Equalizer":
                        Intent intent7 = new Intent(getApplicationContext(), EqualizerActivity.class );
                        startActivity(intent7);
                        break;

                    case "Upload":
                        Intent intent6 = new Intent(getApplicationContext(), MusicUpload.class );
                        startActivity(intent6);
                        break;

                }

                Toast.makeText(MainActivity.this, "You Clicked at " + muziek[+position], Toast.LENGTH_SHORT).show();
            }
        });
    }
}


