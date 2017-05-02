package com.example.lovishverma.transitnow;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lovishverma.APIConfiguration.ApiConfiguration;
import com.example.lovishverma.HttpRequestProcessor.HttpRequestProcessor;
import com.example.lovishverma.HttpRequestProcessor.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin,btnForgotPassword;
    private EditText edtEmail, edtPassword;
    private String UserName, Password;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlLogin, jsonStringToPost, jsonResponseString;
    private boolean success;
    private String ErrorMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnForgotPassword = (Button) findViewById(R.id.forgotPassword);

        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        //Getting base url
        baseURL = apiConfiguration.getApi();
        urlLogin = baseURL + "AccountAPI/GetLoginUser";



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Getting UserName and password
                UserName = edtEmail.getText().toString();
                Password = edtPassword.getText().toString();

//                final String email = edtEmail.getText().toString();
//                if (!isValidEmail(email)) {
//                    edtEmail.setError("Invalid Email");
//                }
//                final String password = edtPassword.getText().toString();
//                if (!isValidPassword(password)) {
//                    edtPassword.setError("Invalid Password");
//                }
                new LoginTask().execute(UserName, Password);



            }

        });

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gmail.com"));
                startActivity(intent);
            }
        });

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
//        if (password != null && password.length() > 6) {
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
                    Toast.makeText(LoginActivity.this,"success",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
                }
                else if(ErrorMessage.equals("Invalid username!!"))
                {
                    Toast.makeText(LoginActivity.this,"Enter Valid UserName",Toast.LENGTH_LONG).show();
                }
                else if(ErrorMessage.equals("Invalid password!!"))
                {
                    Toast.makeText(LoginActivity.this,"Enter Valid Password",Toast.LENGTH_LONG).show();
                }






            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}

