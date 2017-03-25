package com.example.somina.todolist.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class My_Database_Manager {

    private My_Database_Helper dbHelper;

    private Context context;

    private SQLiteDatabase sqLiteDatabase;

    public My_Database_Manager(Context c) {
        context = c;
    }

    public My_Database_Manager open() throws SQLException {
        dbHelper = new My_Database_Helper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String title, String description, String date, Integer status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(My_Database_Helper.COLUMN_TITLE, title);
        contentValues.put(My_Database_Helper.COLUMN_DESCRIPTION, description);
        contentValues.put(My_Database_Helper.COLUMN_DATE, date);
        contentValues.put(My_Database_Helper.COLUMN_STATUS, status);
        sqLiteDatabase.insert(My_Database_Helper.TABLE_TODO, null, contentValues);
    }

    public Cursor fetch(Integer status) {
        String[] columns = new String[]{My_Database_Helper.COLUMN_ID, My_Database_Helper.COLUMN_TITLE, My_Database_Helper.COLUMN_DESCRIPTION, My_Database_Helper.COLUMN_DATE, My_Database_Helper.COLUMN_STATUS};
        Cursor cursor = sqLiteDatabase.query(My_Database_Helper.TABLE_TODO, columns, My_Database_Helper.COLUMN_STATUS + "=" + status, null, null, null, My_Database_Helper.COLUMN_DATE + " ASC");
        if (cursor != null) {
        }
        return cursor;
    }

    public Cursor fetch() {
        String[] columns = new String[]{My_Database_Helper.COLUMN_ID, My_Database_Helper.COLUMN_TITLE, My_Database_Helper.COLUMN_DESCRIPTION, My_Database_Helper.COLUMN_DATE, My_Database_Helper.COLUMN_STATUS};
        Cursor cursor = sqLiteDatabase.query(My_Database_Helper.TABLE_TODO, columns, null, null, null, null, My_Database_Helper.COLUMN_DATE + " ASC");
        if (cursor != null) {
        }
        return cursor;
    }

    public int update(Integer _id, String title, String description, String date, Integer status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(My_Database_Helper.COLUMN_TITLE, title);
        contentValues.put(My_Database_Helper.COLUMN_DESCRIPTION, description);
        contentValues.put(My_Database_Helper.COLUMN_DATE, date);
        contentValues.put(My_Database_Helper.COLUMN_STATUS, status);

        int i = sqLiteDatabase.update(My_Database_Helper.TABLE_TODO, contentValues, My_Database_Helper.COLUMN_ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        sqLiteDatabase.delete(My_Database_Helper.TABLE_TODO, My_Database_Helper.COLUMN_ID + " = " + _id, null);
    }

}