package com.example.jaein.unitaxi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class u01_Intro_Activity extends AppCompatActivity {

    Handler handler_intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u01_intro);

        handler_intro = new Handler();
        handler_intro.postDelayed(run_into, 3000);
    }

    Runnable run_into = new Runnable() {
        @Override
        public void run() {
            Intent i_Login = new Intent(u01_Intro_Activity.this, u02_Login_Activity.class);
            startActivity(i_Login);
            finish();
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}