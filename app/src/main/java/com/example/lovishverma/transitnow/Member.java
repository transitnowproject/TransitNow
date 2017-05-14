package com.example.lovishverma.transitnow;

/**
 * Created by angel on 2017-05-01.
 */

public class Member {

    private String name;
    private int memberId;

    public void setName(String name) {
        this.name = name;
    }

//    public void setMobileNo(String mobileNo) {
//        this.mobileNo = mobileNo;
//    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getName() {

        return name;
    }

//    public String getMobileNo() {
//        return mobileNo;
//    }

    public int getMemberId() {
        return memberId;
    }

    public Member(String name, int memberId) {

        this.name = name;
      //  this.mobileNo = mobileNo;
        this.memberId = memberId;
    }
}
