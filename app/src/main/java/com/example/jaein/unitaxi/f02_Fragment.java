package com.example.jaein.unitaxi;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
                        admin_list.add(m);
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
        admin_list = new ArrayList<>();
        listView = (ListView)getActivity().findViewById(R.id.listView);
        getData();
    }
}
