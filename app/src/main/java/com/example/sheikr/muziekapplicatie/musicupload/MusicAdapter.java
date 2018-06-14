package com.example.sheikr.muziekapplicatie.musicupload;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sheikr.muziekapplicatie.R;

import org.w3c.dom.Text;

import java.util.List;

public class MusicAdapter extends ArrayAdapter<MusicModel> {

    private Activity context;
    private List<MusicModel> muzieklijst;

    public MusicAdapter(Activity context, List<MusicModel> muzieklijst) {
        super(context, R.layout.activity_music_play, muzieklijst);
        this.context = context;
        this.muzieklijst = muzieklijst;
    }

    @NonNull
    @Override
    public View getView(int position,View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.activity_music_play, null);

        TextView textviewTitle = (TextView) listViewItem.findViewById(R.id.txtMusicName);
        TextView textviewLink = (TextView) listViewItem.findViewById(R.id.txtMusicLink);

        MusicModel musicmodel = muzieklijst.get(position);

        textviewTitle.setText(musicmodel.getNaam());
        textviewLink.setText(musicmodel.getUrl());

        return listViewItem;

    }

}