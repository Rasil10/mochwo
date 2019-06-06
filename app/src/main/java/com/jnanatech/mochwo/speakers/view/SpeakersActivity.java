package com.jnanatech.mochwo.speakers.view;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.speakers.model.SpeakerModel;
import com.jnanatech.mochwo.speakers.presenter.SpeakerImplementor;
import com.jnanatech.mochwo.speakers.presenter.SpeakerPresenter;

import java.util.ArrayList;

public class SpeakersActivity extends AppCompatActivity implements SpeakersView {

    RecyclerView speakerRecyclerView;
    SpeakerPresenter speakerPresenter;

    SpeakersAdapter speakersAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speakers);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bindActivity();

        speakerPresenter = new SpeakerImplementor(this, this);
        speakerPresenter.setSpeakers();
    }

    private void bindActivity() {
        speakerRecyclerView = (RecyclerView) findViewById(R.id.speakersRecylerView);
    }

    @Override
    public void getSpeaker(ArrayList<SpeakerModel> speakers) {

        speakersAdapter = new SpeakersAdapter(SpeakersActivity.this, speakers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SpeakersActivity.this);

        speakerRecyclerView.setAdapter(speakersAdapter);
        speakerRecyclerView.setLayoutManager(layoutManager);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                speakersAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                speakersAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.menu_search:
                return true;

            default:
                return true;
        }
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        } else {
            super.onBackPressed();
        }
    }
}
