package com.example.doforyou;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_interface);

        RadioGroup rdGroup = (RadioGroup) findViewById(R.id.radio_group_bot);

        rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                changedInterface(i);

            }
        });
    }


    private void changedInterface(int checkedID) {

        //Declare radio button
        RadioButton rdBottom = (RadioButton) findViewById(checkedID);
        Toast.makeText(this, ""+ rdBottom.getText(), Toast.LENGTH_SHORT).show();


        ImageView ivView = (ImageView) findViewById(R.id.ivMain);

        switch((String) rdBottom.getText()) {
            case "Home":
                ivView.setImageDrawable(getDrawable(R.drawable.flower));
                break;
            case "Category":
                ivView.setImageDrawable(getDrawable(R.drawable.rainbow));
                break;
            default:
                // code block
        };

    }



}