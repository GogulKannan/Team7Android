package com.example.gogul.adandroid;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    String psd="0";
    EditText enteruserid;
    EditText enterpsd;
    LinearLayout fake;
    Button Loginbtn;
    String showdialog="no";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
        setTitle("login");
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        enterpsd=(EditText)findViewById(R.id.enterPassword);
        enteruserid = (EditText)findViewById(R.id.userid);
        role=pref.getString("role", "null");
        userid=pref.getString("userid", "null");
        deptid=pref.getString("deptid", "null");
        permission=pref.getString("permission", "null");

        fake = (LinearLayout) findViewById(R.id.progressBar1);
        Loginbtn =(Button)findViewById(R.id.button);

        Intent getin = getIntent();
        if (getin.hasExtra("intent")) {

            Intent intent ;
            String intentname = getin.getStringExtra("intent");
            String notifyid = getin.getStringExtra("id");
            String pagehead = getin.getStringExtra("pageHeader");
            String extraDetail = getin.getStringExtra("extraDetail");

            if(intentname.equals("ReceiveRequisition"))
            {   intent= new Intent(getApplicationContext(),ReceiveRequisition.class);}
            else if(intentname.equals("StockCard"))
            { intent= new Intent(getApplicationContext(),StockCard.class);}
            else if(intentname.equals("DisbursementList"))
            { intent= new Intent(getApplicationContext(),DisbursementMainmenu.class);showdialog="yes"; }
            else if(intentname.equals("ApproveRequisition"))
            { intent= new Intent(getApplicationContext(),ApproveRequisition.class);}
            else if(intentname.equals("reqaccepted"))
            { intent= new Intent(getApplicationContext(),UnfulfilledRequisitions.class);showdialog="yes";}
            else if(intentname.equals("UnfulfilledRequisitions"))
            { intent= new Intent(getApplicationContext(),UnfulfilledRequisitions.class);}
            else
            { intent= new Intent(getApplicationContext(),MainActivity.class);}

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("cloc", pagehead);
            intent.putExtra("time", extraDetail);
            intent.putExtra("reqid", notifyid);
            intent.putExtra("deptid", deptid);
            intent.putExtra("role", role);
            intent.putExtra("id", userid);
            intent.putExtra("showdialog", showdialog);
            intent.putExtra("permission", permission);
            startActivity(intent);
            finishAffinity();
        }
        else
        {
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
    }

    public void login(View v )
    {
        fake.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        userid=enteruserid.getText().toString();
        psd=enterpsd.getText().toString();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.e("token",token);
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
                else if(result.get("authenticate").equals("failed"))
                {
                    fake.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(getApplicationContext(), "Please Check your Connection.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    fake.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(getApplicationContext(), "Please Check User Name and Password.", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(userid,psd,token);
    }

    public void getmein(wcflogin result)
    {
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
