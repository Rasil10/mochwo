package com.jnanatech.mochwo.aboutUs.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.github.aakira.expandablelayout.ExpandableWeightLayout;
import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.aboutUs.model.MemberModel;
import com.jnanatech.mochwo.aboutUs.presenter.AboutUsImplementor;
import com.jnanatech.mochwo.aboutUs.presenter.AboutUsPresenter;

import java.util.ArrayList;

public class AboutUsActivity extends AppCompatActivity  implements AboutUsView, View.OnClickListener{

    private RecyclerView memberRecyclerView;
    private ExpandableWeightLayout organizingCommitteeExpandableLayout;
    private Button organizingCommiteeButton;


    AboutUsPresenter aboutUsPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        bindActivity();

        aboutUsPresenter = new AboutUsImplementor(this,AboutUsActivity.this);
        aboutUsPresenter.setMembers();

    }

    private void bindActivity() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        organizingCommiteeButton = (Button) findViewById(R.id.organizingCommitteeButton);
        organizingCommiteeButton.setOnClickListener(this);

        organizingCommitteeExpandableLayout = (ExpandableWeightLayout) findViewById(R.id.organizingCommitteeExpandableLayout);
//        organizingCommitteeExpandableLayout.toggle();

        memberRecyclerView = (RecyclerView) findViewById(R.id.membersRecyclerView);

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

    @Override
    public void getMembers(ArrayList<MemberModel> memberModels) {

        GridLayoutManager manager = new GridLayoutManager(this,2);
        memberRecyclerView.setLayoutManager(manager);

        MemberAdapter memberAdapter = new MemberAdapter(this,memberModels);
        memberRecyclerView.setAdapter(memberAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.organizingCommitteeButton:
                organizingCommitteeExpandableLayout.toggle();
                break;
        }

    }
}