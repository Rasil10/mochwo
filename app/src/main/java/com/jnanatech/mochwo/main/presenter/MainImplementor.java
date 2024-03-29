package com.jnanatech.mochwo.main.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jnanatech.mochwo.News.model.NewsModel;
import com.jnanatech.mochwo.main.model.Conference;
import com.jnanatech.mochwo.main.view.MainView;
import com.jnanatech.mochwo.notification.model.NotificationModel;
import com.jnanatech.mochwo.schedule.model.EventModel;
import com.jnanatech.mochwo.speakers.model.SpeakerModel;
import com.jnanatech.mochwo.sponsers.model.SponserModel;
import com.jnanatech.mochwo.utils.Constants;
import com.jnanatech.mochwo.utils.Network;
import com.jnanatech.mochwo.utils.NoInternetDialog;
import com.jnanatech.mochwo.utils.database.RealmController;
import com.jnanatech.mochwo.utils.sharedPreference.NotificationSizeSharedPrefHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;

public class MainImplementor implements MainPresenter {


    private Context context;
    private MainView mainView;
    private long remainingTime;

    private Conference conference = new Conference();

    private ArrayList<SpeakerModel> speakerModels = new ArrayList<>();
    private ArrayList<SponserModel> sponserModels = new ArrayList<>();

    private ArrayList<EventModel> dayOneEvents = new ArrayList<>();
    private ArrayList<EventModel> dayTwoEvents = new ArrayList<>();
    private ArrayList<EventModel> temporaryEvents = new ArrayList<>();

    private ArrayList<NotificationModel> notifications = new ArrayList<>();
    private ArrayList<NewsModel> news = new ArrayList<>();

    private RequestQueue requestQueue;
    private RealmController realmController;
    private NotificationSizeSharedPrefHelper notificationSharedPref;


    ProgressDialog progressDialog;

    public MainImplementor(Context context, MainView mainView) {
        this.context = context;
        this.mainView = mainView;
        requestQueue = Volley.newRequestQueue(context);
        Realm.init(context);
        realmController = RealmController.with((Activity) context);
        notificationSharedPref = new NotificationSizeSharedPrefHelper(context);
        progressDialog = new ProgressDialog(context);
    }

    @Override
    public void setConference() {
        realmController.clearConference();
        conference.setImageUrl("https://www.adlibbing.org/wp-content/uploads/2018/04/confrence-room.jpg");
        conference.setTitle("4th International Conference \n MOUNTAINS IN THE CHANGING WORLD");
        conference.setStartDate("2019-10-18 10:00:00");
        conference.setEndDate("2019-10-19 19:00:00");
        conference.setLocation("KIAS Building");
        conference.setQuote("This is the quote for the mochwo 2019. conference.");

        realmController.addConference(conference);
        mainView.getEvent(conference);
    }

    public Boolean getBookmarkStatus(String id) {
        boolean flag = false;
        for (int i = 0; i < temporaryEvents.size(); i++) {
            if (temporaryEvents.get(i).getId().equals(id)) {
                if (temporaryEvents.get(i).isBookmarked()) {
                    flag = true;
                    break;
                } else {
                    flag = false;
                }
            }
        }
        return flag;
    }

