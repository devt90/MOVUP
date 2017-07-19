package com.example.ayushsethi.movup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import Adapter.Main_adapter;
import Api.Api;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<String> images=new ArrayList<String>();
    public static JSONArray cat1Json;
    public static JSONArray cat2Json;
    public static JSONArray cat3Json;
    public static JSONArray cat4Json;
    public static int var;
    public int checker=1;
    public static String yy;
    public static int check=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        var=0;
        check=1;
        init();
    }


    //function that initialises the activity
    public void init()
    {

        images.clear();
        Load ll=new Load(this);
        ll.execute("load");

    }


    //function fetching json using ION
    public void fetch()
    {
        Calendar cal=Calendar.getInstance();
        Calendar cal2=Calendar.getInstance();
        SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        cal2.add(Calendar.DAY_OF_MONTH,-31);
        cal.add(Calendar.DAY_OF_MONTH,1);
        String lte=format1.format(cal.getTime());
        String gte=format1.format(cal2.getTime());
        String str=Api.API_URL+"?api_key="+Api.API_KEY+"&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&primary_release_date.gte="+gte+"&primary_release_date.lte="+lte;
        String url;

        Ion.with(this).load(str).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray array = obj.getJSONArray("results");
                    JSONObject frst = array.getJSONObject(0);
                    //images.add("http://image.tmdb.org/t/p/w300/"+frst.getString("poster_path"));
                    cat1Json=array;
                    Log.e("hi",result);
                    var++;
                }
                catch (JSONException ee)
                {
                    ee.printStackTrace();
                }

            }
        });

        Random rand= new Random();
        int randy=rand.nextInt(2017-2000+1)+2000;
        String year=new Integer(randy).toString();
        Ion.with(this).load(Api.API_URL+"?api_key="+Api.API_KEY+"&language=en-US&sort_by=popularity.desc&page=1&primary_release_year="+year).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                try{
                    JSONObject obj = new JSONObject(result);
                    JSONArray array = obj.getJSONArray("results");
                    JSONObject frst = array.getJSONObject(0);
                    String str=frst.getString("release_date");
                    yy=str.substring(0,4);
                    //images.add("http://image.tmdb.org/t/p/w300/"+frst.getString("poster_path"));
                    cat2Json=array;
                    var++;
                }
                catch (JSONException r)
                {
                    r.printStackTrace();
                }
            }
        });

        Ion.with(this).load(Api.API_URL+"?api_key="+Api.API_KEY+"&language=en-US&sort_by=popularity.desc&page=1").asString().setCallback(new FutureCallback<String>() {
             @Override
             public void onCompleted(Exception e, String result) {
                 try {
                     JSONObject obj = new JSONObject(result);
                     JSONArray array = obj.getJSONArray("results");
                     cat3Json=array;
                     var++;
                 }
                 catch (JSONException r)
                 {
                     r.printStackTrace();
                 }
             }
         });

        Ion.with(this).load(Api.API_URL+"?api_key="+Api.API_KEY+"&language=en-US&sort_by=popularity.desc&certification_country=US&certification.lte=G&page=1").asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                try{
                    JSONObject obj = new JSONObject(result);
                    JSONArray array = obj.getJSONArray("results");
                    cat4Json=array;
                    var++;
                }
                catch (JSONException r)
                {
                    r.printStackTrace();
                }
            }
        });

    }


    //function to load the data from theimdb
    private class Load extends AsyncTask<String,Void,Void>{
        private ProgressDialog progressDialog;
        private Context context;
        Load(Context context)
        {
            this.context=context;
        }
        protected void onPreExecute() {
            super.onPreExecute();
          if(!isNetworkAvailable())
            {
                checker=0;
                Toast.makeText(context,"Network not available",Toast.LENGTH_SHORT).show();
            }
           else
          {checker=1;
              var=0;
            progressDialog = new ProgressDialog(this.context);
            progressDialog.setMessage("Fetching Data");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();}
        }
        @Override
        protected Void doInBackground(String... params) {
            if(checker==1) {
                fetch();
                while (var != 4) ;
            }
            return null;
        }
        protected void  onPostExecute(Void v){
            if(checker==1) {
                try {
                    JSONObject frst=cat1Json.getJSONObject(0);
                    images.add("http://image.tmdb.org/t/p/w300/"+frst.getString("poster_path"));
                    frst=cat2Json.getJSONObject(0);
                    images.add("http://image.tmdb.org/t/p/w300/" + frst.getString("poster_path"));
                    Random rand=new Random();
                    int randy=rand.nextInt(30);
                    frst=cat3Json.getJSONObject(randy);
                    images.add("http://image.tmdb.org/t/p/w300/" + frst.getString("poster_path"));
                    frst=cat4Json.getJSONObject(randy);
                    images.add("http://image.tmdb.org/t/p/w300/" + frst.getString("poster_path"));

                }
                catch (JSONException rt)
                {
                    rt.printStackTrace();
                }
                GridView gridview = (GridView) findViewById(R.id.gridview);
                Main_adapter main_adapter = new Main_adapter(getApplicationContext());
                gridview.setAdapter(main_adapter);
                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            if (check == 1) {
                                finishActivity(101);
                                check = 0;
                            }
                            Intent i = new Intent(MainActivity.this, MovieActivity.class);
                            i.putExtra("num", 1);
                            i.putExtra("title", "IN CINEMA NOW");
                            i.putExtra("cat", cat1Json.toString());
                            startActivityForResult(i, 101);
                            check = 1;
                        } else if (position == 1) {
                            if (check == 1) {
                                finishActivity(101);
                                check = 0;
                            }
                            Intent i = new Intent(MainActivity.this, MovieActivity.class);
                            i.putExtra("num", 2);
                            i.putExtra("title", "BEST OF " + yy);
                            i.putExtra("cat", cat2Json.toString());
                            startActivityForResult(i, 101);
                            check = 1;
                        } else if (position == 2) {
                            if (check == 1) {
                                finishActivity(101);
                                check = 0;
                            }
                            Intent i = new Intent(MainActivity.this, MovieActivity.class);
                            i.putExtra("num", 3);
                            i.putExtra("cat", cat3Json.toString());
                            i.putExtra("title", "MOST POPULAR");
                            startActivityForResult(i, 101);
                            check = 1;
                            // i.putExtras("code",101);

                            //finishActivity(101);
                        } else {
                            if (check == 1) {
                                finishActivity(101);
                                check = 0;
                            }
                            Intent i = new Intent(MainActivity.this, MovieActivity.class);
                            i.putExtra("num", 4);
                            i.putExtra("cat", cat4Json.toString());
                            i.putExtra("title", "FOR KIDS");
                            startActivityForResult(i, 101);
                            check = 1;

                        }
                    }
                });


                Toast.makeText(context, "Loaded", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }



        }

    }


    //for checking internet connection
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
