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
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by angel on 2017-04-06.
 */

public class FragmentOne extends android.support.v4.app.Fragment {

    private Button btnLogin, btnForgotPassword;
    private EditText edtEmail,edtPassword;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        edtEmail = (EditText) view.findViewById(R.id.email);
        edtPassword = (EditText) view.findViewById(R.id.password);
        btnLogin = (Button) view.findViewById(R.id.login);
        btnForgotPassword = (Button) view.findViewById(R.id.forgotPassword);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = edtEmail.getText().toString();
                if (!isValidEmail(email)) {
                    edtEmail.setError("Invalid Email");
                }
                final String password = edtPassword.getText().toString();
                if (!isValidPassword(password)) {
                    edtPassword.setError("Invalid Password");
                }
                final String pass = edtPassword.getText().toString();
                if(!isPassword(pass))
                {
                    edtPassword.setError("Password contains atleast 6 characters");
                }

                Intent intent= new Intent(getActivity(),DashboardActivity.class);
                startActivity(intent);
            }
        });

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gmail.com"));
                startActivity(intent);
            }
        });

        return view;

    }

    private boolean isValidEmail(String email) {

        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();


    }

    private boolean isValidPassword(String password) {
        if (password != null) {
            return true;
        }
        return false;

    }
    private boolean isPassword(String pass) {
        if (pass.length() == 6 && pass.length() >6) {
            return true;
        }
        return false;

    }


    }

