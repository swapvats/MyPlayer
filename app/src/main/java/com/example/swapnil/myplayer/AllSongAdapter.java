package com.example.swapnil.myplayer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swapnil.myplayer.fragments.SongPlayingFragment;

import java.util.ArrayList;

public class AllSongAdapter extends RecyclerView.Adapter<AllSongAdapter.MyViewHolder> {

    ArrayList<Songs> songsArrayList;
    Context mContext;

    public AllSongAdapter(ArrayList<Songs> songsArrayList, Context mContext) {
        this.songsArrayList = songsArrayList;
        this.mContext = mContext;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_display, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
          final Songs songObject =   songsArrayList.get(i);
     //   myViewHolder.songName.setText(songsArrayList.get(i).getSongTitle());
     //   myViewHolder.artistName.setText(songsArrayList.get(i).getSongArtist());

        myViewHolder.songName.setText(songObject.getSongTitle());
        myViewHolder.artistName.setText(songObject.getSongArtist());

        myViewHolder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongPlayingFragment songPlayingFragment = new SongPlayingFragment();
                Bundle args = new Bundle();
                args.putString("SONG_ARTIST",songObject.getSongArtist());
                args.putString("SONG_TITLE",songObject.getSongTitle());
                args.putString("PATH",songObject.getSongData());
                
                args.putInt("SONG_POS",i);
                args.putParcelableArrayList("SONG_DATA",songsArrayList);

                songPlayingFragment.setArguments(args);


                FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.details_fragment,songPlayingFragment)
                        .commit();

            }
        });



    }

    @Override
    public int getItemCount() {
        return songsArrayList.size();
    }



   // ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView songName,artistName;
        ConstraintLayout mItemView;






        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            songName = itemView.findViewById(R.id.mSongName);
            artistName = itemView.findViewById(R.id.artistName);
            mItemView = itemView.findViewById(R.id.itemConstraintView);
        }
    }
}
