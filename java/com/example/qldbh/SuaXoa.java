package com.example.qldbh;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SuaXoa extends AppCompatActivity {

    EditText sdt,ten,dc;
    Button update_button, delete_button, goi, nt;
    String ssdt, sten, sdc,gsdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_xoa);

        sdt = findViewById(R.id.sdt);
        ten = findViewById(R.id.ten);
        dc = findViewById(R.id.dc);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);
        goi=findViewById(R.id.goi);
        nt=findViewById(R.id.nhant);

        getAndSetIntentData();


        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(sten);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBase myDB = new DataBase(SuaXoa.this);
                ssdt = sdt.getText().toString().trim();
                sten = ten.getText().toString().trim();
                sdc = dc.getText().toString().trim();
                myDB.Sua(ssdt, sten, sdc);
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

        goi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a="tel:"+ssdt;
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse(a));
                startActivity(i);
            }
        });

        nt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a=ssdt;
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", ssdt, null));
                startActivity(i);
            }
        });
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("sdt") && getIntent().hasExtra("ten") &&
                getIntent().hasExtra("dc")){
            ssdt = getIntent().getStringExtra("sdt");
            sten = getIntent().getStringExtra("ten");
            sdc = getIntent().getStringExtra("dc");
            sdt.setText(ssdt);
            ten.setText(sten);
            dc.setText(sdc);
            Log.d("Hi", ssdt+" "+sten+" "+sdc);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa " + sten + " khỏi danh bạ?");
        builder.setMessage("Bạn có muốn xóa " + sten + " không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DataBase myDB = new DataBase(SuaXoa.this);
                myDB.Xoa(ssdt);
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