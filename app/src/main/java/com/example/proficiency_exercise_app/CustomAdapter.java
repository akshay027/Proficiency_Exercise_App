package com.example.proficiency_exercise_app;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private Context context; //context
    private ArrayList<String> items; //data source of the list adapter
    private MainActivity activity;

    //public constructor
    public CustomAdapter(Context context, ArrayList<String> items) {
        this.context = context;
        this.items = items;

    }

    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.sub_list_view, parent, false);
        }

        // get current item to be displayed
        //MainCoursesDetails currentItem = (MainCoursesDetails) getItem(position);

        // get the TextView for item name and item description
        TextView description = convertView.findViewById(R.id.description_view);
        TextView title= convertView.findViewById(R.id.title_vew);
        ImageView image=convertView.findViewById(R.id.image_view);// get the reference of ImageView
       // descriptionView.setText(currentItem.getDescription()); // set logo images

        //textView.setText(currentItem.getUpcasedName());

        // returns the view for the current row
        return convertView;
    }
}
