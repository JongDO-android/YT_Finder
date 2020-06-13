package org.techtown.mp_project.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.api.services.youtube.YouTube;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.mp_project.Activity.MainActivity;
import org.techtown.mp_project.Adapter.CH_RecyclerAdapter;
import org.techtown.mp_project.Adapter.GridAdapter;
import org.techtown.mp_project.Adapter.RecyclerAdapter;
import org.techtown.mp_project.Decoration.RecyclerView_DC;
import org.techtown.mp_project.Model.ChannelDetails;
import org.techtown.mp_project.Model.VideoDetails;
import org.techtown.mp_project.R;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;


public class ChannelList_Page extends Fragment {
    private CH_RecyclerAdapter ch_recyclerAdapter;
    private GridAdapter gridAdapter;
    private RecyclerView recyclerView;
    private List<ChannelDetails> searchList = new ArrayList<>();

    private static String APP_KEY = "AIzaSyCXMEZ3x88ele_xQY5rpQ91G4Z8uUFzUPE";

    //YouTube play list Query
    private static String CHANNEL_ID = "UC1dG3vI9FfHnH3YgyeKUz_A";
    /*private static String CHANNEL_GET_URL = "https://www.googleapis.com/youtube/v3/search?" +
            "part=snippet" +
            "&order=date" +
            "&channelId=" + CHANNEL_ID +
            "&key=" + APP_KEY +
            "&maxResults=50";*/

    //YouTube Search list Query
    private String SEARCH_CATEGORY = "";
    /*private static String SEARCH_GET_URL ="https://www.googleapis.com/youtube/v3/search?"+
            "part=snippet"+
            "&order=date"+
            "&q="+SEARCH_CATEGORY+
            "&key="+APP_KEY+
            "&maxResults=50";*/

    //YouTube Channel statistics Query
    private static String CHANNEL_STATISTICS_URL = "https://www.googleapis.com/youtube/v3/channels?" +
            "part=statistics" +
            "&id=" + CHANNEL_ID +
            "&key=" + APP_KEY;

