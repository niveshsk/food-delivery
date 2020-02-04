package com.example.fooddeliveryclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class WorkPlaceFood extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
   EditText sender_name;
    EditText receiver_name;
     EditText office_name;
     EditText address_to_pickup;
     EditText location_to_de;
    EditText receiver_phno;
    String phno;
    Spinner time_get_food;
    String sender_name1,receiver_name1,office_name1,address_to_pickup1,location_to_de1,receiver_phno1,time_get;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_place_food);
        time_get_food=findViewById(R.id.spinner_office);
        String[] time_data={"9:00am t0 10:00am","10:00am to 11:00am","11:00am to 12:00 pm"};
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,time_data);
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
            Button order_lis_viewt=findViewById(R.id.order_list2);
            order_lis_viewt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1=new Intent(WorkPlaceFood.this,List_Office.class);
                    intent1.putExtra("phno",intent.getStringExtra("phno"));
                    startActivity(intent1);
                }
            });

            firebaseDatabase=FirebaseDatabase.getInstance();
            final DatabaseReference databaseRefe=firebaseDatabase.getReference();


            final String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

            Button button=findViewById(R.id.off_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sender_name=findViewById(R.id.sender_name);
                    receiver_name=findViewById(R.id.receiver_name);
                    office_name=findViewById(R.id.office_name);
                    address_to_pickup=findViewById(R.id.address_to_pickup);
                    location_to_de=findViewById(R.id.location_to_de);
                    receiver_phno=findViewById(R.id.receiver_Phone_number);


                    sender_name1=sender_name.getText().toString();
                     office_name1= office_name.getText().toString();
                   receiver_name1=receiver_name.getText().toString();
                   receiver_phno1=receiver_phno.getText().toString();
                    location_to_de1=location_to_de.getText().toString();
                    address_to_pickup1=address_to_pickup.getText().toString();
                    final DatabaseReference order=databaseRefe.child("food/office/"+phno);
                    DatabaseReference orders=order.child("orders");
                    String or_ra=String.valueOf(order_id_ra);
                    DatabaseReference order_id=orders.child(or_ra);
                    DatabaseReference sender_name=order_id.child("sender_name");
                    DatabaseReference receiver_name=order_id.child("receiver_name");
                    DatabaseReference receiver_phno=order_id.child("receiver_phno");
                    DatabaseReference  office_name=order_id.child("office_name");
                    DatabaseReference time=order_id.child("time");
                    DatabaseReference location_to_de=order_id.child("location_to_de");
                    DatabaseReference address_to_pickup=order_id.child("address_to_pickup");
                    DatabaseReference status=order_id.child("status");
                    DatabaseReference time_to_get=order_id.child("time_to_get");

                    sender_name.setValue(sender_name1);
                   receiver_name.setValue(receiver_name1);
                   receiver_phno.setValue(receiver_phno1);
                     office_name.setValue( office_name1);
                    location_to_de.setValue(location_to_de1);
                    address_to_pickup.setValue(address_to_pickup1);
                    time.setValue(timeStamp);
                    time_to_get.setValue(time_get);
                    status.setValue("pending");
                    Intent intent1=new Intent(WorkPlaceFood.this,List_Office.class);
                    intent1.putExtra("phno",phno);
                    startActivity(intent1);



                }
            });

        }
}
