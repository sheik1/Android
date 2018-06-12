package com.example.sheikr.muziekapplicatie.youtubeLijst;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sheikr.muziekapplicatie.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class YoutubeListPanel extends AppCompatActivity {

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

                    youtubeList.add(youtube);
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
}
