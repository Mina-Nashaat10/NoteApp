package com.minanashaat.myapplication.fragments;

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
import android.widget.Toast;

import com.minanashaat.myapplication.R;
import com.minanashaat.myapplication.asyncTasks.EditAsyncTask;
import com.minanashaat.myapplication.room.Note;
import com.minanashaat.myapplication.room.RoomFactory;


public class EditFragment extends Fragment {

    EditText titleEt;
    EditText detailEt;
    Button update_btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_edit, container, false);
        titleEt = view.findViewById(R.id.edit_title_et);
        detailEt = view.findViewById(R.id.edit_detail_et);
        update_btn = view.findViewById(R.id.update_btn);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        final Note note = (Note) bundle.getSerializable("note");
        titleEt.setText(note.getTitle());
        detailEt.setText(note.getDetail());
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEt.getText().toString();
                String detail = detailEt.getText().toString();
                note.setTitle(title);
                note.setDetail(detail);
                new EditAsyncTask(RoomFactory.getNoteDb(requireContext()).getNoteDao()).execute(note);
                Navigation.findNavController(view).navigate(R.id.action_editFragment_to_homeFragment);
            }
        });
    }
}