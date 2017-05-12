package com.example.lovishverma.transitnow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.example.lovishverma.APIConfiguration.ApiConfiguration;
import com.example.lovishverma.HttpRequestProcessor.HttpRequestProcessor;
import com.example.lovishverma.HttpRequestProcessor.Response;
import com.example.lovishverma.sharedPreferences.MyPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.jar.Attributes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by angel on 2017-04-06.
 */

public class FragmentOne extends android.support.v4.app.Fragment {

    private Button btnLogin, btnForgotPassword;
    private EditText edtEmailId,edtPassword;
    private String UserName, Password;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlLogin, jsonStringToPost, jsonResponseString;
    private boolean success;
    private String ErrorMessage;
    private int logID;
    private String name,mobileNo,applicationUserID;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        edtEmailId = (EditText) view.findViewById(R.id.edtEmailId);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnForgotPassword = (Button) view.findViewById(R.id.btnForgotPassword);

        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        //Getting base url
        baseURL = apiConfiguration.getApi();
        urlLogin = baseURL + "AccountAPI/GetLoginUser";
        Log.e("url", urlLogin);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (edtEmailId.getText().toString().trim().length() == 0) {
                    edtEmailId.setError("Username is not entered");
                    edtEmailId.requestFocus();
                }
                if (edtPassword.getText().toString().trim().length() == 0) {
                    edtPassword.setError("Password is not entered");
                    edtPassword.requestFocus();
                }
                else
                {
                    UserName = edtEmailId.getText().toString();
                    Password = edtPassword.getText().toString();

                    new LoginTask().execute(UserName, Password);
               }




//                if (!isValidEmail(UserName)) {
//                    edtEmailId.setError("Invalid Email");
//                }
//                final String password = edtPassword.getText().toString();
//                if (!isValidPassword(password)) {
//                    edtPassword.setError("Invalid Password");
//                }
//                final String pass = edtPassword.getText().toString();
//                if(!isPassword(pass))
//                {
//                    edtPassword.setError("Password contains atleast 6 characters");
//                }
//
//                Intent intent= new Intent(getActivity(),DashboardActivity.class);
//                startActivity(intent);
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

//    private boolean isValidEmail(String email) {
//
//        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//
//        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
//        Matcher matcher = pattern.matcher(email);
//        return matcher.matches();
//
//
//    }
//
//    private boolean isValidPassword(String password) {
//        if (password != null) {
//            return true;
//        }
//        return false;
//
//    }
//    private boolean isPassword(String pass) {
//        if (pass.length() == 6 && pass.length() >6) {
//            return true;
//        }
//        return false;
//
//    }
    public class LoginTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            UserName = params[0];
            Password = params[1];

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("UserName", UserName);
                jsonObject.put("Password", Password);

                jsonStringToPost = jsonObject.toString();
                response = httpRequestProcessor.pOSTRequestProcessor(jsonStringToPost, urlLogin);
                jsonResponseString = response.getJsonResponseString();


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonResponseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Log.d("Response String", s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getBoolean("success");
                Log.d("Success", String.valueOf(success));
                ErrorMessage = jsonObject.getString("ErrorMessage");
                Log.d("ErrorMessage", ErrorMessage);

                if(ErrorMessage.equals("User Authenticated!!"))
                {
                    name = jsonObject.getString("Name");
                    mobileNo = jsonObject.getString("MobileNo");
                    applicationUserID = jsonObject.getString("ApplicationUserId");


                    sharedPreferences = getActivity().getSharedPreferences(MyPref.Pref_Name, Context.MODE_PRIVATE);
//                    loggedInUserID = sharedPreferences.getString(MyPref.LoggedInUserID,null);
//                    logID = Integer.parseInt(loggedInUserID);
                    editor = sharedPreferences.edit();
                    editor.putString(MyPref.UserName,name);
                    editor.putString(MyPref.MobileNo,mobileNo);
                    editor.putString(MyPref.LoggedInUserID,applicationUserID);
                    editor.commit();



                    Toast.makeText(getActivity(),ErrorMessage,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getActivity(),DashboardActivity.class));
                }
                else if(ErrorMessage.equals("Invalid username!!"))
                {
                    Toast.makeText(getActivity(),ErrorMessage,Toast.LENGTH_LONG).show();
                }
                else if(ErrorMessage.equals("Invalid password!!"))
                {
                    Toast.makeText(getActivity(),ErrorMessage,Toast.LENGTH_LONG).show();
                }






            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



    }}

