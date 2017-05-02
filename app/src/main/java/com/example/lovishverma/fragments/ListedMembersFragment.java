package com.example.lovishverma.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lovishverma.APIConfiguration.ApiConfiguration;
import com.example.lovishverma.HttpRequestProcessor.HttpRequestProcessor;
import com.example.lovishverma.HttpRequestProcessor.Response;
import com.example.lovishverma.transitnow.AdapterMemberListed;
import com.example.lovishverma.transitnow.Member;
import com.example.lovishverma.transitnow.MemberListedActivity;
import com.example.lovishverma.transitnow.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by angel on 2017-04-25.
 */

public class ListedMembersFragment extends Fragment {

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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listed_members,container,false);

        lv= (ListView) v.findViewById(R.id.lv);

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
        adapterMemberListed = new AdapterMemberListed(getActivity(),arrayList);
        lv.setAdapter(adapterMemberListed);
        return v;
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
                    Toast.makeText(getActivity(), "Record loaded Successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Unsuccessful", Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }
}

