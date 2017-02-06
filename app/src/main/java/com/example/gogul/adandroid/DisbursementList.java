package com.example.gogul.adandroid;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DisbursementList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String role;
    String userid;
    String RepName;
    String RepPhone;
    String CollectionPoint;
    String DisListID;
    String name;
    String DeliveryDatetime;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_list);
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
        TextView locationpt = (TextView) findViewById(R.id.textloc);
        TextView dateandtime = (TextView) findViewById(R.id.textView3);
        TextView rname = (TextView) findViewById(R.id.name23);

        Intent i = getIntent();
        role=i.getStringExtra("role");
        userid=i.getStringExtra("id");
        name= i.getStringExtra("Name");
        CollectionPoint= i.getStringExtra("CollectionPoint");
        RepName= i.getStringExtra("RepName");
        RepPhone= i.getStringExtra("RepPhone");
        DisListID= i.getStringExtra("DisListID");
        Log.e("s",DisListID);
        DeliveryDatetime= i.getStringExtra("DeliveryDatetime");
        locationpt.setText(CollectionPoint);
        dateandtime.setText(DeliveryDatetime);
        rname.setText(RepName);
        Menu nav_Menu = navigationView.getMenu();
        navUserrole.setText(role);
        navUserid.setText(userid);
        setTitle(name);

        new AsyncTask<String, Void, List<wcfDisbursementListDetail>>() {
            @Override
            protected List<wcfDisbursementListDetail> doInBackground(String... params) {
                return wcfDisbursementListDetail.getDisbursementListDetail(params[0]);
            }
            @Override
            protected void onPostExecute(List<wcfDisbursementListDetail> result) {
                showlist(result);
            }
        }.execute(DisListID);
    }

    public void showlist(List<wcfDisbursementListDetail> result) {
        final   ListView lv = (ListView) findViewById(R.id.listitemgive);
        lv.setAdapter(new SimpleAdapter
                (this, result, R.layout.row2, new String[]{"ItemName", "PreQty","DisbQty"},
                        new int[]{ R.id.text1, R.id.text2,R.id.text3}));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                wcfDisbursementListDetail listItem = (wcfDisbursementListDetail) lv.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), DeliveryAdj.class);
                String msg=listItem.get("ItemName");
                String remark=listItem.get("Remarks");
                String PreQty=listItem.get("PreQty");
                String DisbQty=listItem.get("DisbQty");
                String Ddid=listItem.get("Ddid");

                intent.putExtra("name", msg);
                intent.putExtra("Remarks", remark);
                intent.putExtra("PreQty", PreQty);
                intent.putExtra("DisbQty", DisbQty);
                intent.putExtra("Ddid", Ddid);
                intent.putExtra("RepPhone", RepPhone);

                intent.putExtra("CollectionPoint", CollectionPoint);
                intent.putExtra("DisListID", DisListID);
                intent.putExtra("DeliveryDatetime", DeliveryDatetime);
                intent.putExtra("Name", name);
                intent.putExtra("RepPhone", RepPhone);
                intent.putExtra("RepName", RepName);

                intent.putExtra("role", role);
                intent.putExtra("id", userid);
                startActivity(intent);
            }
        });
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_camera) {
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
            NotificationManager notifiManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notifiManager.cancelAll();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void sendconfirm(View V)
    {
        new AsyncTask<String, Void, String >() {
            @Override
            protected String  doInBackground(String... params) {
                return wcfCDisbursementList.SendForConfirmation(params[0]);
            }
            @Override
            protected void onPostExecute(String result) {
                finish();
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        }.execute(DisListID);
    }

    public void callrep(View v) {
        String r ="tel:"+RepPhone;
        Uri uri = Uri.parse(r);
        Intent i = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(i);

    }

}
