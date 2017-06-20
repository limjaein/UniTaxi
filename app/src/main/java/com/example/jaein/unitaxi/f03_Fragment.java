package com.example.jaein.unitaxi;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.jaein.unitaxi.u02_Login_Activity.db_manager;
import static com.example.jaein.unitaxi.u02_Login_Activity.loginId;


/**
 * A simple {@link Fragment} subclass.
 */
public class f03_Fragment extends Fragment {
    ListView listView2;
    static ArrayList<manager> admin_list;

    public f03_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f03, container, false);
    }

    public void getData(){//정보다가져오는거
        Query admin_query = db_manager.orderByChild("ad_date");
        admin_list.clear();
        admin_query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {//데이타바뀔떄마다
                admin_list.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    if(data.getValue()!=null){
                        manager m = data.getValue(manager.class);

                        if(m.getAd_check()==false) {
                           if (m.getAd_master().equals(loginId))
                                admin_list.add(m);
                        }
                    }
                }
                // 정보 처리 완료
                if (getActivity() != null) {

                    MyAdminAdapter2 adapter = new MyAdminAdapter2(
                            getActivity(),
                            R.layout.admin_list_row2,
                            admin_list
                    );

                    listView2.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public interface OnFragmentInteractionListener {
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        admin_list = new ArrayList<>();
        listView2= (ListView)getActivity().findViewById(R.id.listView2);

        getData();
    }
}