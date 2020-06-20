package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
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
import com.allyants.notifyme.NotifyMe;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import android.os.Bundle;

import java.util.Calendar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    public static final String DAY_NAME ="dayname";
    public static final String DAY_ID="dayid";
    ListView listview;
    EditText edittext;
    Button btnadd;
    TextView textviewfordel;
    DatabaseReference databasedays;
    ArrayList<Days> dayslist;
    Calendar now = Calendar.getInstance();
    TimePickerDialog tpd;
    DatePickerDialog dpd;
    EditText etTitle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databasedays= FirebaseDatabase.getInstance().getReference("Days");
        listview = (ListView) findViewById(R.id.listview);
        btnadd = (Button) findViewById(R.id.buttonadd);
        textviewfordel=(TextView) findViewById(R.id.TextViewdeleteday);
        edittext = (EditText) findViewById(R.id.et_task);

        dayslist = new ArrayList<>();

        Button btnNotify= findViewById(R.id.btnNotify);
        etTitle=findViewById(R.id.etTitle);
        //get current date & set it to DatePickerDialog
        dpd = DatePickerDialog.newInstance(
                MainActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        //initialize timepickerdialog with current time
        tpd = TimePickerDialog.newInstance(
                MainActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                false

        );

        //show notification
        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dpd.show(getFragmentManager(),"Datepickerdialog");
            }
        });





        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDays();
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Days day =dayslist.get(position);
                deleteDay(day.getDayid());
                return false;
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Days days=dayslist.get(i);
                Intent intent =new Intent(getApplicationContext(),day1.class);
                intent.putExtra(DAY_NAME,days.getDayname());
                intent.putExtra(DAY_ID,days.getDayid());

                startActivity(intent);




            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        databasedays.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dayslist.clear();
                for(DataSnapshot daysnapshot : dataSnapshot.getChildren()){
                    Days days = daysnapshot.getValue(Days.class);
                    dayslist.add(days);
                }
                Dayslistcl adapter=new Dayslistcl(MainActivity.this,dayslist);
                listview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        now.set(Calendar.YEAR,year);
        now.set(Calendar.MONTH,monthOfYear);
        now.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        tpd.show(getFragmentManager(),"Timepickerdialog");

    }
    //set custom to datepickerdialog. Handle notification actions
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        now.set(Calendar.HOUR_OF_DAY,hourOfDay);
        now.set(Calendar.MINUTE,minute);
        now.set(Calendar.SECOND,second);
        //initialize notification
        NotifyMe notifyMe = new NotifyMe.Builder(getApplicationContext())
                .title(etTitle.getText().toString())
                .color(255,0,0,255)
                .led_color(255,255,255,255)
                .time(now)
                .addAction(new Intent(),"Snooze",false)
                .key("text")
                .addAction(new Intent(),"Dismiss",true,false)
                .addAction(new Intent(),"Done")
                .large_icon(R.mipmap.ic_launcher_round)
                .build();
    }

    private void deleteDay(String dayid){
        DatabaseReference dRday = FirebaseDatabase.getInstance().getReference("Days").child(dayid);
        DatabaseReference dRtask = FirebaseDatabase.getInstance().getReference("Tasks").child(dayid);
        dRday.removeValue();
        dRtask.removeValue();
        Toast.makeText(this, "Day Deleted", Toast.LENGTH_LONG).show();



    }

    private void AddDays() {
        String day = edittext.getText().toString().trim();

        if (!TextUtils.isEmpty(day)) {

            String id = databasedays.push().getKey();
            Days days = new Days(id,day);
            databasedays.child(id).setValue(days);
            Toast.makeText(this, "Day Added", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Enter a Day", Toast.LENGTH_LONG).show();
        }
    }
}
