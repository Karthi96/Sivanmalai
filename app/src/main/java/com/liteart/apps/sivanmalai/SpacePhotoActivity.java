package com.liteart.apps.sivanmalai;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.liteart.apps.sivanmalai.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class SpacePhotoActivity extends AppCompatActivity {

    public static final String EXTRA_SPACE_PHOTO = "SpacePhotoActivity.SPACE_PHOTO";
    private ImageView mImageView;
    Button btnsaveImage;
    InterstitialAd mInterstitialAd;

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
    }
    public void displayInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_photo);

        AdView mAdView = (AdView) findViewById(R.id.spacePhotoActivity);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mInterstitialAd = new InterstitialAd(SpacePhotoActivity.this);
                mInterstitialAd.setAdUnitId(getString(R.string.interid1));
                AdRequest adRequest = new AdRequest.Builder()
                        .build();
                mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override

                    public void onAdLoaded() {
                        displayInterstitial();
                    }
                });

            }
        } , 5000);

        btnsaveImage=(Button)findViewById(R.id.save_image);
        mImageView = (ImageView) findViewById(R.id.image);
        final SpacePhoto spacePhoto = getIntent().getParcelableExtra(EXTRA_SPACE_PHOTO);

        Glide.with(this)
                .load(spacePhoto.getUrl())
                .asBitmap()
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mImageView);
        askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,0x3);

        btnsaveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImagefromUrl(spacePhoto.getUrl(), spacePhoto.getTitle());

            }
        });

    }

    private void saveImagefromUrl(String url, final String title) {
        Glide.with(SpacePhotoActivity.this)
                .load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(100,100) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        saveImage(resource,title);
                    }});
    }

    private String saveImage(Bitmap image, String title) {
        String savedImagePath = null;

        String imageFileName =  title + ".jpg";
        File storageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        + "/MurugaPotri");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Add the image to the system gallery
            galleryAddPic(savedImagePath);
            Toast.makeText(SpacePhotoActivity.this, "Image Saved in Pictures Directory", Toast.LENGTH_LONG).show();
        }
        return savedImagePath;
    }

    private void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }
    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(SpacePhotoActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(SpacePhotoActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(SpacePhotoActivity.this, new String[]{permission}, requestCode);
            }

        } else {
           // saveImagefromUrl(url, title);
        }
    }

}
