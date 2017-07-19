package com.example.ayushsethi.movup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapter.Movie_adapter;
import Api.Api;

import static android.R.attr.max;

public class MovieActivity extends AppCompatActivity {
    public  static ArrayList<String> images=new ArrayList<String>();
    public  static JSONArray help_array;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras=getIntent().getExtras();
        if(extras==null)
        {
            return ;
        }
        setTitle(extras.get("title").toString());
        setContentView(R.layout.activity_movie);
        int num=extras.getInt("num");
        images.clear();
        String rec=extras.getString("cat");
        try{
            JSONArray array=new JSONArray(rec);
            help_array=array;
            fetch(array);}
        catch (JSONException t)
        {
            t.printStackTrace();
        }
        GridView gridview=(GridView)findViewById(R.id.gridview);
        Movie_adapter adapter=new Movie_adapter(this);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    JSONObject obj=help_array.getJSONObject(position);
                    Intent i=new Intent(MovieActivity.this,DetailsActivity.class);
                    i.putExtra("title",obj.getString("title"));
                    i.putExtra("release_date",obj.getString("release_date"));
                    i.putExtra("poster_path",obj.getString("poster_path"));
                    i.putExtra("rating",obj.getInt("vote_average"));
                    i.putExtra("overview",obj.getString("overview"));
                    int ide=obj.getInt("id");
                    i.putExtra("id",ide);
                    startActivity(i);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        });


    }


    //adding images to Adapter using Json Array
    public void fetch(JSONArray array)
    {
        int i=0;
        for(i=0;i<array.length();i++)
        {
           try{
            JSONObject temp=array.getJSONObject(i);
            String mystr=Api.IMAGE_URL+Api.IMAGE_SIZE_300+temp.getString("poster_path");
            images.add(mystr);}
           catch (JSONException r)
           {
               r.printStackTrace();
           }
        }
    }

}
