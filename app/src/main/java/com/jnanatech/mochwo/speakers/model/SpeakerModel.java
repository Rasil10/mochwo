package com.jnanatech.mochwo.speakers.model;


import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SpeakerModel extends RealmObject implements Serializable {

    @PrimaryKey
    private int id;

    private String date;

    private String speakerName;

    private String currentPosition;

    private String shortInfo;

    private String abstractt;

    private String keywords;

    private String topic;

    private String email;

    private String imageUrl;



//    private String facebookUrl;
//
//    private String twitterUrl;
//
//    private String linkedInUrl;
//
//    private String instagramUrl;
//
//    private String youtubeUrl;

//    private String personalSite;

//    private String quote;

//    private String education1;
//
//    private String education2;
//
//    private String education3;
//
//    private String education4;
//
//    private String experience1;
//
//    private String experience2;
//
//    private String experience3;
//
//    private String experience4;

//    private String phone;

//    private String address;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getShortInfo() {
        return shortInfo;
    }

    public void setShortInfo(String shortInfo) {
        this.shortInfo = shortInfo;
    }

    public String getAbstractt() {
        return abstractt;
    }

    public void setAbstractt(String abstractt) {
        this.abstractt = abstractt;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
