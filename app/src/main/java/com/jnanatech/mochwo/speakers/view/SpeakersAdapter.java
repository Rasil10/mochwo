package com.jnanatech.mochwo.speakers.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.search.view.SearchActivity;
import com.jnanatech.mochwo.speakerDetail.view.SpeakerDetailActivity;
import com.jnanatech.mochwo.speakers.model.SpeakerModel;
import com.jnanatech.mochwo.utils.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SpeakersAdapter extends RecyclerView.Adapter<SpeakersAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private ArrayList<SpeakerModel> speakerModels = new ArrayList<>();
    private ArrayList<SpeakerModel> speakerModelsFiltered = new ArrayList<>();
    private RequestQueue requestQueue;

    SpeakersAdapter(Context context, ArrayList<SpeakerModel> speakerModels) {
        this.context = context;
        this.speakerModels = speakerModels;
        this.speakerModelsFiltered = speakerModels;
        requestQueue = Volley.newRequestQueue(context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_speaker, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final SpeakerModel currentSpeaker = speakerModelsFiltered.get(position);

        holder.name.setText(currentSpeaker.getSpeakerName());
        holder.qualification.setText(currentSpeaker.getCurrentPosition());
        setImage(currentSpeaker.getFeatureMediaLink(), holder.imageView);


        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SpeakerDetailActivity.class);
                intent.putExtra(Constants.speakerConstant, currentSpeaker.getSpeakerName());
                context.startActivity(intent);
            }
        });


        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SpeakerDetailActivity.class);
                intent.putExtra(Constants.speakerConstant, currentSpeaker.getSpeakerName());
                context.startActivity(intent);
            }
        });

//        holder.bookmark.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (holder.bookmark.getDrawable().getConstantState() == ContextCompat.getDrawable(v.getContext(),
//                        R.drawable.ic_star_border_black_24dp).getConstantState()) {
//                    holder.bookmark.setImageResource(R.drawable.ic_star_black_24dp);
//                } else {
//                    holder.bookmark.setImageResource(R.drawable.ic_star_border_black_24dp);
//                }
//            }
//        });

    }

    private void setImage(String featureMediaLink, final CircleImageView imageView) {
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
                        Toast.makeText(context, "Error Loading Image.", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public int getItemCount() {
        return speakerModelsFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    speakerModelsFiltered = speakerModels;
                } else {
                    ArrayList<SpeakerModel> filteredList = new ArrayList<>();
                    for (SpeakerModel row : speakerModels) {

                        if ((row.getSpeakerName().toLowerCase()).contains(charString.toLowerCase())
                                || ((row.getExperience1()).toLowerCase()).contains(charString.toLowerCase())
                                || ((row.getExperience2()).toLowerCase()).contains(charString.toLowerCase())
                                || ((row.getExperience3()).toLowerCase()).contains(charString.toLowerCase())
                                || ((row.getExperience4()).toLowerCase()).contains(charString.toLowerCase())
                        ) {

                            filteredList.add(row);
                        }
                    }

                    speakerModelsFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = speakerModelsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                speakerModelsFiltered = (ArrayList<SpeakerModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView name;
        TextView qualification;
        CardView mainView;
        TextView bookmark;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (CircleImageView) itemView.findViewById(R.id.speakerImageView);
            name = (TextView) itemView.findViewById(R.id.speakerName);
            qualification = (TextView) itemView.findViewById(R.id.speakerQualification);
            mainView = (CardView) itemView.findViewById(R.id.mainView);
            bookmark = (TextView) itemView.findViewById(R.id.viewMore);
        }
    }


}
