package com.jnanatech.mochwo.utils.database;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.jnanatech.mochwo.News.model.NewsModel;
import com.jnanatech.mochwo.bookmark.model.BookmarkModel;
import com.jnanatech.mochwo.main.model.Conference;
import com.jnanatech.mochwo.notification.model.NotificationModel;
import com.jnanatech.mochwo.schedule.model.EventModel;
import com.jnanatech.mochwo.speakers.model.SpeakerModel;
import com.jnanatech.mochwo.sponsers.model.SponserModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmController {
    public static RealmController instance;
    private final Realm realm;


    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    public void refresh() {
        realm.refresh();
    }

    //Speakers database actions
    public void addAllSpeakers(ArrayList<SpeakerModel> allSpeakers) {

        realm.beginTransaction();
        realm.copyToRealm(allSpeakers);
        realm.commitTransaction();

    }

    public ArrayList<SpeakerModel> getAllSpeakersList() {
        ArrayList<SpeakerModel> list = new ArrayList<>();
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<SpeakerModel> results = realm
                    .where(SpeakerModel.class)
                    .findAll();
            list.addAll(realm.copyFromRealm(results));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return list;
    }

    public SpeakerModel getSpeakerByName(String name) {
        ArrayList<SpeakerModel> list = new ArrayList<>();
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<SpeakerModel> results = realm
                    .where(SpeakerModel.class)
                    .findAll();
            for (int i = 0; i < results.size(); i++) {
                if (results.get(i).getSpeakerName().equalsIgnoreCase(name)) {
                    list.add(results.get(i));
                    break;
                }
            }
        } finally {
            if (realm != null) {
                realm.close();
            }
        }

        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public int getSpeakerListSize() {
        ArrayList<SpeakerModel> list = new ArrayList<>();
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<SpeakerModel> results = realm
                    .where(SpeakerModel.class)
                    .findAll();
            list.addAll(realm.copyFromRealm(results));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return list.size();
    }

    public void clearAllSpeakerList() {
        realm.beginTransaction();
        realm.delete(SpeakerModel.class);
//        realm.delete(CompanyNameModel.class);
        realm.commitTransaction();
    }

    //notification database actions
    public void addNotification(NotificationModel notificationModel) {
        realm.beginTransaction();
        realm.copyToRealm(notificationModel);
        realm.commitTransaction();
    }

    public ArrayList<NotificationModel> getAllNotification() {
        ArrayList<NotificationModel> list = new ArrayList<>();
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<NotificationModel> results = realm
                    .where(NotificationModel.class)
                    .findAll();
            list.addAll(realm.copyFromRealm(results));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return list;
    }
    public void addAllNotification(ArrayList<NotificationModel> allNotification) {

        realm.beginTransaction();
        realm.copyToRealm(allNotification);
        realm.commitTransaction();

    }
    public void clearAllNotification() {
        realm.beginTransaction();
        realm.delete(NotificationModel.class);
//        realm.delete(CompanyNameModel.class);
        realm.commitTransaction();
    }
    //sponsors database actions
    public void addSponsors(SponserModel sponserModel) {
        realm.beginTransaction();
        realm.copyToRealm(sponserModel);
        realm.commitTransaction();
    }
    public void addAllSponsors(ArrayList<SponserModel> allSponsors) {

        realm.beginTransaction();
        realm.copyToRealm(allSponsors);
        realm.commitTransaction();

    }
    public ArrayList<SponserModel> getAllSponsors() {
        ArrayList<SponserModel> list = new ArrayList<>();
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<SponserModel> results = realm
                    .where(SponserModel.class)
                    .findAll();
            list.addAll(realm.copyFromRealm(results));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return list;
    }

    public void clearAllSponsorList() {
        realm.beginTransaction();
        realm.delete(SponserModel.class);
//        realm.delete(CompanyNameModel.class);
        realm.commitTransaction();
    }

    //    schedule database Actions
    public void addEvent(EventModel eventModel) {
        realm.beginTransaction();
        realm.copyToRealm(eventModel);
        realm.commitTransaction();
    }

    public ArrayList<EventModel> getAllEvents() {
        ArrayList<EventModel> list = new ArrayList<>();
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<EventModel> results = realm
                    .where(EventModel.class)
                    .findAll();
            list.addAll(realm.copyFromRealm(results));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return list;
    }

    public ArrayList<EventModel> getEventBySessionName(String sessionName) {
        ArrayList<EventModel> list = new ArrayList<>();
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<EventModel> results = realm
                    .where(EventModel.class)
                    .findAll();
            for (int i = 0; i < results.size(); i++) {
                if (results.get(i).getSessionTitle().equalsIgnoreCase(sessionName)) {
                    list.add(results.get(i));
                }
            }
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return list;
    }

    public EventModel getEventByEventTitle(String title) {
        ArrayList<EventModel> list = new ArrayList<>();
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<EventModel> results = realm
                    .where(EventModel.class)
                    .findAll();
            for (int i = 0; i < results.size(); i++) {
                if (results.get(i).getEventTitle().equalsIgnoreCase(title)) {
                    list.add(results.get(i));
                }
            }
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return list.get(0);
    }
    public ArrayList<EventModel> getFirstDayEvents() {
        ArrayList<EventModel> list = new ArrayList<>();
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<EventModel> results = realm
                    .where(EventModel.class)
                    .findAll();
            for (int i = 0; i < results.size(); i++) {
                if (results.get(i).getScheduleName().equalsIgnoreCase("d1-schedule")) {
                    list.add(results.get(i));
                }
            }
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return list;
    }

    public ArrayList<EventModel> getSecondDayEvents() {
        ArrayList<EventModel> list = new ArrayList<>();
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<EventModel> results = realm
                    .where(EventModel.class)
                    .findAll();
            for (int i = 0; i < results.size(); i++) {
                if (results.get(i).getScheduleName().equalsIgnoreCase("d2-schedule")) {
                    list.add(results.get(i));
                }
            }
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return list;
    }

    public void clearAllEvents() {
        realm.beginTransaction();
        realm.delete(EventModel.class);
//        realm.delete(CompanyNameModel.class);
        realm.commitTransaction();
    }

    public boolean getBookmarkStatus(EventModel eventModel) {
        EventModel object = realm.where(EventModel.class)
                .equalTo("id", eventModel.getId())
                .findFirst();
        if (object == null) {
            return false;
        } else {
            return object.isBookmarked();
        }
    }

    public void updateEvent(EventModel eventModel) {
        EventModel object = realm.where(EventModel.class)
                .equalTo("id", eventModel.getId())
                .findFirst();
        Log.d("bookmarkStatus", object.isBookmarked() + "///");
        realm.beginTransaction();
        if (eventModel == null) {

            // set the fields here
        } else {
            object.setBookmarked(true);
        }
        Log.d("bookmarkStatus", object.isBookmarked() + "///");

        realm.commitTransaction();
    }

    //bookmark database actions
    public void addBookmark(BookmarkModel bookmarkModel) {
        realm.beginTransaction();
        realm.copyToRealm(bookmarkModel);
        realm.commitTransaction();
    }

    public ArrayList<BookmarkModel> getBookmarks() {
        ArrayList<BookmarkModel> list = new ArrayList<>();
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<BookmarkModel> results = realm
                    .where(BookmarkModel.class)
                    .findAll();
            list.addAll(realm.copyFromRealm(results));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return list;
    }

    public void clearBookmarks() {
        realm.beginTransaction();
        realm.delete(BookmarkModel.class);
//        realm.delete(CompanyNameModel.class);
        realm.commitTransaction();
    }

    //Conference database actions
    public void addConference(Conference conference) {

        realm.beginTransaction();
        realm.copyToRealm(conference);
        realm.commitTransaction();

    }

    public Conference getConference() {
        ArrayList<Conference> list = new ArrayList<>();
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<Conference> results = realm
                    .where(Conference.class)
                    .findAll();
            list.addAll(realm.copyFromRealm(results));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return list.get(0);
    }

    public void clearConference() {
        realm.beginTransaction();
        realm.delete(Conference.class);
//        realm.delete(CompanyNameModel.class);
        realm.commitTransaction();
    }

    //news database actions
    public void addAllNews(ArrayList<NewsModel> allNews) {

        realm.beginTransaction();
        realm.copyToRealm(allNews);
        realm.commitTransaction();

    }

    public ArrayList<NewsModel> getNews() {
        ArrayList<NewsModel> list = new ArrayList<>();
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<NewsModel> results = realm
                    .where(NewsModel.class)
                    .findAll();
            list.addAll(realm.copyFromRealm(results));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return list;
    }

    public void clearNews() {
        realm.beginTransaction();
        realm.delete(NewsModel.class);
//        realm.delete(CompanyNameModel.class);
        realm.commitTransaction();
    }
}
