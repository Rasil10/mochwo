package com.jnanatech.mochwo.sponsers.view;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.sponsers.model.SponserModel;
import com.jnanatech.mochwo.utils.Constants;
import com.jnanatech.mochwo.utils.database.RealmController;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SponserActivity extends AppCompatActivity {

    RecyclerView sponsersRecyclerView;
    RecyclerView partnersRecyclerView;
    RecyclerView coOrganizerRecyclerView;
    ArrayList<SponserModel> allSponsorsList = new ArrayList<>();
    ArrayList<SponserModel> coOrgzniersList = new ArrayList<>();
    ArrayList<SponserModel> partnersList = new ArrayList<>();
    ArrayList<SponserModel> sponsersList = new ArrayList<>();
    private RealmController realmController;

    ImageView hostImageVIew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponser);

        realmController = RealmController.with(this);
        allSponsorsList = realmController.getAllSponsors();

        for (int i = 0; i < allSponsorsList.size(); i++) {
            if (allSponsorsList.get(i).getCategory().equals("Sponsors"))
                sponsersList.add(allSponsorsList.get(i));
            else if (allSponsorsList.get(i).getCategory().equals("Coorganizers"))
                coOrgzniersList.add(allSponsorsList.get(i));
            else if (allSponsorsList.get(i).getCategory().equals("Partners"))
                partnersList.add(allSponsorsList.get(i));
        }

        bindActivity();
        initRecyclerView();

    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        sponsersRecyclerView.setLayoutManager(layoutManager1);
        partnersRecyclerView.setLayoutManager(layoutManager2);
        coOrganizerRecyclerView.setLayoutManager(layoutManager3);

        SponsersAdapter sponsersAdapter = new SponsersAdapter(this, sponsersList);
        sponsersRecyclerView.setAdapter(sponsersAdapter);

        SponsersAdapter partnersAdapter = new SponsersAdapter(this, partnersList);
        partnersRecyclerView.setAdapter(partnersAdapter);

        SponsersAdapter coorganizersAdapter = new SponsersAdapter(this, coOrgzniersList);
        coOrganizerRecyclerView.setAdapter(coorganizersAdapter);


    }

    private void bindActivity() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        hostImageVIew = (ImageView) findViewById(R.id.hostImageView);
        Picasso.get().load(Constants.kiasImageURL)
                .placeholder(R.drawable.ic_terrain_black_24dp)
                .error(R.drawable.ic_terrain_black_24dp)
                .into(hostImageVIew);

        coOrganizerRecyclerView = (RecyclerView) findViewById(R.id.coOrganizerRecyclerView);
        partnersRecyclerView = (RecyclerView) findViewById(R.id.partnersRecyclerView);
        sponsersRecyclerView = (RecyclerView) findViewById(R.id.sponsersRecyclerView);

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
