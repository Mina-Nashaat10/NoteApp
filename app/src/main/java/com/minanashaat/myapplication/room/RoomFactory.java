package com.minanashaat.myapplication.room;

import android.content.Context;
import android.content.Intent;

import androidx.room.Room;

public class RoomFactory {
    private static NotesDb noteDb;

    public static NotesDb getNoteDb(Context context) {
        if (noteDb == null) {
            noteDb = Room.databaseBuilder(context, NotesDb.class, "Notes_Db").build();
        }
        return noteDb;
    }
}
