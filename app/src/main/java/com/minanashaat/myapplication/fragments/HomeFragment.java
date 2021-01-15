package com.minanashaat.myapplication.fragments;

import android.app.AlertDialog;
import android.app.DirectAction;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.state.State;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.minanashaat.myapplication.asyncTasks.DeleteAsyncTask;
import com.minanashaat.myapplication.asyncTasks.GetAsyncTask;
import com.minanashaat.myapplication.room.Note;
import com.minanashaat.myapplication.NoteRvAdapter;
import com.minanashaat.myapplication.R;
import com.minanashaat.myapplication.room.RoomFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment {


    RecyclerView recyclerView;
    NoteRvAdapter noteRvAdapter;
    FloatingActionButton addFab;
    ImageView noteImageView;
    List<Note> noteList = new ArrayList<>();
    ItemTouchHelper.SimpleCallback simpleCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.notes_rv);
        addFab = view.findViewById(R.id.addnote_fab);
        noteImageView = view.findViewById(R.id.empty_note_iv);
        return view;
    }

    private void itemTouchSwap() {
        simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Note note = noteList.get(viewHolder.getAdapterPosition());
                new DeleteAsyncTask(RoomFactory.getNoteDb(requireContext()).getNoteDao()).execute(note);
                noteList.remove(viewHolder.getAdapterPosition());
                noteRvAdapter.notifyDataSetChanged();
                checkEmptyNotes();
            }
        };
        ItemTouchHelper touchHelper = new ItemTouchHelper(simpleCallback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecycleView();
        getAllNotes();
        checkEmptyNotes();
        itemTouchSwap();
    }

    private void checkEmptyNotes() {
        if (noteList.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            noteImageView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noteImageView.setVisibility(View.GONE);
        }
    }

    private void setUpRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_addFragment);
            }
        });
        noteRvAdapter = new NoteRvAdapter(noteList, new NoteRvAdapter.OnNoteClick() {
            @Override
            public void onItemClick(final View view, final int position) {
                deleteUpdateNote(view, position);
            }
        });
        recyclerView.setAdapter(noteRvAdapter);
    }

    private void deleteUpdateNote(final View view, final int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());

        dialog.setTitle("Edit or Delete ?");
        dialog.setMessage("do u want to edit or delete this note");
        dialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Note clickedNote = noteList.get(position);
                new DeleteAsyncTask(RoomFactory.getNoteDb(requireContext()).getNoteDao()).execute(clickedNote);
                noteRvAdapter.notifyDataSetChanged();
                getAllNotes();
                checkEmptyNotes();

            }
        });

        dialog.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Note note = noteList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("note", note);
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_editFragment, bundle);
            }
        });
        dialog.show();
    }

    private void getAllNotes() {
        noteList.clear();
        try {
            noteList.addAll(new GetAsyncTask(RoomFactory.getNoteDb(requireContext()).getNoteDao()).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}