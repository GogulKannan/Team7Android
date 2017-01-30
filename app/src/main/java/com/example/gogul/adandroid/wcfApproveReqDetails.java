package com.example.gogul.adandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e0046709 on 1/18/2017.
 */

public class wcfApproveReqDetails extends java.util.HashMap<String,String> {

        final static String host = wcflogin.hostname();

public wcfApproveReqDetails(String itemname, String quanity, String uom) {
        put("Item", itemname);
        put("Quantity", quanity);
        put("UOM", uom);
        }
public wcfApproveReqDetails() {
        }


public static List<wcfApproveReqDetails> getApproveReqDetails(String deptid,String reqid) {
        List<wcfApproveReqDetails> list = new ArrayList<wcfApproveReqDetails>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(host+"/wcfApproveReqDetails?d="+deptid+"&r="+reqid);
        try {
        for (int i =0; i<a.length(); i++) {
        JSONObject b = a.getJSONObject(i);

        list.add(new wcfApproveReqDetails(b.getString("Item"), b.getString("Quantity"),
        b.getString("UOM")));
        }
        } catch (Exception e) {
        Log.e("wcfApproveReqDetails", "JSONArray error");
        }
                return list;
        }




        public static String approvereq(String reqid) {

                String a = JSONParser.getStream(host+"/wcfSubmitApproveReq?reqId="+reqid);
                if(a.substring(1,a.length()-2).equals("True"))
                {
                        return "Approved";
                }
                else
                        return "Sorry, try again.";
        }




        public static String rejectreq(String reqid,String remarks) {

                String a = JSONParser.getStream(host+"/wcfSubmitRejectReq?reqId="+reqid+"&remarks="+remarks);
                if(a.substring(1,a.length()-2).equals("True"))
                {
                        return "Rejected";
                }
                else
                return "Sorry, try again.";

        }
        }