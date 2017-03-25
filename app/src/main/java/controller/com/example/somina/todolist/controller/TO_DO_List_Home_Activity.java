package com.example.somina.todolist.controller;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.somina.todolist.R;
import com.example.somina.todolist.helper.My_Database_Helper;
import com.example.somina.todolist.helper.My_Database_Manager;

import java.util.ArrayList;
import java.util.Objects;

public class TO_DO_List_Home_Activity extends AppCompatActivity {

    List_View_Adapter listViewAdapter;
    ListView listView;
    Toolbar toolbar;

    public String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("To-do List");
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.list_todo);
        updateUI();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                My_Database_Manager myDatabaseManager = new My_Database_Manager(getApplicationContext()).open();

                final String idText = ((TextView) view.findViewById(R.id.taskId)).getText().toString();
                final String statusText = ((TextView) view.findViewById(R.id.taskStatus)).getText().toString();
                final String titleText = ((TextView) view.findViewById(R.id.title)).getText().toString();
                final String descriptionText = ((TextView) view.findViewById(R.id.description)).getText().toString();
                final String dateText = ((TextView) view.findViewById(R.id.timestamp)).getText().toString();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (Objects.equals(statusText, "0")) {
                        myDatabaseManager.update(Integer.parseInt(String.valueOf(idText)), titleText, descriptionText, dateText, 1);
                        Toast.makeText(TO_DO_List_Home_Activity.this, "This task is marked complete.", Toast.LENGTH_SHORT).show();
                    } else {
                        myDatabaseManager.update(Integer.parseInt(String.valueOf(idText)), titleText, descriptionText, dateText, 0);
                        Toast.makeText(TO_DO_List_Home_Activity.this, "This task is marked incomplete.", Toast.LENGTH_SHORT).show();
                    }
                }

                updateUI();
                myDatabaseManager.close();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String idText = ((TextView) view.findViewById(R.id.taskId)).getText().toString();
                final String titleText = ((TextView) view.findViewById(R.id.title)).getText().toString();
                final String descriptionText = ((TextView) view.findViewById(R.id.description)).getText().toString();
                final String dateText = ((TextView) view.findViewById(R.id.timestamp)).getText().toString();

                AlertDialog.Builder dialog = new AlertDialog.Builder(TO_DO_List_Home_Activity.this);

                LayoutInflater inflater = getLayoutInflater();
                final View inflaterView = inflater.inflate(R.layout.dilog, null);
                dialog.setView(inflaterView);

                final TextView taskEditId = (TextView) inflaterView.findViewById(R.id.taskEditId);
                final EditText title = (EditText) inflaterView.findViewById(R.id.input_title);
                final EditText description = (EditText) inflaterView.findViewById(R.id.input_description);
                final DatePicker date = (DatePicker) inflaterView.findViewById(R.id.datePicker);

                taskEditId.setText(idText);
                title.setText(titleText);
                description.setText(descriptionText);
                int year = Integer.parseInt(dateText.substring(6, 10));
                int monthOfYear = Integer.parseInt(dateText.substring(3, 5)) - 1;
                int dayOfMonth = Integer.parseInt(dateText.substring(0, 2));
                date.init(year, monthOfYear, dayOfMonth, null);

                dialog.setPositiveButton("Add", null);
                dialog.setNegativeButton("Cancel", null);

                final AlertDialog alertDialog = dialog.create();
                alertDialog.show();

                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

                    String getDate() {
                        StringBuilder builder = new StringBuilder();
                        builder.append(date.getDayOfMonth() + "/");
                        String dateMonth = String.valueOf(date.getMonth() + 1);
                        if (dateMonth.length() <= 1) {
                            dateMonth = "0" + dateMonth;
                        }
                        builder.append((dateMonth) + "/");
                        builder.append(date.getYear());
                        return builder.toString();
                    }

                    @Override
                    public void onClick(View v) {
                        if (titleText.equals("")) {
                            title.setError("Title cannot be empty.");
                        } else if (descriptionText.equals("")) {
                            description.setError("Description cannot be empty.");
                        } else {
                            String date = getDate();
                            final My_Database_Manager myDatabaseManager = new My_Database_Manager(getApplicationContext());
                            myDatabaseManager.open();
                            myDatabaseManager.update(Integer.parseInt(String.valueOf(taskEditId.getText())), title.getText().toString(), description.getText().toString(), date, 0);
                            myDatabaseManager.close();
                            updateUI();
                            Toast.makeText(TO_DO_List_Home_Activity.this, "Task is updated.", Toast.LENGTH_SHORT).show();
                            alertDialog.hide();
                        }
                    }
                });
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);

                LayoutInflater inflater = getLayoutInflater();
                final View inflaterView = inflater.inflate(R.layout.dilog, null);
                dialog.setView(inflaterView);


                final EditText title = (EditText) inflaterView.findViewById(R.id.input_title);
                final EditText description = (EditText) inflaterView.findViewById(R.id.input_description);
                final DatePicker date = (DatePicker) inflaterView.findViewById(R.id.datePicker);

                dialog.setPositiveButton("Add", null);
                dialog.setNegativeButton("Cancel", null);

                final AlertDialog alertDialog = dialog.create();
                alertDialog.show();

                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

                    String getDate() {
                        StringBuilder builder = new StringBuilder();
                        builder.append(date.getDayOfMonth() + "/");
                        String dateMonth = String.valueOf(date.getMonth() + 1);
                        if (dateMonth.length() <= 1) {
                            dateMonth = "0" + dateMonth;
                        }
                        builder.append((dateMonth) + "/");
                        builder.append(date.getYear());
                        return builder.toString();
                    }

                    @Override
                    public void onClick(View v) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            if (Objects.equals(title.getText().toString(), "") && Objects.equals(description.getText().toString(), "")) {
                                title.setError("Title cannot be empty.");
                                description.setError("Description cannot be empty.");
                            } else if (Objects.equals(title.getText().toString(), "")) {
                                title.setError("Title cannot be empty.");
                            } else if (Objects.equals(description.getText().toString(), "")) {
                                description.setError("Description cannot be empty.");
                            } else {
                                String date = getDate();
                                final My_Database_Manager myDatabaseManager = new My_Database_Manager(getApplicationContext());
                                myDatabaseManager.open();
                                myDatabaseManager.insert(title.getText().toString(), description.getText().toString(), date, 0);
                                myDatabaseManager.close();
                                alertDialog.dismiss();
                                updateUI();
                                Toast.makeText(TO_DO_List_Home_Activity.this, "New Task created.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                return true;
            case R.id.complete:
                Intent intent = new Intent(TO_DO_List_Home_Activity.this, Completed_Task_Activity.class);
                startActivity(intent);
                return true;
            case R.id.help:
                Intent intent1 = new Intent(TO_DO_List_Home_Activity.this, Help_Activity.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        My_Database_Manager db = new My_Database_Manager(getApplicationContext()).open();

        Cursor cursor = db.fetch();

        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<String> title = new ArrayList<>();
        ArrayList<String> description = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<Integer> status = new ArrayList<>();

        while (cursor.moveToNext()) {
            ids.add(cursor.getInt(cursor.getColumnIndex(My_Database_Helper.COLUMN_ID)));
            title.add(cursor.getString(cursor.getColumnIndex(My_Database_Helper.COLUMN_TITLE)));
            description.add(cursor.getString(cursor.getColumnIndex(My_Database_Helper.COLUMN_DESCRIPTION)));
            date.add(cursor.getString(cursor.getColumnIndex(My_Database_Helper.COLUMN_DATE)));
            status.add(cursor.getInt(cursor.getColumnIndex(My_Database_Helper.COLUMN_STATUS)));
        }
        listViewAdapter = new List_View_Adapter(this, ids, title, description, date, status);
        listView.setAdapter(listViewAdapter);
        cursor.close();
        db.close();
    }
}