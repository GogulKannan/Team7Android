package com.example.gogul.adandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e0046709 on 1/19/2017.
 */

public class wcfallocate extends java.util.HashMap<String,String> {

    final static String host = wcflogin.hostname();

    public wcfallocate(String id, String name ,String reqid ) {
        put("ItemName", id);
        put("PreQty", name);
        put("DisbQty", reqid);

    }

    public wcfallocate(){}


    public static String getallocate() {

        String a = JSONParser.getStream(host+"/wcfallocate");
        Log.e("getallocate",a.toString());
        if(a.substring(1,a.length()-2).equals("True"))
        {
            return "Allocated";
        }
        else
            return "Sorry, try again.";
    }


}