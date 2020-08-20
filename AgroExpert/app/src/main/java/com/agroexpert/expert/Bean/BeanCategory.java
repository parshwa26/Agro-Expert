package com.agroexpert.expert.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jaina on 20/03/18.
 */

public class BeanCategory implements Parcelable{

    @SerializedName("cid")
    public String cid;

    @SerializedName("category_flag")
    public String category_flag;

    public  BeanCategory(String cid,String category){
        this.cid = cid;
        this.category_flag = category;
    }


    protected BeanCategory(Parcel in) {
        cid = in.readString();
        category_flag = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cid);
        dest.writeString(category_flag);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BeanCategory> CREATOR = new Creator<BeanCategory>() {
        @Override
        public BeanCategory createFromParcel(Parcel in) {
            return new BeanCategory(in);
        }

        @Override
        public BeanCategory[] newArray(int size) {
            return new BeanCategory[size];
        }
    };
}
