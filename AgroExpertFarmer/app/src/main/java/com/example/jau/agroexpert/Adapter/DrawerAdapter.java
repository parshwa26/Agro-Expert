package com.example.jau.agroexpert.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.jau.agroexpert.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Jaina on 13/12/17.
 */

public class DrawerAdapter extends BaseExpandableListAdapter {


    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    Context _context;

   /* //Parameterised Constructor
    public DrawerAdapter(@NonNull Activity context, ArrayList<ModelDrawer> versions) {
        super(context,0,versions);
    }*/

    public DrawerAdapter(Context context, List<String> listDataHeader,
                         HashMap<String,List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;

    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_view_item_row, null);
        }



        TextView version_name = (TextView) convertView.findViewById(R.id.version_name);
        version_name.setTypeface(null, Typeface.BOLD);
        version_name.setText(headerTitle);


        version_name.setTextColor(R.color.black);


        return convertView;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_view_item_row, null);
        }

        TextView version_name = (TextView) convertView.findViewById(R.id.version_name);
        version_name.setTextColor(R.color.cardview_dark_background);
        version_name.setText(childText);

        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
