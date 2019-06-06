package com.jnanatech.mochwo.search.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.schedule.model.EventModel;
import com.jnanatech.mochwo.scheduleDetail.ScheduleDetailActivity;
import com.jnanatech.mochwo.speakerDetail.view.SpeakerDetailActivity;
import com.jnanatech.mochwo.speakers.model.SpeakerModel;
import com.jnanatech.mochwo.sponsers.model.SponserModel;
import com.jnanatech.mochwo.sponsers.view.SponsersAdapter;
import com.jnanatech.mochwo.utils.Constants;
import com.jnanatech.mochwo.utils.database.RealmController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    RealmController realmController;

    RecyclerView speakerSearchRecyclerView;
    RecyclerView sponsorSearchRecyclerView;

    SpeakerSearchAdapter speakerSearchAdapter;
    SponsersAdapter sponserSearchAdapter;

    //Search
    private SectionedRecyclerViewAdapter sectionAdapter;

    ArrayList<SpeakerModel> speakerModels = new ArrayList<>();
    ArrayList<SponserModel> sponsorsModels = new ArrayList<>();
    ArrayList<EventModel> eventModels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        realmController = RealmController.with(this);
        sectionAdapter = new SectionedRecyclerViewAdapter();

        speakerModels = realmController.getAllSpeakersList();
        sponsorsModels = realmController.getAllSponsors();
        eventModels = realmController.getAllEvents();


        bindActivity();
        initSpeaker();
        initSponsors();

        initSearch();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this instanceof AppCompatActivity) {
            AppCompatActivity activity = this;
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setTitle(R.string.nav_search);
            }
        }
    }

    private void initSearch() {
        String categories[] = new String[]{"Speakers", "Schedules", "Sponsors"};
        for (int i = 0; i < categories.length; i++) {
            List<String> contacts = getContactsWithLetter(categories[i]);

            if (contacts.size() > 0) {
                ContactsSection contactsSection = new ContactsSection(categories[i], contacts);
                sectionAdapter.addSection(contactsSection);
            }
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(sectionAdapter);
    }

    @Override
    public boolean onQueryTextChange(String query) {

        for (Section section : sectionAdapter.getCopyOfSectionsMap().values()) {
            if (section instanceof FilterableSection) {
                ((FilterableSection) section).filter(query);
            }
        }
        sectionAdapter.notifyDataSetChanged();

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<String> getContactsWithLetter(String category) {
        List<String> contacts = new ArrayList<>();

        switch (category) {
            case "Speakers":
                for (int i = 0; i < speakerModels.size(); i++)
                    contacts.add(speakerModels.get(i).getSpeakerName());
                break;
            case "Sponsors":
                for (int i = 0; i < sponsorsModels.size(); i++)
                    contacts.add(sponsorsModels.get(i).getTitle());
                break;
            case "Schedules":
                for (int i = 0; i < eventModels.size(); i++)
                    contacts.add(eventModels.get(i).getTopic());
                break;

        }
//        for (String contact : getResources().getStringArray(R.array.names)) {
//            if (contact.charAt(0) == letter) {
//                contacts.add(contact);
//            }
//        }

        return contacts;
    }

    private void initSponsors() {
        sponserSearchAdapter = new SponsersAdapter(this, realmController.getAllSponsors());
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        sponsorSearchRecyclerView.setLayoutManager(manager);
        sponsorSearchRecyclerView.setAdapter(sponserSearchAdapter);
    }

    private void initSpeaker() {
        speakerSearchAdapter = new SpeakerSearchAdapter(this, realmController.getAllSpeakersList());
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        speakerSearchRecyclerView.setLayoutManager(manager);
        speakerSearchRecyclerView.setAdapter(speakerSearchAdapter);

    }

    private void bindActivity() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        searchResultListView = (ListView) findViewById(R.id.lv_toolbarsearch);
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrays);
//        searchResultListView.setAdapter(adapter);

        speakerSearchRecyclerView = (RecyclerView) findViewById(R.id.speakerSearchRecyclerView);
        sponsorSearchRecyclerView = (RecyclerView) findViewById(R.id.sponsorSearchRecyclerView);

    }

    private class ContactsSection extends StatelessSection implements FilterableSection {

        final String title;
        final List<String> list;
        List<String> filteredList;

        ContactsSection(String title, List<String> list) {
            super(SectionParameters.builder()
                    .itemResourceId(R.layout.section_ex7_item)
                    .headerResourceId(R.layout.section_ex7_header)
                    .build());

            this.title = title;
            this.list = list;
            this.filteredList = new ArrayList<>(list);
        }

        @Override
        public int getContentItemsTotal() {
            return filteredList.size();
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;

            final String name = filteredList.get(position);
            itemHolder.tvItem.setText(name);

            if (title.equals("Speakers")) {
                itemHolder.imgItem.setImageResource(R.drawable.ic_speaker);
            } else if (title.equals("Sponsors")) {
                itemHolder.imgItem.setImageResource(R.drawable.ic_sponsers);

            } else if (title.equals("Schedules")) {
                itemHolder.imgItem.setImageResource(R.drawable.ic_event_note_black_24dp);

            }

            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    intentToCorrespondingField(title, name);
//                    Toast.makeText(SearchActivity.this,
//                            String.format("Clicked on position #%s of Section %s",
//                                    sectionAdapter.getPositionInSection(itemHolder.getAdapterPosition()),
//                                    title),
//                            Toast.LENGTH_SHORT).show();
//                    Log.d("position",itemHolder.getAdapterPosition() + "--1st");
//                    Log.d("position",itemHolder.getLayoutPosition() + "---2nd");
//                    Log.d("position",itemHolder.getOldPosition() + "---3rd");
//                    Toast.makeText(SearchActivity.this,
//                            String.format("Clicked on position #%s of Section %s",
//                                    sectionAdapter.getPositionInSection(itemHolder.getLayoutPosition()),
//                                    title),
//                            Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void intentToCorrespondingField(String category, String name) {
            Toast.makeText(SearchActivity.this, name, Toast.LENGTH_SHORT).show();
            switch (category) {
                case "Speakers":
                    SpeakerModel speakerModel = new SpeakerModel();
                    for (int i = 0; i < speakerModels.size(); i++) {
                        Log.d("itemCheck",name + " -- " + category + " -- "+ speakerModels.get(i).getSpeakerName());
                        if (name.equals(speakerModels.get(i).getSpeakerName())) {
//                            startIntent
                            speakerModel = speakerModels.get(i);
                            Intent intent = new Intent(SearchActivity.this, SpeakerDetailActivity.class);
                            intent.putExtra(Constants.speakerConstant, speakerModels.get(i).getSpeakerName());
                            startActivity(intent);
                            Toast.makeText(SearchActivity.this, "speaker", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case "Sponsors":
                    SponserModel sponserModel = new SponserModel();
                    for (int i = 0; i < sponsorsModels.size(); i++) {
                        if (name.equals(sponsorsModels.get(i).getTitle())) {
//                            startIntent
                            sponserModel = sponsorsModels.get(i);
                            Toast.makeText(SearchActivity.this, "sponsor", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                case "Schedules":
                    EventModel eventModel = new EventModel();
                    for (int i = 0; i < eventModels.size(); i++) {
                        if (name.equals(eventModels.get(i).getTopic())) {
//                            startIntent
                            eventModel = eventModels.get(i);

                            Intent in = new Intent(SearchActivity.this, ScheduleDetailActivity.class);

                            Bundle bundle = new Bundle();

                            bundle.putString(Constants.scheduleStartTimeConstant, eventModel.getStartTime());
                            bundle.putString(Constants.scheduleEndTimeConstant, eventModel.getEndTime());
                            bundle.putString(Constants.scheduleTopicConstant, eventModel.getTopic());
                            bundle.putString(Constants.scheduleDescriptionConstant, eventModel.getDescription());
                            bundle.putString(Constants.scheduleKeywordsConstant, eventModel.getKeywords());
                            bundle.putString(Constants.scheduleSpeakersConstant, eventModel.getSpeakers());
                            bundle.putString(Constants.scheduleIDConstant,eventModel.getId());
                            bundle.putString(Constants.scheduleScheduleConstant,eventModel.getScheduleName());
                            bundle.putString(Constants.scheduleSessionConstant,eventModel.getSessionName());
                            bundle.putBoolean(Constants.scheduleBookmarkConstant,eventModel.isBookmarked());

                            in.putExtras(bundle);
                            startActivity(in);
                            Toast.makeText(SearchActivity.this, "event", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
            }


        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {

            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.tvTitle.setText(title);

        }

        @Override
        public void filter(String query) {
            if (TextUtils.isEmpty(query)) {
                filteredList = new ArrayList<>(list);
                this.setVisible(true);
            } else {
                filteredList.clear();
                for (String value : list) {
                    if (value.toLowerCase(Locale.getDefault())
                            .contains(query.toLowerCase(Locale.getDefault()))) {
                        filteredList.add(value);
                    }
                }

                this.setVisible(!filteredList.isEmpty());
            }
        }
    }


    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;

        HeaderViewHolder(View view) {
            super(view);

            tvTitle = view.findViewById(R.id.tvTitle);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final ImageView imgItem;
        private final TextView tvItem;

        ItemViewHolder(View view) {
            super(view);

            rootView = view;
            imgItem = view.findViewById(R.id.imgItem);
            tvItem = view.findViewById(R.id.tvItem);
        }
    }

    interface FilterableSection {
        void filter(String query);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem mSearchmenuItem = menu.findItem(R.id.menu_search);

        SearchView searchView = (SearchView) mSearchmenuItem.getActionView();
        searchView.setQueryHint("enter Text");

        searchView.setOnQueryTextListener(this);

        return true;
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
