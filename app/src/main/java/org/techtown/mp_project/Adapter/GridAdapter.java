package org.techtown.mp_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.techtown.mp_project.R;


public class GridAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private int[] gridresources = {R.drawable.movie, R.drawable.league_of_legend, R.drawable.music,
                                    R.drawable.food, R.drawable.dog, R.drawable.trip, R.drawable.issue,
                                    R.drawable.scissors};
    private String[] gridtitle = {"영화 리뷰", "롤", "음악", "요리/먹방", "애견", "여행", "이슈", "미용"};
    private int layout;

    public GridAdapter(Context context, int layout){
        this.context = context;
        this.layout = layout;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return gridresources.length;
    }

    @Override
    public String getItem(int i) {
        return gridtitle[i];
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

        ImageView imageView = view.findViewById(R.id.grid_item);
        imageView.setImageResource(gridresources[i]);

        TextView textView = view.findViewById(R.id.grid_title);
        textView.setText(gridtitle[i]);

        return view;
    }


}
