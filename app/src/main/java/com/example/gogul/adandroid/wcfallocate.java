package com.example.gogul.adandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class wcfallocate extends java.util.HashMap<String,String> {

    final static String host = wcflogin.hostname();

    public wcfallocate(String id, String name ,String reqid ) {
        put("ItemName", id);
        put("PreQty", name);
        put("DisbQty", reqid);
    }

    public wcfallocate(){}


    public static String getallocate(String datesending) {

        String a = JSONParser.getStream(host+"/wcfallocate?deliverydate="+datesending);
        if(a.substring(1,a.length()-2).equals("True"))
        {
            return "Allocated";
        }
        else
            return "Sorry, try again.";
    }
}