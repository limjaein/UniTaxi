package com.example.jaein.unitaxi;

import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class u04_Main_Activity extends AppCompatActivity
        implements f01_Fragment.OnFragmentInteractionListener,
        f02_Fragment.OnFragmentInteractionListener,
        f03_Fragment.OnFragmentInteractionListener,
        f04_Fragment.OnFragmentInteractionListener {
    ViewPager viewPager;
    String Frag;

    Double latitude = 0.0;
    Double longitude = 0.0;

    LocationManager manager;

    public void setFragment(String t){
        Frag = t;
    }

    public String getFragment(){
        return Frag;
    }

    public ViewPager getViewPager() {
        if (null == viewPager) {
            viewPager = (ViewPager) findViewById(R.id.vp_pager);
        }
        return viewPager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u04_main);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl_tabs);
        viewPager = (ViewPager) findViewById(R.id.vp_pager);

        Fragment[] arrFragments = new Fragment[4];
        arrFragments[0] = new f01_Fragment();
        arrFragments[1] = new f02_Fragment();
        arrFragments[2] = new f03_Fragment();
        arrFragments[3] = new f04_Fragment();

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), arrFragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.tab0);
        tabLayout.getTabAt(1).setIcon(R.drawable.tab1);
        tabLayout.getTabAt(2).setIcon(R.drawable.tab2);
        tabLayout.getTabAt(3).setIcon(R.drawable.tab3);

        //Toast.makeText(getApplicationContext(), latitude+" "+longitude, Toast.LENGTH_SHORT).show();
    }

    public void onFragmentInteraction(Uri uri){

    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        private Fragment[] arrFragments;

        public MyPagerAdapter(FragmentManager fm, Fragment[] arrFragments) {
            super(fm);
            this.arrFragments = arrFragments;
        }

        @Override
        public Fragment getItem(int position) {
            return arrFragments[position];
        }
        @Override
        public int getCount() {
            return arrFragments.length;
        }

        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "등록";
                case 1:
                    return "관리";
                case 2:
                    return "알림";
                case 3:
                    return "설정";
                default:
                    return "";
            }
        }
    }
}