package com.example.sheikr.muziekapplicatie.musicPlayer;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.sheikr.muziekapplicatie.R;

import java.io.IOException;

public class PlayListActivity extends Activity {


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            MediaPlayer player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource("https://firebasestorage.googleapis.com/v0/b/musicapp-82f20.appspot.com/o/music%2F1ab89be3-abcf-41ea-800b-8e860b89d641?alt=media&token=215f5606-44d6-4b72-a5c5-cb588b4f3713");
            player.prepare();
            player.start();

        } catch (IOException e) {
            e.printStackTrace();
        }


        /*
        Intent intent = new Intent(getApplicationContext(), MainActivity.class );
        startActivity(intent);

        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://streamboxr.com/index.php/auth/login"));
        startActivity(browserIntent);
        */

    }
}

