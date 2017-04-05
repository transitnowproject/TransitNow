package com.example.lovishverma.transitnow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by angel on 2017-04-05.
 */

public class HomeActivity extends AppCompatActivity {

    private ImageButton imgBtnDriver,imgBtnPassenger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imgBtnDriver= (ImageButton) findViewById(R.id.imgBtnDriver);
        imgBtnPassenger= (ImageButton) findViewById(R.id.imgBtnPassenger);

        imgBtnDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imgBtnPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
