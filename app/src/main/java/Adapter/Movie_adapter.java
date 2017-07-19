package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ayushsethi.movup.MovieActivity;
import com.example.ayushsethi.movup.R;
import com.squareup.picasso.Picasso;

/**
 * Created by ayushsethi on 12/07/17.
 */

public class Movie_adapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inft;
    public Movie_adapter(Context context)
    {
        this.context=context;
        inft=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return MovieActivity.images.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView img;
        if(convertView==null)
        {
            convertView=inft.inflate(R.layout.movie_image,null);
        }
        img=(ImageView)convertView.findViewById(R.id.img);

        Picasso.with(context).load(MovieActivity.images.get(position)).into(img);
        return convertView;
    }
}
