package com.agroexpert.expert.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agroexpert.expert.Activity.ActivityInstruction;
import com.agroexpert.expert.Activity.WholeQuestion;
import com.agroexpert.expert.Bean.BeanSelectMyQuestion;
import com.agroexpert.expert.R;


import java.util.ArrayList;

public class AdapterSuggestion extends BaseAdapter {
    private ArrayList<BeanSelectMyQuestion> list;
    Context mContext;
    LayoutInflater inflter;




    public AdapterSuggestion(ArrayList<BeanSelectMyQuestion> beanSuggestion, Context context) {
        this.list = beanSuggestion;
        this.mContext= context;
        inflter = (LayoutInflater.from(mContext));
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
    public View getView(final int position, @Nullable View listItemViewconvertView, @NonNull ViewGroup parent) {

        Holder holder;
        if (listItemViewconvertView == null) {
            listItemViewconvertView = inflter.inflate(R.layout.single_row_suggestion, parent, false);
            holder = new Holder(listItemViewconvertView);
            listItemViewconvertView.setTag(holder);
        } else {
            holder = (Holder) listItemViewconvertView.getTag();
        }

        holder.instruction_id.setText("I#\n"+list.get(position).iid);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
       {
           holder.tv_instruction.setText(Html.fromHtml(list.get(position).title, Html.FROM_HTML_MODE_COMPACT));
       }
       else
       {
           holder.tv_instruction.setText(Html.fromHtml(list.get(position).title));
       }
        if((position%2)==0)
        {
            holder.single_ll.setBackgroundColor(Color.parseColor("#ddffd8"));
        }
        else
        {
            holder.single_ll.setBackgroundColor(Color.parseColor("#d0e6ea"));
        }
        holder.single_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, ActivityInstruction.class);
                intent.putExtra("Flag","INS");
                intent.putExtra("iid",list.get(position).iid);
                mContext.startActivity(intent);
            }
        });

 return listItemViewconvertView;
    }

    public static class Holder{

        TextView tv_instruction,instruction_id;
        LinearLayout single_ll;

        public Holder(View v)
        {
            tv_instruction=v.findViewById(R.id.tv_instruction);
            instruction_id=v.findViewById(R.id.instruction_id);
            single_ll=v.findViewById(R.id.single_ll);
        }
    }

}