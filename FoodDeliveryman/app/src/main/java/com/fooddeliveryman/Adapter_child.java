package com.fooddeliveryman;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Map;

public class Adapter_child extends SimpleAdapter {
    private final Context context;
    private final String a;
    String status_update;
    private final List<? extends Map<String,?>> data;
    private final String[] from;
    private final int[] to;
    DatabaseReference firebaseDatabase;


    public Adapter_child(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,String a) {
        super(context, data, resource, from, to);
        this.context=context;
        this.data=data;
        this.from=from;
        this.to=to;
        this.a=a;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView1, ViewGroup parent) {

        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView1=layoutInflater.inflate(R.layout.schoollayout,parent,false);
        final TextView parent_name=convertView1.findViewById(R.id.p_na);
        TextView child_name=convertView1.findViewById(R.id.c_na);
        TextView child_section=convertView1.findViewById(R.id.c_sec);
        final TextView sender_phno=convertView1.findViewById(R.id.send_ph_s);
        TextView child_school=convertView1.findViewById(R.id.sch);
        TextView time=convertView1.findViewById(R.id.tim);
        TextView location=convertView1.findViewById(R.id.loc);
        TextView address=convertView1.findViewById(R.id.add);
        TextView time_get=convertView1.findViewById(R.id.time_get);

        final TextView status = convertView1.findViewById(R.id.status_s);
        Button update=convertView1.findViewById(R.id.update_s);
        final TextView order_id=convertView1.findViewById(R.id.o_id_s);
        AppCompatSpinner spinner=convertView1.findViewById(R.id.spinner_s);
        String[] status_list = new String[]{"pending", "received", "delivered"};
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(context,R.layout.support_simple_spinner_dropdown_item,status_list);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(1);
        status_update=spinner.toString();
        sender_phno.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse(sender_phno.getText().toString()));
                context.startActivity(intent);

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase=FirebaseDatabase.getInstance().getReference();
                DatabaseReference databaseReference=firebaseDatabase.child("food/school/"+sender_phno.getText().toString()+"/orders/"+order_id.getText().toString()+"/status");
                databaseReference.setValue(status_update);
                Intent intent=new Intent(context.getApplicationContext(),Child_school.class);
                intent.putExtra("status",a);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);

            }
        });
        if(data.get(position).get("status")=="pending" && !data.get(position).isEmpty()) {
            parent_name.setText(data.get(position).get("parent_name").toString());
            child_name.setText(data.get(position).get("child_name").toString());
            child_section.setText(data.get(position).get("child_section").toString());
            child_school.setText(data.get(position).get("child_school").toString());
            time.setText(data.get(position).get("time").toString());
            location.setText(data.get(position).get("location").toString());
            address.setText(data.get(position).get("address").toString());
            status.setText(data.get(position).get("status").toString());
            sender_phno.setText(data.get(position).get("sender_phmo").toString());
            order_id.setText(data.get(position).get("order_id").toString());
            time_get.setText(data.get(position).get("time_to_get").toString());
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                status_update=adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return super.getView(position, convertView1, parent);

    }
}
