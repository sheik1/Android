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
import com.example.sheikr.muziekapplicatie.visualizer.VisualizerActivity;
import com.example.sheikr.muziekapplicatie.youtubeplayer.youtubeActivity;


public class MainActivity extends AppCompatActivity {

    GridView grid;
    String[] muziek = {
            "Music Player",
            "Youtube Player",
            "Visualizer",
            "Equalizer",
            "Music Player List",
            "Youtube Player List",
            "Drumpad",
            "About"
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
                        Intent intent2 = new Intent(getApplicationContext(), youtubeActivity.class );
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

                    case "Music Player List":
                        break;

                    case "Youtube Player List":
                        break;

                    case "Equalizer":
                        Intent intent7 = new Intent(getApplicationContext(), EqualizerActivity.class );
                        startActivity(intent7);
                        break;

                    case "About":
                        break;
                }

                Toast.makeText(MainActivity.this, "You Clicked at " + muziek[+position], Toast.LENGTH_SHORT).show();
            }
        });
    }
}


