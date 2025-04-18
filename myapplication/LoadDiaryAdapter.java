package com.example.myapplication;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LoadDiaryAdapter extends RecyclerView.Adapter<LoadDiaryAdapter.ViewHolder>{

    public interface OnItemClickListener {
        void onItemClick(String filename, int position);
    }
    private final ArrayList<LoadDiaryModel> diaryList;

    private final OnItemClickListener listener;


    public LoadDiaryAdapter(ArrayList<LoadDiaryModel> diaryList, OnItemClickListener listener) {
        this.diaryList = diaryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoadDiaryModel item = diaryList.get(position);
        holder.dateTextView.setText(item.getDate());
        holder.contentTextView.setText(item.getContent());

        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(item.getDate(), position);
        });
        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), Diary.class);
            intent.putExtra("SELECTED_DATE", item.getDate());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView;
        public TextView contentTextView;

        public ImageView editButton;

        public ViewHolder(View view) {
            super(view);
            dateTextView = view.findViewById(R.id.textView4);
            contentTextView = view.findViewById(R.id.textView5);
            editButton = view.findViewById(R.id.editButton);
        }
    }

}
