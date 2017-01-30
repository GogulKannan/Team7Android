package com.example.gogul.adandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e0046709 on 1/19/2017.
 */

public class wcfCDisbursementList extends java.util.HashMap<String,String> {

    final static String host = wcflogin.hostname();

    public wcfCDisbursementList(String CollectionPoint, String DeliveryDate,String DeptName, String DisListID ,String RepName,String RepPhone) {
        put("CollectionPoint", CollectionPoint);
        put("DeliveryDatetime", DeliveryDate);
        put("DeptName", DeptName);
        put("DisListID", DisListID);
        put("RepName", RepName);
        put("RepPhone", RepPhone);

    }

    public wcfCDisbursementList() {
    }


    public static List<wcfCDisbursementList> getclerkCDisbursementList() {
        List<wcfCDisbursementList> list = new ArrayList<wcfCDisbursementList>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(host + "/wcfDisbursementList");
        try {
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);

                list.add(new wcfCDisbursementList(b.getString("CollectionPoint"), b.getString("DeliveryDatetime"), b.getString("DeptName"), b.getString("DisListID"),b.getString("RepName"),b.getString("RepPhone")));
            }
        } catch (Exception e) {
            Log.e("wcfCDisbursementList", "JSONArray error");
        }
        return list;
    }


    public static  String SendForConfirmation(String DisbListId)
    {
        String a="";
        try{
            a =   JSONParser.getStream(host+"/wcfSendForConfirmation?DisbursementListID="+DisbListId);
        }catch (Exception e)
        {}
        if(a.substring(1,a.length()-2).equals("true"))
        {
            return "Sent";
        }
        else
            return "Sorry, try again.";
    }
}

