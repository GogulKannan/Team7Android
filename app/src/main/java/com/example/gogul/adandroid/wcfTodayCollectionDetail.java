package com.example.gogul.adandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e0046709 on 1/18/2017.
 */

public class wcfTodayCollectionDetail extends java.util.HashMap<String,String> {

    final static String host = wcflogin.hostname();

    public wcfTodayCollectionDetail(String id, String name ,String reqid ) {
        put("cloc", id);
        put("time", name);
        put("reqid", reqid);

    }

    public wcfTodayCollectionDetail(){}


    public static List<wcfTodayCollectionDetail> getTodayCollectionDetail(String deptid,String did) {
        List<wcfTodayCollectionDetail> list = new ArrayList<wcfTodayCollectionDetail>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(host+"/wcfTodayCollectionDetail?d="+deptid+"&r="+did);
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);

                list.add(new wcfTodayCollectionDetail(b.getString("ItemDescription"), b.getString("RequestedQty"), b.getString("DisbursedQty")));
            }
        } catch (Exception e) {
            Log.e("wcfTodayCollectio", "JSONArray error");
        }
        return list;
    }

}