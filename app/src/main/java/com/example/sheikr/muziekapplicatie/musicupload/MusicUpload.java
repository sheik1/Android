package com.example.sheikr.muziekapplicatie.musicupload;

import android.app.ProgressDialog;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sheikr.muziekapplicatie.MainActivity;
import com.example.sheikr.muziekapplicatie.R;
import com.example.sheikr.muziekapplicatie.UserLogin;
import com.example.sheikr.muziekapplicatie.drumpad.DrumpadActivity;
import com.example.sheikr.muziekapplicatie.equalizer.EqualizerActivity;
import com.example.sheikr.muziekapplicatie.musicPlayer.PlayListActivity;
import com.example.sheikr.muziekapplicatie.visualizer.VisualizerActivity;
import com.example.sheikr.muziekapplicatie.youtubeLijst.YoutubeListPanel;
import com.example.sheikr.muziekapplicatie.youtubeplayer.YoutubeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class MusicUpload extends MainActivity{

    private DatabaseReference mDatabaseReference;
    private StorageReference musicReference;

    FirebaseStorage storage;
    StorageReference storageReference;
    private DrawerLayout drawer;
    private FirebaseAuth mAuth;
    private TextView username;
    private Button btnChoose, btnUpload, streamBtn;
    private String user;

    private Uri filePath;

    private final int PICK_MUSIC_REQUEST = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_upload);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MusicUpload.this);
        user = sp.getString("gebruiker", null);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("music");
        musicReference = FirebaseStorage.getInstance().getReference().child("music");

        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnUpload = (Button) findViewById(R.id.uploadBTN);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.draw_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navview = (NavigationView) findViewById(R.id.nav_view);
        View headerView =  navview.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.user_email_nav);
        navUsername.setText(user);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseMP3();

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadMP3();

            }

        });
    }

    private void chooseMP3() {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Music"), PICK_MUSIC_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_MUSIC_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
        }
    }

    private void uploadMP3() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("music/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            String name = taskSnapshot.getMetadata().getName();
                            String downloadUrl = taskSnapshot.getMetadata().getReference().toString();

                            writeNewMusicInfoToDB(user, name, downloadUrl);

                            Toast.makeText(MusicUpload.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MusicUpload.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    private void writeNewMusicInfoToDB(String gebruiker, String name, String url){
        MusicModel info = new MusicModel(gebruiker, name, url);

        String key = mDatabaseReference.push().getKey();
        mDatabaseReference.child(key).setValue(info);
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
        switch (item.getItemId()){
            case R.id.nav_firebase_music:
                Intent music = new Intent(getApplicationContext(), MusicActivity.class );
                startActivity(music);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
            case R.id.nav_youtube:
                Intent youtube = new Intent(getApplicationContext(), YoutubeActivity.class );
                startActivity(youtube);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
            case R.id.nav_youtube_list:
                Intent youtubelist = new Intent(getApplicationContext(), YoutubeListPanel.class );
                startActivity(youtubelist);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
            case R.id.nav_visualizer:
                Intent viz = new Intent(getApplicationContext(), VisualizerActivity.class );
                startActivity(viz);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
            case R.id.nav_equalizer:
                Intent equa = new Intent(getApplicationContext(), EqualizerActivity.class );
                startActivity(equa);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
            case R.id.nav_drumpad:
                Intent drum = new Intent(getApplicationContext(), DrumpadActivity.class );
                startActivity(drum);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
            case R.id.nav_upload:
                Intent upload = new Intent(getApplicationContext(), MusicUpload.class );
                startActivity(upload);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
            case R.id.nav_signout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), UserLogin.class );
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;

            default:
                break;
        }
        return true;
    }
}