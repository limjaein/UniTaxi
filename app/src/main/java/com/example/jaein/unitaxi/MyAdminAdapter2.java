package com.example.jaein.unitaxi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.jaein.unitaxi.f02_Fragment.admin_list;
import static com.example.jaein.unitaxi.u02_Login_Activity.db_manager;
import static com.example.jaein.unitaxi.u02_Login_Activity.loginId;

/**
 * Created by DoKyung on 2017. 6. 20..
 */

public class MyAdminAdapter2 extends BaseAdapter {
    ArrayList<manager> data;
    Context context;
    int layout;
    LayoutInflater lf;

    TextView tv_dest, tv_source, tv_date, tv_time;

    public MyAdminAdapter2(Context context, int layout, ArrayList<manager> data){
        this.data = data;
        this.layout = layout;
        this.context = context;
        lf = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = lf.inflate(R.layout.admin_list_row2,null);
        }

        tv_dest = (TextView) convertView.findViewById(R.id.L_dest2);
        tv_source = (TextView) convertView.findViewById(R.id.L_source2);
        tv_date = (TextView) convertView.findViewById(R.id.date2);
        tv_time = (TextView) convertView.findViewById(R.id.time2);


        String date = "";
        date += data.get(position).getAd_date().substring(0,4)+"년 ";
        date += data.get(position).getAd_date().substring(4,6)+"월 ";
        date += data.get(position).getAd_date().substring(6,8)+"일";

        tv_date.setText(date);
        String time = "";
        time += data.get(position).getAd_time().substring(0,2)+"시";
        time += data.get(position).getAd_time().substring(2,4)+"분";

        tv_time.setText(time);

        tv_dest.setText(data.get(position).getAd_dest());
        tv_source.setText(data.get(position).getAd_source());

        return convertView;
    }
}
