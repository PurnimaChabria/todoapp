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

public class day2 extends AppCompatActivity {
    ListView listview2;
    ArrayList<String> list2;
    EditText edittext2;
    Button btnadd2;
    Button btndel2;
    ArrayAdapter<String> arrayAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day2);
        listview2=(ListView) findViewById(R.id.listview2);
        btnadd2= (Button) findViewById(R.id.buttonadd2);
        btndel2= (Button) findViewById(R.id.buttondel2);
        edittext2=(EditText) findViewById(R.id.et_task2);



        list2=new ArrayList<String>();
        list2.add("Learn Python");
        list2.add("Learn Java");
        arrayAdapter2= new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,list2);

        listview2.setAdapter(arrayAdapter2);


        btnadd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names=edittext2.getText().toString();
                list2.add(names);
                listview2.setAdapter(arrayAdapter2);
                arrayAdapter2.notifyDataSetChanged();


            }
        });

        btndel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names=edittext2.getText().toString();
                list2.remove(names);
                listview2.setAdapter(arrayAdapter2);
                arrayAdapter2.notifyDataSetChanged();


            }
        });


    }
}
