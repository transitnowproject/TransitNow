package com.example.lovishverma.transitnow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.lovishverma.transitnow.R.id.btnLinkToRegisterScreen;

public class RegistrationActivity extends AppCompatActivity {

    private Button btnRegister,btnLinkToLoginScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btnRegister= (Button) findViewById(R.id.btnRegister);
        btnLinkToLoginScreen= (Button) findViewById(R.id.btnLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
