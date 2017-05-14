package com.example.lovishverma.transitnow;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lovishverma.sharedPreferences.MyPref;

public class MyProfileActivity extends AppCompatActivity {

    private TextView txtName, txtAddress, txtEmailId, txtMobileNo;
    private SharedPreferences sharedPreferences;
    private String name, address, emailId, mobileNo;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        txtName = (TextView) findViewById(R.id.txtName1);
        txtAddress = (TextView) findViewById(R.id.txtAddress1);
        txtEmailId = (TextView) findViewById(R.id.txtEmailId1);
        txtMobileNo = (TextView) findViewById(R.id.txtMobileNo1);

        sharedPreferences = getSharedPreferences(MyPref.Pref_Name, Context.MODE_PRIVATE);
        name = sharedPreferences.getString(MyPref.UserName, null);
        address = sharedPreferences.getString(MyPref.Address, null);
        emailId = sharedPreferences.getString(MyPref.EmailId, null);
        mobileNo = sharedPreferences.getString(MyPref.MobileNo, null);

        txtName.setText(name);
        txtAddress.setText(address);
        txtEmailId.setText(emailId);
        txtMobileNo.setText(mobileNo);


    }
}
