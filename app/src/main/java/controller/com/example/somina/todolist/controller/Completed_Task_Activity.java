package com.example.somina.todolist.controller;

import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.somina.todolist.R;
import com.example.somina.todolist.helper.My_Database_Helper;
import com.example.somina.todolist.helper.My_Database_Manager;

import java.util.ArrayList;

public class Completed_Task_Activity extends AppCompatActivity {

    List_View_Adapter listViewAdapter;
    ListView listView;
    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Completed Tasks");
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
        listView = (ListView) findViewById(R.id.list_todo);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {

                final AlertDialog.Builder dialog = new AlertDialog.Builder(Completed_Task_Activity.this);

                dialog.setMessage("Are you sure? You want to delete this task?");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        My_Database_Manager db = new My_Database_Manager(getApplicationContext()).open();

                        String ids = ((TextView) view.findViewById(R.id.taskId)).getText().toString();
                        db.delete(Long.parseLong(ids));
                        Toast.makeText(Completed_Task_Activity.this, "Task is deleted.", Toast.LENGTH_LONG).
                                show();
                        updateUI();
                        db.close();
                    }
                });
                dialog.setNegativeButton("Cancel", null);
                dialog.create();
                dialog.show();
                return true;
            }

        });
        updateUI();
    }

    private void updateUI() {
        My_Database_Manager db = new My_Database_Manager(getApplicationContext()).open();
        Cursor cursor = db.fetch(1);
        final ArrayList<Integer> id = new ArrayList<>();
        final ArrayList<String> title = new ArrayList<>();
        ArrayList<String> description = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<Integer> status = new ArrayList<>();

        while (cursor.moveToNext()) {
            id.add(cursor.getInt(cursor.getColumnIndex(My_Database_Helper.COLUMN_ID)));
            title.add(cursor.getString(cursor.getColumnIndex(My_Database_Helper.COLUMN_TITLE)));
            description.add(cursor.getString(cursor.getColumnIndex(My_Database_Helper.COLUMN_DESCRIPTION)));
            date.add(cursor.getString(cursor.getColumnIndex(My_Database_Helper.COLUMN_DATE)));
            status.add(cursor.getInt(cursor.getColumnIndex(My_Database_Helper.COLUMN_STATUS)));
        }
        listViewAdapter = new List_View_Adapter(this, id, title, description, date, status);
        listView.setAdapter(listViewAdapter);
        cursor.close();
        db.close();
    }
}