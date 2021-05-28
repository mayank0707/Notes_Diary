package com.example.notesdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.HashSet;

import static com.example.notesdiary.MainActivity.arrayAdapter;
import static com.example.notesdiary.MainActivity.notes;

public class NoteEditorActivity extends AppCompatActivity {

    int noteId;
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.note_editor_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.deleteNote) {
            notes.remove(noteId);
            arrayAdapter.notifyDataSetChanged();
            SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.notesdiary", Context.MODE_PRIVATE);
            HashSet<String> set=new HashSet<>(notes);
            sharedPreferences.edit().putStringSet("notes",set).apply();
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);

        }
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editText=findViewById(R.id.editTextTextMultiLine);
        Intent intent=getIntent();
        noteId=intent.getIntExtra("noteId",-1);
        //Log.i("Note",""+noteId);
        if (noteId != -1) {
            editText.setText(notes.get(noteId));
        }
        else{
            notes.add("");
            noteId=notes.size()-1;
        }


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                notes.set(noteId,String.valueOf(s));
                if(s.toString().trim().length()==0){
                    notes.remove(noteId);
                }
                arrayAdapter.notifyDataSetChanged();
                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.notesdiary", Context.MODE_PRIVATE);
                HashSet<String> set=new HashSet<>(notes);
                sharedPreferences.edit().putStringSet("notes",set).apply();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}