package com.agroexpert.expert.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jaina on 20/03/18.
 */

public class BeanSubCategory implements Parcelable{


    @SerializedName("pid")
    public String pid;

    @SerializedName("category_flag")
    public String category_flag;


    protected BeanSubCategory(Parcel in) {
        pid = in.readString();
        category_flag = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pid);
        dest.writeString(category_flag);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BeanSubCategory> CREATOR = new Creator<BeanSubCategory>() {
        @Override
        public BeanSubCategory createFromParcel(Parcel in) {
            return new BeanSubCategory(in);
        }

        @Override
        public BeanSubCategory[] newArray(int size) {
            return new BeanSubCategory[size];
        }
    };
}
