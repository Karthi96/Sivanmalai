package com.liteart.apps.sivanmalai;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liteart.apps.sivanmalai.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        setTitle(R.string.gallery);
        AdView mAdView = (AdView) findViewById(R.id.galleryActivity);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
try {
    SharedPreferences pref = getApplicationContext().getSharedPreferences("dataPref", MODE_PRIVATE);
    Gson gson = new Gson();
    String json = pref.getString("titleListImages", null);
    String jsonUrl = pref.getString("urlListImages", null);
    Type type = new TypeToken<ArrayList<String>>() {
    }.getType();
    ArrayList<String> titleList = gson.fromJson(json, type);
    ArrayList<String> urlList = gson.fromJson(jsonUrl, type);

    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_images);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(layoutManager);

    ImageGalleryAdapter adapter = new ImageGalleryAdapter(this, SpacePhoto.getSpacePhotos(urlList, titleList));
    recyclerView.setAdapter(adapter);
}
catch (Exception e)
{

}

    }
    private class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder>  {

        @Override
        public ImageGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View photoView = inflater.inflate(R.layout.gallery_row, parent, false);
            ImageGalleryAdapter.MyViewHolder viewHolder = new ImageGalleryAdapter.MyViewHolder(photoView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ImageGalleryAdapter.MyViewHolder holder, int position) {

            SpacePhoto spacePhoto = mSpacePhotos[position];
            ImageView imageView = holder.mPhotoImageView;

            Glide.with(mContext)
                    .load(spacePhoto.getUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .fitCenter()
                    .centerCrop()
                    .into(imageView);
        }

        @Override
        public int getItemCount() {
            return (mSpacePhotos.length);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public ImageView mPhotoImageView;

            public MyViewHolder(View itemView) {

                super(itemView);
                mPhotoImageView = (ImageView) itemView.findViewById(R.id.iv_photo);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {

                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    SpacePhoto spacePhoto = mSpacePhotos[position];
                    Intent intent = new Intent(mContext, SpacePhotoActivity.class);
                    intent.putExtra(SpacePhotoActivity.EXTRA_SPACE_PHOTO, spacePhoto);
                    startActivity(intent);
                }
            }
        }

        private SpacePhoto[] mSpacePhotos;
        private Context mContext;

        public ImageGalleryAdapter(Context context, SpacePhoto[] spacePhotos) {
            mContext = context;
            mSpacePhotos = spacePhotos;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}

