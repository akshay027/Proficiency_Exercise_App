package com.example.proficiency_exercise_app;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private Context context; //context
    private ArrayList<Row> items; //data source of the list adapter
    private MainActivity activity;
    public static CustomAdapter.OnItemClickListener mItemClickListener;
    //public constructor
    public CustomAdapter(Context context, ArrayList<Row> items) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.sub_list_view, parent, false);
        }

        //get current item to be displayed
        Row rowItem = (Row) getItem(position);

        //get the TextView for item name and item description
        TextView description = convertView.findViewById(R.id.description_view);
        TextView title = convertView.findViewById(R.id.title_vew);
        ImageView image = convertView.findViewById(R.id.image_view);// get the reference of ImageView

        if (rowItem.getDescription() == null) {
            description.setText("No Data ");
        } else {
            description.setText(rowItem.getDescription()); // set logo images
        }
        if (rowItem.getTitle() == null) {
            title.setText("No Data ");
        } else {
            title.setText(rowItem.getTitle());
        }
        if (rowItem.getImageHref() == null) {

        } else {
            Glide.with(context)
                    .load(rowItem.getImageHref())
                    .into(image);
        }
        // returns the view for the current row

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(v, position);
            }
        });
        return convertView;
    }

    public interface OnItemClickListener {

        public void onItemClick(View view, int position);



    }

    public void SetOnItemClickListener(CustomAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}
