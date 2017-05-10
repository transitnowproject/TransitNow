package com.example.lovishverma.transitnow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lovishverma.APIConfiguration.ApiConfiguration;
import com.example.lovishverma.HttpRequestProcessor.HttpRequestProcessor;
import com.example.lovishverma.HttpRequestProcessor.Response;

import java.util.ArrayList;

/**
 * Created by angel on 2017-05-01.
 */

public class AdapterMemberListed extends BaseAdapter {

    private String name, mobileNo, memberId;
    private Context context;
    private ArrayList<Member> arrayList;
    private LayoutInflater inflater;

    public AdapterMemberListed(Context context, ArrayList<Member> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.member, parent, false);

        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
        TextView txtMobileNo = (TextView) convertView.findViewById(R.id.txtMobileNo);

        Member member = arrayList.get(position);

        name = member.getName();
        mobileNo = member.getMobileNo();
        int mId = member.getMemberId();
        memberId = String.valueOf(mId);

        txtName.setText(name);
        txtMobileNo.setText(mobileNo);

        return convertView;
    }
}
