package com.jnanatech.mochwo.notification.model;

import io.realm.RealmObject;

public class NotificationModel extends RealmObject {
    private String title;
    private String detail;
    private  String category;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
