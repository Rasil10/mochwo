package com.jnanatech.mochwo.sponsers.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.sponsers.model.SponserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SponsersAdapter extends RecyclerView.Adapter<SponsersAdapter.MyViewHolder> {
    Context context;
    ArrayList<SponserModel> sponserModels;


    public SponsersAdapter(Context context, ArrayList<SponserModel> sponserModels) {
        this.context = context;
        this.sponserModels = sponserModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sponsers, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SponserModel currentSponser = sponserModels.get(position);
        Log.d("url",currentSponser.getUrl());
        Picasso.get().load(currentSponser.getUrl())
                .placeholder(R.drawable.ic_terrain_black_24dp)
                .error(R.drawable.ic_terrain_black_24dp)
                .into(holder.sponsersImageView);
    }

    @Override
    public int getItemCount() {
        return sponserModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView sponsersImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sponsersImageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
