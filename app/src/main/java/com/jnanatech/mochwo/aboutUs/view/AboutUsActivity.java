package com.jnanatech.mochwo.aboutUs.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableWeightLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.aboutUs.model.MemberModel;
import com.jnanatech.mochwo.aboutUs.presenter.AboutUsImplementor;
import com.jnanatech.mochwo.aboutUs.presenter.AboutUsPresenter;

import java.util.ArrayList;

public class AboutUsActivity extends AppCompatActivity  implements AboutUsView, View.OnClickListener{

    private RecyclerView organizingCommiteeRecyclerView;
    private RecyclerView scientificCommiteeRecyclerView;
    private ExpandableWeightLayout organizingCommitteeExpandableLayout;
    private ExpandableWeightLayout scientificCommitteeExpandableLayout;
    private Button organizingCommiteeButton;
    private Button scientificCommiteeButton;
    private AppBarLayout mAppBarLayout;
    private TextView mTitleTextView;
    private Button backButton;

    AboutUsPresenter aboutUsPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        bindActivity();

        aboutUsPresenter = new AboutUsImplementor(this,AboutUsActivity.this);
        aboutUsPresenter.setOrganizingCommiteeMembers();
        aboutUsPresenter.setScientificCommiteeMembers();

    }

    private void bindActivity() {

        mAppBarLayout = (AppBarLayout)findViewById(R.id.app_bar);

        mTitleTextView = (TextView)findViewById(R.id.mTitleTextView);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                mTitleTextView.setAlpha(Math.abs(verticalOffset / (float)
                        appBarLayout.getTotalScrollRange()));
            }
        });

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        organizingCommiteeButton = (Button) findViewById(R.id.organizingCommitteeButton);
        organizingCommiteeButton.setOnClickListener(this);

        scientificCommiteeButton = (Button) findViewById(R.id.scientificCommitteeButton);
        scientificCommiteeButton.setOnClickListener(this);

        organizingCommitteeExpandableLayout = (ExpandableWeightLayout) findViewById(R.id.organizingCommitteeExpandableLayout);
        scientificCommitteeExpandableLayout = (ExpandableWeightLayout) findViewById(R.id.scientificCommitteeExpandableLayout);
//        organizingCommitteeExpandableLayout.toggle();

        organizingCommiteeRecyclerView = (RecyclerView) findViewById(R.id.membersRecyclerView);
        scientificCommiteeRecyclerView = (RecyclerView) findViewById(R.id.scientificCommitteeRecyclerView);

    }
    @Override
    public void setOrganizingCommiteeMembers(ArrayList<MemberModel> organizingCommiteeMembers) {
        GridLayoutManager manager = new GridLayoutManager(this,3);
        organizingCommiteeRecyclerView.setLayoutManager(manager);

        MemberAdapter organizingComiteeAdapter = new MemberAdapter(this,organizingCommiteeMembers);
        organizingCommiteeRecyclerView.setAdapter(organizingComiteeAdapter);
    }

    @Override
    public void setScientificCommiteeMembers(ArrayList<MemberModel> scientificCommiteeMembers) {

        GridLayoutManager manager = new GridLayoutManager(this,3);
        scientificCommiteeRecyclerView.setLayoutManager(manager);

        MemberAdapter scientificCommiteeAdapter = new MemberAdapter(this,scientificCommiteeMembers);
        scientificCommiteeRecyclerView.setAdapter(scientificCommiteeAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.organizingCommitteeButton:
                organizingCommitteeExpandableLayout.toggle();
                if(scientificCommitteeExpandableLayout.isExpanded()){
                    scientificCommitteeExpandableLayout.collapse();
                }
                break;

            case R.id.scientificCommitteeButton:
                scientificCommitteeExpandableLayout.toggle();
                if(organizingCommitteeExpandableLayout.isExpanded()){
                    organizingCommitteeExpandableLayout.collapse();
                }
                break;

            case R.id.backButton:
                onBackPressed();
                break;
        }

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