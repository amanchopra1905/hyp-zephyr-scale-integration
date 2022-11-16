package com.lambdatest;


import java.io.File;
import okhttp3.*;


public class PostTestResults {

    public void callApi() {

        String fileName = "" ; //provide the file name of the test results
        String filePath = ""; //provide the file path of the test result file
        String projectKey = ""; //provide the JIRA project key 
        String token = ""; // provide Zypher scale access token

        OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
        //   MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("file", fileName ,
                RequestBody.create(MediaType.parse("application/octet-stream"),
                    new File(filePath)))
            .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.zephyrscale.smartbear.com/v2/automations/executions/junit").newBuilder();
        urlBuilder.addQueryParameter("projectKey", projectKey);
        urlBuilder.addQueryParameter("autoCreateTestCases", "true");
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
            .url(url)
            .method("POST", body)
            .addHeader("Authorization", "Bearer "+token)
            .build();

        try {
            
            Response response = client.newCall(request).execute();
            System.out.println("Result Posted to Zephyr Scale");
            System.out.println(response.code());
            System.out.println(response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}