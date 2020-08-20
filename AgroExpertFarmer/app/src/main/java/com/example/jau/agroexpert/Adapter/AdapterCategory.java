package com.example.jau.agroexpert.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.RequestQueue;
import com.example.jau.agroexpert.Activity.SelectSubCategory;
import com.example.jau.agroexpert.Bean.BeanCategory;
import com.example.jau.agroexpert.Bean.BeanSubCategory;
import com.example.jau.agroexpert.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jaina on 14/01/18.
 */

public class AdapterCategory extends BaseAdapter {


    private ArrayList<BeanCategory> list;
    Context mContext;
    LayoutInflater inflter;


    static int i=0,j=0;

    ArrayList<BeanCategory> beanCategories;


    String c_id,category_name,sub_category_name;
    int k=0;
    int last_position=0,recent_position=0;

    



    public AdapterCategory(ArrayList<BeanCategory> list, Context context) {
        this.list = list;
        this.mContext=context;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View listItemViewconvertView, @NonNull ViewGroup parent) {


        View listItemView = listItemViewconvertView;
        // beanCategories = new ArrayList<>();

        listItemView = inflter.inflate(R.layout.row_item, null);
        TextView tv_title;
        TextView tv_id;
        LinearLayout ll;

        tv_title = (TextView) listItemView.findViewById(R.id.tv_title);
        tv_id = (TextView) listItemView.findViewById(R.id.tv_id);

        ll = listItemView.findViewById(R.id.ll);

        tv_title.setText(list.get(position).category_flag);
        tv_id.setText(list.get(position).cid);

        return listItemView;

    }

}

