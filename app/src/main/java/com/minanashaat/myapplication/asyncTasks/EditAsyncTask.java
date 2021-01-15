package com.minanashaat.myapplication.asyncTasks;

import android.os.AsyncTask;

import com.minanashaat.myapplication.room.Note;
import com.minanashaat.myapplication.room.NoteDao;

public class EditAsyncTask extends AsyncTask<Note,Void,Void> {

    NoteDao noteDao;

    public EditAsyncTask(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        noteDao.updateNote(notes[0]);
        return null;
    }
}
