package com.example.sheikr.muziekapplicatie.musicPlayer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.sheikr.muziekapplicatie.MainActivity;
import com.example.sheikr.muziekapplicatie.R;

public class PlayListActivity extends Activity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class );
        startActivity(intent);

        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://streamboxr.com/index.php/auth/login"));
        startActivity(browserIntent);

    }
}

