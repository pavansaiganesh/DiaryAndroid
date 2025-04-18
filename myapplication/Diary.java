package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Diary extends AppCompatActivity {

    EditText editTextContent, editTextTitle;

    Button buttonSave;

    public static String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(new Date());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        buttonSave = findViewById(R.id.buttonSave);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("SELECTED_DATE")) {
            String selectedDate = intent.getStringExtra("SELECTED_DATE");
            editTextTitle.setText(selectedDate);
        }
        else {
            editTextTitle.setText(getFormattedDate());
        }

        String content = readNote(this, editTextTitle.getText().toString() );
        editTextContent.setText(content);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename = editTextTitle.getText().toString();
                String content = editTextContent.getText().toString();
                saveNote(Diary.this, filename, content);
                Toast.makeText(Diary.this, "Memory saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    public void saveNote(Context context, String filename, String noteContent) {
        try {
            FileOutputStream fos = context.openFileOutput(filename + ".txt", Context.MODE_PRIVATE);
            fos.write(noteContent.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error saving note", Toast.LENGTH_SHORT).show();
        }
    }

    public String readNote(Context context, String filename) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fis = context.openFileInput(filename + ".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            Toast.makeText(context, "Update Diary", Toast.LENGTH_SHORT).show();
            reader.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "New Day New Story \uD83D\uDE0A", Toast.LENGTH_SHORT).show();
        }
        return stringBuilder.toString();
    }
}