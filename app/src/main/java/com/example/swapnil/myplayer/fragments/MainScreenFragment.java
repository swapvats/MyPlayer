package com.example.swapnil.myplayer.fragments;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swapnil.myplayer.AllSongAdapter;
import com.example.swapnil.myplayer.R;
import com.example.swapnil.myplayer.Songs;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainScreenFragment extends Fragment {


    RecyclerView songRecyclerView;

    Activity myActivity;

    ArrayList<Songs> allsongs;

    ArrayList<String> songsNames;


    public MainScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);



        songRecyclerView = view.findViewById(R.id.recycler_SongList);
        allsongs = getSongsFromHome();



//       ArrayList<Songs> mySongs = new ArrayList<>();
//
//       mySongs.add(new Songs(12,"Dum Maro Sum","Rihanna","Lmao",311));
//       mySongs.add(new Songs(12,"Dum Maro Dum","Rihanna","Lmao",311));
//       mySongs.add(new Songs(12,"Jannat","Rihanna","Lmao",311));
//       mySongs.add(new Songs(12,"Pagal Kudi","Rihanna","Lmao",311));
//       mySongs.add(new Songs(12,"DilliWalliGF","Rihanna","Lmao",311));




        AllSongAdapter allSongAdapter = new AllSongAdapter(allsongs,view.getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());

        songRecyclerView.setLayoutManager(layoutManager);
        songRecyclerView.setAdapter(allSongAdapter);


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myActivity = (Activity) context;

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myActivity = activity;
    }

    ArrayList<Songs> getSongsFromHome(){
        // Content Resolver And Content Provider To Assess Data


        ArrayList<Songs> songsArrayList = new ArrayList<>();


        ContentResolver contentResolver = myActivity.getContentResolver();

        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        //Cursor
        Cursor songCursor = contentResolver.query(songUri,null,null,null,null);

        if (songCursor!=null && songCursor.moveToFirst()){

            int songId = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songData = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int dateAdded = songCursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED);


            while (songCursor.moveToNext()){
                long currentSongId = songCursor.getLong(songId);
                String currentSongTitle = songCursor.getString(songTitle);
                String currentSongArtist = songCursor.getString(songArtist);
                String currentSongData = songCursor.getString(songData);
                String path = null;


                long currentDateAdded = songCursor.getLong(dateAdded);



                songsArrayList.add(new Songs(currentSongId,currentSongTitle,currentSongArtist,currentSongData,currentDateAdded));

            }


        }

        return songsArrayList;
    }

    }


