package com.example.jaein.unitaxi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class u05_User_Activity extends AppCompatActivity {

    private Intent intent;
    private TextView textPosition;
    Button FinalBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u05_user);
        init();
        intent = getIntent();
        textPosition = (TextView) findViewById(R.id.textPosition);
        textPosition.setText(toString().valueOf(intent.getIntExtra("position",0)) + "번 리스트를 클릭");
    }

    private void init() {
        FinalBtn = (Button)findViewById(R.id.setFinalBtn);
        //마스터 인지 확인 후 버튼 visible 속성 바꾸기
        FinalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼 클릭되었을때
                //ad_check -> false
            }
        });
    }
}