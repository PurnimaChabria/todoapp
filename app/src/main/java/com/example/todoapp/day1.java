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

public class day1 extends AppCompatActivity {
    ListView listview1;
    ArrayList<String> list1;
    EditText edittext1;
    Button btnadd1;
    Button btndel1;
    ArrayAdapter<String> arrayAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day1);
        listview1=(ListView) findViewById(R.id.listview1);
        btnadd1= (Button) findViewById(R.id.buttonadd1);
        btndel1= (Button) findViewById(R.id.buttondel1);
        edittext1=(EditText) findViewById(R.id.et_task1);



        list1=new ArrayList<String>();
        list1.add("Learn english");
        list1.add("Learn Math");
        arrayAdapter1= new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,list1);

        listview1.setAdapter(arrayAdapter1);


        btnadd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names=edittext1.getText().toString();
                list1.add(names);
                listview1.setAdapter(arrayAdapter1);
                arrayAdapter1.notifyDataSetChanged();


            }
        });

        btndel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names=edittext1.getText().toString();
                list1.remove(names);
                listview1.setAdapter(arrayAdapter1);
                arrayAdapter1.notifyDataSetChanged();


            }
        });


    }
}
