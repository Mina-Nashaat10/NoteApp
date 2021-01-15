package com.minanashaat.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minanashaat.myapplication.room.Note;

import java.util.List;

public class NoteRvAdapter extends RecyclerView.Adapter<NoteRvAdapter.NoteViewHolder> {

    List<Note> notes;
    OnNoteClick onNoteClick;

    public interface OnNoteClick {

        void onItemClick(View view, int position);
    }


    public NoteRvAdapter(List<Note> notes, OnNoteClick onNoteClick) {
        this.notes = notes;
        this.onNoteClick = onNoteClick;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_rv_row, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, final int position) {
        Note note = notes.get(position);
        holder.titleTv.setText(note.getTitle());
        holder.detailTv.setText(note.getDetail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNoteClick.onItemClick(view, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView titleTv;
        TextView detailTv;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.title_tv);
            detailTv = itemView.findViewById(R.id.detail_tv);
        }
    }
}
