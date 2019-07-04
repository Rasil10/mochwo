package com.jnanatech.mochwo.main.view;

import com.jnanatech.mochwo.main.model.Conference;
import com.jnanatech.mochwo.notification.model.NotificationModel;
import com.jnanatech.mochwo.sponsers.model.SponserModel;

import java.util.ArrayList;

public interface MainView {

    void getEvent(Conference conference);
    void getRemainingTime(long remainingTime);
    void getNotificationChangeSize(int changeInSize,int newSize, boolean changed, NotificationModel notificationModel);

}
