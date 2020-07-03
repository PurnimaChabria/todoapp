package com.example.todoapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Dayslistcl extends ArrayAdapter<Days> {
    private Activity context;
    private List<Days> dayslist;

    public Dayslistcl(Activity context,List<Days> dayslist){
        super(context,R.layout.list_layout,dayslist);
        this.context=context;
        this.dayslist=dayslist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();

        View listViewItem=inflater.inflate(R.layout.list_layout,null,true);

        TextView TextView1= (TextView) listViewItem.findViewById(R.id.TextView1);

        Days days=dayslist.get(position);

        TextView1.setText(days.getDayname());
        return listViewItem;
    }
}

