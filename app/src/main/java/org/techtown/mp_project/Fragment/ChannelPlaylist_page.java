package org.techtown.mp_project.Fragment;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.mp_project.Adapter.RecyclerAdapter;
import org.techtown.mp_project.Decoration.RecyclerView_DC;
import org.techtown.mp_project.Model.VideoDetails;
import org.techtown.mp_project.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChannelPlaylist_page extends Fragment {
    private ArrayList<VideoDetails> mList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    //Channel Playlist Query

    public ChannelPlaylist_page() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_channel_platlist_page, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        initList(mList);
        TextView channelTitle = v.findViewById(R.id.channel_title);
        ImageView thumbnail = v.findViewById(R.id.circleImageView);

        if(getArguments() != null){
            String channel_ID = getArguments().getString("channelID");
            String channel_title = getArguments().getString("channel_title");
            String channel_thumbnail = getArguments().getString("thumbnail");

            channelTitle.setText(channel_title);
            Glide.with(v.getContext()).load(channel_thumbnail).into(thumbnail);

            new YouTubeAPIRequest(channel_ID).execute();
        }

        return v;
    }
    private void initList(List<VideoDetails> mList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter = new RecyclerAdapter(getActivity(), mList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addItemDecoration(new RecyclerView_DC(15));
    }

    @SuppressLint("StaticFieldLeak")
    public class YouTubeAPIRequest extends AsyncTask<Void, String, String> {
        private String CHANNEL_GET_URL;
        private String APP_KEY = "AIzaSyAYnBWhkb6lulCaBrpqOzWfYmyQ7YePSLI";

        private YouTubeAPIRequest(String channelID){
            CHANNEL_GET_URL = "https://www.googleapis.com/youtube/v3/search?" +
                    "part=snippet" +
                    "&order=date"+
                    "&channelId="+channelID+
                    "&key="+APP_KEY+
                    "&maxResults=50";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(CHANNEL_GET_URL);
            Log.e("URL", CHANNEL_GET_URL);

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
            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    Log.e("Response", jsonObject.toString());
                    mList = parseVideoListFromResponse(jsonObject);
                    initList(mList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private ArrayList<VideoDetails> parseVideoListFromResponse(JSONObject jsonObject) {
        ArrayList<VideoDetails> mList = new ArrayList<>();
        if (jsonObject.has("items")) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("items");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    if (json.has("id")) {
                        JSONObject jsonID = json.getJSONObject("id");
                        String video_ID = jsonID.getString("videoId");

                        if (json.has("kind")) {
                            if (jsonID.getString("kind").equals("youtube#video")) {
                                JSONObject jsonSnippet = json.getJSONObject("snippet");

                                String title = jsonSnippet.getString("title");
                                String publishedAt = jsonSnippet.getString("publishedAt");
                                String Thumbnail = jsonSnippet.getJSONObject("thumbnails")
                                        .getJSONObject("high").getString("url");


                                VideoDetails videoDetails = new VideoDetails();
                                videoDetails.setVideo_ID(video_ID);
                                videoDetails.setTitle(title);
                                videoDetails.setPublishedAt(publishedAt);
                                videoDetails.setThumbnail(Thumbnail);
                                mList.add(videoDetails);
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return mList;
    }
}
