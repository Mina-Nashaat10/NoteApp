package com.minanashaat.myapplication.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.minanashaat.myapplication.asyncTasks.InsertAsyncTask;
import com.minanashaat.myapplication.R;
import com.minanashaat.myapplication.room.Note;
import com.minanashaat.myapplication.room.RoomFactory;

public class AddFragment extends Fragment {

    EditText titleET;
    EditText detailET;
    TextInputLayout titleLayout;
    TextInputLayout detailLayout;
    Button saveNote;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        titleET = view.findViewById(R.id.title_et);
        detailET = view.findViewById(R.id.detail_et);
        saveNote = view.findViewById(R.id.save_btn);
        titleLayout = view.findViewById(R.id.title_layout);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleET.getText().toString();
                String detail = detailET.getText().toString();
                if (title.equals("") && detail.equals("")) {
                    titleET.setError("Enter Title Note");
                    detailET.setError("Enter Detail Note");
                } else if (title.equals("")) {
                    titleET.setError("Enter Title Note");
                } else if (detail.equals("")) {
                    detailET.setError("Enter Detail Note");
                } else {
                    new InsertAsyncTask(RoomFactory.getNoteDb(requireContext()).getNoteDao()).execute(new Note(title, detail));
                    Navigation.findNavController(view).navigate(R.id.action_addFragment_to_homeFragment);
                }
            }
        });


    }
}