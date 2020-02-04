package com.fooddeliveryman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Chooser_child extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser_child);
        Button pending=findViewById(R.id.pending_c);
        Button received=findViewById(R.id.receiver_c);
        Button delivered=findViewById(R.id.deliverd_c);
        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Chooser_child.this,Child_school.class);
                intent.putExtra("status","pending");
                startActivity(intent);
            }
        });
        received.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Chooser_child.this,Child_school.class);
                intent.putExtra("status","received");
                startActivity(intent);
            }
        });
        delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Chooser_child.this,Child_school.class);
                intent.putExtra("status","delivered");
                startActivity(intent);
            }
        });
    }
}
