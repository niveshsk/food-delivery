package com.example.fooddeliveryclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class List_Office extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    
    final ArrayList<HashMap<String, String>> orderlist = new ArrayList<>();
    ArrayList<HashMap<String, String>> result = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__office);
        final ProgressBar progressBar=findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);
            final ListView listView = findViewById(R.id.list1);
            firebaseDatabase = FirebaseDatabase.getInstance();
            Intent intent = getIntent();
            String phno = intent.getStringExtra("phno");
            DatabaseReference databaseReference = firebaseDatabase.getReference();
            final DatabaseReference databaseReference1 = databaseReference.child("food/office/" + phno);
            DatabaseReference order=databaseReference1.child("orders");
            order.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> dataSnapshot1 = dataSnapshot.getChildren();
                    while (dataSnapshot1.iterator().hasNext()) {
                        String o = dataSnapshot1.iterator().next().getKey().toString();
                        HashMap<String, String> order = new HashMap<>();
                        order.put("sender_name", dataSnapshot.child(o).child("sender_name").getValue(String.class));
                        order.put("order_id", dataSnapshot.child(o).getKey());
                        order.put("receiver_name", dataSnapshot.child(o).child("receiver_name").getValue(String.class));
                        order.put("receiver_phno", dataSnapshot.child(o).child("receiver_phno").getValue(String.class));
                        order.put("time", dataSnapshot.child(o).child("time").getValue(String.class));
                        order.put("location_to_de", dataSnapshot.child(o).child("location_to_de").getValue(String.class));
                        order.put("status", dataSnapshot.child(o).child("status").getValue(String.class));
                        order.put("office_name", dataSnapshot.child(o).child("office_name").getValue(String.class));
                        order.put("address_to_pickup", dataSnapshot.child(o).child("address_to_pickup").getValue(String.class));
                        order.put("time_to_get", dataSnapshot.child(o).child("time_to_get").getValue(String.class));

                        orderlist.add(order);
                        System.out.println("fgdfgd"+orderlist);
                        result=orderlist;
                        System.out.println("res"+result);


                    }
                    String[] data={"order_id","sender_name","receiver_name","receiver_phno","office_name","time","location_to_de","address_to_pickup","status","time_to_get"};
                    int[] id_data={R.id.o_id_o,R.id.s_na_o,R.id.r_na_o,R.id.r_ph,R.id.o_name,R.id.tim_o,R.id.loc_o,R.id.add_o,R.id.status_o,R.id.time_to_get_o};
                    SimpleAdapter simpleAdapter=new SimpleAdapter(List_Office.this,result,R.layout.layoutoffice,data,id_data);
                    listView.setAdapter(simpleAdapter);
                    progressBar.setVisibility(View.INVISIBLE);
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            Log.d("sds","sdjshd");
            System.out.println(result);




        }
}
