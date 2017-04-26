package com.example.lovishverma.transitnow;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lovishverma.APIConfiguration.ApiConfiguration;
import com.example.lovishverma.HttpRequestProcessor.HttpRequestProcessor;
import com.example.lovishverma.HttpRequestProcessor.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by angel on 2017-04-06.
 */

public class FragmentTwo extends android.support.v4.app.Fragment {

    private Button btnRegister;
    private RadioGroup radioGroup;
    private RadioButton male, female;
    private int Option;
    private String Name, Address, EmailId, MobileNo, Gender, UserName, Password;
    private EditText edtName, edtAddress, edtEmailId, edtMobileNo, edtUserName, edtPassword;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlRegister;
    private String jsonPostString, jsonResponseString;
    private int responseData;
    private boolean success;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);

        btnRegister = (Button) view.findViewById(R.id.btnRegister);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        male = (RadioButton) view.findViewById(R.id.male);
        female = (RadioButton) view.findViewById(R.id.female);
        edtName = (EditText) view.findViewById(R.id.edtName);
        edtAddress = (EditText) view.findViewById(R.id.edtAddress);
        edtEmailId = (EditText) view.findViewById(R.id.edtEmailId);
        edtMobileNo = (EditText) view.findViewById(R.id.edtMobileNo);
        edtUserName = (EditText) view.findViewById(R.id.edtUserName);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);

        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        //Getting BaseURL
        baseURL = apiConfiguration.getApi();
        urlRegister = baseURL + "AccountAPI/SaveApplicationUser";
        Log.e("url", urlRegister);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getting values
                Name = edtName.getText().toString();
                Address = edtAddress.getText().toString();
                EmailId = edtEmailId.getText().toString();
                MobileNo = edtMobileNo.getText().toString();
                Option = radioGroup.getCheckedRadioButtonId();
                Gender = String.valueOf(Option);
                UserName = edtUserName.getText().toString();
                Password = edtPassword.getText().toString();
                new RegistrationTask().execute(Name, Address, EmailId, MobileNo, UserName, Password);
                Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_LONG).show();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int i) {

                switch (i) {
                    case R.id.male:
                        break;
                    case R.id.female:
                        break;
                }

            }
        });
        return view;
    }

    private class RegistrationTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            Name = params[0];
            Log.e("Name", Name);
            Address = params[1];
            Log.e("Address", Address);
            EmailId = params[2];
            MobileNo = params[3];
            Gender = params[4];
            UserName = params[5];
            Password = params[6];

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Name", Name);
                jsonObject.put("Address", Address);
                jsonObject.put("EmailId", EmailId);
                jsonObject.put("MobileNo", MobileNo);
                jsonObject.put("Gender", Gender);
                jsonObject.put("DateOfBirth", "2011-10-04");
                jsonObject.put("FatherName", "ABC ");
                jsonObject.put("MotherName", "xyz ");
                jsonObject.put("UserName", UserName);
                jsonObject.put("Password", Password);
                jsonObject.put("CreatedBy", "1");
                jsonObject.put("ModifiedBy", "1");


                jsonPostString = jsonObject.toString();
                Log.e("jsonPostString", jsonPostString);
                response = httpRequestProcessor.pOSTRequestProcessor(jsonPostString, urlRegister);
                jsonResponseString = response.getJsonResponseString();
                Log.e("jsonResponseString", jsonResponseString);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonResponseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // Log.d("Response String", s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getBoolean("success");
                Log.d("Success", String.valueOf(success));

                responseData = jsonObject.getInt("responseData");
                // Log.d("message", message);

                if (responseData == 1) {
                    Toast.makeText(getActivity(), "Data Registered Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), FragmentOne.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Registration Unsuccessful", Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}

