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
    private double ad_lat;
    private double ad_lon;
    private int ad_partNum;
    private int ad_wholeNum;

    manager(){
        this.ad_date = "00000000";
        this.ad_time = "00000000";
        this.ad_goback = true;
        this.ad_lat = 0.0;
        this.ad_lon = 0.0;
        this.ad_partNum = 0;
        this.ad_wholeNum = 0;
    }

    manager(String date, String time, String master, String people, boolean goback, double lat, double lon, int partnum, int wholenum){
        this.ad_date = date;
        this.ad_time = time;
        this.ad_master = master;
        this.ad_goback = goback;
        this.ad_people = people;
        this.ad_lat = lat;
        this.ad_lon = lon;
        this.ad_partNum = partnum;
        this.ad_wholeNum = wholenum;
    }

    public String getAd_date(){
        return this.ad_date;
    }
    public int getAd_partNum(){
        return this.ad_partNum;
    }
    public int getAd_wholeNum(){
        return this.ad_wholeNum;
    }
    public String getAd_time(){
        return this.ad_time;
    }
    public String getAd_master(){ return this.ad_master;}
    public String getAd_people(){ return this.ad_people;}
    public boolean getAd_goback(){
        return this.ad_goback;
    }
    public double getAd_lat(){
        return this.ad_lat;
    }
    public double getAd_lon(){
        return this.ad_lon;
    }
    public void setAd_date(String ad_date){
        this.ad_date = ad_date;
    }
    public void setAd_partNum(int ad_partNum){
        this.ad_partNum = ad_partNum;
    }
    public void setAd_wholeNum(int ad_wholeNum){
        this.ad_wholeNum = ad_wholeNum;
    }
}
