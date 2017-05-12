package com.example.lovishverma.transitnow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class RateActivity extends AppCompatActivity {
    private Button btnSubmit;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        btnSubmit= (Button) findViewById(R.id.btnSubmit);
        ratingBar= (RatingBar) findViewById(R.id.rateUs);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float ratingValue= ratingBar.getRating();
                Toast.makeText(RateActivity.this,"Rating is : "+ratingValue,Toast.LENGTH_LONG).show();
            }
        });
    }
}
