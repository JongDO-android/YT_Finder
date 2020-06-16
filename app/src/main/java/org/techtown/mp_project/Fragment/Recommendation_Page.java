package org.techtown.mp_project.Fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ActivityListResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.mp_project.Activity.ChannelInfoActivity;
import org.techtown.mp_project.Adapter.RecomGridAdapter;
import org.techtown.mp_project.Model.Rec_Items;
import org.techtown.mp_project.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Recommendation_Page extends Fragment {
    private static String APP_KEY = "AIzaSyCXMEZ3x88ele_xQY5rpQ91G4Z8uUFzUPE";
    private static String CHANNEL_ID;
    private ArrayList<Rec_Items> rec_cList = new ArrayList<>();
    private GridView gridView;
    private RecomGridAdapter recomGridAdapter;


    public Recommendation_Page() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recommendation_page, container, false);
        TextView text_title = v.findViewById(R.id.text_title);
        ImageView ch_thumbnail = v.findViewById(R.id.channel_thumbnail);

        if(getArguments() != null){
            CHANNEL_ID = getArguments().getString("channelID");
            text_title.setText(getArguments().getString("channel_title"));
            Glide.with(v.getContext()).load(getArguments().getString("thumbnail")).into(ch_thumbnail);

        }
        gridView = v.findViewById(R.id.grid_view);


        recomGridAdapter = new RecomGridAdapter(Objects.requireNonNull(getContext()),
                R.layout.grid_item_recommendation,
                rec_cList);
        gridView.setAdapter(recomGridAdapter);


        new YoutubeGet_Recommendation().execute();

        return v;
    }

    @SuppressLint("StaticFieldLeak")
    public class YoutubeGet_Recommendation extends AsyncTask<Void, String, String>{
        private String GetRecommendation_URL = "https://www.googleapis.com/youtube/v3/channels?" +
                "part=brandingSettings" +
                "&id=" + CHANNEL_ID +
                "&key=" + APP_KEY;

        @Override
        protected String doInBackground(Void... voids) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(GetRecommendation_URL);
            try {
                HttpResponse response;
                response = httpClient.execute(httpGet);
                HttpEntity httpEntity = response.getEntity();

                return EntityUtils.toString(httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                parseRecommendationChannelList(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void parseRecommendationChannelList(JSONObject jsonObject){
            try {
                JSONObject json = jsonObject.getJSONArray("items").getJSONObject(0);
                JSONArray jsonArray = json.getJSONObject("brandingSettings").
                        getJSONObject("channel").
                        getJSONArray("featuredChannelsUrls");
                for(int i = 0 ; i < jsonArray.length() ; i ++){
                    Log.e("Recommendation Channel Number :" , jsonArray.getString(i));
                    new YouTube_Channel_Info(jsonArray.getString(i)).execute();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    @SuppressLint("StaticFieldLeak")
    public class YouTube_Channel_Info extends AsyncTask<Void, String, String>{
        private String Get_Channel_Info_URL;

        private YouTube_Channel_Info(String channel_id){
            Get_Channel_Info_URL = "https://www.googleapis.com/youtube/v3/channels?" +
                    "part=snippet" +
                    "&id=" + channel_id +
                    "&key=" + APP_KEY;
        }

        @Override
        protected String doInBackground(Void... voids) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(Get_Channel_Info_URL);

            Log.e("Channel Info URL : ", Get_Channel_Info_URL);

            try {
                HttpResponse httpResponse;
                httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();

                return EntityUtils.toString(httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s != null){
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject json = jsonObject.getJSONArray("items").getJSONObject(0);
                    String title = json.getJSONObject("snippet").getString("title");
                    String thumbnailURL = json.getJSONObject("snippet")
                            .getJSONObject("thumbnails")
                            .getJSONObject("high")
                            .getString("url");

                    Rec_Items rec_items = new Rec_Items();
                    rec_items.setTitle(title);
                    rec_items.setThumbnail(thumbnailURL);
                    Log.e("Recommendation Title :", rec_items.getTitle());
                    Log.e("Recommendation Thumbnail URL :", rec_items.getThumbnail());

                    recomGridAdapter.addItem(rec_items);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
