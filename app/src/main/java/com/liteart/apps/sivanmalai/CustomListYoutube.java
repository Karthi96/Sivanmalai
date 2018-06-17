package com.liteart.apps.sivanmalai;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liteart.apps.sivanmalai.R;

public class CustomListYoutube extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
    private final String[] imageId;
    public CustomListYoutube(Activity context,
                             String[] web, String[] imageId) {
        super(context, R.layout.list_row_youtube, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_row_youtube, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web[position]);
        Glide.with(context)
                .load(imageId[position])
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                .into(imageView);

       // imageView.setImageResource(imageId[position]);
        return rowView;
    }
}