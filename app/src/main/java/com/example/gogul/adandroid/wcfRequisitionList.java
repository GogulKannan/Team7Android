package com.example.gogul.adandroid;

import android.text.LoginFilter;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e0046709 on 1/17/2017.
 */

public class wcfRequisitionList extends java.util.HashMap<String,String> {

    final static String host = wcflogin.hostname();

    public wcfRequisitionList(String id, String name, String status,String OrderDate) {
        put("Id", id);
        put("Name", name);
        put("status", status);
        put("OrderDate", OrderDate);
    }


    public wcfRequisitionList(){}

    public static List<wcfRequisitionList> reqlist(String id) {
        List<wcfRequisitionList> list = new ArrayList<wcfRequisitionList>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(host+"/wcfRequisitionList/"+id);
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);

                list.add(new wcfRequisitionList(b.getString("Id"), b.getString("Employeename"),
                     b.getString("Status"),b.getString("OrderDate")));
            }
        } catch (Exception e) {
            Log.e("wcfRequisitionList", "JSONArray error");
        }
        return list;
    }

    public static List<String> spinnercall(String id) {
        List<String> list = new ArrayList<String>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(host+"/wcfCollectionPoint/"+id);
        try {
            for (int i =0; i<a.length(); i++)
                list.add(a.getString(i));
        } catch (Exception e) {
        }
        return(list);
    }

    public static Void collected(String collected , String itemno)
    {
        try{
            String a =   JSONParser.getStream(host+"/wcfMarkasCollected?collected="+collected+"&itemNo="+itemno);
        }catch (Exception e)
        {   Log.e("ads",e.toString());    }

        return  null;
    }

    public static String updatecollectionpt(String location,String deptid) {
        String a="";
        try{
           a =   JSONParser.getStream(host+"/wcfChangecollectionpt?dept="+deptid+"&location="+location);
           }catch (Exception e)
        {
            Log.e("changed","done");
        }
        if(a.substring(1,a.length()-2).equals("true"))
        {
            return "true";
        }
        else
            return "false";




    }
}