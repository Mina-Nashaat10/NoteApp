package com.minanashaat.myapplication.asyncTasks;

import android.os.AsyncTask;

import com.minanashaat.myapplication.room.Note;
import com.minanashaat.myapplication.room.NoteDao;

import java.util.List;

public class GetAsyncTask extends AsyncTask<Void,Void, List<Note>> {

    private NoteDao notesDao;

    public GetAsyncTask(NoteDao notesDao) {
        this.notesDao = notesDao;
    }
    @Override
    protected List<Note> doInBackground(Void... voids) {
        return notesDao.getAllNotes();
    }
}
