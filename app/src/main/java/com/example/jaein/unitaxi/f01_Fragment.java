package com.example.jaein.unitaxi;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapGpsManager;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.jaein.unitaxi.u02_Login_Activity.db_manager;
import static com.example.jaein.unitaxi.u02_Login_Activity.loginId;
import static com.example.jaein.unitaxi.u02_Login_Activity.loginUni;
import static java.lang.Math.abs;

/**
 * A simple {@link Fragment} subclass.
 */
public class f01_Fragment extends Fragment implements TMapGpsManager.onLocationChangedCallback {

    private Context mContext = null;
    private boolean m_bTrackingMode = true;

    private TMapData tmapdata=null;
    private TMapGpsManager tmapgps = null;
    private TMapView tmapview = null;
    private static String mApiKey = "8e36c4a4-66cd-3e71-8f93-06d5754f647a"; // 발급받은 appKey
    private static int mMarkerID;

    private ArrayList<TMapPoint> m_tmapPoint = new ArrayList<TMapPoint>();
    private ArrayList<String> mArrayMarkerID = new ArrayList<String>();
    private ArrayList<MapPoint> m_mapPoint = new ArrayList<MapPoint>();

    private String address;
    double lat1, lat2;
    double lon1, lon2;

    Button taxiBtn;

    EditText et_addr1, et_addr2;
    FrameLayout framelayout;
    TMapMarkerItem marker1, marker2;

    private int myYear, myMonth, myDay, myHour, myMinute;

    TextView fore_time, fore_money, fore_divide_money;

    Button searchBtn;
    LinearLayout info_box;

    int cost,time;

    String total_date,total_time;


    String myTag;

    ToggleButton gobackBtn;

    public f01_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View containerView = inflater.inflate(R.layout.fragment_f01, container, false);
        framelayout = (FrameLayout) containerView.findViewById(R.id.mapview);

