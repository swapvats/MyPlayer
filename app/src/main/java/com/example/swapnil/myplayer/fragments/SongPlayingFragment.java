package com.example.swapnil.myplayer.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swapnil.myplayer.CurrentSongHelper;
import com.example.swapnil.myplayer.R;
import com.example.swapnil.myplayer.Songs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class SongPlayingFragment extends Fragment {

    TextView mDisplaySongArtist,mDisplaySongTitle,mDisplaySongPath,start,end;
    Button play,prev,next,stop;
    SeekBar seekBar;
    Activity mActivity;
    MaterialButton pause;


    int current_pos = 0;
    double startTime = 0;
    double endTime = 0;
    int movePosition = 0;

    ArrayList<Songs> songs;
    static MediaPlayer mediaPlayer = new MediaPlayer();

    public static MediaPlayer getMediaPlayer() {

        return mediaPlayer;
    }

    private Handler handler = new Handler();
   static CurrentSongHelper songHelper = new CurrentSongHelper();



    public SongPlayingFragment() {
        // Required empty public constructor

    }


    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_playing, container, false);



       // mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        //Buttons
        play = view.findViewById(R.id.play_button);
        pause = view.findViewById(R.id.pause_button);
        prev = view.findViewById(R.id.prev_button);
        next = view.findViewById(R.id.next_button);
        stop = view.findViewById(R.id.stop_Button);
        seekBar = view.findViewById(R.id.seekBar);
        start = view.findViewById(R.id.start_Duration);
        end = view.findViewById(R.id.end_Duration);
        mDisplaySongArtist = view.findViewById(R.id.displaySongArtist);
        mDisplaySongTitle = view.findViewById(R.id.displaySongTitle);
        mDisplaySongPath = view.findViewById(R.id.displaySongPath);


        //Declaration
        String songName,songArtist,songPath;



        Bundle args = getArguments();


        songName = args.getString("SONG_TITLE");
        songArtist = args.getString("SONG_ARTIST");
        songPath = args.getString("PATH");



        songs = args.getParcelableArrayList("SONG_DATA");
        current_pos = args.getInt("SONG_POS");





        songHelper.setSongArtist(songArtist);
        songHelper.setSongTitle(songName);
        songHelper.setSongData(songPath); // song path wala
        songHelper.setTrackPostion(current_pos);


        songHelper.setPlaying(true);
        songHelper.setLoop(false);
        songHelper.setShuffle(false);





        // Displaying Data
        mDisplaySongArtist.setText(songArtist);
        mDisplaySongTitle.setText(songName);
        mDisplaySongPath.setText(songPath);






        // TODO: LOOK HERE




            try {
                mediaPlayer.setDataSource(mActivity,Uri.parse(songPath));

                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaPlayer.start();


        int duration = mediaPlayer.getDuration();




        start.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                startTime)))
        );

        end.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) duration),
                TimeUnit.MILLISECONDS.toSeconds((long) duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                duration)))
        );


        // SeekBar

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBar.setMin(0);
        }

        seekBar.setMax(duration);

        seekBar.setProgress((int) startTime);
        handler.postDelayed(UpdateSongTime,1000);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // mediaPlayer.seekTo();

                movePosition =  progress;

                // seekBar.setTranslationX(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


                mediaPlayer.seekTo(movePosition);


            }
        });



            clickHandler();


        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;

    }


    private Runnable UpdateSongTime = new Runnable() {
        @SuppressLint("DefaultLocale")
        @Override
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            endTime = mediaPlayer.getDuration();

            start.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))





            );

            end.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) endTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) endTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                    endTime)))
            );





            seekBar.setProgress((int) startTime);
            handler.postDelayed(this,100);
        }

    };





    void clickHandler(){
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    songHelper.setPlaying(false);
                    play.setText("PLAY");
                }
                else {
                    mediaPlayer.start();
                    songHelper.setPlaying(true);
                    play.setText("Pause");
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Sup Bitch",Toast.LENGTH_SHORT).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                play.setText("Pause");

                mediaPlayer.stop();
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songHelper.setPlaying(true);
                play.setText("Pause");
                playNext();

            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songHelper.setPlaying(true);
                play.setText("Pause");
                playPrev();

            }
        });



    }


    void playNext(){
         current_pos = current_pos + 1;
                 Songs nextsong = songs.get(current_pos);
        songHelper.setSongArtist(nextsong.getSongArtist());
        songHelper.setSongTitle(nextsong.getSongTitle());
        songHelper.setSongData(nextsong.getSongData());


        mediaPlayer.reset();

        try {
            mediaPlayer.setDataSource(songHelper.getSongData());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mDisplaySongArtist.setText(songHelper.getSongArtist());
        mDisplaySongTitle.setText(songHelper.getSongTitle());
        mDisplaySongPath.setText(songHelper.getSongData());

    }


    void playPrev(){
        current_pos = current_pos - 1;
        Songs prevsong = songs.get(current_pos);
        songHelper.setSongArtist(prevsong.getSongArtist());
        songHelper.setSongTitle(prevsong.getSongTitle());
        songHelper.setSongData(prevsong.getSongData());

        mediaPlayer.reset();

        try {
            mediaPlayer.setDataSource(songHelper.getSongData());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mDisplaySongArtist.setText(songHelper.getSongArtist());
        mDisplaySongTitle.setText(songHelper.getSongTitle());
        mDisplaySongPath.setText(songHelper.getSongData());

    }

}
