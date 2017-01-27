package com.example.gogul.adandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e0046709 on 1/18/2017.
 */


public class wcfRequisitionitem extends java.util.HashMap<String,String> {

    final static String host = wcflogin.hostname();

    public wcfRequisitionitem(String itemname, String quanity, String uom) {
        put("itemname", itemname);
        put("quanity", quanity);
        put("uom", uom);
    }

    public wcfRequisitionitem() {
    }


    public static List<wcfRequisitionitem> getwcfreqitem(String  deptid,String id) {
        List<wcfRequisitionitem> list = new ArrayList<wcfRequisitionitem>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(host+"/wcfRequisitionItem?d="+deptid+"&r="+id);
//        Log.i("link","/wcfRequisitionItem/?d="+deptid+"&r="+id);
        Log.i("json",a.toString());
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new wcfRequisitionitem(b.getString("Itemname"), b.getString("Quantity"),
                        b.getString("Uom")));
            }
        } catch (Exception e) {
            Log.e("getwcfreqitem00000", "JSONArray error");
        }
        return list;
    }
    }