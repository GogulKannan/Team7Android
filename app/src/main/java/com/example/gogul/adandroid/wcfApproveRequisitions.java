package com.example.gogul.adandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e0046709 on 1/18/2017.
 */

public class wcfApproveRequisitions  extends java.util.HashMap<String,String> {

    final static String host = wcflogin.hostname();

    public wcfApproveRequisitions(String itemname, String quanity, String uom) {
        put("EmpName", itemname);
        put("ReqDate", quanity);
        put("ReqID", uom);
    }

    public wcfApproveRequisitions() {
    }


    public static List<wcfApproveRequisitions> getApproveRequisitions(String id) {
        List<wcfApproveRequisitions> list = new ArrayList<wcfApproveRequisitions>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(host+"/wcfApproveRequisitions/"+id);
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);

                list.add(new wcfApproveRequisitions(b.getString("EmpName"), b.getString("ReqDate"),
                        b.getString("ReqID")));
            }
        } catch (Exception e) {
            Log.e("wcfApproveRequisitions", "JSONArray error");
        }
        return list;
    }
}