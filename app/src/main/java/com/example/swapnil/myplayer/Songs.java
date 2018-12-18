package com.example.swapnil.myplayer;

import android.os.Parcel;
import android.os.Parcelable;

public class Songs implements Parcelable {
    private long songID;
    private String songTitle;
    private String songArtist;
    private String songData;

    private long dateAdded;


    public Songs(long songID, String songTitle, String songArtist, String songData, long dateAdded) {
        this.songID = songID;
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.songData = songData;
        this.dateAdded = dateAdded;

    }


    public long getSongID() {
        return songID;
    }

    public void setSongID(long songID) {
        this.songID = songID;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getSongData() {
        return songData;
    }

    public void setSongData(String songData) {
        this.songData = songData;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }


    protected Songs(Parcel in) {
        songID = in.readLong();
        songTitle = in.readString();
        songArtist = in.readString();
        songData = in.readString();
        dateAdded = in.readLong();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(songID);
        dest.writeString(songTitle);
        dest.writeString(songArtist);
        dest.writeString(songData);
        dest.writeLong(dateAdded);

    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Songs> CREATOR = new Parcelable.Creator<Songs>() {
        @Override
        public Songs createFromParcel(Parcel in) {
            return new Songs(in);
        }

        @Override
        public Songs[] newArray(int size) {
            return new Songs[size];
        }
    };
}