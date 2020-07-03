package com.example.todoapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TaskList extends ArrayAdapter {

    private Activity context;
    private List<Task> tasklist;

    public TaskList(Activity context,List<Task> tasklist){
        super(context,R.layout.list_task_layout,tasklist);
        this.context=context;
        this.tasklist=tasklist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();

        View listViewItem=inflater.inflate(R.layout.list_task_layout,null,true);

        TextView TextViewtask= (TextView) listViewItem.findViewById(R.id.TextViewtask);

        Task task=tasklist.get(position);

        TextViewtask.setText(task.getTaskName());
        return listViewItem;
    }
}
