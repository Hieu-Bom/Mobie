package com.example.qldbh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Them extends AppCompatActivity {
    EditText sdt, ten, dc;
    Button add_button;
    FloatingActionButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them);

        sdt = findViewById(R.id.sdt);
        ten = findViewById(R.id.ten);
        dc = findViewById(R.id.dc);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBase myDB = new DataBase(Them.this);
                myDB.Them(sdt.getText().toString().trim(),
                        ten.getText().toString().trim(),
                        dc.getText().toString().trim());
            }
        });
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Them.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}