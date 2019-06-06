package com.jnanatech.mochwo.speakers.model;


import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SpeakerModel extends RealmObject implements Serializable {

    @PrimaryKey
    private int id;

    private String date;

    private String facebookUrl;

    private String twitterUrl;

    private String linkedInUrl;

    private String instagramUrl;

    private String youtubeUrl;

    private String speakerName;

    private String currentPosition;

    private String shortInfo;

    private String personalSite;

    private String description;

    private String quote;

    private String education1;

    private String education2;

    private String education3;

    private String education4;

    private String experience1;

    private String experience2;

    private String experience3;

    private String experience4;

    private String phone;

    private String email;

    private String address;

    private int featuredMediaId;

    private String featureMediaLink;

    public String getFeatureMediaLink() {
        return featureMediaLink;
    }

    public void setFeatureMediaLink(String featureMediaLink) {
        this.featureMediaLink = featureMediaLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getLinkedInUrl() {
        return linkedInUrl;
    }

    public void setLinkedInUrl(String linkedInUrl) {
        this.linkedInUrl = linkedInUrl;
    }

    public String getInstagramUrl() {
        return instagramUrl;
    }

    public void setInstagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
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

    public String getPersonalSite() {
        return personalSite;
    }

    public void setPersonalSite(String personalSite) {
        this.personalSite = personalSite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getEducation1() {
        return education1;
    }

    public void setEducation1(String education1) {
        this.education1 = education1;
    }

    public String getEducation2() {
        return education2;
    }

    public void setEducation2(String education2) {
        this.education2 = education2;
    }

    public String getEducation3() {
        return education3;
    }

    public void setEducation3(String education3) {
        this.education3 = education3;
    }

    public String getEducation4() {
        return education4;
    }

    public void setEducation4(String education4) {
        this.education4 = education4;
    }

    public String getExperience1() {
        return experience1;
    }

    public void setExperience1(String experience1) {
        this.experience1 = experience1;
    }

    public String getExperience2() {
        return experience2;
    }

    public void setExperience2(String experience2) {
        this.experience2 = experience2;
    }

    public String getExperience3() {
        return experience3;
    }

    public void setExperience3(String experience3) {
        this.experience3 = experience3;
    }

    public String getExperience4() {
        return experience4;
    }

    public void setExperience4(String experience4) {
        this.experience4 = experience4;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFeaturedMediaId() {
        return featuredMediaId;
    }

    public void setFeaturedMediaId(int featuredMediaId) {
        this.featuredMediaId = featuredMediaId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
