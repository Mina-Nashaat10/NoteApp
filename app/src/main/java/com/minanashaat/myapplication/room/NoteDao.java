package com.minanashaat.myapplication.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void addNote(Note note);

    @Query("Select * from notes")
    List<Note> getAllNotes();

    @Delete
    void deleteNote(Note note);

    @Update
    void updateNote(Note note);
}
