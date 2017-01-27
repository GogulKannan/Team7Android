package com.example.gogul.adandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e0046709 on 1/19/2017.
 */

public class wcfRetrivalList  extends java.util.HashMap<String,String> {

    final static String host = wcflogin.hostname();

    public wcfRetrivalList(String ItemName, String RequestedQty, String RetrievedQty, String Status,String ItemNo) {
        put("ItemName", ItemName);
        put("RequestedQty", RequestedQty);
        put("RetrievedQty", RetrievedQty);
        put("Status", Status);
        put("ItemNo", ItemNo);
    }

    public wcfRetrivalList(){}


    public static List<wcfRetrivalList> getRetrivalList() {
        List<wcfRetrivalList> list = new ArrayList<wcfRetrivalList>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(host+"/wcfRetrivalList");
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);

                list.add(new wcfRetrivalList(b.getString("ItemName"), b.getString("RequestedQty"),
                        b.getString("RetrievedQty"), b.getString("Status"), b.getString("ItemNo")));
            }
        } catch (Exception e) {
            Log.e("wcfRetrivalList", "JSONArray error");
        }
        return list;
    }

}