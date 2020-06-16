package org.techtown.mp_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.techtown.mp_project.Model.Rec_Items;
import org.techtown.mp_project.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecomGridAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<Rec_Items> rec_items;
    private Context context;
    private int layout;

    public RecomGridAdapter(Context context, int layout, ArrayList<Rec_Items> rec_items){
        this.context = context;
        this.layout = layout;
        this.rec_items = rec_items;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(Rec_Items rec_item){
        rec_items.add(rec_item);
        notifyDataSetChanged();
    }

    public void clear(){
        rec_items.clear();
    }

    @Override
    public int getCount() {
        return rec_items.size();
    }

    @Override
    public Object getItem(int i) {
        return rec_items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = layoutInflater.inflate(layout, null);
        }

        CircleImageView circleImageView = view.findViewById(R.id.channel_thumbnail);
        TextView textView = view.findViewById(R.id.channel_title);

        Glide.with(view).load(rec_items.get(i).getThumbnail()).into(circleImageView);
        textView.setText(rec_items.get(i).getTitle());
        return view;
    }
}
