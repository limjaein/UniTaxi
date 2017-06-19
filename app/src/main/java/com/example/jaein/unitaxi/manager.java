package com.example.jaein.unitaxi;

/**
 * Created by jaein on 2017-05-30.
 */

public class manager {
    private String ad_date;
    private String ad_time;
    private String ad_master;
    private String ad_people;
    private boolean ad_goback;
    private String ad_source;
    private String ad_dest;
    private int ad_partNum;
    private boolean ad_check; // 방의 존재 유무

    manager(){
    }

    manager(String date, String time, String master, String people, boolean goback, String source, String dest, int partnum){
        this.ad_date = date;
        this.ad_time = time;
        this.ad_master = master;
        this.ad_goback = goback;
        this.ad_people = people;
        this.ad_source = source;
        this.ad_dest = dest;
        this.ad_partNum = partnum;
        this.ad_check = true;
    }

    public String getAd_date(){
        return this.ad_date;
    }
    public int getAd_partNum(){
        return this.ad_partNum;
    }
    public String getAd_time(){
        return this.ad_time;
    }
    public String getAd_master(){ return this.ad_master;}
    public String getAd_people(){ return this.ad_people;}
    public boolean getAd_goback(){
        return this.ad_goback;
    }
    public boolean getAd_check(){
        return this.ad_check;
    }
    public String getAd_source(){
        return this.ad_source;
    }
    public String getAd_dest(){
        return this.ad_dest;
    }
    public void setAd_date(String ad_date){
        this.ad_date = ad_date;
    }
    public void setAd_check(Boolean check){this.ad_check = check;}
    public void setAd_people(String ad_people){
        this.ad_people = ad_people;
    }
    public void setAd_partNum(int ad_partNum){
        this.ad_partNum = ad_partNum;
    }
}
