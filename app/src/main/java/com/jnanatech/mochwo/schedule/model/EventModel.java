package com.jnanatech.mochwo.schedule.model;


import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class EventModel extends RealmObject implements Serializable {

    @PrimaryKey
    private String id;

    private String startTime;
    private String endTime;
    private String chairPersonName;
    private String chairPersonDetail;

    private String eventTitle;
    private String eventSpeaker;
    private String speakerDetail;
    private String abstractDetail;
    private String keywords;


    private String scheduleName;
    private String sessionTitle;

    private boolean bookmarked;

    public EventModel() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getChairPersonName() {
        return chairPersonName;
    }

    public void setChairPersonName(String chairPersonName) {
        this.chairPersonName = chairPersonName;
    }

    public String getChairPersonDetail() {
        return chairPersonDetail;
    }

    public void setChairPersonDetail(String chairPersonDetail) {
        this.chairPersonDetail = chairPersonDetail;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventSpeaker() {
        return eventSpeaker;
    }

    public void setEventSpeaker(String eventSpeaker) {
        this.eventSpeaker = eventSpeaker;
    }

    public String getSpeakerDetail() {
        return speakerDetail;
    }

    public void setSpeakerDetail(String speakerDetail) {
        this.speakerDetail = speakerDetail;
    }

    public String getAbstractDetail() {
        return abstractDetail;
    }

    public void setAbstractDetail(String abstractDetail) {
        this.abstractDetail = abstractDetail;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public String getSessionTitle() {
        return sessionTitle;
    }

    public void setSessionTitle(String sessionTitle) {
        this.sessionTitle = sessionTitle;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }
}
