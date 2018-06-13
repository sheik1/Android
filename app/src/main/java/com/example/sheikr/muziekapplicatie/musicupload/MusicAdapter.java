package com.example.sheikr.muziekapplicatie.musicupload;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sheikr.muziekapplicatie.R;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    private Context context;
    private List<MusicModel> muzieklijst;

    public MusicAdapter(Context context, List<MusicModel> muzieklijst) {
        this.context = context;
        this.muzieklijst = muzieklijst;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_music_play, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {

        MusicModel lijst = muzieklijst.get(position);

        holder.textviewTitle.setText(lijst.getNaam());
        holder.textviewLink.setText(lijst.getUrl());
    }

    @Override
    public int getItemCount() {
        return muzieklijst.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textviewTitle;
        public TextView textviewLink;

        public ViewHolder(View itemView) {
            super(itemView);

            textviewTitle = (TextView) itemView.findViewById(R.id.txtMusicName);
            textviewLink = (TextView) itemView.findViewById(R.id.txtMusicLink);
        }
    }

}
