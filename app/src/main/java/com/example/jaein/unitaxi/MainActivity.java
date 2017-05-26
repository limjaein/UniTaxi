package com.example.jaein.unitaxi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("products");
        DatabaseReference rDatabase = mDatabase.child("items");
    }

    public void btnClick(View view) {

    }
}
