package org.techtown.mp_project.Model;

public class ChannelDetails {
    private String channel_ID;
    private String channel_title;
    private String subscribers;
    private String contents_num;
    private String channel_thumbnail;

    public void setChannel_ID(String channel_ID){
        this.channel_ID = channel_ID;
    }
    public String getChannel_ID(){
        return channel_ID;
    }

    public void setChanneltitle(String channel_title){
        this.channel_title = channel_title;
    }
    public String getChanneltitle(){
        return channel_title;
    }

    public void setSubscribers(String subscribers){
        this.subscribers = subscribers;
    }
    public String getSubscribers(){
        return subscribers;
    }

    public void setContentsNum(String contents_num){
        this.contents_num = contents_num;
    }
    public String getContentsNum(){
        return contents_num;
    }

    public void setChannelThumbnail(String channel_thumbnail){
        this.channel_thumbnail = channel_thumbnail;
    }
    public String getChannel_thumbnail(){
        return channel_thumbnail;
    }

}
