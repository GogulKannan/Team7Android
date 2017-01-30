package com.example.gogul.adandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e0046709 on 1/19/2017.
 */

public class wcfDisbursementListDetail extends java.util.HashMap<String,String> {

        final static String host = wcflogin.hostname();

public wcfDisbursementListDetail(String DisbQty, String ItemName, String Remarks,String PreQty,String itemid,String Ddid) {
        put("DisbQty", DisbQty);
        put("ItemName", ItemName);
        put("Remarks", Remarks);
        put("PreQty", PreQty);
        put("Itemid", itemid);
        put("Ddid", Ddid);

        }

public wcfDisbursementListDetail() {
        }


public static List<wcfDisbursementListDetail> getDisbursementListDetail(String id) {
        List<wcfDisbursementListDetail> list = new ArrayList<wcfDisbursementListDetail>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(host + "/wcfDisbursementListDetail/"+id);
        try {
        for (int i = 0; i < a.length(); i++) {
        JSONObject b = a.getJSONObject(i);

        list.add(new wcfDisbursementListDetail(b.getString("DisbQty"), b.getString("ItemName"), b.getString("Remarks"), b.getString("PreQty"), b.getString("Itemid"),b.getString("Ddid")));}
        } catch (Exception e) {
        Log.e("wcfDisburs", "JSONArray error");
        }
        return list;
        }

        public static void updatedqun(wcfDisbursementListDetail c ) {
                JSONObject jc = new JSONObject();
                try {
                        jc.put("DisbQty",c.get("DisbQty"));
                        jc.put("Remarks",c.get("Remarks"));
                        jc.put("Ddid",c.get("Ddid"));
                } catch (Exception e) {
                }
                String result = JSONParser.postStream(host+"/UpdateDisbQty", jc.toString());
        }
        }

