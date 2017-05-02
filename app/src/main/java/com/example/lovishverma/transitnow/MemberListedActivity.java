package com.example.lovishverma.transitnow;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lovishverma.APIConfiguration.ApiConfiguration;
import com.example.lovishverma.HttpRequestProcessor.HttpRequestProcessor;
import com.example.lovishverma.HttpRequestProcessor.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MemberListedActivity extends AppCompatActivity {

    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlRegister;
    private String jsonPostString, jsonResponseString;
    private boolean success;
    private String name, mobileNo,message;
    private int responseData;
    private int memberId;
    private ListView lv;
    private ArrayList<Member> arrayList;
    AdapterMemberListed adapterMemberListed;
    private Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_listed);

        lv= (ListView) findViewById(R.id.lv);

        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        //Getting BaseURL
        baseURL = apiConfiguration.getApi();
        urlRegister = baseURL + "MemberAPI/GetApplicationMemberList";
        Log.e("url", urlRegister);
        arrayList =  new ArrayList<>();
        new getMemberListTask().execute();
        adapterMemberListed = new AdapterMemberListed(MemberListedActivity.this,arrayList);
        lv.setAdapter(adapterMemberListed);

    }

    public class getMemberListTask extends AsyncTask<String,String,String>
    {


        @Override
        protected String doInBackground(String... params) {
            jsonResponseString=httpRequestProcessor.gETRequestProcessor(urlRegister);
            return jsonResponseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            super.onPostExecute(s);
            // Log.d("Response String", s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getBoolean("success");
                Log.d("Success", String.valueOf(success));
                message = jsonObject.getString("message");

                responseData = jsonObject.getInt("responseData");
                // Log.d("message", message);

                if (responseData == 1) {

                    adapterMemberListed.notifyDataSetChanged();
                    Toast.makeText(MemberListedActivity.this, "Record loaded Successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MemberListedActivity.this, "Unsuccessful", Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }
}
