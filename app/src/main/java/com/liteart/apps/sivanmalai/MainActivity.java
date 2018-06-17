package com.liteart.apps.sivanmalai;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import com.google.gson.Gson;
import com.kobakei.ratethisapp.RateThisApp;
import com.liteart.apps.sivanmalai.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{
    MenuItem playMenu;
    private SoundPool soundPool;
    private int soundID;
    boolean plays = false, loaded = false;

    int counter;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 1000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 4000; // time in milliseconds between successive task executions.

    Button btn_arupadaiveedugal,btn_story,btn_videos,
            btn_songs, btn_fm, btn_gallery;
    Button mainmap1;
    ViewPager viewPager;
    int images[] = {R.drawable.scroll1, R.drawable.scroll3,
            R.drawable.scroll2,R.drawable.scroll};
    MyCustomPagerAdapter myCustomPagerAdapter;

    public ArrayList<String> titleList,urlList;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nav);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1439926476174790~5913211087");
        AdView mAdView = (AdView) findViewById(R.id.adView_main);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
            mainmap1=(Button)findViewById(R.id.btn_maps);
        setTitle(getString(R.string.app_name_header));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(!isConnected(getApplicationContext())) {
            Snackbar snackbar = Snackbar
                    .make(toolbar, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    });

// Changing message text color
            snackbar.setActionTextColor(Color.GREEN);


// Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        try{

            if (getIntent().getStringExtra("intent").equalsIgnoreCase("news")) {
                startActivity(new Intent(getApplicationContext(), NewsListActivity.class));
            }
            else if (getIntent().getStringExtra("intent").equalsIgnoreCase("gallery")) {
                startActivity(new Intent(getApplicationContext(), GalleryActivity.class));
            }
            else if (getIntent().getStringExtra("intent").equalsIgnoreCase("songs")) {
                startActivity(new Intent(getApplicationContext(), BakthiPaadalgal.class));
            }
            else if (getIntent().getStringExtra("intent").equalsIgnoreCase("fm")) {
                startActivity(new Intent(getApplicationContext(), RadioFMActivity.class));
            }
        }
        catch (Exception e)
        {

        }

        pref = getApplicationContext().getSharedPreferences("dataPref", MODE_PRIVATE);
        editor = pref.edit();
      /*  new Thread()
        {
            public void run()
            {

            }
        }.start();
*/
       // new Background().execute();


        String mode=getIntent().getStringExtra("mode");
       // if(mode)
        String list=pref.getString("titleList",null);
        if(list==null)
            new HttpAsyncTask().execute("https://www.liteartapps.com/android_production/android_murugan/murugan.json");

        String listImages=pref.getString("titleListImages",null);
        if(listImages==null)
            new HttpAsyncTaskImages().execute("https://www.liteartapps.com/android_production/android_murugan/muruganimages.json");
        String listRadio=pref.getString("titleListRadio",null);
        if(listRadio==null)
            new HttpAsyncTaskRadio().execute("https://www.liteartapps.com/android_production/android_murugan/radiourl.json");
        String listNews=pref.getString("listnews",null);
        if(listNews==null)
            new HttpAsyncTaskNews().execute("https://www.liteartapps.com/android_production/android_murugan/news_updates/murugannews.json");

        new HttpAsyncTaskPetti().execute("https://liteartapps.com/android_production/android_murugan/sivanmalai/sivanmalai_porul.json");

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == images.length) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer .schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
        myCustomPagerAdapter = new MyCustomPagerAdapter(MainActivity.this, images);
        viewPager.setAdapter(myCustomPagerAdapter);

        btn_arupadaiveedugal=(Button)findViewById(R.id.btn_arupadaiveedugal);
        btn_arupadaiveedugal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ArupadaiVeedugalContentActivity.class);
                i.putExtra("temple_name","thiruparakundram");
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        btn_story=(Button)findViewById(R.id.btn_story);
        btn_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),ArupadaiVeedugalContentActivity.class);
                i.putExtra("temple_name","tiruchentur");
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        btn_videos=(Button)findViewById(R.id.btn_videos);
        btn_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ArupadaiVeedugalContentActivity.class);
                i.putExtra("temple_name","palani");
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
       /* btn_kanthapuranam=(Button)findViewById(R.id.btn_kanthapuranam);
        btn_kanthapuranam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(getApplicationContext(), EssayWebviewActivity.class);
                ii.putExtra("url", "kantha_sasti");
                startActivity(ii);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });*/
        mainmap1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps/place/Sivanmalai+Murugar+Temple/@11.0357335,77.5357532,17z/data=!3m1!4b1!4m5!3m4!1s0x3ba9a1b57131bb79:0x6c507cd5c2443e4d!8m2!3d11.0357282!4d77.5379419"));
                startActivity(intent);

            }
        });
        btn_songs=(Button)findViewById(R.id.btn_songs);
        btn_songs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected(getApplicationContext())) {
                    Intent story = new Intent(getApplicationContext(), Utharavu_porul.class);
                    startActivity(story);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                else{
                    Snackbar snackbar = Snackbar
                            .make(v, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    finish();
                                }
                            });

