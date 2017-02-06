package com.example.gogul.adandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e0046709 on 1/18/2017.
 */

public class wcfTodayCollection extends java.util.HashMap<String,String> {

    final static String host = wcflogin.hostname();

    public wcfTodayCollection(String id, String name ,String reqid ) {
        put("cloc", id);
        put("time", name);
        put("reqid", reqid);

    }

    public wcfTodayCollection(){}


    public static List<wcfTodayCollection> getTodayCollection(String id) {
        List<wcfTodayCollection> list = new ArrayList<wcfTodayCollection>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(host+"/wcfTodayCollection/"+id);
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);

                list.add(new wcfTodayCollection(b.getString("Collectionpt"), b.getString("Time"), b.getString("DisbursementListID")));
            }
        } catch (Exception e) {
            Log.e("wcfTodayCollection", "JSONArray error");
        }
        return list;
    }


    public static  String AcceptCollection(String DisListID)
    {
        String a="";
        try{
            a =   JSONParser.getStream(host+"/wcfAcceptCollection?DisbursementListID="+DisListID);
            Log.e("Ddffd",a);
        }catch (Exception e)
        {
            Log.e("changed","done");
        }
        if(a.substring(1,a.length()-2).equals("true"))
        {
            Log.e("Dd",a);
            return "Collected";
        }
        else
            return "Sorry, try again.";


    }

}