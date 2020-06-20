package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class day1 extends AppCompatActivity {
    TextView textView2;
    TextView textView3;
    ListView listview1;
    ArrayList<Task> tasklist1;
    EditText edittext1;
    Button btnadd1;

    DatabaseReference databasetasks;

    ArrayAdapter<String> arrayAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day1);

        textView2 = (TextView) findViewById(R.id.TextView2);
        textView3 = (TextView) findViewById(R.id.TextView3);
        listview1=(ListView) findViewById(R.id.listview1);
        btnadd1= (Button) findViewById(R.id.buttonadd1);


        edittext1=(EditText) findViewById(R.id.et_task1);
        tasklist1=new ArrayList<>();

        Intent intent = getIntent();

        final String dayid = intent.getStringExtra(MainActivity.DAY_ID);
        String name = intent.getStringExtra(MainActivity.DAY_NAME);
        textView2.setText(name);

        databasetasks= FirebaseDatabase.getInstance().getReference("Tasks").child(dayid);

        btnadd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });
        listview1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {
                Task task = tasklist1.get(i);
                showupdatedialog(dayid,task.getTaskId(),task.getTaskName());
                return false;
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        databasetasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tasklist1.clear();
                for(DataSnapshot tasksnapshot : dataSnapshot.getChildren()){
                    Task task = tasksnapshot.getValue(Task.class);
                    tasklist1.add(task);
                }
                TaskList tracklistadapter=new TaskList(day1.this,tasklist1);
                listview1.setAdapter(tracklistadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void showupdatedialog(final String dayid, final String taskId, String tname){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        final View  dialogView = inflater.inflate(R.layout.update_dialog,null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Updating Task" + tname);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        final Button buttonupdate1= (Button)dialogView.findViewById(R.id.buttonupdateid);
        final EditText et_updatetask =(EditText)dialogView.findViewById(R.id.et_updatetask);

        buttonupdate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tname = et_updatetask.getText().toString().trim();
                if(TextUtils.isEmpty(tname)){
                    et_updatetask.setError("TaskName Required");
                    return;
                }
                updatetask(dayid,taskId,tname);
                alertDialog.dismiss();

            }
        });


    }
    private  boolean updatetask(String dayid,String id,String name){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Tasks").child(dayid).child(id);
        Task task =new Task(id,name);
        databaseReference.setValue(task);
        Toast.makeText(this, "Task Updated successfully", Toast.LENGTH_LONG).show();
        return true;
    }

    private void saveTask(){
        String taskname=edittext1.getText().toString().trim();

        if (!TextUtils.isEmpty(taskname)) {

            String tid = databasetasks.push().getKey();
            Task task = new Task(tid,taskname);
            databasetasks.child(tid).setValue(task);
            Toast.makeText(this, "Task Added", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Enter a Task", Toast.LENGTH_LONG).show();
        }


    }
}
