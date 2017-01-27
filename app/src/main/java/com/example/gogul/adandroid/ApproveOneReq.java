package com.example.gogul.adandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ApproveOneReq extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String role;
    String userid;
    String deptid;
    TextView orderdate;
    SharedPreferences pref;
    String ReqID;
    String permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_one_req);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(1).setChecked(true);
        View headerView = navigationView.getHeaderView(0);
        TextView navUserrole = (TextView) headerView.findViewById(R.id.textView1);
        TextView navUserid = (TextView) headerView.findViewById(R.id.textView);

        //menu hiding
        Intent i = getIntent();
        role=i.getStringExtra("role");
        userid=i.getStringExtra("id");
        deptid=i.getStringExtra("deptid");
        Menu nav_Menu = navigationView.getMenu();
        navUserrole.setText(role);
        navUserid.setText(userid);
        String name = i.getStringExtra("name");
        String date = i.getStringExtra("Desc");
       ReqID = i.getStringExtra("ReqID");
        orderdate = (TextView) findViewById(R.id.date);
        setTitle(name);
        orderdate.setText(date);

        permission = i.getStringExtra("permission");
        String[] substring = permission.split("-");
        if(substring[0].equals("1")){nav_Menu.findItem(R.id.nav_camera).setVisible(true);}
        else{ nav_Menu.findItem(R.id.nav_camera).setVisible(false);}
        if(substring[1].equals("1")){nav_Menu.findItem(R.id.nav_gallery).setVisible(true);}
        else{nav_Menu.findItem(R.id.nav_gallery).setVisible(false);}
        if(substring[2].equals("1")){ nav_Menu.findItem(R.id.nav_slideshow).setVisible(true);}
        else{ nav_Menu.findItem(R.id.nav_slideshow).setVisible(false);}
        if(substring[3].equals("1")){ nav_Menu.findItem(R.id.nav_manage).setVisible(true);}
        else{ nav_Menu.findItem(R.id.nav_manage).setVisible(false);}
        nav_Menu.findItem(R.id.nav_send).setVisible(true);

//
//        ListView lv = (ListView) findViewById(R.id.listoneapp);
//        lv.setAdapter(new SimpleAdapter
//                (this, students, R.layout.rowreqoone, new String[]{"name", "total","avi"},
//                        new int[]{ R.id.text1, R.id.text2,R.id.text3}));

        new AsyncTask<String, Void, List<wcfApproveReqDetails>>() {
            @Override
            protected List<wcfApproveReqDetails> doInBackground(String... params) {
                return wcfApproveReqDetails.getApproveReqDetails(params[0],params[1]);
            }

            @Override
            protected void onPostExecute(List<wcfApproveReqDetails> result) {
                showlist(result);
            }
        }.execute(deptid,ReqID);
    }


    public void showlist(List<wcfApproveReqDetails> result)
    {
        Log.i("p",result.toString());
        ListView lv = (ListView) findViewById(R.id.listoneapp);
        lv.setAdapter(new SimpleAdapter
                (this, result, R.layout.rowreqoone, new String[]{"Item", "Quantity","UOM"},
                        new int[]{ R.id.text1, R.id.text2,R.id.text3}));
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.approve_one_req, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent = new Intent(this, ViewRequisition.class);
            intent.putExtra("role", role);
            intent.putExtra("deptid", deptid);
            intent.putExtra("id", userid);
            intent.putExtra("permission", permission);
            startActivity(intent);
            finishAffinity();
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, ApproveRequisition.class);
            intent.putExtra("role", role);
            intent.putExtra("id", userid);
            intent.putExtra("deptid", deptid);
            intent.putExtra("permission", permission);
            startActivity(intent);
            finishAffinity();

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, ChangeCollection.class);
            intent.putExtra("role", role);
            intent.putExtra("id", userid);
            intent.putExtra("deptid", deptid);
            intent.putExtra("permission", permission);
            startActivity(intent);
            finishAffinity();

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this, ViewCollectionDetails.class);
            intent.putExtra("role", role);
            intent.putExtra("id", userid);
            intent.putExtra("deptid", deptid);
            intent.putExtra("permission", permission);
            startActivity(intent);
            finishAffinity();

        } else if (id == R.id.nav_send) {
            new AsyncTask<String, Void,String>() {
                @Override
                protected String  doInBackground(String... params) {
                    return wcflogin.wcflogmeout(params[0]);
                }

                @Override
                protected void onPostExecute(String result) {
                    if(result.equals("Logged Out"))
                    { pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = pref.edit();
                        role = "null";
                        editor.putString("userid", role);
                        editor.putString("role", role);
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();}
                    else
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                }
            }.execute(userid);


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void approve(View v)
    {
        String text="null";
        String status="Approve";
        Log.i("reqid",ReqID);
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {

                return wcfApproveReqDetails.approvereq(params[0]);

            }

            @Override
            protected void onPostExecute(String result) {

                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ApproveRequisition.class);

                intent.putExtra("role", role);
                intent.putExtra("id", userid);
                intent.putExtra("deptid", deptid);
                intent.putExtra("permission", permission);
                startActivity(intent);
                finish();

            }
        }.execute(ReqID);

    }

    public void viewlist(View v) {
        ListView lv = (ListView) findViewById(R.id.listoneapp);
        lv.setVisibility(View.VISIBLE);
        LinearLayoutCompat heading = (LinearLayoutCompat)findViewById(R.id.heading);
        heading.setVisibility(View.VISIBLE);
        LinearLayoutCompat lin = (LinearLayoutCompat)findViewById(R.id.comments);
        lin.setVisibility(View.GONE);
        LinearLayoutCompat rej = (LinearLayoutCompat)findViewById(R.id.but);
        rej.setVisibility(View.VISIBLE);

    }

    public void reject(View v)
    {
        ListView lv = (ListView) findViewById(R.id.listoneapp);
        lv.setVisibility(View.GONE);
        LinearLayoutCompat lin = (LinearLayoutCompat)findViewById(R.id.comments);
        lin.setVisibility(View.VISIBLE);
        LinearLayoutCompat rej = (LinearLayoutCompat)findViewById(R.id.but);
        rej.setVisibility(View.GONE);
        LinearLayoutCompat heading = (LinearLayoutCompat)findViewById(R.id.heading);
        heading.setVisibility(View.GONE);

    }
    public void rejectdone(View v) {

        TextView remarks = (TextView)findViewById(R.id.editText3);
        String text=remarks.getText().toString();



        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {

                return wcfApproveReqDetails.rejectreq(params[0],params[1]);

            }

            @Override
            protected void onPostExecute(String result) {

                Intent intent = new Intent(getApplicationContext(), ApproveRequisition.class);
                intent.putExtra("role", role);
                intent.putExtra("id", userid);
                intent.putExtra("deptid", deptid);
                intent.putExtra("permission", permission);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                finish();

            }
        }.execute(ReqID,text);

    }

}
