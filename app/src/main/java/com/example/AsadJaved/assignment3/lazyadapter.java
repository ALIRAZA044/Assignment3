package com.example.AsadJaved.assignment3;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abdulmoiedawan.assignment3.R;

import java.util.List;

/**
 * Created by Abdul Moied Awan on 3/20/2016.
 */
public class lazyadapter extends BaseAdapter {
    private Context activity;
    private List<Bitmap> img;
    private List<String> des;
    private static LayoutInflater inflater=null;
    public  ImageView imageLoader;

    public lazyadapter(Context context, MovieData mv)
    {
        activity=context;
        inflater = LayoutInflater.from(context);
        img=mv.getImgurl();
        des=mv.getDescription();
      //  inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return img.size();
    }


    public Object getImg(int position) {
        return img.get(position);
    }
    public Object getdescription(int pos)
    {
        return des.get(pos);

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clearData() {
        // clear the data
        img.clear();
        des.clear();
    }
    public void AddAll(MovieData mv)
    {
        img=mv.getImgurl();
        des=mv.getDescription();

    }
public void Clear()
{
    img.clear();
    des.clear();

}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Bitmap s = img.get(position);
        String txt=des.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_forecast, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.list_item_forecast_textview);
            holder.pic = (ImageView) convertView.findViewById(R.id.list_item_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(txt);
        if (s != null) {
            holder.pic.setImageBitmap(s);
        } else {
            // MY DEFAULT IMAGE
            holder.pic.setImageResource(R.drawable.generic_profile_man);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView name;

        ImageView pic;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

    }
}

