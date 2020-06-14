package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AdapterRV extends RecyclerView.Adapter<AdapterRV.RecyclingViewHolder> {

    private Context context;
    private ArrayList<images> imglist;

    public AdapterRV(Context context, ArrayList<images> img) {
        this.context = context;
        this.imglist = img;
    }


    @NonNull
    @Override
    public AdapterRV.RecyclingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cardviewlayout, parent, false);

        return new RecyclingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclingViewHolder holder, int position) {
        holder.imageView.setImageResource(imglist.get(position).getImageUrl());
        holder.title.setText(imglist.get(position).getTitle());
    }


    @Override
    public int getItemCount() {
        return imglist.size();
    }


// view holder is used to show the data in xml


    public class RecyclingViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title;

        public RecyclingViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_id);
            title = itemView.findViewById(R.id.title_id);
        }
    }
}
