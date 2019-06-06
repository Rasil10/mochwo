package com.jnanatech.mochwo.search.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.speakerDetail.view.SpeakerDetailActivity;
import com.jnanatech.mochwo.speakers.model.SpeakerModel;
import com.jnanatech.mochwo.utils.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SpeakerSearchAdapter extends RecyclerView.Adapter<SpeakerSearchAdapter.MyViewHolder> {
    Context context;
    List<SpeakerModel> speakerModels;
    private RequestQueue requestQueue;


    public SpeakerSearchAdapter(Context context, List<SpeakerModel> speakerModels) {
        this.context = context;
        this.speakerModels = speakerModels;
        requestQueue = Volley.newRequestQueue(context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_speaker_search, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final SpeakerModel currentSpeaker = speakerModels.get(position);

        holder.textView.setText(currentSpeaker.getSpeakerName());
        setImage(currentSpeaker.getFeatureMediaLink(), holder.imageView);


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SpeakerDetailActivity.class);
                intent.putExtra(Constants.eventConstant, currentSpeaker);
                context.startActivity(intent);
            }
        });


    }

    private void setImage(String featureMediaLink, final ImageView imageView) {
        JsonArrayRequest
                jsonArrayRequest
                = new JsonArrayRequest(
                Request.Method.GET,
                featureMediaLink,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(0);
                                JSONObject mediaObject = jsonObject.getJSONObject("media_details");
                                JSONObject imageSizeObject = mediaObject.getJSONObject("sizes");
                                JSONObject finalImageObject = imageSizeObject.getJSONObject("et-pb-gallery-module-image-portrait");
                                String url = finalImageObject.getString("source_url");

                                Picasso.get().load(url)
                                        .placeholder(R.drawable.ic_terrain_black_24dp)
                                        .error(R.drawable.ic_terrain_black_24dp)
                                        .into(imageView);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(jsonArrayRequest);
    }


    @Override
    public int getItemCount() {
        return speakerModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        CardView layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.speakerImageView);
            textView = itemView.findViewById(R.id.speakerNameTextView);
            layout = itemView.findViewById(R.id.speakerSearchLayout);
        }
    }
}
