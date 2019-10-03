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
    ImageView coOrganizerImageView;
    ImageView partnerIV1, partnerIV2, partnerIV3, partnerIV4;



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
        loadImageinto(hostImageVIew,Constants.kiasImageURL);

        coOrganizerRecyclerView = (RecyclerView) findViewById(R.id.coOrganizerRecyclerView);
        partnersRecyclerView = (RecyclerView) findViewById(R.id.partnersRecyclerView);
        sponsersRecyclerView = (RecyclerView) findViewById(R.id.sponsersRecyclerView);

        coOrganizerImageView = (ImageView) findViewById(R.id.coOrganizerImageView);
        loadImageinto(coOrganizerImageView,"https://conference.kias.org.np/wp-content/uploads/2019/07/KCRE-logo.png");

        partnerIV1 = (ImageView) findViewById(R.id.partnersImageView1);
        loadImageinto(partnerIV1,"http://conference.kias.org.np/wp-content/uploads/2019/08/S4W-Nepal_Logo-1-e1570089633822.png");

        partnerIV2 = (ImageView) findViewById(R.id.partnersImageView2);
        loadImageinto(partnerIV2,"http://conference.kias.org.np/wp-content/uploads/2019/07/e9a340efbcb9b4971de76be2005a1e3d.png.png");

        partnerIV3 = (ImageView) findViewById(R.id.partnersImageView3);
        loadImageinto(partnerIV3,"http://conference.kias.org.np/wp-content/uploads/2019/08/CDHM-TU-logo-small.png");

        partnerIV4 = (ImageView) findViewById(R.id.partnersImageView4);
        loadImageinto(partnerIV4,"http://conference.kias.org.np/wp-content/uploads/2019/10/SCB-LOGO.jpg");


    }

    private void loadImageinto(ImageView coOrganizerImageView, String imageUrl) {
        Picasso.get().load(imageUrl)
                .placeholder(R.drawable.ic_terrain_black_24dp)
                .error(R.drawable.ic_terrain_black_24dp)
                .into(coOrganizerImageView);
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
