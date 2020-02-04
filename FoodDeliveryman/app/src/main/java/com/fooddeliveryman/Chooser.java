package com.fooddeliveryman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Chooser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);
        Button pending=findViewById(R.id.pending_o);
        Button received=findViewById(R.id.receiver_o);
        Button delivered=findViewById(R.id.deliverd_o);
        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Chooser.this,Office.class);
                intent.putExtra("status","pending");
                startActivity(intent);
            }
        });
received.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(Chooser.this,Office.class);
        intent.putExtra("status","received");
        startActivity(intent);
    }
});
delivered.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(Chooser.this,Office.class);
        intent.putExtra("status","delivered");
        startActivity(intent);
    }
});
    }
}
