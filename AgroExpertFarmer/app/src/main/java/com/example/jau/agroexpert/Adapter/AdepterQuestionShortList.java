package com.example.jau.agroexpert.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jau.agroexpert.Activity.WholeQuestion;
import com.example.jau.agroexpert.Bean.BeanSelectMyQuestion;
import com.example.jau.agroexpert.R;

import java.util.ArrayList;

public class AdepterQuestionShortList extends BaseAdapter {
    private ArrayList<BeanSelectMyQuestion> list;
    Context mContext;
    LayoutInflater inflter;
    int i=1;
    String Flag;




    public AdepterQuestionShortList(ArrayList<BeanSelectMyQuestion> beanSelectAnswered, Context context, String Flag) {
        this.list = beanSelectAnswered;
        this.mContext= context;
        this.Flag=Flag;
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
        if(listItemViewconvertView==null)
        {
            listItemViewconvertView = inflter.inflate(R.layout.single_question_row, parent,false);
            holder=new Holder(listItemViewconvertView);
            listItemViewconvertView.setTag(holder);
        }
        else {
            holder= (Holder) listItemViewconvertView.getTag();
        }
        int i=position;
        i++;
        if((position%2)==0)
        {
            holder.single_ll.setBackgroundColor(Color.parseColor("#ddffd8"));
        }
        else
        {
            holder.single_ll.setBackgroundColor(Color.parseColor("#d0e6ea"));
        }
        holder.question_counter.setText("Q#\n "+list.get(position).qid);
        holder.tv_question.setText(list.get(position).question);
        holder.tv_category.setText(list.get(position).category);
        holder.single_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, WholeQuestion.class);
               // Toast.makeText(mContext, list.get(position).qid, Toast.LENGTH_SHORT).show();
                intent.putExtra("qid",list.get(position).qid);
                intent.putExtra("Flag",Flag);
                intent.putExtra("fid",list.get(position).fid);
                mContext.startActivity(intent);
            }
        });
        return listItemViewconvertView;
    }

    public static class Holder{

        TextView tv_question,tv_category,question_counter;
        LinearLayout single_ll;
        public Holder(View v)
        {
            tv_question=v.findViewById(R.id.tv_question);
            tv_category=v.findViewById(R.id.tv_category);
            question_counter=v.findViewById(R.id.question_count);
            single_ll=v.findViewById(R.id.single_ll);
        }
    }

}