        return containerView;
    }

    public interface OnFragmentInteractionListener {

    }

    /// tmap gps listener
    @Override
    public void onLocationChange(Location location) {
        if (m_bTrackingMode) {
            tmapview.setLocationPoint(location.getLongitude(), location.getLatitude());
        }
    }

    public void getAddress(){


        Geocoder gc = new Geocoder(getActivity(), Locale.KOREA);
        String addr1 = et_addr1.getText().toString();
        String addr2 = et_addr2.getText().toString();


        try {
            List<Address> addrList1 = gc.getFromLocationName(addr1, 5);
            List<Address> addrList2 = gc.getFromLocationName(addr2, 5);
            if(addrList1!=null && addrList2!=null){
                lat1 = addrList1.get(0).getLatitude();
                lon1 = addrList1.get(0).getLongitude();

                lat2 = addrList2.get(0).getLatitude();
                lon2 = addrList2.get(0).getLongitude();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static String initTime()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date now = new Date();

        String strDate = formatter.format(now);

        return strDate;
    }


    public void initMap(){

        myTag = getTag();
        ((u04_Main_Activity)getActivity()).setFragment(myTag);
        et_addr1 = (EditText)getActivity().findViewById(R.id.et_Faddr);
        et_addr2 = (EditText)getActivity().findViewById(R.id.et_Laddr);
        et_addr2.setText(loginUni);

        taxiBtn = (Button)getActivity().findViewById(R.id.taxiBtn);
        searchBtn = (Button)getActivity().findViewById(R.id.searchBtn);
        info_box = (LinearLayout)getActivity().findViewById(R.id.info_box);
        gobackBtn = (ToggleButton)getActivity().findViewById(R.id.gobackBtn);


        fore_time = (TextView)getActivity().findViewById(R.id.fore_time);
        fore_money = (TextView)getActivity().findViewById(R.id.fore_money);
        fore_divide_money = (TextView)getActivity().findViewById(R.id.fore_divide_money);

        gobackBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){ // 등교
                    et_addr1.setText(loginUni);
                    et_addr2.setText("");
                }else{
                    et_addr2.setText(loginUni);
                    et_addr1.setText("");
                }
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info_box.setVisibility(View.VISIBLE);
                taxiBtn.setVisibility(View.VISIBLE);
                getAddress();

                tmapview.setCenterPoint((lon1+lon2)/2,(lat1+lat2)/2,true);


                //출발지 목적지
                TMapPoint point1 = new TMapPoint(lat1,lon1);
                TMapPoint point2 = new TMapPoint(lat2,lon2);

                //경유지 리스트 추가
                ArrayList<TMapPoint> passList = new ArrayList<TMapPoint>();
                passList.add(new TMapPoint(37.543470, 127.076327));
                passList.add(new TMapPoint(37.5520312,127.0874413));

                //경유지 함수
                tmapdata.findMultiPointPathData(point1, point2, passList, 0,
                        new TMapData.FindPathDataListenerCallback() {
                            @Override
                            public void onFindPathData(TMapPolyLine tMapPolyLine) {

                                tmapview.addTMapPath(tMapPolyLine);

                                double latSpan = abs(lat1 - lat2);

                                double lonSpan = abs(lon1 - lon2);

                                tmapview.zoomToSpan(latSpan, lonSpan);

                                cost=(int) (2400 + (tMapPolyLine.getDistance()-2000)*100/144);
                                time=(int)(tMapPolyLine.getDistance()/1000);
                                if(cost <= 3000){
                                    cost = 3000;
                                }

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getActivity().runOnUiThread(new Runnable(){
                                            @Override
                                            public void run() {
                                                fore_time.setText(time+"분");
                                                fore_money.setText(cost+"원");
                                                fore_divide_money.setText(cost/4+"원");
                                            }
                                        });
                                    }
                                }).start();

                            }
                        });
            }
        });

        taxiBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Query man_query = db_manager.orderByChild("ad_date").equalTo(total_date);
                man_query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String date = initTime();
                        manager man = new manager(date, total_time, loginId, "", true, et_addr1.getText().toString(),
                                et_addr2.getText().toString(), 0);

                        db_manager.child(date).setValue(man);
                        String TabTag  = ((u04_Main_Activity)getActivity()).getFragment();

                        f01_Fragment frag = (f01_Fragment)getActivity().getSupportFragmentManager().findFragmentByTag(TabTag);


                        ((u04_Main_Activity)getActivity()).getViewPager().setCurrentItem(1);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        //Tmap 각종 객체 선언
        tmapdata = new TMapData(); //POI검색, 경로검색 등의 지도데이터를 관리하는 클래스
        if (getActivity() != null) {
            tmapview = new TMapView(getActivity()); // 이 부분 자꾸 오류난다-재인
        }
        framelayout.addView(tmapview);
        tmapview.setSKPMapApiKey(mApiKey);

        //addPoint();
        //showMarkerPoint();

        /* 현재 보는 방향 */
        tmapview.setCompassMode(false);

        /* 현위치 아이콘표시 */
        tmapview.setIconVisibility(true);

        /* 줌레벨 */
        tmapview.setZoomLevel(15);
        /* 지도타입 */
        tmapview.setMapType(TMapView.MAPTYPE_STANDARD);
        /* 언어 설정*/
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);

        tmapgps = new TMapGpsManager(getActivity());//단말기 위치탐색을 위한 클래
        tmapgps.setMinTime(1000); //위치변경 인식 최소시간설정
        tmapgps.setMinDistance(5);// 위치변경 인식 최소거리설정
        //tmapgps.setProvider(tmapgps.NETWORK_PROVIDER); //연결된 인터넷으로 현 위치를 받습니다.
        //실내일 때 유용합니다.
        tmapgps.setProvider(tmapgps.GPS_PROVIDER); //gps로 현 위치를 잡습니다.

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //권한이 없을 경우
            //최초 권한 요청인지, 혹은 사용자에 의한 재요청인지 확인
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // 사용자가 임의로 권한을 취소시킨 경우
                // 권한 재요청
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            }
        } else {
            /// 권한이 있을때만
            ///////////
            tmapgps.OpenGps();

            /*  화면중심을 단말의 현재위치로 이동 */
            tmapview.setTrackingMode(true);
            tmapview.setSightVisible(true);
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Button datePicker = (Button) getActivity().findViewById(R.id.datepicker);
        final Button timePicker = (Button) getActivity().findViewById(R.id.timepicker);

        final DatePickerDialog.OnDateSetListener myDateSetListener
                = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                datePicker.setText(String.format("%d년 %d월 %d일", year, monthOfYear + 1, dayOfMonth));

                total_date = String.format("%d%02d%02d",year,monthOfYear + 1, dayOfMonth);
                Toast.makeText(getActivity(), total_date, Toast.LENGTH_SHORT).show();

            }
        };
        final TimePickerDialog.OnTimeSetListener myTimeSetListener
                = new TimePickerDialog.OnTimeSetListener() {

            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timePicker.setText(String.format("%d시 %d분", hourOfDay, minute));

                total_time = String.format("%02d%02d", hourOfDay, minute);
                Toast.makeText(getActivity(), total_time, Toast.LENGTH_SHORT).show();
            }
        };

        datePicker.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                myYear = c.get(Calendar.YEAR);
                myMonth = c.get(Calendar.MONTH);
                myDay = c.get(Calendar.DAY_OF_MONTH);
                Dialog dlgDate = new DatePickerDialog(getActivity(), myDateSetListener,
                        myYear, myMonth, myDay);
                dlgDate.show();
            }
        });

        timePicker.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                myHour = c.get(Calendar.HOUR_OF_DAY);
                myMinute = c.get(Calendar.MINUTE);
                Dialog dlgTime = new TimePickerDialog(getActivity(), myTimeSetListener,
                        myHour, myMinute, false);
                dlgTime.show();
            }
        });
        initMap();
    }


}