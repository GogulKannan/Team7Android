package com.example.gogul.adandroid;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by e0046709 on 1/25/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG ="MyFirebaseInsIDService";

    @Override
    public void onTokenRefresh() {
        //get updated token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "New Token: "+refreshedToken);

      //  registerToken(refreshedToken);
    }

//    public void registerToken (String token){
//
//        OkHttpClient client = new OkHttpClient();
//        RequestBody body = new FormBody.Builder()
//                .add("Token", token)
//                .build();
//
//        Request request = new Request.Builder()
//                .url("http://172.23.135.91/fcm/test")
//                .post(body)
//                .build();
//
//        try {
//            client.newCall(request).execute();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

