package com.example.somina.todolist.controller;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.somina.todolist.R;

import java.util.ArrayList;


public class List_View_Adapter extends BaseAdapter {

    private Activity context;
    private ArrayList<Integer> taskIds;
    private ArrayList<String> date;
    private ArrayList<String> title;
    private ArrayList<String> description;
    private ArrayList<Integer> status;

    List_View_Adapter(Activity context, ArrayList<Integer> ids, ArrayList<String> title, ArrayList<String> description, ArrayList<String> date, ArrayList<Integer> status) {
        super();
        this.taskIds = ids;
        this.context = context;
        this.date = date;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    @Override
    public int getCount() {
        return taskIds.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    private class ViewHolder {
        TextView textViewId;
        TextView textViewDate;
        TextView textViewTitle;
        TextView textViewDescription;
        ImageView statusView;
        TextView textViewDuedate;
        TextView textViewStatus;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        LayoutInflater layoutInflater = context.getLayoutInflater();

        if (view == null) {
            view = layoutInflater.inflate(R.layout.row, null);
            holder = new ViewHolder();
            holder.textViewId = (TextView) view.findViewById(R.id.taskId);
            holder.textViewStatus = (TextView) view.findViewById(R.id.taskStatus);
            holder.textViewDate = (TextView) view.findViewById(R.id.date);
            holder.textViewTitle = (TextView) view.findViewById(R.id.title);
            holder.textViewDescription = (TextView) view.findViewById(R.id.description);
            holder.statusView = (ImageView) view.findViewById(R.id.status);
            holder.textViewDuedate = (TextView) view.findViewById(R.id.timestamp);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textViewId.setText(String.valueOf(taskIds.get(position)));
        holder.textViewStatus.setText(String.valueOf(status.get(position)));
        holder.textViewDate.setText(date.get(position));
        holder.textViewTitle.setText(title.get(position));
        holder.textViewDescription.setText(description.get(position));
        if (status.get(position) == 0) {
            holder.statusView.setImageResource(R.drawable.ic_action_inc);
        } else {
            holder.statusView.setImageResource(R.drawable.ic_action_comp);
        }
        holder.textViewDuedate.setText(date.get(position));
        return view;
    }
}