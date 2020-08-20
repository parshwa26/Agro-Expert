package com.example.jau.agroexpert.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jaina on 20/03/18.
 */

public class BeanDistrict implements Parcelable{

    @SerializedName("did")
    public String did;

    @SerializedName("district")
    public String district;

    BeanDistrict(String did, String district){
        this.did = did;
        this.district = district;
    }

    protected BeanDistrict(Parcel in) {
        did = in.readString();
        district = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(did);
        dest.writeString(district);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BeanDistrict> CREATOR = new Creator<BeanDistrict>() {
        @Override
        public BeanDistrict createFromParcel(Parcel in) {
            return new BeanDistrict(in);
        }

        @Override
        public BeanDistrict[] newArray(int size) {
            return new BeanDistrict[size];
        }
    };
}
