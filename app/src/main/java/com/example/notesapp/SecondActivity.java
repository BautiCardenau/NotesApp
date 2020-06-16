package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import static com.example.notesapp.MainActivity.arrayAdapter;
import static com.example.notesapp.MainActivity.notes;

public class SecondActivity extends AppCompatActivity {

    EditText writingEditText;
    int number;
    SharedPreferences sharedPreferences;


    @Override
    protected void onStop() {
        ///Cuando el usuario toque para atras tengo que guardar la nota
        if (writingEditText.getText().toString().trim().length() > 0) {
            if (number == -1) {
                notes.add(writingEditText.getText().toString());
                arrayAdapter.notifyDataSetChanged();
                try {
                    sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(notes)).apply();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Log.i("number", Integer.toString(number));
                notes.set(number, writingEditText.getText().toString());
                arrayAdapter.notifyDataSetChanged();
            }
        }
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.second_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        writingEditText =  (EditText) findViewById(R.id.writingEditText);
        sharedPreferences = getSharedPreferences("com.example.notesapp", Context.MODE_PRIVATE);
        number = sharedPreferences.getInt("Number",-1);

        if (number != -1) {
            writingEditText.setText(notes.get(sharedPreferences.getInt("Number",-1)));
        }

    }
}
