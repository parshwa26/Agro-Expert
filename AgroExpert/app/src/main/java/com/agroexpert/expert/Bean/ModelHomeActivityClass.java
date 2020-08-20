package com.agroexpert.expert.Bean;

/**
 * Created by Jaina on 14/12/17.
 */

public class ModelHomeActivityClass {

    private int  ig_home_img;
    private String tv_title_home,tv_time_home;


    public ModelHomeActivityClass(String tv_title_home, int ig_home_img, String tv_time_home){

        this.tv_title_home = tv_title_home;
        this.ig_home_img = ig_home_img;
        this.tv_time_home = tv_time_home;

    }


    public String getTv_title_home(){
        return tv_title_home;
    }

    public int getIg_home_img(){
        return ig_home_img;
    }

    public String getTv_time_home(){
        return tv_time_home;
    }



}
