package com.jnanatech.mochwo.schedule.presenter;

import android.app.Activity;
import android.content.Context;

import com.jnanatech.mochwo.schedule.model.EventModel;
import com.jnanatech.mochwo.schedule.view.DayOneView;
import com.jnanatech.mochwo.utils.database.RealmController;

import java.util.ArrayList;

import io.realm.Realm;

public class DayOneImplementor implements DayOnePresenter {
    Context context;
    DayOneView dayOneView;

    RealmController realmController;
    ArrayList<EventModel> firstDayEvents;
    ArrayList<EventModel> secondDayEvents;

    public DayOneImplementor(Context context, DayOneView dayOneView) {
        this.context = context;
        this.dayOneView = dayOneView;
        realmController = RealmController.with((Activity)context);
    }

    @Override
    public void setDayOneEvents() {

        firstDayEvents = realmController.getFirstDayEvents();
        dayOneView.getDayOneEvents(firstDayEvents);

    }

    @Override
    public void setDayTwoEvents() {
        secondDayEvents = realmController.getSecondDayEvents();
        dayOneView.getDayOneEvents(secondDayEvents);
    }
}
