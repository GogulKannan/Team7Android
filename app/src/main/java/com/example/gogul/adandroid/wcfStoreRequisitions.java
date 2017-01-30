package com.example.gogul.adandroid;

import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e0046709 on 1/24/2017.
 */

public class wcfStoreRequisitions extends java.util.HashMap<String,String> {

    final static String host = wcflogin.hostname();

    public wcfStoreRequisitions(String ApprovalDate, String DeptName, String ReqStatus ) {
        put("ApprovalDate", ApprovalDate);
        put("DeptName", DeptName);
        put("ReqStatus", ReqStatus);
    }

    public wcfStoreRequisitions() {
    }


    public static List<wcfStoreRequisitions> getStoreRequisitions() {
        List<wcfStoreRequisitions> list = new ArrayList<wcfStoreRequisitions>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(host + "/wcfStoreRequisitions");
        try {
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);

                list.add(new wcfStoreRequisitions(b.getString("ApprovalDate"), b.getString("DeptName"), b.getString("ReqStatus")));
            }
        } catch (Exception e) {
            Log.e("wcfStoreRequisitions", "JSONArray error");
        }
        return list;
    }


    public static  String getBtnReqList()
    {
        String a="";
        try{
            a =   JSONParser.getStream(host+"/wcfBtnReqList");

        }catch (Exception e)
        {
            Log.e("changed","done");
        }
        if(a.substring(1,a.length()-2).equals("view"))
        {
            return "view";
        }
        else
            return "generate";

    }





    public static  String GenerateBtnOk()
    {
        String a="";
        try{
            a =   JSONParser.getStream(host+"/wcfGenerateBtnOk");

        }catch (Exception e)
        {
            Log.e("changed","done");
        }
        if(a.substring(1,a.length()-2).equals("true"))
        {
            return "Generated";
        }
        else
            return "Sorry, try again.";


    }

    public static  String ClearbtnOk()
    {
        String a="";
        try{
            a =   JSONParser.getStream(host+"/wcfClearListBtnOK");

        }catch (Exception e)
        {
            Log.e("changed","done");
        }
        if(a.substring(1,a.length()-2).equals("true"))
        {
            return "Cleared";
        }
        else
            return "Sorry, try again.";


    }
}
