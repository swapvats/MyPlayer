package com.example.swapnil.myplayer.fragments;


import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.swapnil.myplayer.R;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavFragment extends Fragment {

    ArrayList<String> songsNames;

    ListView mListView;


    public FavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        mListView = view.findViewById(R.id.myListView);
        songsNames = new ArrayList<>();
        getMusic();


        ArrayAdapter arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_list_item_1, songsNames);

        mListView.setAdapter(arrayAdapter);


        return view;

    }


    public void getMusic() {


        ContentResolver contentResolver = Objects.requireNonNull(getContext()).getContentResolver();

        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        //Cursor
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);

        if (songCursor != null && songCursor.moveToFirst()) {

            int songId = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songData = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int dateAdded = songCursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED);

            while (songCursor.moveToNext()) {
                long currentSongId = songCursor.getLong(songId);
                String currentSongTitle = songCursor.getString(songTitle);
                String currentSongArtist = songCursor.getString(songArtist);
                String currentSongData = songCursor.getString(songData);
                long currentDateAdded = songCursor.getLong(dateAdded);


                songsNames.add(currentSongTitle);


            }


        }


    }

}
