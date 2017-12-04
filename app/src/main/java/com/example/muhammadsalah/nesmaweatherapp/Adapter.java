package com.example.muhammadsalah.nesmaweatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Muhammad Salah on 11/27/2017.
 */

public class Adapter extends ArrayAdapter<Item> {

    public Adapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return 0;
        else
            return 1;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Item item = getItem(position);
        //View listItemView = convertView;
        if(position==0){
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.firstitem,parent,false);
            }
            TextView day = (TextView) convertView.findViewById(R.id.day1);
            TextView status = (TextView) convertView.findViewById(R.id.status1);
            TextView upper = (TextView) convertView.findViewById(R.id.upperdegree1);
            TextView lower = (TextView) convertView.findViewById(R.id.lowerdegree1);
            ImageView image = (ImageView) convertView.findViewById(R.id.img1);

            day.setText(item.day);
            status.setText(item.status);
            upper.setText(item.upper);
            lower.setText(item.lower);
            image.setImageResource(item.img);

            return convertView;
        }
        else{

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        TextView day = (TextView) convertView.findViewById(R.id.day);
        TextView status = (TextView) convertView.findViewById(R.id.status);
        TextView upper = (TextView) convertView.findViewById(R.id.upperdegree);
        TextView lower = (TextView) convertView.findViewById(R.id.lowerdegree);
        ImageView image = (ImageView) convertView.findViewById(R.id.img);

        day.setText(item.day);
        status.setText(item.status);
        upper.setText(item.upper);
        lower.setText(item.lower);
        image.setImageResource(item.img);

        return convertView;


        }
    }




}
