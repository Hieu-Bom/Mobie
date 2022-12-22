package com.example.qldbh;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class HienDB extends RecyclerView.Adapter<HienDB.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList asdt,aten,adc;

    HienDB(Activity activity, Context context, ArrayList asdt, ArrayList aten, ArrayList adc){
        this.activity = activity;
        this.context = context;
        this.asdt = asdt;
        this.aten = aten;
        this.adc = adc;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_hiendb, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.sdt.setText(String.valueOf(asdt.get(position)));
        holder.ten.setText(String.valueOf(aten.get(position)));
        holder.dc.setText(String.valueOf(adc.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SuaXoa.class);
                intent.putExtra("sdt", String.valueOf(asdt.get(position)));
                intent.putExtra("ten", String.valueOf(aten.get(position)));
                intent.putExtra("dc", String.valueOf(adc.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return aten.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sdt,ten,dc;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sdt = itemView.findViewById(R.id.sdt);
            ten = itemView.findViewById(R.id.ten);
            dc = itemView.findViewById(R.id.dc);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }
}