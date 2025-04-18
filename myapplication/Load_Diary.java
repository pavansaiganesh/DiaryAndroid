package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;


public class Load_Diary extends AppCompatActivity implements LoadDiaryAdapter.OnItemClickListener{

    private ArrayList<LoadDiaryModel> diaryEntries = new ArrayList<>();
    private LoadDiaryAdapter adapter;
    private RecyclerView recyclerView;
    private Button btnWriteDiary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_diary);

        recyclerView = findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnWriteDiary = findViewById(R.id.btnWriteDiary);

        adapter = new LoadDiaryAdapter(diaryEntries, this);
        recyclerView.setAdapter(adapter);

        loadAndSortEntries();

        btnWriteDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Load_Diary.this, Diary.class));
            }
        });
    }

    private void loadAndSortEntries() {
        diaryEntries.clear();
        loadAllDiaryEntries();
        sortEntriesByDate();
        adapter.notifyDataSetChanged();
    }

    private void sortEntriesByDate() {
        Collections.sort(diaryEntries, (o1, o2) -> {
            Date date1 = parseDate(o1.getDate());
            Date date2 = parseDate(o2.getDate());
            return date2.compareTo(date1); // Descending order (newest first)
        });
    }

    private void loadAllDiaryEntries() {
        File directory = getFilesDir();
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files != null && files.length > 0) {
            for (File file : files) {
                String filename = file.getName().replace(".txt", "");
                String content = loadDiaryContent(this, filename);

                diaryEntries.add(new LoadDiaryModel(filename, content));
            }
        } else {
            Toast.makeText(this, "No diary entries found", Toast.LENGTH_SHORT).show();
        }
    }
    private String loadDiaryContent(Context context, String filename) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fis = context.openFileInput(filename + ".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            reader.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAndSortEntries();
    }

    @Override
    public void onItemClick(String filename, int position) {
        showDeleteAlert(filename, position);
    }
    private void showDeleteAlert(String filename, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Diary")
                .setMessage("You sure you wanna delete this diary?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    deleteDiaryFile(filename, position);
                })
                .setNegativeButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteDiaryFile(String filename, int position) {
        File file = new File(getFilesDir(), filename + ".txt");
        if (file.exists() && file.delete()) {
            diaryEntries.remove(position);
            adapter.notifyItemRemoved(position);
            Toast.makeText(this, "Diary deleted", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Failed to delete diary", Toast.LENGTH_SHORT).show();
        }
    }

    private Date parseDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            sdf.setLenient(false);
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return new Date(0); // Return epoch as fallback
        }
    }
}