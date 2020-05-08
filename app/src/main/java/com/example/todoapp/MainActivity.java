package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listview;
    ArrayList<String> list;
    EditText edittext;
    Button btnadd;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview=(ListView) findViewById(R.id.listview);
        btnadd= (Button) findViewById(R.id.buttonadd);
        edittext=(EditText) findViewById(R.id.et_task);



        list=new ArrayList<String>();
        list.add("Day1");
        list.add("Day2");
        arrayAdapter= new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,list);

        listview.setAdapter(arrayAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent;
                    intent = new Intent(MainActivity.this,day1.class);
                    startActivity(intent);

                }
                if (position == 1) {
                    Intent intent;
                    intent = new Intent(MainActivity.this, day2.class);
                    startActivity(intent);

                }
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names=edittext.getText().toString();
                list.add(names);
                listview.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();


            }
        });


    }
}
