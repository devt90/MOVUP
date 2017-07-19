package com.example.ayushsethi.movup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Api.Api;

public class DetailsActivity extends AppCompatActivity {
    public static JSONObject link;
    public static String url;
    public static String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        Bundle extras = getIntent().getExtras();
        url=null;
        title=extras.get("title").toString();
        String overview=extras.get("overview").toString();
        String release_date=extras.get("release_date").toString();
        String poster_path=extras.get("poster_path").toString();
        String rating =extras.get("rating").toString();
        TextView txt=(TextView)findViewById(R.id.title);
        txt.setText(title);
        txt=(TextView)findViewById(R.id.overview);
        txt.setText(overview);
        String year=release_date.substring(0,4);
        txt=(TextView)findViewById(R.id.year);
        txt.setText("Release year : "+year);
        txt=(TextView)findViewById(R.id.rating);
        txt.setText("Rating : "+rating);
        ImageView img=(ImageView)findViewById(R.id.poster);
        Picasso.with(this).load(Api.IMAGE_URL+Api.IMAGE_SIZE_300+poster_path).fit().into(img);
        RatingBar ratingbar=(RatingBar)findViewById(R.id.ratingbar);
        //ratingbar.setStepSize((float)0.5);
        ratingbar.setMax(10);
        ratingbar.setRating(Float.parseFloat(rating));
        int id=extras.getInt("id");
        String ss="https://api.themoviedb.org/3/movie/"+Integer.toString(id)+"/videos?api_key="+Api.API_KEY+"&language=en-US";
        //Toast.makeText(this,ss,Toast.LENGTH_SHORT).show();
        Ion.with(this).load(ss).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                try{
                    JSONObject object=new JSONObject(result);
                    JSONArray array=object.getJSONArray("results");
                    JSONObject object1=array.getJSONObject(0);
                    url=object1.getString("key");
                    Toast.makeText(DetailsActivity.this,"Thanks For Watching",Toast.LENGTH_SHORT).show();
                }
                catch (JSONException rr)
                {
                    rr.printStackTrace();
                }
            }
        });
        Button but=(Button)findViewById(R.id.trailer);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(url==null)
                {
                    Toast.makeText(DetailsActivity.this,"NO TRAILER FOUND",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    String finalurl="https://www.youtube.com/watch?v="+url;
                    Intent i=new Intent(DetailsActivity.this,Youtube.class);
                    i.putExtra("link",finalurl);
                    i.putExtra("title",title);
                    startActivity(i);
                }
            }
        });


    }
}
