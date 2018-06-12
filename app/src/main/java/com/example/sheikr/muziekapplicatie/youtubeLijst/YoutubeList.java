package com.example.sheikr.muziekapplicatie.youtubeLijst;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sheikr.muziekapplicatie.R;

import java.util.List;

public class YoutubeList extends ArrayAdapter<Youtube> {

    private Activity context;
    private List<Youtube> youtubeList;

    public YoutubeList( Activity context, List<Youtube> youtubeList) {
        super(context, R.layout.activity_database_list, youtubeList);
        this.context =  context;
        this.youtubeList = youtubeList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.activity_database_list, null);

        TextView txtViewName = (TextView) listViewItem.findViewById(R.id.txtTitle);
        TextView txtViewLink = (TextView) listViewItem.findViewById(R.id.txtLink);

        Youtube youtube = youtubeList.get(position);

        txtViewName.setText(youtube.getTitel());
        txtViewLink.setText(youtube.getLink());

        return listViewItem;


    }
}
