package com.jnanatech.mochwo.sponsers.view;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.sponsers.model.SponserModel;
import com.jnanatech.mochwo.utils.Constants;
import com.jnanatech.mochwo.utils.database.RealmController;

import java.util.ArrayList;

public class SponserActivity extends AppCompatActivity {

    RecyclerView sponsersRecyclerView;
    ArrayList<SponserModel> sponserModels = new ArrayList<>();
    private RealmController realmController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponser);

        realmController = RealmController.with(this);


        if (getIntent() != null)
            sponserModels = (ArrayList<SponserModel>) getIntent().getSerializableExtra(Constants.sponsersConstant);
        else
            sponserModels = realmController.getAllSponsors();


        bindActivity();
        initRecyclerView();

    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        SponsersAdapter sponsersAdapter = new SponsersAdapter(this, sponserModels);

        sponsersRecyclerView.setLayoutManager(gridLayoutManager);
        sponsersRecyclerView.setAdapter(sponsersAdapter);
    }

    private void bindActivity() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
