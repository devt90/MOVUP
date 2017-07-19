package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ayushsethi.movup.MainActivity;
import com.example.ayushsethi.movup.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.zip.Inflater;

/**
 * Created by ayushsethi on 11/07/17.
 */

public class Main_adapter extends BaseAdapter {
    Context context;
    LayoutInflater inft;
    public Main_adapter(Context context)
    {
        this.context=context;
        inft=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return MainActivity.images.size();
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
        if(convertView==null) {
            convertView = inft.inflate(R.layout.main_image, null);
        }
        img=(ImageView)convertView.findViewById(R.id.img);
        if(position==0)
        {
            TextView txt=(TextView)convertView.findViewById(R.id.txt);
            txt.setText("IN CINEMA NOW");
        }
        else if(position==1)
        {
            TextView txt=(TextView)convertView.findViewById(R.id.txt);
            try {
                JSONObject obj = MainActivity.cat2Json.getJSONObject(0);
                String str=obj.getString("release_date");
                String ar=str.substring(0,4);
                txt.setText("BEST OF "+ar);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
        else if(position==2)
        {
            TextView txt=(TextView)convertView.findViewById(R.id.txt);
            txt.setText("MOST POPULAR");
        }
        else
        {
            TextView txt=(TextView)convertView.findViewById(R.id.txt);
            txt.setText("FOR KIDS");
        }
        Picasso.with(context).load(MainActivity.images.get(position)).into(img);
        return convertView;
    }
}
