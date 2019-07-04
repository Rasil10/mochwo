package com.jnanatech.mochwo.News.view;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jnanatech.mochwo.News.model.NewsModel;
import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.utils.Constants;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    Context context;
    ArrayList<NewsModel> newsModels = new ArrayList<>();

    public NewsAdapter(Context context, ArrayList<NewsModel> newsModels) {
        this.context = context;
        this.newsModels = newsModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final NewsModel currentNews = newsModels.get(position);

        holder.title.setText(currentNews.getHeading());
        holder.content.setText(Html.fromHtml(currentNews.getContent()));
        holder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,WebPageActivity.class);
                intent.putExtra(Constants.webUrlConstant,currentNews.getLink());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        TextView viewMore;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.newsTitle);
            content = (TextView) itemView.findViewById(R.id.newsContent);
            viewMore = (TextView) itemView.findViewById(R.id.viewMore);

        }
    }
}
