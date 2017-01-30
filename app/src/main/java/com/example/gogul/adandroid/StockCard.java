package com.example.gogul.adandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StockCard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String role;
    String userid;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_card);
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
        navigationView.getMenu().getItem(2).setChecked(true);
        View headerView = navigationView.getHeaderView(0);
        TextView navUserrole = (TextView) headerView.findViewById(R.id.textView1);
        TextView navUserid = (TextView) headerView.findViewById(R.id.textView);


        //menu hiding
        Intent i = getIntent();
        role=i.getStringExtra("role");
        userid=i.getStringExtra("id");
        Menu nav_Menu = navigationView.getMenu();
        navUserrole.setText(role);
        navUserid.setText(userid);




        new AsyncTask<String, Void, List<wcfStockReorder>>() {
            @Override
            protected List<wcfStockReorder> doInBackground(String... params) {
                return wcfStockReorder.getStockReorder();
            }

            @Override
            protected void onPostExecute(List<wcfStockReorder> result) {
                showlist(result);
            }
        }.execute();




    }


    public void showlist(List<wcfStockReorder> result)
    {

        final ListView lv = (ListView) findViewById(R.id.listshort);
        lv.setAdapter(new SimpleAdapter
                (this, result, R.layout.rowshortage, new String[]{"ItemName", "ActualQty"},
                        new int[]{ R.id.text1, R.id.text2}));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                wcfStockReorder listItem = (wcfStockReorder) lv.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), StockSupplier.class);
                String ItemName=listItem.get("ItemName");
                intent.putExtra("ItemName", ItemName);
                String ActualQty=listItem.get("ActualQty");
                String ReorderLevel=listItem.get("ReorderLevel");
                String ReorderQty=listItem.get("ReorderQty");
                String S1Phone=listItem.get("S1Phone");
                String S2Phone=listItem.get("S2Phone");
                String S3Phone=listItem.get("S3Phone");
                String S1Price=listItem.get("S1Price");
                String S2Price=listItem.get("S2Price");
                String S3Price=listItem.get("S3Price");
                String Supplier1=listItem.get("Supplier1");
                String Supplier2=listItem.get("Supplier2");
                String Supplier3=listItem.get("Supplier3");

                intent.putExtra("ItemName", ItemName);
                intent.putExtra("ActualQty", ActualQty);
                intent.putExtra("ReorderLevel", ReorderLevel);
                intent.putExtra("ReorderQty", ReorderQty);
                intent.putExtra("S1Phone", S1Phone);
                intent.putExtra("S2Phone", S2Phone);
                intent.putExtra("S3Phone", S3Phone);
                intent.putExtra("S1Price", S1Price);
                intent.putExtra("S2Price", S2Price);
                intent.putExtra("S3Price", S3Price);
                intent.putExtra("Supplier1", Supplier1);
                intent.putExtra("Supplier2", Supplier2);
                intent.putExtra("Supplier3", Supplier3);



                intent.putExtra("role", role);
                intent.putExtra("id", userid);
                startActivity(intent);
            }
        });
    }


//
//        final ListView lv = (ListView) findViewById(R.id.listshort);
//        lv.setAdapter(new SimpleAdapter
//                (this, students, R.layout.rowshortage, new String[]{"name", "quantity"},
//                        new int[]{ R.id.text1, R.id.text2}));
//
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Product listItem = (Product) lv.getItemAtPosition(position);
//                Intent intent = new Intent(getApplicationContext(), StockSupplier.class);
//                String msg=listItem.get("name");
//                intent.putExtra("name", msg);
//                String qua=listItem.get("quantity");
//                intent.putExtra("quantity", qua);
//               intent.putExtra("role", role);
//                intent.putExtra("id", userid);
//                startActivity(intent);
//            }
//        });


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
//        getMenuInflater().inflate(R.menu.stock_card, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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
            //TODO proper logout

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
