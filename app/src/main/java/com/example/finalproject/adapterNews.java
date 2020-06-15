package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Callback;

public class adapterNews extends RecyclerView.Adapter<adapterNews.ViewHolder> {

    private Context context;
    private News2Model news2Models;
    private Newsmodel newsModel;

    public adapterNews(Context context, News2Model news2Models) {
        this.context = context;
        this.news2Models = news2Models;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        Binder method is used to bind the data with the respective views

        newsModel = news2Models.getData().get(position);

        final String Title = newsModel.getTitle();

        Picasso.with(context).load(newsModel.getImageUrl()).into(holder.imageCountry);
        holder.country.setText(Title);
        holder.source.setText(newsModel.getSource());
        holder.date.setText(newsModel.getCreatedAt());

        try {
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // passing the data between the intents

                    Intent intent = new Intent(context, NewsDetail.class);
                    intent.putExtra("Source", newsModel.getSource());
                    intent.putExtra("date", newsModel.getCreatedAt());
                    intent.putExtra("image", newsModel.getImageUrl());
                    intent.putExtra("Title", newsModel.getTitle());
                    intent.putExtra("summary", newsModel.getSummary());
                    context.startActivity(intent);
                }
            });
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public int getItemCount() {
        return news2Models.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView country, source, date;
        CardView card;
        ImageView imageCountry;


        // working with the views

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            country = itemView.findViewById(R.id.tvTitle);
            imageCountry = itemView.findViewById(R.id.image_country);
            card = itemView.findViewById(R.id.cardView);
            source = itemView.findViewById(R.id.tvSource);
            date = itemView.findViewById(R.id.tvDate);

        }
    }
}