    @Override
    public void setSchedule1() {
        if (Network.isNetworkAvailable(context)) {
            JsonObjectRequest
                    jsonObjectRequest
                    = new JsonObjectRequest(
                    Request.Method.GET,
                    Constants.URLSchedule1,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONObject acfObject = response.getJSONObject("acf");
                                JSONArray scheduleD1Array = acfObject.getJSONArray("d1-schedule");


                                for (int i = 0; i < scheduleD1Array.length(); i++) {

                                    JSONObject schedulObject = scheduleD1Array.getJSONObject(i);

                                    String startTime = schedulObject.getString("start-time");
                                    String endTime = schedulObject.getString("end-time");

                                    String sessionTitle = schedulObject.getString("session-title");
                                    String chairPerson = schedulObject.getString("chair-person");
                                    String chairPersonDetail = schedulObject.getString("chair-detail");

                                    JSONArray eventArray = schedulObject.getJSONArray("sub-session");
                                    for (int ss = 0; ss < eventArray.length(); ss++) {

                                        EventModel event = new EventModel();

                                        JSONObject sessionObject = eventArray.getJSONObject(ss);

                                        event.setEventTitle(sessionObject.getString("title"));
                                        event.setEventSpeaker(sessionObject.getString("speakers"));
                                        event.setSpeakerDetail(sessionObject.getString("speakers-detail"));
                                        event.setAbstractDetail(sessionObject.getString("abstract"));
                                        event.setKeywords(sessionObject.getString("keywords"));

                                        event.setStartTime(startTime);
                                        event.setEndTime(endTime);
                                        event.setSessionTitle(sessionTitle);
                                        event.setChairPersonName(chairPerson);
                                        event.setChairPersonDetail(chairPersonDetail);

                                        event.setScheduleName("d1-schedule");
                                        event.setId("d1-schedule" + sessionTitle + event.getEventTitle());

                                        if (getBookmarkStatus(event.getId()))
                                            event.setBookmarked(true);
                                        else
                                            event.setBookmarked(false);

                                        dayOneEvents.add(event);

                                    }


                                }



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            try{
                                realmController.clearAllEvents();
                                for (int i = 0; i < dayOneEvents.size(); i++) {
                                    realmController.addEvent(dayOneEvents.get(i));
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

//                            realmController.clearAllEvents();
//
//                            if (realmController.getAllEvents().size() < 1) {
//                                NoInternetDialog noInternetDialog = new NoInternetDialog(context);
//                                noInternetDialog.showDialog();
//                            }


                        }
                    });
            requestQueue.add(jsonObjectRequest);
        } else {
            if (realmController.getAllEvents().size() < 1) {
                NoInternetDialog noInternetDialog = new NoInternetDialog(context);
                noInternetDialog.showDialog();
            }
        }

    }

    @Override
    public void setSchedule2() {
        if (Network.isNetworkAvailable(context)) {
            JsonObjectRequest
                    jsonObjectRequest
                    = new JsonObjectRequest(
                    Request.Method.GET,
                    Constants.URLSchedule2,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONObject acfObject = response.getJSONObject("acf");
                                JSONArray scheduleD1Array = acfObject.getJSONArray("d1-schedule");


                                for (int i = 0; i < scheduleD1Array.length(); i++) {

                                    JSONObject schedulObject = scheduleD1Array.getJSONObject(i);

                                    String startTime = schedulObject.getString("start-time");
                                    String endTime = schedulObject.getString("end-time");
                                    String sessionTitle = schedulObject.getString("session-title");
                                    String chairPerson = schedulObject.getString("chair-person");
                                    String chairPersonDetail = schedulObject.getString("chair-detail");

                                    JSONArray eventArray = schedulObject.getJSONArray("sub-session");
                                    for (int ss = 0; ss < eventArray.length(); ss++) {

                                        EventModel event = new EventModel();

                                        JSONObject sessionObject = eventArray.getJSONObject(ss);

                                        event.setEventTitle(sessionObject.getString("title"));
                                        event.setEventSpeaker(sessionObject.getString("speakers"));
                                        event.setSpeakerDetail(sessionObject.getString("speakers-detail"));
                                        event.setAbstractDetail(sessionObject.getString("abstract"));
                                        event.setKeywords(sessionObject.getString("keywords"));

                                        event.setStartTime(startTime);
                                        event.setEndTime(endTime);
                                        event.setSessionTitle(sessionTitle);
                                        event.setChairPersonName(chairPerson);
                                        event.setChairPersonDetail(chairPersonDetail);

                                        event.setScheduleName("d2-schedule");
                                        event.setId("d2-schedule" + sessionTitle + event.getEventTitle());

                                        if (getBookmarkStatus(event.getId()))
                                            event.setBookmarked(true);
                                        else
                                            event.setBookmarked(false);

                                        dayTwoEvents.add(event);

                                    }


                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                for (int i = 0; i < dayTwoEvents.size(); i++) {
                                    realmController.addEvent(dayTwoEvents.get(i));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            if (realmController.getAllEvents().size() < 1) {
//                                NoInternetDialog noInternetDialog = new NoInternetDialog(context);
//                                noInternetDialog.showDialog();
//                            }
                        }
                    });
            requestQueue.add(jsonObjectRequest);
        } else {
            if (realmController.getAllEvents().size() < 1) {
                NoInternetDialog noInternetDialog = new NoInternetDialog(context);
                noInternetDialog.showDialog();
            }
        }
    }

    @Override
    public void setRemainingTime() {

        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.dateFormatDefault);
        try {
            //conference date
            Date conferenceDateTime = dateFormat.parse(conference.getStartDate());

            //current date
            Calendar c = Calendar.getInstance();
            String formattedDate = dateFormat.format(c.getTime());
            Date currentDateTime = dateFormat.parse(formattedDate);

            remainingTime = conferenceDateTime.getTime() - currentDateTime.getTime();


        } catch (ParseException e) {
            e.printStackTrace();
        }

        mainView.getRemainingTime(remainingTime);
    }

