package com.jnanatech.mochwo.schedule.view;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.schedule.model.EventModel;
import com.jnanatech.mochwo.speakers.model.SpeakerModel;
import com.jnanatech.mochwo.utils.database.RealmController;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayTwoFragment extends Fragment {
    private RealmController realmController;

    private ArrayList<EventModel> dayTwoEvents = new ArrayList<>();
    private ArrayList<SpeakerModel> speakerModels = new ArrayList<>();

    RecyclerView eventRecyclerView;

    public DayTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_day_two, container, false);

        realmController = RealmController.with((Activity) getActivity());

        dayTwoEvents = realmController.getSecondDayEvents();
        speakerModels = realmController.getAllSpeakersList();

        eventRecyclerView = (RecyclerView) rootView.findViewById(R.id.eventOneRecylerView);

        EventAdapter eventAdapter = new EventAdapter(getActivity(), dayTwoEvents, speakerModels);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        eventRecyclerView.setLayoutManager(linearLayoutManager);
        eventRecyclerView.setAdapter(eventAdapter);

        return rootView;
    }

}
