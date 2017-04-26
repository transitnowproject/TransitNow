package com.example.lovishverma.HttpRequestProcessor;

/**
 * Created by angel on 2017-04-25.
 */

public class Response {
    String jsonResponseString;
    int responseCode;

    public String getJsonResponseString() {
        return jsonResponseString;
    }

    public void setJsonResponseString(String jsonResponseString) {
        this.jsonResponseString = jsonResponseString;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
