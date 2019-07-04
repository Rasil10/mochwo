package com.jnanatech.mochwo.News.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.jnanatech.mochwo.News.model.NewsModel;
import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.utils.database.RealmController;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    RecyclerView newsRecyclerView;
    RealmController realmController;
    ArrayList<NewsModel> news = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        realmController = RealmController.with(this);

        newsRecyclerView = (RecyclerView) findViewById(R.id.newsRecyclerView);

        news = realmController.getNews();
        NewsAdapter newsAdapter = new NewsAdapter(this,realmController.getNews());

        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsRecyclerView.setAdapter(newsAdapter);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
