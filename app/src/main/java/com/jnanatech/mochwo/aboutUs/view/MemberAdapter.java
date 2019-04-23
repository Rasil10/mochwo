package com.jnanatech.mochwo.aboutUs.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.aboutUs.model.MemberModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MyViewHolder> {
    Context context;
    ArrayList<MemberModel> memberModels;

    public MemberAdapter(Context context, ArrayList<MemberModel> memberModels) {
        this.context = context;
        this.memberModels = memberModels;
    }

    @NonNull
    @Override
    public MemberAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_member,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.MyViewHolder holder, int position) {
        MemberModel currentMember = memberModels.get(position);

        holder.name.setText(currentMember.getMemberName());
        holder.position.setText(currentMember.getMemeberPosition());
        Picasso.get()
                .load(currentMember.getMemeberImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return memberModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,position;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.memberName);
            position = (TextView) itemView.findViewById(R.id.memberPosition);
            image = (ImageView) itemView.findViewById(R.id.memeberImageView);
        }
    }
}
