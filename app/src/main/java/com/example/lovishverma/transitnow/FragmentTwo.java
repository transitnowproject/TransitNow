package com.example.lovishverma.transitnow;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by angel on 2017-04-06.
 */

public class FragmentTwo extends android.support.v4.app.Fragment {

    private Button btnRegister;
    // private Button btnMale, btnFemale;
    //private EditText edtDOB;
    private RadioGroup radioGroup;
    private RadioButton male, female;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);

        btnRegister = (Button) view.findViewById(R.id.btnRegister);
//        edtDOB = (EditText) view.findViewById(R.id.edtDOB);
//        btnMale = (Button) view.findViewById(R.id.btnMale);
//        btnFemale = (Button) view.findViewById(R.id.btnFemale);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        male = (RadioButton) view.findViewById(R.id.male);
        female = (RadioButton) view.findViewById(R.id.female);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_LONG).show();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int i) {

                switch (i)
                {
                    case R.id.male:
                                        break;
                    case R.id.female:
                                        break;
                }

            }
        });

//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, @IdRes int i) {
//                switch (i) {
//                    case R.id.btnMale:
//                        btnMale.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                btnFemale.setEnabled(false);
//                                btnFemale.setClickable(false);
//
//                            }
//                        });
//                        break;
//                    case R.id.btnFemale:
//                        btnFemale.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                btnMale.setEnabled(false);
//                                btnMale.setClickable(false);
//                            }
//                        });
//                        break;
//                }
//            }
//        });

//        btnMale.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btnFemale.setEnabled(false);
//                btnFemale.setClickable(false);
//
//            }
//        });
//
//        btnFemale.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btnMale.setEnabled(false);
//                btnMale.setClickable(false);
//            }
//        });


        return view;


    }
}

