package com.example.jaein.unitaxi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class u03_Register_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u03_register);
    }

    private void goto_Login() {
        Intent i_Login = new Intent(u03_Register_Activity.this, u02_Login_Activity.class);
        startActivity(i_Login);
    }

    public void RegisterToLogin(View view) { goto_Login(); };
}