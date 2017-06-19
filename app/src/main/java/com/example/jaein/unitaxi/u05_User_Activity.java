package com.example.jaein.unitaxi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class u05_User_Activity extends AppCompatActivity {

    private Intent intent;
    private TextView textPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u05_user);
        intent = getIntent();
        textPosition = (TextView) findViewById(R.id.textPosition);
        textPosition.setText(toString().valueOf(intent.getIntExtra("position",0)) + "번 리스트를 클릭");
    }
}