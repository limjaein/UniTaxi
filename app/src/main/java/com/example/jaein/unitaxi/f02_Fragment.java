package com.example.jaein.unitaxi;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.jaein.unitaxi.u02_Login_Activity.db_manager;


/**
 * A simple {@link Fragment} subclass.
 */
public class f02_Fragment extends Fragment {
    ListView listView;
    static ArrayList<manager> admin_list;

    public f02_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f02, container, false);
    }

    public void getData(){
        Query admin_query = db_manager.orderByChild("ad_date");
        admin_list.clear();
        admin_query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                admin_list.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    if(data.getValue()!=null){
                        manager m = data.getValue(manager.class);
                        if(m.getAd_check()) {
                            admin_list.add(m);
                        }
                    }
                }
                // 정보 처리 완료
                if (getActivity() != null) {

                    MyAdminAdapter adapter = new MyAdminAdapter(
                            getActivity(),
                            R.layout.admin_list_row,
                            admin_list
                    );

                    listView.setAdapter(adapter);
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
        init();

        getData();
    }

    private void init() {
        admin_list = new ArrayList<>();
        listView = (ListView)getActivity().findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String Tab = ((u04_Main_Activity)getActivity()).getFragment();

                f01_Fragment Frag = (f01_Fragment)getActivity().getSupportFragmentManager().findFragmentByTag(Tab);

                Frag.setClickedData(position);

                ((u04_Main_Activity)getActivity()).getViewPager().setCurrentItem(0);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                DeleteData(position);
                return true;
            }

        });
    }

    public void DeleteData(final int pos){
        Query admin_query = db_manager.orderByChild("ad_date");
        admin_list.clear();
        admin_query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                admin_list.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    if(data.getValue()!=null){
                        manager m = data.getValue(manager.class);
                        if(m.getAd_check()) {
                            admin_list.add(m);
                        }
                     }
                }
                if(admin_list.size()!=0){
                    admin_list.get(pos).setAd_check(false);
                    db_manager.child(admin_list.get(pos).getAd_date()).setValue(admin_list.get(pos));
                }


                // 정보 처리 완료
                if (getActivity() != null) {

                    MyAdminAdapter adapter = new MyAdminAdapter(
                            getActivity(),
                            R.layout.admin_list_row,
                            admin_list
                    );

                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}