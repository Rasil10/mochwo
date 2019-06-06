package com.jnanatech.mochwo.bookmark.model;

import io.realm.Realm;
import io.realm.RealmObject;

public class BookmarkModel extends RealmObject {
    String id;

    public BookmarkModel() {
    }

    public BookmarkModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
