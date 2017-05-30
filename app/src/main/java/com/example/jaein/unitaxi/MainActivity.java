package com.example.jaein.unitaxi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText query;
    TextView queryResult;

    DatabaseReference dbTable;
    DatabaseReference db_uni, db_member, db_manager; // 전부다 저장해두기

    EditText id,univer,gender;

    TextView tv_id;
    TextView tv_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        Toast.makeText(this, initTime(), Toast.LENGTH_SHORT).show();
        initDB();
    }

    private String initTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", java.util.Locale.getDefault());
        Date date = new Date();
        String strDate = dateFormat.format(date);

        return strDate;
    }

    private void initDB() {
        dbTable = FirebaseDatabase.getInstance().getReference("UniTaxi");
        db_uni = dbTable.child("uni_info");
        db_member = dbTable.child("member_info");
        db_manager = dbTable.child("admin_info");
    }

    private void init() {

        tv_id = (TextView)findViewById(R.id.query_id);
        tv_pass = (TextView)findViewById(R.id.query_password);
        query = (EditText)findViewById(R.id.query);
        queryResult = (TextView)findViewById(R.id.queryResult);

    }

    public void loginBtnClick(View view) { // DB확인용!
        String id;

        id = query.getText().toString();

        Query query= db_member.orderByKey().equalTo(id);//키가 id와 같은걸 쿼리로 가져옴

        query.addListenerForSingleValueEvent(new ValueEventListener() {//그걸 처리해줘야겠지

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot data : dataSnapshot.getChildren()) {
                        member m = data.getValue(member.class);
                        tv_id.setText(m.getMember_id()+"");
                        tv_pass.setText(m.getMember_uni()+"");
                    }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void joinBtnClick(View view) {
        id = (EditText)findViewById(R.id.id);
        univer = (EditText)findViewById(R.id.uni);
        gender = (EditText)findViewById(R.id.gender);
        String st1 = id.getText().toString();
        String st2 = univer.getText().toString();
        String st3 = gender.getText().toString();
        member m = new member(st1,st2,st3);

        db_member.push().setValue(m); // db에 저장 끝
    }
}
