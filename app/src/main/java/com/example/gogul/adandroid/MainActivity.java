package com.example.gogul.adandroid;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import junit.framework.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  {

    SharedPreferences pref;
    String userid;
    String role;
    String deptid;
    String permission;
    String EmpName;

    String loginid="0";
    String psd="0";
    EditText enteruserid;
    EditText enterpsd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
        setTitle("login");


        Log.e("s","login");

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        enterpsd=(EditText)findViewById(R.id.enterPassword);
        enteruserid = (EditText)findViewById(R.id.userid);
        role=pref.getString("role", "null");
        userid=pref.getString("userid", "null");
        deptid=pref.getString("deptid", "null");
        permission=pref.getString("permission", "null");
       // EmpName=pref.getString("EmpName", "null");



//        FirebaseMessaging.getInstance().subscribeToTopic("t");
//        FirebaseInstanceId.getInstance().getToken();
//        String token = FirebaseInstanceId.getInstance().getToken();
//        Log.e("D",token);



        if(!role.equals("null")) {
            if (role.equals("Store Clerk")) {
                Intent intent = new Intent(this, UnfulfilledRequisitions.class);
                intent.putExtra("role", role);
                intent.putExtra("id", userid);
                startActivity(intent);
                finishAffinity();
            }
            else if (role.equals("Employee")||role.equals("Representative")||role.equals("Department Head")) {
                Intent intent = new Intent(this, ViewRequisition.class);
                intent.putExtra("role", role);
                intent.putExtra("id", userid);
                intent.putExtra("deptid", deptid);
                intent.putExtra("permission", permission);
                startActivity(intent);
                finishAffinity();
            }



        }
    }




    public void Clerk(View v)
    {

        SharedPreferences.Editor editor = pref.edit();
        role="Store Clerk";
        userid="2";
        deptid="0";
        editor.putString("userid", userid);
        editor.putString("role", role);
        editor.commit();
        Intent intent = new Intent(this, UnfulfilledRequisitions.class);
        intent.putExtra("role", role);
        intent.putExtra("id", userid);
        startActivity(intent);
        finishAffinity();

    }
    public void Employee(View v)
    {
        SharedPreferences.Editor editor = pref.edit();
        role="Employee";
        userid="14";
        deptid="4";
        permission ="1-0-0-0";
        editor.putString("userid", userid);
        editor.putString("role", role);
        editor.putString("deptid", deptid);
        editor.putString("permission", permission);
        editor.commit();
        Intent intent = new Intent(this, ViewRequisition.class);
        intent.putExtra("role", role);
        intent.putExtra("id", userid);
        intent.putExtra("deptid", "4");
        intent.putExtra("permission", permission);
        startActivity(intent);
        finishAffinity();


    }
    public void Boss(View v)
    {
        SharedPreferences.Editor editor = pref.edit();
        role="Department Head";
        userid="8";
        deptid="4";
        permission ="1-1-1-0";
        editor.putString("userid", userid);
        editor.putString("role", role);
        editor.putString("deptid", deptid);
        editor.putString("permission", permission);
        editor.commit();
        Intent intent = new Intent(this, ViewRequisition.class);
        intent.putExtra("role", role);
        intent.putExtra("id", userid);
        intent.putExtra("deptid", "4");
        intent.putExtra("permission", permission);
        startActivity(intent);
        finishAffinity();

    }
    public void representative(View v)
    {
        SharedPreferences.Editor editor = pref.edit();
        role="Representative";
        userid="19";
        deptid="4";
        permission="1-0-1-1";
        editor.putString("userid", userid);
        editor.putString("role", role);
        editor.putString("deptid", deptid);
        editor.putString("permission", permission);
        editor.commit();
        Intent intent = new Intent(this, ViewRequisition.class);
        intent.putExtra("role", role);
        intent.putExtra("id", userid);
        intent.putExtra("deptid", "4");
        intent.putExtra("permission", permission);
        startActivity(intent);
        finishAffinity();



    }

    public void login(View v )
    {


        userid=enteruserid.getText().toString();
        psd=enterpsd.getText().toString();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.e("taking",token.toString());
        new AsyncTask<String, Void,wcflogin>() {

            @Override
            protected wcflogin doInBackground(String... params) {

                return wcflogin.gologin(params[0],params[1],params[2]);
            }

            @Override
            protected void onPostExecute(wcflogin result) {
                if ( result.get("authenticate").equals("true")) {
                   getmein(result);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please Check User Name and Password.", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(userid,psd,token);

    }

    public void getmein(wcflogin result)
    {
        Log.i("role", result.toString());
        SharedPreferences.Editor editor = pref.edit();
        role=result.get("role");
        userid=result.get("userid");

        deptid=result.get("deptid");
        permission=result.get("Permission");
        editor.putString("userid", userid);
        editor.putString("role", role);
        editor.putString("deptid", deptid);
        editor.putString("permission", permission);
        editor.commit();

        if(!role.equals("null")) {

            if (role.equals("Store Clerk")) {
                Intent intent = new Intent(this, UnfulfilledRequisitions.class);
                intent.putExtra("role", role);
                intent.putExtra("id", userid);
                startActivity(intent);
                finishAffinity();
            }
            else if (role.equals("Employee")||role.equals("Representative")||role.equals("Department Head")) {
                Intent intent = new Intent(this, ViewRequisition.class);
                intent.putExtra("role", role);
                intent.putExtra("id", userid);
                intent.putExtra("deptid", deptid);
                intent.putExtra("permission", permission);
                startActivity(intent);
                finishAffinity();
            }



        }
        Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
    }



}
