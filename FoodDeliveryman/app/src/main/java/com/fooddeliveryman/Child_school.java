package com.fooddeliveryman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Child_school extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;

    final ArrayList<HashMap<String, String>> orderlist = new ArrayList<>();
    ArrayList<HashMap<String, String>> result = new ArrayList<>();
    String status_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_school);
        final ListView listView=findViewById(R.id.list_ch);

        Intent intent=getIntent();
        status_data=intent.getStringExtra("status");

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        final DatabaseReference databaseReference1 = databaseReference.child("food/school");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> dataSnapshot1 = dataSnapshot.getChildren();
                while (dataSnapshot1.iterator().hasNext()) {
                    String phno_number = dataSnapshot1.iterator().next().getKey().toString();
                    Log.d("hi",phno_number);
                    Iterable<DataSnapshot> dataSnapshot2=dataSnapshot.child(phno_number).child("orders").getChildren();
                    while(dataSnapshot2.iterator().hasNext()) {
                        String o = dataSnapshot2.iterator().next().getKey().toString();
                        if(dataSnapshot.child(phno_number).child("orders").child(o).child("status").getValue(String.class).toString().equals(status_data)) {
                            Log.d("hi", o);
                            HashMap<String, String> order = new HashMap<>();
                            order.put("sender_phmo", phno_number);
                            order.put("parent_name", dataSnapshot.child(phno_number).child("orders").child(o).child("parent_name").getValue(String.class));
                            order.put("order_id", dataSnapshot.child(phno_number).child("orders").child(o).getKey());
                            order.put("child_name", dataSnapshot.child(phno_number).child("orders").child(o).child("child_name").getValue(String.class));
                            order.put("child_section", dataSnapshot.child(phno_number).child("orders").child(o).child("child_section").getValue(String.class));
                            order.put("time", dataSnapshot.child(phno_number).child("orders").child(o).child("time").getValue(String.class));
                            order.put("location", dataSnapshot.child(phno_number).child("orders").child(o).child("location").getValue(String.class));
                            order.put("status", dataSnapshot.child(phno_number).child("orders").child(o).child("status").getValue(String.class));
                            order.put("child_school", dataSnapshot.child(phno_number).child("orders").child(o).child("child_school").getValue(String.class));
                            order.put("address", dataSnapshot.child(phno_number).child("orders").child(o).child("address").getValue(String.class));
                            order.put("time_to_get", dataSnapshot.child(phno_number).child("orders").child(o).child("time_to_get").getValue(String.class));

                            orderlist.add(order);
                            result = orderlist;
                            System.out.println("res" + result);
                        }
                    }
                    String[] data={"order_id","parent_name","child_name","child_section","child_school","time","location","address","status","sender_phmo","time_to_get"};
                    int[] id_data={R.id.o_id_s,R.id.p_na,R.id.c_na,R.id.c_sec,R.id.sch,R.id.tim,R.id.loc,R.id.add,R.id.status_s,R.id.send_ph_s,R.id.time_get};
                    Adapter_child simpleAdapter=new Adapter_child(Child_school.this,result,R.layout.schoollayout,data,id_data,status_data);
                    listView.setAdapter(simpleAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }



        });

        Log.d("sds","sdjshd");
        System.out.println(result);
    }
}
