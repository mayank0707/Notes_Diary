package com.example.notesdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<String> notes;
    static ArrayAdapter<String> arrayAdapter;
    SharedPreferences sharedPreferences;
    FloatingActionButton myFab;


    /*public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.hideNote){
            //Log.i("Note Added","Successfully");
            Intent intent=new Intent(getApplicationContext(),NoteEditorActivity.class);
            startActivity(intent);
        }

        return true;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myFab=(FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //myListView.setAdapter(arrayAdapter);
                Intent intent=new Intent(getApplicationContext(),NoteEditorActivity.class);
                startActivity(intent);

            }
        });

        sharedPreferences=getApplicationContext().getSharedPreferences("com.example.notesdiary", Context.MODE_PRIVATE);
        HashSet<String> set=(HashSet<String>)sharedPreferences.getStringSet("notes",null);
        if(set==null){
            notes=new ArrayList<>();
        }
        else{
            notes=new ArrayList<>(set);
        }
        ListView myListView=findViewById(R.id.listView);
        //notes.add("Welcome Note");
        //Log.i("Note",notes.get(0));
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,notes);
        myListView.setAdapter(arrayAdapter);



        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),NoteEditorActivity.class);
                intent.putExtra("noteId",position);
                //Log.i("Index",""+position);
                startActivity(intent);

            }
        });

        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are You Sure!?")
                        .setMessage("Do you really want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notes.remove(position);
                                arrayAdapter.notifyDataSetChanged();

                                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.notesdiary", Context.MODE_PRIVATE);
                                HashSet<String> set=new HashSet<>(notes);
                                sharedPreferences.edit().putStringSet("notes",set).apply();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;

            }
        });

    }
}
