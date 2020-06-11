package org.techtown.mp_project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.techtown.mp_project.Activity.VideoPlayActivity;
import org.techtown.mp_project.Model.Item;
import org.techtown.mp_project.Model.VideoDetails;
import org.techtown.mp_project.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.VideoDetailsViewHolder> { // RecyclerView Adapter
    private List<VideoDetails> videoDetailsList;
    private Context context;
    private String converted_Date;

    public RecyclerAdapter(Context context, List<VideoDetails> videoDetailsList){
        this.context = context;
        this.videoDetailsList = videoDetailsList;
    }

    @NonNull
    @Override
    public VideoDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new VideoDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoDetailsViewHolder holder, int position) {
        holder.publishedAt.setText(setUpDateTime(videoDetailsList.get(position).getPublishedAt()));

        TextView title = holder.title;
        TextView publishedAt = holder.publishedAt;
        ImageView thumbnail = holder.thumbnail;

        VideoDetails videoDetails = videoDetailsList.get(position);

        title.setText(videoDetails.getTitle());
        publishedAt.setText(setUpDateTime(videoDetails.getPublishedAt()));

        Glide.with(context).load(videoDetails.getThumbnail()).into(thumbnail);
    }

    private String setUpDateTime(String publishedAt) {
        try{
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat format = new SimpleDateFormat("YYYY/MM/dd");
            Date date = dateFormat.parse(publishedAt);
            converted_Date = format.format(date);

        } catch (ParseException exception) {
            Toast.makeText(context, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return converted_Date;
    }

    @Override
    public int getItemCount() {
        return videoDetailsList.size();
    }

    class VideoDetailsViewHolder extends RecyclerView.ViewHolder{
        private TextView publishedAt;
        private TextView title;
        private ImageView thumbnail;

        private VideoDetailsViewHolder(View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.title);
            publishedAt = itemView.findViewById(R.id.publishedAt);
            thumbnail = itemView.findViewById(R.id.profile);

            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(context, VideoPlayActivity.class);
                        intent.putExtra("video_ID", videoDetailsList.get(pos).getVideo_ID());

                        context.startActivity(intent);
                    }
                }
            });

        }
    }

}
