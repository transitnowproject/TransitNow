package com.example.lovishverma.transitnow;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import static com.example.lovishverma.transitnow.R.id.btnLinkToRegisterScreen;

public class RegistrationActivity extends AppCompatActivity {

    private Button btnRegister,btnMale,btnFemale;
    private EditText edtDOB;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btnRegister= (Button) findViewById(R.id.btnRegister);
        btnMale= (Button) findViewById(R.id.btnMale);
        btnFemale= (Button) findViewById(R.id.btnFemale);
        edtDOB= (EditText) findViewById(R.id.edtDOB);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btnMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setVisible(false);
                //btnMale.setBackground(getResources().getColor(R.color.input_register_hint));
            }
        });
        edtDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DatePicker dp = new DatePicker();
            }
        });

    }
}
