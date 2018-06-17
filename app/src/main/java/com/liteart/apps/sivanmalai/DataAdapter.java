package com.liteart.apps.sivanmalai;

/**
 * Created by magudesh on 11/23/17.
 */
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jean.jcplayer.JcAudio;
import com.example.jean.jcplayer.JcPlayerView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.liteart.apps.sivanmalai.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Context.DOWNLOAD_SERVICE;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<String> dataString,urlList;
    JcPlayerView jcplayerView;
    ArrayList<JcAudio> jcAudios;
    Context context;
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
    public DataAdapter(ArrayList<String> dataString,ArrayList<String> urlList,JcPlayerView jcPlayerView,ArrayList<JcAudio> jcAudios,Context context) {
        this.dataString = dataString;
        this.urlList=urlList;
        this.jcplayerView=jcPlayerView;
        this.jcAudios=new ArrayList<>(jcAudios);
        this.context=context;
    }
    public DataAdapter(ArrayList<String> dataString) {
        this.dataString = dataString;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        viewHolder.tv_country.setText(dataString.get(i));
        viewHolder.tv_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                     //   MobileAds.initialize(context, "ca-app-pub-1439926476174790~9143120480");
                        mInterstitialAd = new InterstitialAd(context);
                        mInterstitialAd.setAdUnitId(context.getString(R.string.interid1));
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
                },100 );
                JcAudio audio=jcAudios.get(i);
                jcplayerView.playAudio(audio);
            }
        });
        viewHolder.btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
               Uri uri = Uri.parse(urlList.get(i));
               /* Intent it  = new Intent(Intent.ACTION_VIEW,uri);
                context.startActivity(it);*/
                /*Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        downloadFile(urlList.get(i),dataString.get(i));
                    }
                });
                thread.start();*/
                DownloadManager.Request r = new DownloadManager.Request(uri);

// This put the download in the same Download dir the browser uses
                r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, dataString.get(i));

// When downloading music and videos they will be listed in the player
// (Seems to be available since Honeycomb only)
                r.allowScanningByMediaScanner();

// Notify user when download is completed
// (Seems to be available since Honeycomb only)
                r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

// Start download
                DownloadManager dm = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(r);
                    Snackbar.make(v,"Downloading started...",Snackbar.LENGTH_LONG).show();
                /*
                Uri uri = Uri.parse(urlList.get(i));
                if ("content".equals(uri.getScheme())) {

                    uri = Uri.fromFile(new File(filePath));
                }
                if ("file".equals(uri.getScheme()) ){
                    Intent installIntent = new Intent(Intent.ACTION_VIEW);
                    installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Required if launching outside of an activity
                    installIntent.setDataAndType(uri, downloadManager.getMimeTypeForDownloadedFile(downloadId));
                    startActivity(installIntent);
                }
*/}
catch (Exception e)
{
    e.printStackTrace();
}
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataString.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_country;
        private Button btn_download;
        public ViewHolder(View view) {
            super(view);

            tv_country = (TextView)view.findViewById(R.id.tv_dataKavithaigal);
            btn_download=(Button)view.findViewById(R.id.btn_download);
        }
    }
    static void downloadFile(String dwnload_file_path, String fileName) {


        int downloadedSize = 0;
        int totalSize = 0;

        try {
            Log.e("status","inside Download");
            URL url = new URL(dwnload_file_path);
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            // connect
            urlConnection.connect();
            Log.e("status","after connect");
            File myDir;
            myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
                    + "/MurugaPotri");
            myDir.mkdirs();

            // create a new file, to save the downloaded file

            String mFileName = fileName;
            File file = new File(myDir, mFileName);

            FileOutputStream fileOutput = new FileOutputStream(file);

            // Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            // this is the total size of the file which we are downloading
            totalSize = urlConnection.getContentLength();
            Log.e("status","inside total");
            // runOnUiThread(new Runnable() {
            // public void run() {
            // pb.setMax(totalSize);
            // }
            // });

            // create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                // update the progressbar //
                // runOnUiThread(new Runnable() {
                // public void run() {
                // pb.setProgress(downloadedSize);
                // float per = ((float)downloadedSize/totalSize) * 100;
                // cur_val.setText("Downloaded " + downloadedSize + "KB / " +
                // totalSize + "KB (" + (int)per + "%)" );
                // }
                // });
            }
            // close the output stream when complete //
            fileOutput.close();
            // runOnUiThread(new Runnable() {
            // public void run() {
            // // pb.dismiss(); // if you want close it..
            // }
            // });

        } catch (final MalformedURLException e) {
            // showError("Error : MalformedURLException " + e);
            Log.e("Exce" ,e.toString());
        } catch (final IOException e) {
            // showError("Error : IOException " + e);
            Log.e("Exce" ,e.toString());
        } catch (final Exception e) {
            Log.e("Exce" ,e.toString());
        }
    }

}