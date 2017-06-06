package com.example.jaein.unitaxi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class u02_Login_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u02_login);
    }

    private void goto_Main() {
        Intent i_main = new Intent(u02_Login_Activity.this, u04_Main_Activity.class);
        startActivity(i_main);
    }

    private void goto_Register() {
        Intent i_register = new Intent(u02_Login_Activity.this, u03_Register_Activity.class);
        startActivity(i_register);
    }

    public void LoginToMain(View view) { goto_Main(); };
    public void LoginToRegister(View view) { goto_Register(); };
}