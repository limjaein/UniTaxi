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
 * Created by jaein on 2017-06-09.
 */

public class MyAdminAdapter extends BaseAdapter{
    ArrayList<manager> data;
    Context context;
    int layout;
    LayoutInflater lf;

    TextView tv_dest, tv_source, tv_date, tv_time, tv_number;

    public MyAdminAdapter(Context context, int layout, ArrayList<manager> data){
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
            convertView = lf.inflate(R.layout.admin_list_row,null);
        }

        tv_dest = (TextView) convertView.findViewById(R.id.L_dest);
        tv_source = (TextView) convertView.findViewById(R.id.L_source);
        tv_date = (TextView) convertView.findViewById(R.id.date);
        tv_time = (TextView) convertView.findViewById(R.id.time);
        tv_number = (TextView) convertView.findViewById(R.id.L_number);
        final ImageView Btn = (ImageView)convertView.findViewById(R.id.inBtn);


        String date = "";
        date += data.get(position).getAd_date().substring(0,4)+"년 ";
        date += data.get(position).getAd_date().substring(4,6)+"월 ";
        date += data.get(position).getAd_date().substring(6,8)+"일";

        tv_date.setText(date);
        String time = "";
        time += data.get(position).getAd_time().substring(0,2)+"시";
        time += data.get(position).getAd_time().substring(2,4)+"분";

        tv_time.setText(time);
        tv_number.setText(data.get(position).getAd_partNum()+"명");

        tv_dest.setText(data.get(position).getAd_dest());
        tv_source.setText(data.get(position).getAd_source());

        // 버튼 체크
        manager check = admin_list.get(position);
        String pp = check.getAd_people();

        String[] pp_list = pp.split(",");
        ArrayList<String> new_list1 = new ArrayList<String>();

        for (int i = 0; i < pp_list.length; i++) {
            new_list1.add(pp_list[i]);
        }

        for (int i = 0; i < new_list1.size(); i++) {
            if ((new_list1.get(i)).equals(loginId)) {
                Btn.setImageResource(R.drawable.button_out);
            }else{
                Btn.setImageResource(R.drawable.button_in);
            }
        }

        final String f_date = data.get(position).getAd_date();

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable in = Btn.getDrawable();
                Drawable in1 = context.getResources().getDrawable(R.drawable.button_in);

                Bitmap tmpBitmap = ((BitmapDrawable) in).getBitmap();  //지금 적용된 이미지
                Bitmap tmpBitmap1 = ((BitmapDrawable) in1).getBitmap();

                if (tmpBitmap.equals(tmpBitmap1))    //빈 버튼일때
                {
                    Btn.setImageResource(R.drawable.button_out);
                    Boolean ischeck = false;
                    manager newMan = admin_list.get(position);
                    int previous_num = newMan.getAd_partNum();

                    //실제로 목록에 넣어주기
                    String people = newMan.getAd_people();

                    String[] p_list = people.split(",");
                    ArrayList<String> new_list = new ArrayList<String>();

                    for (int i = 0; i < p_list.length; i++) {
                        new_list.add(p_list[i]);
                    }

                    for (int i = 0; i < new_list.size(); i++) {
                        if ((new_list.get(i)).equals(loginId)) {
                            ischeck = true;
                        }
                    }
                    if (ischeck) {//목록에있다.

                    } else {
                        newMan.setAd_partNum(previous_num + 1); // 사람 수 1 늘려주기
                        String new_str = "";
                        new_list.add(loginId);
                        for (int i = 0; i < new_list.size(); i++) { // 새로운 문자열 생성
                            if (new_list.get(i) == "")
                                continue;
                            if (i == new_list.size() - 1) {
                                new_str += new_list.get(i);
                            } else {
                                new_str += new_list.get(i) + ",";
                            }
                        }
                        newMan.setAd_people(new_str);

                        db_manager.child(f_date).setValue(newMan);
                    }
                }
                else {
                    //Btn.setImageResource(R.drawable.button_in);
                    manager newMan = admin_list.get(position);
                    //실제로 목록에서 제외하기
                    String people = newMan.getAd_people();
                    String[] p_list = people.split(",");
                    ArrayList<String> new_list = new ArrayList<String>();
                    for (int i = 0; i < p_list.length; i++) {
                        new_list.add(p_list[i]);
                    }
                    for (int i = 0; i < new_list.size(); i++) {
                        if ((new_list.get(i)).equals(loginId)) {
                            int previous_num = newMan.getAd_partNum();
                            newMan.setAd_partNum(previous_num - 1); // 사람 수 1 줄여주기
                            new_list.remove(i);
                        }
                    }

                    String new_str = "";
                    for (int i = 0; i < new_list.size(); i++) { // 새로운 문자열 생성
                        if (new_list.get(i) == "")
                            continue;
                        if (i == new_list.size() - 1) {
                            new_str += new_list.get(i);
                        } else {
                            new_str += new_list.get(i) + ",";
                        }
                    }
                    newMan.setAd_people(new_str);

                    db_manager.child(f_date).setValue(newMan);
                }
            }
        });

        return convertView;
    }
}