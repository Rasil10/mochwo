package com.jnanatech.mochwo.bookmark.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.bookmark.model.BookmarkModel;
import com.jnanatech.mochwo.schedule.model.EventModel;
import com.jnanatech.mochwo.schedule.view.EventAdapter;
import com.jnanatech.mochwo.speakers.model.SpeakerModel;
import com.jnanatech.mochwo.utils.database.RealmController;

import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity {

    private RecyclerView bookmarkRecyclerView;
    private RealmController realmController;

    private ArrayList<SpeakerModel> speakerModels = new ArrayList<>();
    private ArrayList<EventModel> bookmarkedEvents = new ArrayList<>();
    private ArrayList<BookmarkModel> bookmarksIds = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        realmController = RealmController.with(this);
        bookmarksIds = realmController.getBookmarks();

        if(bookmarksIds.size() >=1) {
            ArrayList<EventModel> allEvents = realmController.getAllEvents();
            for (int i = 0; i < allEvents.size(); i++) {
                for(int j =0 ; j<bookmarksIds.size();j++){
                    if(bookmarksIds.get(j).getId().equals(allEvents.get(i).getId())){
                        bookmarkedEvents.add(allEvents.get(i));
                    }
                }
            }

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            speakerModels = realmController.getAllSpeakersList();

            bookmarkRecyclerView = (RecyclerView) findViewById(R.id.bookmarkRecyclerView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            BookmarkAdapter eventAdapter = new BookmarkAdapter(this, bookmarkedEvents, speakerModels);

            bookmarkRecyclerView.setLayoutManager(linearLayoutManager);
            bookmarkRecyclerView.setAdapter(eventAdapter);
        }
        else{
            onBackPressed();
            Toast.makeText(this, "No Bookmarked Events.", Toast.LENGTH_SHORT).show();
        }

//        if (bookmarkedEvents.size() < 1) {
//            onBackPressed();
//        } else {
//
//        }


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
