package com.jnanatech.mochwo.schedule.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.schedule.model.EventModel;
import com.jnanatech.mochwo.schedule.presenter.DayOneImplementor;
import com.jnanatech.mochwo.schedule.presenter.DayOnePresenter;

import java.util.ArrayList;

public class DayOneFragment extends Fragment implements DayOneView {

    DayOnePresenter dayOnePresenter;


    private ArrayList<EventModel> dayOneEvents = new ArrayList<>();
    private RecyclerView eventRecyclerView;
    private ExpandableEventAdapter expandableEventAdapter;


    EditText searchEditText;

    public DayOneFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dayOnePresenter = new DayOneImplementor(getActivity(), this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_day_one, container, false);

        dayOnePresenter.setDayOneEvents();

        eventRecyclerView = (RecyclerView) rootView.findViewById(R.id.eventOneRecylerView);
        searchEditText = (EditText) rootView.findViewById(R.id.searchEditText);


        expandableEventAdapter = new ExpandableEventAdapter(getActivity(), dayOneEvents);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        eventRecyclerView.setLayoutManager(linearLayoutManager);
        eventRecyclerView.setAdapter(expandableEventAdapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    performSearch(v.getText().toString());
//                    return true;
//                }
//                return false;
//            }
//        });
        return rootView;
    }

    private void performSearch(String query) {
        ArrayList<EventModel> newModels = new ArrayList<>();
        for (int i = 0; i < dayOneEvents.size(); i++) {
            EventModel model = dayOneEvents.get(i);



            if (
                    model.getEventSpeaker().toLowerCase().contains(query) ||
                    model.getEventTitle().toLowerCase().contains(query) ||
                    model.getSessionTitle().toLowerCase().contains(query) ||
                    model.getChairPersonName().toLowerCase().contains(query) ||
                    model.getKeywords().toLowerCase().contains(query)
            ) {
                newModels.add(model);
            }
        }

        if (newModels.size() == 0) {
            Toast.makeText(getActivity(), "No Matching Event Found.", Toast.LENGTH_SHORT).show();
        } else {
            expandableEventAdapter = new ExpandableEventAdapter(getActivity(), newModels);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

            eventRecyclerView.setLayoutManager(linearLayoutManager);
            eventRecyclerView.setAdapter(expandableEventAdapter);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    @Override
    public void getDayOneEvents(ArrayList<EventModel> dayOneEvents) {
        this.dayOneEvents = dayOneEvents;
        if(dayOneEvents.size()==0){
            Toast.makeText(getActivity(), "No Schedule Found at this time.", Toast.LENGTH_SHORT).show();
        }
    }


}

