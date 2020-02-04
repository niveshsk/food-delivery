package com.example.fooddeliveryclient;

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
        final Intent intent=getIntent();
        String phno=intent.getStringExtra("phno");
        Button child=findViewById(R.id.child);
        Button office=findViewById(R.id.office);
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(Chooser.this,Detailfood.class);
                intent1.putExtra("phno",intent.getStringExtra("phno"));
                startActivity(intent1);
            }
        });
        office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(Chooser.this,WorkPlaceFood.class);
                intent1.putExtra("phno",intent.getStringExtra("phno"));
                startActivity(intent1);
            }
        });
    }
}
