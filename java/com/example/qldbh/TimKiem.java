package com.example.qldbh;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TimKiem extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView empty_imageview;
    FloatingActionButton back;
    TextView no_data;
    private Context context;
    DataBase myDB;
    ArrayList<String> asdt,aten,adc;
    HienDB customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);

        recyclerView = findViewById(R.id.recyclerView);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimKiem.this, MainActivity.class);
                startActivity(intent);
            }
        });
        String x=getIntent().getExtras().getString("tk");
        myDB = new DataBase(TimKiem.this);
        asdt= new ArrayList<>();
        aten = new ArrayList<>();
        adc = new ArrayList<>();
        storeDataInArrays(x);

        customAdapter = new HienDB(TimKiem.this,this, asdt, aten, adc);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(TimKiem.this));
    }

    private void storeDataInArrays(String a) {
        Cursor cursor = myDB.timkiem(a);
        if(cursor.getCount() == 0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                asdt.add(cursor.getString(0));
                aten.add(cursor.getString(1));
                adc.add(cursor.getString(2));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }
}