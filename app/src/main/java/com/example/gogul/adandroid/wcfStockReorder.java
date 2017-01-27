package com.example.gogul.adandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e0046709 on 1/19/2017.
 */

public class wcfStockReorder extends java.util.HashMap<String,String> {

    final static String host = wcflogin.hostname();

    public wcfStockReorder(String ActualQty, String ItemName, String ReorderLevel,String ReorderQty, String S1Phone, String S2Phone,String S3Phone, String Supplier1, String Supplier2, String Supplier3, String S1Price, String S2Price, String S3Price) {
        put("ActualQty", ActualQty);
        put("ItemName", ItemName);
        put("ReorderLevel", ReorderLevel);
        put("ReorderQty", ReorderQty);
        put("S1Phone", S1Phone);
        put("S2Phone", S2Phone);
        put("S3Phone", S3Phone);
        put("Supplier1", Supplier1);
        put("Supplier2", Supplier2);
        put("Supplier3", Supplier3);
        put("S1Price", S1Price);
        put("S2Price", S2Price);
        put("S3Price", S3Price);
    }

    public wcfStockReorder(){}



    public static List<wcfStockReorder> getStockReorder() {
        List<wcfStockReorder> list = new ArrayList<wcfStockReorder>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(host+"/wcfStockReorder");
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);

                list.add(new wcfStockReorder(b.getString("ActualQty"), b.getString("ItemName"),
                        b.getString("ReorderLevel"),b.getString("ReorderQty"),b.getString("S1Phone"), b.getString("S2Phone"),
                        b.getString("S3Phone"), b.getString("Supplier1"), b.getString("Supplier2"), b.getString("Supplier3"),
                         b.getString("S1Price"), b.getString("S2Price"), b.getString("S3Price")));
            }
        } catch (Exception e) {
            Log.e("wcfStockReorder", "JSONArray error");
        }
        return list;
    }

}
