package com.minanashaat.myapplication.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NotesDb extends RoomDatabase {
    public abstract NoteDao getNoteDao();
}
