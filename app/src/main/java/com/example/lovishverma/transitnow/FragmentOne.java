package com.example.lovishverma.transitnow;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by angel on 2017-04-06.
 */

public class FragmentOne extends android.support.v4.app.Fragment {

    private Button btnLogin, btnForgotPassword;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one,container,false);


        btnLogin = (Button) view.findViewById(R.id.login);
        btnForgotPassword = (Button) view.findViewById(R.id.forgotPassword);


//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(getActivity(),DashboardActivity.class);
//                startActivity(intent);
//            }
//        });

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gmail.com"));
                startActivity(intent);
            }
        });

        return view;
    }
}
