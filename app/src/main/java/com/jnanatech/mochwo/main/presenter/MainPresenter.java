package com.jnanatech.mochwo.main.presenter;

public interface MainPresenter {

    void setConference();
    void setSchedule1();
    void setSchedule2();
    void setRemainingTime();
    void getSpeakers();
    void checkSpeakerSize();
    void getSponsers();

    void showLoadingDialog();
    void dismissLoadingDialog();
}
