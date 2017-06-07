package com.example.jaein.unitaxi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class u02_Login_Activity extends AppCompatActivity {

    static DatabaseReference dbTable;
    static DatabaseReference db_uni, db_member, db_manager; // 전부다 저장해두기
    EditText id,pass;
    String inputPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u02_login);

        init();
    }

    private void init() {
        dbTable = FirebaseDatabase.getInstance().getReference("UniTaxi");
        db_uni = dbTable.child("uni_info");
        db_member = dbTable.child("member_info");
        db_manager = dbTable.child("admin_info");

        id = (EditText)findViewById(R.id.loginId);
        pass = (EditText)findViewById(R.id.loginPasswd);
    }

    private String initTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", java.util.Locale.getDefault());
        Date date = new Date();
        String strDate = dateFormat.format(date);

        return strDate;
    }

    public void goto_Register(View view) {
        Intent i_register = new Intent(u02_Login_Activity.this, u03_Register_Activity.class);
        startActivity(i_register);
    }

    public void goto_Main(View view) { // 로그인 하는 부분
        String str_id;
        final String str_pwd;

        str_id = id.getText().toString();
        str_pwd = pass.getText().toString();

        Query query= db_member.orderByKey().equalTo(str_id);//키가 id와 같은걸 쿼리로 가져옴

        query.addListenerForSingleValueEvent(new ValueEventListener() {//그걸 처리해줘야겠지

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    member m = data.getValue(member.class);
                    if(str_pwd.equals(m.getMember_passwd())){
                        Intent loginIntent = new Intent(u02_Login_Activity.this, u04_Main_Activity.class);
                        startActivity(loginIntent);
                    }
                    else{
                        id.setText("");
                        pass.setText("");
                        Toast.makeText(u02_Login_Activity.this, "일치하는 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}