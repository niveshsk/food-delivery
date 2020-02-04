package com.fooddeliveryman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Office extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;

    final ArrayList<HashMap<String, String>> orderlist = new ArrayList<>();
    ArrayList<HashMap<String, String>> result = new ArrayList<>();
String status_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office);
        final ListView listView=findViewById(R.id.list);

Intent intent=getIntent();
status_data=intent.getStringExtra("status");

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        final DatabaseReference databaseReference1 = databaseReference.child("food/office");
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
                                order.put("sender_name", dataSnapshot.child(phno_number).child("orders").child(o).child("sender_name").getValue(String.class));
                                order.put("order_id", dataSnapshot.child(phno_number).child("orders").child(o).getKey());
                                order.put("receiver_name", dataSnapshot.child(phno_number).child("orders").child(o).child("receiver_name").getValue(String.class));
                                order.put("receiver_phno", dataSnapshot.child(phno_number).child("orders").child(o).child("receiver_phno").getValue(String.class));
                                order.put("time", dataSnapshot.child(phno_number).child("orders").child(o).child("time").getValue(String.class));
                                order.put("location_to_de", dataSnapshot.child(phno_number).child("orders").child(o).child("location_to_de").getValue(String.class));
                                order.put("status", dataSnapshot.child(phno_number).child("orders").child(o).child("status").getValue(String.class));
                                order.put("office_name", dataSnapshot.child(phno_number).child("orders").child(o).child("office_name").getValue(String.class));
                                order.put("address_to_pickup", dataSnapshot.child(phno_number).child("orders").child(o).child("address_to_pickup").getValue(String.class));
                                order.put("time_to_get", dataSnapshot.child(phno_number).child("orders").child(o).child("time_to_get").getValue(String.class));

                                orderlist.add(order);
                                result = orderlist;
                                System.out.println("res" + result);
                            }
                        }
                        String[] data={"order_id","sender_name","receiver_name","receiver_phno","office_name","time","location_to_de","address_to_pickup","status","sender_phmo","time_to_get"};
                        int[] id_data={R.id.o_id_o,R.id.s_na_o,R.id.r_na_o,R.id.r_ph,R.id.o_name,R.id.tim_o,R.id.loc_o,R.id.add_o,R.id.status_o,R.id.send_ph_o,R.id.time_get_o};
                        Adapter simpleAdapter=new Adapter(Office.this,result,R.layout.officlayout,data,id_data,status_data);
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
