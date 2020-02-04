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

public class Adapter extends SimpleAdapter {
    private final Context context;
    private final String a;
    String status_update;
    private final List<? extends Map<String,?>> data;
    private final String[] from;
    private final int[] to;
    DatabaseReference firebaseDatabase;


    public Adapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,String a) {
        super(context, data, resource, from, to);
        this.context=context;
        this.data=data;
        this.from=from;
        this.to=to;
        this.a=a;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=layoutInflater.inflate(R.layout.officlayout,parent,false);
        final TextView sender_name=convertView.findViewById(R.id.s_na_o);
        TextView receiver_name=convertView.findViewById(R.id.r_na_o);
        final TextView receiver_phno=convertView.findViewById(R.id.r_ph);
        final TextView sender_phno=convertView.findViewById(R.id.send_ph_o);
        TextView office_name=convertView.findViewById(R.id.o_name);
        TextView time=convertView.findViewById(R.id.tim_o);
        TextView location_to_de=convertView.findViewById(R.id.loc_o);
        TextView address_to_pickup=convertView.findViewById(R.id.add_o);
        TextView time_get=convertView.findViewById(R.id.time_get_o);

        final TextView status = convertView.findViewById(R.id.status_o);
        Button update=convertView.findViewById(R.id.update);
        final TextView order_id=convertView.findViewById(R.id.o_id_o);
        AppCompatSpinner spinner=convertView.findViewById(R.id.spinner);
        status_update=spinner.toString();
        String[] status_list = new String[]{"pending", "received", "delivered"};
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(context,R.layout.support_simple_spinner_dropdown_item,status_list);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);
        receiver_phno.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse(receiver_phno.getText().toString()));
                context.startActivity(intent);
            }
        });
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
        DatabaseReference databaseReference=firebaseDatabase.child("food/office/"+sender_phno.getText().toString()+"/orders/"+order_id.getText().toString()+"/status");
        databaseReference.setValue(status_update);
        Intent intent=new Intent(context.getApplicationContext(),Office.class);
        intent.putExtra("status",a);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);

    }
});
        if(data.get(position).get("status")=="pending" && !data.get(position).isEmpty()) {
            sender_name.setText(data.get(position).get("sender_name").toString());
            receiver_name.setText(data.get(position).get("receiver_name").toString());
            receiver_phno.setText(data.get(position).get("receiver_phno").toString());
            office_name.setText(data.get(position).get("office_name").toString());
            time.setText(data.get(position).get("time").toString());
            location_to_de.setText(data.get(position).get("location_to_de").toString());
            address_to_pickup.setText(data.get(position).get("address_to_pickup").toString());
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

        return super.getView(position, convertView, parent);

    }
}
