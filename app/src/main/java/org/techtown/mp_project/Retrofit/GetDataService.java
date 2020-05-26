package org.techtown.mp_project.Retrofit;

import org.techtown.mp_project.Model.VideoDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {
    @GET("search?")
    Call<VideoDetails> getVideoData(
            @Query("part") String part,
            @Query("channelId") String ChannelID,
            @Query("key") String key,
            @Query("order") String order
    );
}
