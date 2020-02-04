package com.example.fooddeliveryclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Detailfood extends AppCompatActivity {
FirebaseDatabase firebaseDatabase;
EditText parent_name;
    EditText child_name;
    EditText child_section;
    EditText child_school;
    EditText location;
    EditText address;
Spinner time_get_food;
    String phno;
 String parent_name1,child_name1,child_section1,child_school1,location1,address1,time_get;;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailfood);
        time_get_food=findViewById(R.id.spinner3);
        String[] time_data={"9:00am t0 10:00am","10:00am to 11:00am","11:00am to 12:00 pm"};
        ArrayAdapter<String>arrayAdapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,time_data);
        time_get_food.setAdapter(arrayAdapter);
        time_get_food.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                time_get=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        final Intent intent=getIntent();
        Random random= new Random();
        final int order_id_ra=random.nextInt(999999999);
        phno=intent.getStringExtra("phno");
        Button order_lis_viewt=findViewById(R.id.order_list);
        order_lis_viewt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(Detailfood.this,List.class);
                intent1.putExtra("phno",intent.getStringExtra("phno"));
                startActivity(intent1);
            }
        });

        firebaseDatabase=FirebaseDatabase.getInstance();
        final DatabaseReference databaseRefe=firebaseDatabase.getReference();


        final String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent_name=findViewById(R.id.parent_name);
                child_name=findViewById(R.id.child_name);
                child_section=findViewById(R.id.child_section);
                child_school=findViewById(R.id.school_name);
                location=findViewById(R.id.location);
                address=findViewById(R.id.address);
                parent_name1=parent_name.getText().toString();
                child_school1=child_school.getText().toString();
                child_name1=child_name.getText().toString();
                child_section1=child_section.getText().toString();
                location1=location.getText().toString();
                address1=address.getText().toString();
                final DatabaseReference order=databaseRefe.child("food/school/"+phno);

                            DatabaseReference orders=order.child("orders");


                            String or_ra=String.valueOf(order_id_ra);
                            DatabaseReference order_id=orders.child(or_ra);
                            DatabaseReference parent_name=order_id.child("parent_name");
                            DatabaseReference child_name=order_id.child("child_name");
                            DatabaseReference child_section=order_id.child("child_section");
                            DatabaseReference child_school=order_id.child("child_school");
                            DatabaseReference time=order_id.child("time");
                            DatabaseReference location=order_id.child("location");
                            DatabaseReference address=order_id.child("address");
                            DatabaseReference status=order_id.child("status");
                            DatabaseReference time_to_get=order_id.child("time_to_get");

                            parent_name.setValue(parent_name1);
                            child_name.setValue(child_name1);
                            child_section.setValue(child_section1);
                            child_school.setValue(child_school1);
                            location.setValue(location1);
                            address.setValue(address1);
                            time.setValue(timeStamp);
                            time_to_get.setValue(time_get);

                            status.setValue("pending");
                            Intent intent1=new Intent(Detailfood.this,List.class);
                            intent1.putExtra("phno",phno);
                            startActivity(intent1);



                          }
        });


    }
}
