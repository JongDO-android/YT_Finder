package org.techtown.mp_project.Model;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoDetails {
    private String title;
    private String description;
    private String publishedAt;
    private String video_ID;
    private String channeltitle;

    private String thumbnail;

    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    public List<Item> getItems() {
        return items;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setChanneltitle(String channeltitle){this.channeltitle = channeltitle;}
    public String getChanneltitle(){
        return channeltitle;
    }

    public void setVideo_ID(String video_ID){this.video_ID = video_ID;}
    public String getVideo_ID(){return video_ID;}

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return title;
    }

    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return description;
    }

    public void setPublishedAt(String publishedAt){
        this.publishedAt = publishedAt;
    }
    public String getPublishedAt(){
        return publishedAt;
    }

    public void setThumbnail(String thumbnail){
        this.thumbnail = thumbnail;
    }
    public String getThumbnail(){
        return thumbnail;
    }


}