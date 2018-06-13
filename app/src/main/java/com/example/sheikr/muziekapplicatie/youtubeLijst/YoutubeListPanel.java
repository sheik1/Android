package com.example.sheikr.muziekapplicatie.youtubeLijst;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sheikr.muziekapplicatie.MainActivity;
import com.example.sheikr.muziekapplicatie.R;
import com.example.sheikr.muziekapplicatie.UserLogin;
import com.example.sheikr.muziekapplicatie.drumpad.DrumpadActivity;
import com.example.sheikr.muziekapplicatie.equalizer.EqualizerActivity;
import com.example.sheikr.muziekapplicatie.musicPlayer.PlayListActivity;
import com.example.sheikr.muziekapplicatie.musicupload.MusicUpload;
import com.example.sheikr.muziekapplicatie.visualizer.VisualizerActivity;
import com.example.sheikr.muziekapplicatie.youtubeplayer.YoutubeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prof.youtubeparser.models.stats.Main;

import java.util.ArrayList;
import java.util.List;

public class YoutubeListPanel extends MainActivity {

    private DrawerLayout drawer;

    EditText edtTitle;
    EditText edtLink;
    Button btnAdd;

    ImageView imageView;

    DatabaseReference databaseYoutube;

    ListView listViewYoutubeLinks;

    List<Youtube> youtubeList;

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_add);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(YoutubeListPanel.this);
         user = sp.getString("gebruiker", null);

        databaseYoutube = FirebaseDatabase.getInstance().getReference("youtube");

        edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtLink = (EditText) findViewById(R.id.edtLink);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        imageView = (ImageView)findViewById(R.id.imageView);
        listViewYoutubeLinks = (ListView) findViewById(R.id.listviewLinks);

        youtubeList = new ArrayList<>();

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

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addYoutubeLink();
            }
        });

        listViewYoutubeLinks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {

                Youtube youtube = youtubeList.get(i);

                showUpdateDialog(youtube.getId(), youtube.getTitel(), youtube.getLink());
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseYoutube.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                youtubeList.clear();

                for(DataSnapshot youtubeSnapshot: dataSnapshot.getChildren()){
                    Youtube youtube = youtubeSnapshot.getValue(Youtube.class);

                    if(user.equals(youtube.getGebruiker()) ) {

                        youtubeList.add(youtube);
                    }
                }

                YoutubeList adapter = new YoutubeList(YoutubeListPanel.this, youtubeList);
                listViewYoutubeLinks.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDialog(String youtubeID, String naam, String link ){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.activity_database_update_dialog, null);

        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.edt_update_name);
        final EditText editTextLink = (EditText) dialogView.findViewById(R.id.edt_update_link);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.btn_update);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.btn_delete);

        dialogBuilder.setTitle("youtube link " +  editTextName.getText().toString());

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String link = editTextLink.getText().toString().trim();

                updateYoutube(youtubeID, name, link);

                alertDialog.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteYoutubeLink(youtubeID);
            }
        });
    }

    private boolean updateYoutube(String id, String name, String link){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("youtube").child(id);

        Youtube youtube = new Youtube(id, user, name, link);

        databaseReference.setValue(youtube);

        Toast.makeText(this, "link geupdated", Toast.LENGTH_SHORT).show();

        return true;
    }

    private void deleteYoutubeLink(String youtubeID){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("youtube").child(youtubeID);

        databaseReference.removeValue();

        Toast.makeText(this, "link is verwijderd",Toast.LENGTH_SHORT).show();
    }

    private void addYoutubeLink(){
        String title = edtTitle.getText().toString().trim();
        String link = edtLink.getText().toString().trim();

        if(!TextUtils.isEmpty(title)){

            String id = databaseYoutube.push().getKey();
            Youtube youtubeData = new Youtube(id,  user, title ,link);

            databaseYoutube.child(id).setValue(youtubeData);

            Toast.makeText(this, "toegevoegd", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "er is geen titel", Toast.LENGTH_SHORT).show();
        }
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
        Log.d("MUsicupload", "onNavigationItemSelected: ");
        switch (item.getItemId()){
            case R.id.nav_youtube:
                Intent youtube = new Intent(getApplicationContext(), YoutubeActivity.class );
                startActivity(youtube);
                break;
            case R.id.nav_youtube_list:
                Intent youtubelist = new Intent(getApplicationContext(), YoutubeListPanel.class );
                startActivity(youtubelist);
                break;
            case R.id.nav_streamboxr:
                Intent stream = new Intent(getApplicationContext(), PlayListActivity.class );
                startActivity(stream);
                break;
            case R.id.nav_visualizer:
                Intent viz = new Intent(getApplicationContext(), VisualizerActivity.class );
                startActivity(viz);
                break;
            case R.id.nav_equalizer:
                Intent equa = new Intent(getApplicationContext(), EqualizerActivity.class );
                startActivity(equa);
                break;
            case R.id.nav_drumpad:
                Intent drum = new Intent(getApplicationContext(), DrumpadActivity.class );
                startActivity(drum);
                break;
            case R.id.nav_upload:
                Intent upload = new Intent(getApplicationContext(), MusicUpload.class );
                startActivity(upload);
                break;
            case R.id.nav_signout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), UserLogin.class );
                startActivity(intent);
                break;

            default:
                break;
        }
        return true;
    }
}
