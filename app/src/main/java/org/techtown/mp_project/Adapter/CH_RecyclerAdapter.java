package org.techtown.mp_project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.techtown.mp_project.Activity.ChannelInfoActivity;
import org.techtown.mp_project.Fragment.ChannelPlaylist_page;
import org.techtown.mp_project.Fragment.List_Page;
import org.techtown.mp_project.Model.ChannelDetails;
import org.techtown.mp_project.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import io.opencensus.resource.Resource;

public class CH_RecyclerAdapter extends RecyclerView.Adapter<CH_RecyclerAdapter.ChannelDetailsViewHolder> {
    private List<ChannelDetails> channelList;
    private Context context;

    public CH_RecyclerAdapter(Context context, List<ChannelDetails> channelDetails){
        this.context = context;
        this.channelList = channelDetails;
    }

    @NonNull
    @Override
    public ChannelDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_item, parent, false);
        return new ChannelDetailsViewHolder(v);
    }

    @Override    public void onBindViewHolder(@NonNull ChannelDetailsViewHolder holder, int position) {
        TextView channel_title = holder.channel_title;
        TextView subscribers = holder.subscribers;
        TextView contentsNumber = holder.contentsNumber;
        final ImageView channel_thumbnail = holder.channel_thumbnail;
        final ChannelDetails channelDetails = channelList.get(position);


        channel_title.setText(channelDetails.getChanneltitle());

        if(Integer.parseInt(channelDetails.getSubscribers()) >= 10000){
            int a = Integer.parseInt(channelDetails.getSubscribers()) / 10000;
            int b = Integer.parseInt(channelDetails.getSubscribers()) % 10000 / 1000;

            String s = a + "." + b + " 만 명";
            subscribers.setText(s);
        }
        else{
            String sub = toNumFormat(Integer.parseInt(channelDetails.getSubscribers())) + " 명";
            subscribers.setText(sub);
        }
        contentsNumber.setText(channelDetails.getContentsNum());

        Glide.with(context).load(channelDetails.getChannel_thumbnail()).into(channel_thumbnail);
        Button Playlist_btn = holder.Go_playlist_btn;
        Playlist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChannelInfoActivity.class);
                intent.putExtra("channelID", channelDetails.getChannel_ID());
                intent.putExtra("channel_title", channelDetails.getChanneltitle());
                intent.putExtra("thumbnail", channelDetails.getChannel_thumbnail());

                view.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return channelList.size();
    }

    public static String toNumFormat(int num) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(num);
    }

    class ChannelDetailsViewHolder extends RecyclerView.ViewHolder {
        private TextView channel_title;
        private TextView subscribers;
        private TextView contentsNumber;
        private Button Go_playlist_btn;
        private ImageView channel_thumbnail;

        private ChannelDetailsViewHolder(@NonNull View itemView) {
            super(itemView);

            channel_title = itemView.findViewById(R.id.channel_title);
            subscribers = itemView.findViewById(R.id.channel_subscribers);
            contentsNumber = itemView.findViewById(R.id.contents_number);
            channel_thumbnail = itemView.findViewById(R.id.channel_thumbnail);
            Go_playlist_btn = itemView.findViewById(R.id.go_playlist);

        }

    }
}
