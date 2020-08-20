package com.agroexpert.expert.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agroexpert.expert.Bean.BeanSubCategory;
import com.agroexpert.expert.R;

import java.util.ArrayList;

/**
 * Created by Jaina on 14/01/18.
 */

public class AdapterSubCategory extends BaseAdapter {


    private ArrayList<BeanSubCategory> list;
    Context mContext;
    LayoutInflater inflter;

    public AdapterSubCategory(ArrayList<BeanSubCategory> list, Context context) {
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

        listItemView = inflter.inflate(R.layout.row_item, null);
        TextView tv_title;
        TextView tv_id;

        tv_title = (TextView) listItemView.findViewById(R.id.tv_title);
        tv_id = (TextView) listItemView.findViewById(R.id.tv_id);

        tv_title.setText(list.get(position).category_flag);
        tv_id.setText(list.get(position).pid);
        return listItemView;


    }

}

