package com.example.lovishverma.transitnow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin= (Button) findViewById(R.id.btnLogin);
       // btnLinkToRegisterScreen= (Button) findViewById(R.id.btnLinkToRegisterScreen);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        btnLinkToRegisterScreen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}