    private String category_title;
    private ImageButton search_btn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.list_page, container, false);

        //Recycler View 불러오기
        recyclerView = v.findViewById(R.id.recyclerView);
        initChannelList();

        //선택 버튼 클릭 시 카테고리 선택하는 대화상자 생성
        gridAdapter = new GridAdapter(Objects.requireNonNull(getActivity()), R.layout.grid_items);
        final Button select_btn = v.findViewById(R.id.select_button);
        select_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("InflateParams")
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                final View gv = inflater.inflate(R.layout.gridview_dialog, null);
                GridView gridView = gv.findViewById(R.id.grid_view);
                gridView.setAdapter(gridAdapter);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        category_title = gridAdapter.getItem(i);
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setIcon(R.drawable.myicon);
                builder.setView(gv);
                builder.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        select_btn.setText(category_title);
                    }
                });
                builder.show();
            }
        });

        search_btn = v.findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!select_btn.getText().equals("선택")) {
                    SEARCH_CATEGORY = select_btn.getText().toString();
                    try {
                        searchList.clear();
                        SEARCH_CATEGORY = URLEncoder.encode(SEARCH_CATEGORY, "UTF-8");
                        new YouTubeAPIRequest_Search().execute();
                        /*.get();
                        JSONObject json = new JSONObject(s);

                        //parseChannelListFromResponse(json);*/

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getContext(), "카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    private void initChannelList(){
        ch_recyclerAdapter = new CH_RecyclerAdapter(getActivity(), searchList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(ch_recyclerAdapter);
    }
    //유튜브 검색을 통해 다양한 채널의 정보를 불러오는 Task
    @SuppressLint("StaticFieldLeak")
    public class YouTubeAPIRequest_Search extends AsyncTask<Void, String, String> {
        private String SEARCH_GET_URL = "https://www.googleapis.com/youtube/v3/search?" +
                "part=snippet" +
                "&order=date" +
                "&q=" + SEARCH_CATEGORY +
                "&key=" + APP_KEY +
                "&maxResults=1";

        ProgressDialog progressDialog = new ProgressDialog(getContext());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("채널 리스트 불러오는중...");

            Log.e("TAG", SEARCH_GET_URL);

            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(SEARCH_GET_URL);
            Log.e("Search Category :", SEARCH_CATEGORY);
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
                    parseChannelListFromResponse(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            progressDialog.dismiss();
        }

    }

    //채널의 구독자 수와 콘텐츠 수에 대한 정보를 불러오는 Task
    @SuppressLint("StaticFieldLeak")
    public class YouTubeAPIRequest_ChannelStatistics extends AsyncTask<Void, String, String> {
        private String CHANNEL_STATISTICS_URL;

        private YouTubeAPIRequest_ChannelStatistics(String channelID) {
            CHANNEL_STATISTICS_URL = "https://www.googleapis.com/youtube/v3/channels?" +
                    "part=statistics" +
                    "&id=" + channelID +
                    "&key=" + APP_KEY;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(CHANNEL_STATISTICS_URL);

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
        }
    }
    //채널 Thumbnail 정보 가져오는 Task
    @SuppressLint("StaticFieldLeak")
    public class YouTubeAPIRequest_GetThumbnail extends AsyncTask<Void, String, String> {
        private String Channel_Thumbnail_URL;

        YouTubeAPIRequest_GetThumbnail(String channelID) {
            Channel_Thumbnail_URL = "https://www.googleapis.com/youtube/v3/channels?" +
                    "part=snippet" +
                    "&id=" + channelID +
                    "&key=" + APP_KEY;

            Log.e("Channel Thumbnail URL :", Channel_Thumbnail_URL);
        }

        @Override
        protected String doInBackground(Void... voids) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(Channel_Thumbnail_URL);
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

    }

    //채널 ID, 채널 Title 가져오기
    private void parseChannelListFromResponse(JSONObject jsonObject) {
        if (jsonObject.has("items")) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("items");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    if (json.has("id")) {
                        JSONObject jsonID = json.getJSONObject("id");
                        if (json.has("kind")) {
                            if (jsonID.getString("kind").equals("youtube#video")) {
                                JSONObject jsonSnippet = json.getJSONObject("snippet");

                                String channelID = jsonSnippet.getString("channelId");
                                ChannelDetails channelDetails = new ChannelDetails();
                                channelDetails.setChannel_ID(channelID);
                                channelDetails.setChanneltitle(jsonSnippet.getString("channelTitle"));

                                String s0 = new YouTubeAPIRequest_ChannelStatistics(channelID).execute().get();
                                JSONObject jsonObj0 = new JSONObject(s0);
                                String[] sList = paresChannelStatistics(jsonObj0);

                                assert sList != null;
                                channelDetails.setSubscribers(sList[0]);
                                channelDetails.setContentsNum(sList[1]);

                                String s1 = new YouTubeAPIRequest_GetThumbnail(channelID).execute().get();
                                JSONObject jsonObj1 = new JSONObject(s1);

                                //채널 썸네일 가져오기
                                channelDetails.setChannelThumbnail(paresChannelThumbnail(jsonObj1));

                                searchList.add(channelDetails);
                                ch_recyclerAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                }
            } catch (JSONException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
    ////채널 구독자 수, 채널 동영상 수 가져오기
    private String[] paresChannelStatistics(JSONObject jsonObject) {
        String[] s = new String[2];
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            JSONObject json = jsonArray.getJSONObject(0);
            s[0] = json.getJSONObject("statistics").getString("subscriberCount");
            s[1] = json.getJSONObject("statistics").getString("videoCount");

            return s;
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    //채널 썸네일 가져오기
    private String paresChannelThumbnail(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            JSONObject json = jsonArray.getJSONObject(0);

            return json.getJSONObject("snippet").getJSONObject("thumbnails")
                    .getJSONObject("high").getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}