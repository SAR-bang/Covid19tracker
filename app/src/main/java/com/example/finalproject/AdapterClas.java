package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterClas extends RecyclerView.Adapter<AdapterClas.ViewHolder> {

    Context context;
    List<ApiModel> countries;
    ApiModel apiModel;
    int chck = 0;

    // constructor

    public AdapterClas(Context context, List<ApiModel> countries) {
        this.context = context;
        this.countries = countries;
    }

    public AdapterClas(Context context, ApiModel apiModel) {
        this.context = context;
        this.apiModel = apiModel;
    }

    @NonNull
    @Override
    public AdapterClas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClas.ViewHolder holder, int position) {

        // bind the data with views

        final ApiModel country;


        try {
            countries.size();
            chck = 1;

        } catch (Exception e) {
            chck = 0;
        }

        if (chck == 0) {
            country = apiModel;
        } else {
            country = countries.get(position);
        }
        final String imageUrl = country.getCountryInfo().getFlag();
        String cases = country.getCases().toString();
        String deathcase = country.getDeaths().toString();

        Picasso.with(context).load(imageUrl).into(holder.imageCountry);
        holder.country.setText(country.getCountry());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                passing to another intent
                Intent intent = new Intent(context, Detailed.class);

//              this below is used to pass the data between the intents.

                intent.putExtra("country", country.getCountry());
                intent.putExtra("confirmed", country.getCases().toString()
                );
                intent.putExtra("dead", country.getDeaths().toString());
                intent.putExtra("recovered", country.getRecovered().toString());
                intent.putExtra("tested", country.getTests().toString());
                intent.putExtra("active", country.getActive().toString());
                intent.putExtra("critical", country.getCritical().toString());
                intent.putExtra("flag", imageUrl);
                context.startActivity(intent);
                // starting the activity

            }
        });
    }

    @Override
    public int getItemCount() {
        if (chck == 1) {
            // controlling the request and search functionality

            return countries.size();
        } else {
            return 1;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView country;
        CardView cardView;
        ImageView imageCountry;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // intializing the views
            
            country = itemView.findViewById(R.id.tvTitle);
            imageCountry = itemView.findViewById(R.id.image_country);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
}