    @Override
    public void getSpeakers() {
        if (Network.isNetworkAvailable(context)) {
            JsonArrayRequest
                    jsonArrayRequest
                    = new JsonArrayRequest(
                    Request.Method.GET,
                    Constants.URLSpeakersAPI,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject speakerObject = response.getJSONObject(i);

                                    speakerModels.add(parseSpeakerJson(speakerObject));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            realmController.clearAllSpeakerList();
                            realmController.addAllSpeakers(speakerModels);


                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (realmController.getAllSpeakersList().size() < 1) {
                                NoInternetDialog noInternetDialog = new NoInternetDialog(context);
                                noInternetDialog.showDialog();
                            }
                        }
                    });
            requestQueue.add(jsonArrayRequest);
        } else {
            if (realmController.getAllSponsors().size() < 1) {
                NoInternetDialog noInternetDialog = new NoInternetDialog(context);
                noInternetDialog.showDialog();
            }
        }

    }


    @Override
    public void getSponsers() {
        if (Network.isNetworkAvailable(context)) {
            JsonObjectRequest
                    jsonObjectRequest
                    = new JsonObjectRequest(
                    Request.Method.GET,
                    Constants.URLSponserAPI,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONObject acfObject = response.getJSONObject("acf");
                                JSONArray sponserArray = acfObject.getJSONArray("sponsors");

                                for (int i = 0; i < sponserArray.length(); i++) {
                                    SponserModel sponserModel = new SponserModel();
                                    sponserModel.setCategory("Sponsors");

                                    JSONObject object = sponserArray.getJSONObject(i);
                                    JSONObject sponserObject = object.getJSONObject("sponsor_logo");

                                    sponserModel.setId(sponserObject.getInt("id"));
                                    sponserModel.setTitle(sponserObject.getString("title"));
                                    sponserModel.setUrl(sponserObject.getString("url"));

                                    sponserModels.add(sponserModel);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                JSONObject acfObject = response.getJSONObject("acf");
                                JSONArray sponserArray = acfObject.getJSONArray("co-organizers");

                                for (int i = 0; i < sponserArray.length(); i++) {
                                    SponserModel sponserModel = new SponserModel();
                                    sponserModel.setCategory("Coorganizers");

                                    JSONObject object = sponserArray.getJSONObject(i);
                                    JSONObject sponserObject = object.getJSONObject("co-organizers_logo");

                                    sponserModel.setId(sponserObject.getInt("id"));
                                    sponserModel.setTitle(sponserObject.getString("title"));
                                    sponserModel.setUrl(sponserObject.getString("url"));

                                    sponserModels.add(sponserModel);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                JSONObject acfObject = response.getJSONObject("acf");
                                JSONArray sponserArray = acfObject.getJSONArray("partners");

                                for (int i = 0; i < sponserArray.length(); i++) {
                                    SponserModel sponserModel = new SponserModel();
                                    sponserModel.setCategory("Partners");

                                    JSONObject object = sponserArray.getJSONObject(i);
                                    JSONObject sponserObject = object.getJSONObject("partners_logo");

                                    sponserModel.setId(sponserObject.getInt("id"));
                                    sponserModel.setTitle(sponserObject.getString("title"));
//                                    sponserModel.setUrl(sponserObject.getString("url"));

                                    JSONObject imageObject = sponserObject.getJSONObject("sizes");
                                    sponserModel.setUrl(imageObject.getString("medium_large"));

                                    sponserModels.add(sponserModel);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            realmController.clearAllSponsorList();
                            realmController.addAllSponsors(sponserModels);

                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            if (realmController.getAllSponsors().size() < 1) {
                                NoInternetDialog noInternetDialog = new NoInternetDialog(context);
                                noInternetDialog.showDialog();
                            }
                        }
                    });
            requestQueue.add(jsonObjectRequest);
        } else {
            if (realmController.getAllSponsors().size() < 1) {
                NoInternetDialog noInternetDialog = new NoInternetDialog(context);
                noInternetDialog.showDialog();
            }
        }
    }

    @Override
    public void getNews() {
        if (Network.isNetworkAvailable(context)) {

            news.clear();
            JsonArrayRequest
                    jsonArrayRequest
                    = new JsonArrayRequest(
                    Request.Method.GET,
                    Constants.URLNEWS,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    NewsModel newsModel = new NewsModel();

                                    JSONObject newsObject = response.getJSONObject(i);

                                    newsModel.setId(newsObject.getInt("id"));
                                    newsModel.setLink(newsObject.getString("link"));

                                    JSONObject titleObject = newsObject.getJSONObject("title");
                                    newsModel.setHeading(titleObject.getString("rendered"));

                                    JSONObject contentObjcet = newsObject.getJSONObject("excerpt");
                                    newsModel.setContent(contentObjcet.getString("rendered"));

                                    news.add(newsModel);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            realmController.clearNews();
                            realmController.addAllNews(news);

//                            for (int i = 0; i < response.length(); i++) {
//                                try {
//                                    JSONObject speakerObject = response.getJSONObject(i);
//
//                                    speakerModels.add(parseSpeakerJson(speakerObject));
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            realmController.clearAllSpeakerList();
//                            realmController.addAllSpeakers(speakerModels);


                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (realmController.getNews().size() < 1) {
                                NoInternetDialog noInternetDialog = new NoInternetDialog(context);
                                noInternetDialog.showDialog();
                            }
                        }
                    });
            requestQueue.add(jsonArrayRequest);
        } else {
            if (realmController.getAllSponsors().size() < 1) {
                NoInternetDialog noInternetDialog = new NoInternetDialog(context);
                noInternetDialog.showDialog();
            }
        }

    }

    @Override
    public void checkNotificationSize() {

        if (realmController.getAllNotification().size() == notificationSharedPref.getNotificationSize()) {
            mainView.getNotificationChangeSize(0, realmController.getAllNotification().size(), false, null);

        } else {
            mainView.getNotificationChangeSize(realmController.getAllNotification().size() - notificationSharedPref.getNotificationSize(), realmController.getAllNotification().size(), true, realmController.getAllNotification().get(realmController.getAllNotification().size() - 1));
        }

    }

    @Override
    public void getUpdates() {

        new GetNotification().execute();

//        notifications.clear();
//        if (Network.isNetworkAvailable(context)) {
//            JsonObjectRequest
//                    jsonObjectRequest
//                    = new JsonObjectRequest(
//                    Request.Method.GET,
//                    Constants.URLUpdates,
//                    null,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//
//
//                            try {
//                                JSONObject acfObject = response.getJSONObject("acf");
//                                JSONArray updatesArray = acfObject.getJSONArray("notification");
//
//                                for (int i = 0; i < updatesArray.length(); i++) {
//                                    NotificationModel notificationModel = new NotificationModel();
//                                    JSONObject updateObject = updatesArray.getJSONObject(i);
//
//
//                                    if (updateObject.getString("title").length() > 0)
//                                        notificationModel.setTitle(updateObject.getString("title"));
//
//                                    if (updateObject.getString("details").length() > 0)
//                                        notificationModel.setDetail(updateObject.getString("details"));
//
//                                    if (updateObject.getString("category").length() > 0)
//                                        notificationModel.setCategory(updateObject.getString("category"));
//
//                                    notifications.add(notificationModel);
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                            realmController.clearAllNotification();
//                            realmController.addAllNotification(notifications);
//
//                        }
//
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            if (realmController.getAllNotification().size() < 1) {
//                                NoInternetDialog noInternetDialog = new NoInternetDialog(context);
//                                noInternetDialog.showDialog();
//                            }
//                        }
//                    });
//            requestQueue.add(jsonObjectRequest);
//        } else {
//            if (realmController.getAllSponsors().size() < 1) {
//                NoInternetDialog noInternetDialog = new NoInternetDialog(context);
//                noInternetDialog.showDialog();
//            }
//        }
    }

    private class GetNotification extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            notifications.clear();
            requestQueue.getCache().clear();
            if (Network.isNetworkAvailable(context)) {
                JsonObjectRequest
                        jsonObjectRequest
                        = new JsonObjectRequest(
                        Request.Method.GET,
                        Constants.URLUpdates,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {


                                try {
                                    JSONObject acfObject = response.getJSONObject("acf");
                                    JSONArray updatesArray = acfObject.getJSONArray("notification");

                                    for (int i = 0; i < updatesArray.length(); i++) {
                                        NotificationModel notificationModel = new NotificationModel();
                                        JSONObject updateObject = updatesArray.getJSONObject(i);


                                        if (updateObject.getString("title").length() > 0)
                                            notificationModel.setTitle(updateObject.getString("title"));

                                        if (updateObject.getString("details").length() > 0)
                                            notificationModel.setDetail(updateObject.getString("details"));

                                        if (updateObject.getString("category").length() > 0)
                                            notificationModel.setCategory(updateObject.getString("category"));

                                        notifications.add(notificationModel);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                realmController.clearAllNotification();
                                realmController.addAllNotification(notifications);

                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (realmController.getAllNotification().size() < 1) {
                                    NoInternetDialog noInternetDialog = new NoInternetDialog(context);
                                    noInternetDialog.showDialog();
                                }
                            }
                        });
                requestQueue.add(jsonObjectRequest);
            } else {
                if (realmController.getAllSponsors().size() < 1) {
                    NoInternetDialog noInternetDialog = new NoInternetDialog(context);
                    noInternetDialog.showDialog();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private SpeakerModel parseSpeakerJson(JSONObject jsonObject) throws JSONException {
        SpeakerModel speakerModel = new SpeakerModel();

        speakerModel.setId(jsonObject.getInt("id"));
        speakerModel.setDate(jsonObject.getString("date"));

        JSONObject acfObject = jsonObject.getJSONObject("acf");

        speakerModel.setSpeakerName(acfObject.getString("speaker_name"));
        speakerModel.setCurrentPosition(acfObject.getString("current_position"));
        speakerModel.setShortInfo(acfObject.getString("short_info"));

        speakerModel.setAbstractt(acfObject.getString("description"));
        speakerModel.setTopic(acfObject.getString("topic"));
        speakerModel.setKeywords(acfObject.getString("keywords"));

        speakerModel.setEmail(acfObject.getString("e-mail"));

        JSONObject imageParetnObject = jsonObject.getJSONObject("better_featured_image");
        JSONObject mediaDetailObject = imageParetnObject.getJSONObject("media_details");
        JSONObject sizedImageObject = mediaDetailObject.getJSONObject("sizes");
        JSONObject imageObject = sizedImageObject.getJSONObject("et-pb-portfolio-module-image");

        speakerModel.setImageUrl(imageObject.getString("source_url"));

        return speakerModel;

    }


}
