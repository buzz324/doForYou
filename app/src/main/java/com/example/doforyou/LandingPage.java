package com.example.doforyou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class LandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
    }

    public void onclickStart (View v){

        Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, SignIn.class);
        startActivity(i);
    }
}