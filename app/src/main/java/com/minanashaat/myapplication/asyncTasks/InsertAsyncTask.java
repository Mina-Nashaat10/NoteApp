package com.minanashaat.myapplication.asyncTasks;

import android.os.AsyncTask;

import com.minanashaat.myapplication.room.Note;
import com.minanashaat.myapplication.room.NoteDao;

public class InsertAsyncTask extends AsyncTask<Note,Void,Void> {
    private NoteDao noteDao;

    public InsertAsyncTask(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        noteDao.addNote(notes[0]);
        return null;
    }
}