// Changing message text color
                    snackbar.setActionTextColor(Color.GREEN);

// Changing action button text color
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();   }}
        });
        btn_fm =(Button)findViewById(R.id.btn_fm);
        btn_gallery =(Button)findViewById(R.id.btn_gallery);
        btn_fm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected(getApplicationContext())) {
                    startActivity(new Intent(getApplicationContext(), RadioFMActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                else{
                    Snackbar snackbar = Snackbar
                            .make(v, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    finish();
                                }
                            });

// Changing message text color
                    snackbar.setActionTextColor(Color.GREEN);


// Changing action button text color
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                }
            }
        });
        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected(getApplicationContext())) {
                    startActivity(new Intent(getApplicationContext(), GalleryActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                else{
                    Snackbar snackbar = Snackbar
                            .make(v, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    finish();
                                }
                            });

// Changing message text color
                    snackbar.setActionTextColor(Color.GREEN);


// Changing action button text color
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();   }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (plays)
        soundPool.autoPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopSound();
        finishAffinity();
    }



    public void pauseSound() {
        if (plays) {
            soundPool.stop(soundID);
          //  soundID = soundPool.load(this, R.raw.om, counter);
            plays = false;
        }
    }

    public void stopSound() {
        if (plays) {
            soundPool.stop(soundID);
            //soundID = soundPool.load(this, R.raw.om, counter);
            plays = false;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
           case R.id.menu_news:
                // Single menu item is selected do something
                // Ex: launching new activity/screen or show alert message
                startActivity(new Intent(getApplicationContext(),NewsListActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;
            case R.id.menu_petti:
                // Single menu item is selected do something
                // Ex: launching new activity/screen or show alert message
                startActivity(new Intent(getApplicationContext(),Utharavu_porul.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;
           /*  case R.id.menu_radio:
                // Single menu item is selected do something
                // Ex: launching new activity/screen or show alert message
                startActivity(new Intent(getApplicationContext(),RadioFMActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;
            case R.id.menu_gallery:
                // Single menu item is selected do something
                // Ex: launching new activity/screen or show alert message
                startActivity(new Intent(getApplicationContext(),GalleryActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;

           */
            case R.id.kanthasasti:
                Intent ii=new Intent(getApplicationContext(), EssayWebviewActivity.class);
                ii.putExtra("url", "kantha_sasti");
                startActivity(ii);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;
                     /* case R.id.menu_stop:

                soundPool.autoPause();
                Toast.makeText(getApplicationContext(),"Music stopped",Toast.LENGTH_LONG).show();
                return true;*/


            case R.id.privacy:
                startActivity(new Intent(getApplicationContext(),PrivacyPolicyActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;
            case R.id.rateus:
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(myAppLinkToMarket);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
                }
                return true;

            case R.id.share:
                int applicationNameId = getApplicationContext().getApplicationInfo().labelRes;
                final String appPackageName = getApplicationContext().getPackageName();
                Intent i1 = new Intent(Intent.ACTION_SEND);
                i1.setType("text/plain");
                i1.putExtra(Intent.EXTRA_SUBJECT, this.getString(applicationNameId));
                String text = "Install this cool application: ";
                String link = "https://play.google.com/store/apps/details?id=" + appPackageName;
                i1.putExtra(Intent.EXTRA_TEXT, text + " " + link);
                startActivity(Intent.createChooser(i1, "Share link:"));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();


            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {

                JSONObject obj = new JSONObject(result);
                JSONArray array=obj.getJSONArray("murugan");
                Log.e("arrray= ", array.toString());
                titleList=new ArrayList<>();
                urlList=new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {

                    JSONObject jsn = array.getJSONObject(i);

                    titleList.add(jsn.getString("title").trim());
                    urlList.add(jsn.getString("url").trim());
                }
                Log.e("titlelist= ", titleList.toString());
                Log.e("Urllist= ", urlList.toString());
                Gson gson = new Gson();
                editor.putString("titleList",gson.toJson(titleList));
                editor.putString("urlList",gson.toJson(urlList));
                editor.commit();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
           // Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
        }
    }
    private class HttpAsyncTaskImages extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {

                JSONObject obj = new JSONObject(result);
                JSONArray array=obj.getJSONArray("murugan");
                Log.e("arrray= ", array.toString());
                titleList=new ArrayList<>();
                urlList=new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {

                    JSONObject jsn = array.getJSONObject(i);

                    titleList.add(jsn.getString("title").trim());
                    urlList.add(jsn.getString("url").trim());
                }
                Log.e("titlelist= ", titleList.toString());
                Log.e("Urllist= ", urlList.toString());
                Gson gson = new Gson();
                editor.putString("titleListImages",gson.toJson(titleList));
                editor.putString("urlListImages",gson.toJson(urlList));
                editor.commit();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            // Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
        }
    }
    private class HttpAsyncTaskRadio extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {

                JSONObject obj = new JSONObject(result);
                JSONArray array=obj.getJSONArray("murugan");
                Log.e("arrray= ", array.toString());
                titleList=new ArrayList<>();
                urlList=new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {

                    JSONObject jsn = array.getJSONObject(i);

                    titleList.add(jsn.getString("title").trim());
                    urlList.add(jsn.getString("url").trim());
                }
                Log.e("titlelist= ", titleList.toString());
                Log.e("Urllist= ", urlList.toString());
                Gson gson = new Gson();
                editor.putString("titleListRadio",gson.toJson(titleList));
                editor.putString("urlListRadio",gson.toJson(urlList));
                editor.commit();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            // Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
        }
    }
    private class HttpAsyncTaskNews extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String newsresult) {
            try {
                JSONObject rootobj=new JSONObject(newsresult);
                JSONArray array=rootobj.getJSONArray("murugan_news");
                ArrayList<CommonModel> arraylistnews=new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {

                    JSONObject jsn = array.getJSONObject(i);
                    Log.e("description", jsn.getString("description"));
                    Log.e("date",  jsn.getString("date_to_show "));

                    arraylistnews.add(new CommonModel(jsn.getString("description"),jsn.getString("date_to_show ")));

                }

                SharedPreferences pref = getApplicationContext().getSharedPreferences("dataPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                Gson gson = new Gson();
                editor.putString("listnews",gson.toJson(arraylistnews));
                editor.commit();

            } catch (JSONException e) {
                Log.e("exception",e.toString());
                e.printStackTrace();
            }
            // Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
        }
    }
    private class HttpAsyncTaskPetti extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String newsresult) {
            try {
                JSONObject rootobj=new JSONObject(newsresult);
                JSONArray array=rootobj.getJSONArray("sivanmalai_porul");
                ArrayList<CommonModelPetti> arraylistnews=new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {

                    JSONObject jsn = array.getJSONObject(i);
                  /*  Log.e("description", jsn.getString("description"));
                    Log.e("date",  jsn.getString("date_to_show "));*/

                    arraylistnews.add(new CommonModelPetti(jsn.getString("name"),jsn.getString("date_created"),jsn.getString("address")));

                }

                SharedPreferences pref = getApplicationContext().getSharedPreferences("dataPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                Gson gson = new Gson();
                editor.putString("listpetti",gson.toJson(arraylistnews));
                editor.commit();

            } catch (JSONException e) {
                Log.e("exception",e.toString());
                e.printStackTrace();
            }
            // Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
        }
    }


   /* @Override
    public void onUpdateNeeded(final String updateUrl) {
        AlertDialog dialog = new AlertDialog.Builder(this,R.style.MyAlertDialogStyle)
                .setTitle("New version available")
                .setMessage("Please, update app to new version .")
                .setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                redirectStore(updateUrl);
                            }
                        }).setNegativeButton("Later",null
                        ).create();
        dialog.show();
    }

    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }*/
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_news:
                // Single menu item is selected do something
                // Ex: launching new activity/screen or show alert message
                startActivity(new Intent(getApplicationContext(), NewsListActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            case R.id.nav_songs:
                // Single menu item is selected do something
                // Ex: launching new activity/screen or show alert message
                startActivity(new Intent(getApplicationContext(),BakthiPaadalgal.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;
             case R.id.nav_fm:
                // Single menu item is selected do something
                // Ex: launching new activity/screen or show alert message
                startActivity(new Intent(getApplicationContext(),RadioFMActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;
            case R.id.nav_gallery:
                // Single menu item is selected do something
                // Ex: launching new activity/screen or show alert message
                startActivity(new Intent(getApplicationContext(),GalleryActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;
            case R.id.nav_kanthasasti:
                Intent ii=new Intent(getApplicationContext(), EssayWebviewActivity.class);
                ii.putExtra("url", "kantha_sasti");
                startActivity(ii);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;

            case R.id.nav_privacy:
                startActivity(new Intent(getApplicationContext(), PrivacyPolicyActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            case R.id.nav_rateus:
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(myAppLinkToMarket);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
                }
                return true;

            case R.id.nav_share:
                int applicationNameId = getApplicationContext().getApplicationInfo().labelRes;
                final String appPackageName = getApplicationContext().getPackageName();
                Intent i1 = new Intent(Intent.ACTION_SEND);
                i1.setType("text/plain");
                i1.putExtra(Intent.EXTRA_SUBJECT, this.getString(applicationNameId));
                String text = "Install this cool application: ";
                String link = "https://play.google.com/store/apps/details?id=" + appPackageName;
                i1.putExtra(Intent.EXTRA_TEXT, text + " " + link);
                startActivity(Intent.createChooser(i1, "Share link:"));
                return true;


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }/*
    class Background extends  AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run()
                {
                    //Do your UI operations like dialog opening or Toast here
                    try {
                        ForceUpdateChecker.with(MainActivity.this).onUpdateNeeded(MainActivity.this).check();
                        RateThisApp.onCreate(MainActivity.this);
                        // If the condition is satisfied, "Rate this app" dialog will be shown
                        RateThisApp.showRateDialogIfNeeded(MainActivity.this);
                        RateThisApp.Config config = new RateThisApp.Config(3, 5);
                        RateThisApp.init(config);
                        RateThisApp.setCallback(new RateThisApp.Callback() {
                            @Override
                            public void onYesClicked() {
                                RateThisApp.stopRateDialog(getApplicationContext());
                            }

                            @Override
                            public void onNoClicked() {
                                //Toast.makeText(MainActivity.this, "No event", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelClicked() {
                                //Toast.makeText(MainActivity.this, "Cancel event", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } catch (Exception e) {
                        Log.e("exception", e.toString());
                    }

                }
            });
            return null;
        }
    }*/
    protected void createNetErrorDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You need a network connection to use this application. Please turn on mobile network or Wi-Fi in Settings.")
                .setTitle("Unable to connect")
                .setCancelable(false)
                .setPositiveButton("Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                startActivity(i);
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                               finish();
                            }
                        }
                );
        AlertDialog alert = builder.create();
        alert.show();
    }
    private boolean isConnected(Context ctx){
        ConnectivityManager connMgr = (ConnectivityManager)ctx.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

}