package com.example.gogul.adandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e0046709 on 1/20/2017.
 */

public class wcflogin extends java.util.HashMap<String,String> {

    final static String host = wcflogin.hostname();

    public wcflogin(String authenticate, String userid, String role, String deptid,String Permission,String EmpName) {
        put("authenticate", authenticate);
        put("userid", userid);
        put("role",role);
        put("deptid",deptid);
        put("Permission",Permission);
        put("EmpName",EmpName);
    }

    public wcflogin() {
    }


    public static  wcflogin gologin(String userid , String psd,String token)
    {

        JSONObject b = JSONParser.getJSONFromUrl(host+"/wcflogin?userid="+userid+"&password="+psd+"&token="+token);

        try {
            return new wcflogin(b.getString("Authenticate"),b.getString("Userid"),
                    b.getString("Role"),  b.getString("Deptid"),b.getString("Permission"),b.getString("EmpName"));
        } catch (Exception e) {

            return new wcflogin("failed","","","","","");
        }
    }


    public static String hostname()
    {
        String host = "http://172.17.254.183/Service.svc";
        return host;
    }

    public static  String  wcflogmeout(String userid)
    {
        String a = JSONParser.getStream(host+"/wcfLogout?userid="+userid);
        if(a.substring(1,a.length()-2).equals("true"))
        {
            return "Logged Out";
        }
        else
            return "Sorry, try again.";
    }

}