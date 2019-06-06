package com.jnanatech.mochwo.notification.model;

import io.realm.RealmObject;

public class NotificationModel extends RealmObject {

    String category;
    String description;
    String date;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
