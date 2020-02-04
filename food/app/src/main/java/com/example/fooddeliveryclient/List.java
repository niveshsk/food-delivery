package com.example.fooddeliveryclient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class List extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    static Long co_or;
    final ArrayList<HashMap<String, String>> orderlist = new ArrayList<>();
    ArrayList<HashMap<String, String>> result = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        final ProgressBar progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        final ListView listView = findViewById(R.id.list);
        firebaseDatabase = FirebaseDatabase.getInstance();
        Intent intent = getIntent();
        String phno = intent.getStringExtra("phno");
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        final DatabaseReference databaseReference1 = databaseReference.child("food/school/" + phno);
        DatabaseReference order=databaseReference1.child("orders");
        order.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.VISIBLE);
                    Iterable<DataSnapshot> dataSnapshot1 = dataSnapshot.getChildren();
                    while (dataSnapshot1.iterator().hasNext()) {
                        String o = dataSnapshot1.iterator().next().getKey().toString();
                        HashMap<String, String> order = new HashMap<>();
                        order.put("parent_name", dataSnapshot.child(o).child("parent_name").getValue(String.class));
                        order.put("order_id", dataSnapshot.child(o).getKey());
                        order.put("child_name", dataSnapshot.child(o).child("child_name").getValue(String.class));
                        order.put("child_section", dataSnapshot.child(o).child("child_section").getValue(String.class));
                        order.put("time", dataSnapshot.child(o).child("time").getValue(String.class));
                        order.put("location", dataSnapshot.child(o).child("location").getValue(String.class));
                        order.put("status", dataSnapshot.child(o).child("status").getValue(String.class));
                        order.put("child_school", dataSnapshot.child(o).child("child_school").getValue(String.class));
                        order.put("address", dataSnapshot.child(o).child("address").getValue(String.class));
                        order.put("time_to_get", dataSnapshot.child(o).child("time_to_get").getValue(String.class));

                        orderlist.add(order);
                        System.out.println("fgdfgd"+orderlist);
                        result=orderlist;
                        System.out.println("res"+result);


                    }
                 String[] data={"order_id","parent_name","child_name","child_section","child_school","time","location","address","status","time_to_get"};
                int[] id_data={R.id.o_id,R.id.p_na,R.id.c_na,R.id.c_sec,R.id.sch,R.id.tim,R.id.loc,R.id.add,R.id.status,R.id.time_to_get_s};
                SimpleAdapter simpleAdapter=new SimpleAdapter(List.this,result,R.layout.layout,data,id_data);
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