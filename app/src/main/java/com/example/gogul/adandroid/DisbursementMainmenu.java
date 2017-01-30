package com.example.gogul.adandroid;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DisbursementMainmenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String role;
    String userid;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_mainmenu);
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



        Intent i = getIntent();
        role = i.getStringExtra("role");
        userid = i.getStringExtra("id");
        Menu nav_Menu = navigationView.getMenu();
        navUserrole.setText(role);
        navUserid.setText(userid);

        if(getIntent().getExtras().containsKey("showdialog"))
        {
            if(i.getStringExtra("showdialog").equals("yes"))
                dialogmsg(i.getStringExtra("cloc"),i.getStringExtra("reqid"));

        }

        new AsyncTask<String, Void, List<wcfCDisbursementList>>() {
            @Override
            protected List<wcfCDisbursementList> doInBackground(String... params) {
                return wcfCDisbursementList.getclerkCDisbursementList();
            }

            @Override
            protected void onPostExecute(List<wcfCDisbursementList> result) {
                showlist(result);

            }
        }.execute();
    }

    public void showlist(List<wcfCDisbursementList> result)
    {
        final ListView lv = (ListView) findViewById(R.id.listalldept);
        lv.setAdapter(new SimpleAdapter
                (this, result, R.layout.rowdept, new String[]{"DeptName","DeliveryDatetime" ,"CollectionPoint"},
                        new int[]{ R.id.text1,R.id.text2,R.id.text3}));



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                wcfCDisbursementList listItem = (wcfCDisbursementList) lv.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), DisbursementList.class);
                String msg = listItem.get("DeptName");
                intent.putExtra("Name", msg);
                String qua = listItem.get("status");
                String CollectionPoint = listItem.get("CollectionPoint");
                String RepName = listItem.get("RepName");
                String RepPhone = listItem.get("RepPhone");
                String DisListID = listItem.get("DisListID");
                String DeliveryDatetime = listItem.get("DeliveryDatetime");
                intent.putExtra("status", qua);
                intent.putExtra("CollectionPoint", CollectionPoint);
                intent.putExtra("DisListID", DisListID);
                intent.putExtra("DeliveryDatetime", DeliveryDatetime);

                intent.putExtra("RepPhone", RepPhone);
                intent.putExtra("RepName", RepName);


                String itemid = listItem.get("Id");
                intent.putExtra("itemid", itemid);
                intent.putExtra("role", role);
                intent.putExtra("id", userid);

                startActivity(intent);
            }
        });
        for (wcfCDisbursementList re :result ) {

            Log.i("gokul",re.toString());

        }

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
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.disbursement_mainmenu, menu);
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
            Intent intent = new Intent(this, UnfulfilledRequisitions.class);
            intent.putExtra("role", role);
            intent.putExtra("id", userid);
            startActivity(intent);
            finishAffinity();
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, DisbursementMainmenu.class);
            intent.putExtra("role", role);
            intent.putExtra("id", userid);
            startActivity(intent);
            finishAffinity();
        }
        else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, StockCard.class);
            intent.putExtra("role", role);
            intent.putExtra("id", userid);
            startActivity(intent);
            finishAffinity();

        }else if (id == R.id.nav_send) {
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

    public void dialogmsg(String deptname,String location)
    {

        final Dialog d = new Dialog(this);
        d.setTitle(deptname);
        d.setContentView(R.layout.successchange);
        d.setCancelable(true);
        TextView msgread= (TextView)d.findViewById(R.id.messagedia);
        msgread.setText("have changed the collection point to "+location+".");
        Button t = (Button) d.findViewById(R.id.btnsuccok);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }
}
