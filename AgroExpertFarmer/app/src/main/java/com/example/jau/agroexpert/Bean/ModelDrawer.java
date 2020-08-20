package com.example.jau.agroexpert.Bean;

/**
 * Created by Jaina on 13/12/17.
 */

public class ModelDrawer {

    private String version_name;
    private String version_number;
    private int img_sourceid;


    public ModelDrawer(String version_name, int img_sourceid){

        this.version_name = version_name;
        this.version_number = version_number;
        this.img_sourceid = img_sourceid;

    }

 public String getVersion_name(){
        return version_name;
    }

    public int getImg_sourceid(){
        return img_sourceid;
    }

}
