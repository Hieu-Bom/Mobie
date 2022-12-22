package com.example.qldbh;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public Editable timk;
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageview;
    TextView no_data;
    DataBase myDB;
    ArrayList<String> asdt,aten,adc;
    HienDB customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Them.class);
                startActivity(intent);
            }
        });
        myDB = new DataBase(MainActivity.this);
        asdt= new ArrayList<>();
        aten = new ArrayList<>();
        adc = new ArrayList<>();
        storeDataInArrays();
        customAdapter = new HienDB(MainActivity.this,this, asdt, aten, adc);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.LayDL();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.timkiem){
            EditText tk;
            Button btntk, btnthoat;
            AlertDialog.Builder mBuilder =	new AlertDialog.Builder(MainActivity.this);
            View mView	=	getLayoutInflater().inflate(R.layout.timkiem, null);
            tk=(EditText) mView.findViewById(R.id.ten);
            timk=tk.getText();
            btntk=(Button) mView.findViewById(R.id.tk);
            btnthoat=(Button) mView.findViewById(R.id.thoat);
            btntk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(MainActivity.this, timk, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, TimKiem.class);
                    String s=(String) timk.toString();
                    i.putExtra("tk",s);
                    startActivity(i);
                }
            });
            btnthoat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
            mBuilder.setView(mView);
            AlertDialog dialog=mBuilder.create();
            dialog.show();
        }
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        if(item.getItemId() == R.id.tt){
            AlertDialog.Builder mBuilder =	new AlertDialog.Builder(MainActivity.this);
            View mView	=	getLayoutInflater().inflate(R.layout.thongtin, null);
            mBuilder.setView(mView);
            AlertDialog dialog=mBuilder.create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa Tất Cả Danh Sách?");
        builder.setMessage("Bạn có muốn xóa tất cả hay không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DataBase myDB = new DataBase(MainActivity.this);
                myDB.XoaAll();

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}