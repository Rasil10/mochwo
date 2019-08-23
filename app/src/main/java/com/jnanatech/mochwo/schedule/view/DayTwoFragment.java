package com.jnanatech.mochwo.schedule.view;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class DayTwoFragment extends Fragment implements DayOneView {
    DayOnePresenter dayOnePresenter;


    private ArrayList<EventModel> dayTwoEvents = new ArrayList<>();
    private RecyclerView eventRecyclerView;
    private ExpandableEventAdapter expandableEventAdapter;


    EditText searchEditText;

    public DayTwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dayOnePresenter = new DayOneImplementor(getActivity(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_day_two, container, false);

        dayOnePresenter.setDayTwoEvents();

        eventRecyclerView = (RecyclerView) rootView.findViewById(R.id.eventOneRecylerView);
        searchEditText = (EditText) rootView.findViewById(R.id.searchEditText);

        expandableEventAdapter = new ExpandableEventAdapter(getActivity(), dayTwoEvents);
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

        return rootView;
    }

    private void performSearch(String query) {
        ArrayList<EventModel> newModels = new ArrayList<>();
        for (int i = 0; i < dayTwoEvents.size(); i++) {
            EventModel model = dayTwoEvents.get(i);


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
    public void getDayOneEvents(ArrayList<EventModel> dayTwoEvents) {
        this.dayTwoEvents = dayTwoEvents;
        if(dayTwoEvents.size()==0){
            Toast.makeText(getActivity(), "No Schedule Found at this time.", Toast.LENGTH_SHORT).show();

        }

    }


}
