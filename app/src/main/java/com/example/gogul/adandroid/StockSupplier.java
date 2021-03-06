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
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;



public class StockSupplier extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    String role;
    String userid;
    String S1Phone ;
    String S2Phone ;
    String S3Phone ;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_supplier);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

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

        Intent i = getIntent();
        role=i.getStringExtra("role");
        userid=i.getStringExtra("id");
        String ItemName =i.getStringExtra("ItemName");
        String ActualQty =i.getStringExtra("ActualQty");
        String ReorderLevel =i.getStringExtra("ReorderLevel");
        String ReorderQty =i.getStringExtra("ReorderQty");
        S1Phone =i.getStringExtra("S1Phone");
        S2Phone =i.getStringExtra("S2Phone");
        S3Phone =i.getStringExtra("S3Phone");
        String Supplier1 =i.getStringExtra("Supplier1");
        String Supplier2 =i.getStringExtra("Supplier2");
        String Supplier3 =i.getStringExtra("Supplier3");
        String S1Price =i.getStringExtra("S1Price");
        String S2Price =i.getStringExtra("S2Price");
        String S3Price =i.getStringExtra("S3Price");
        Menu nav_Menu = navigationView.getMenu();
        navUserrole.setText(role);
        navUserid.setText(userid);
        setTitle(ItemName);

        TextView actual = (TextView) findViewById(R.id.actual);
        TextView reorder = (TextView) findViewById(R.id.reorder);
        TextView reorderq = (TextView) findViewById(R.id.reorderq);
        TextView p1 = (TextView) findViewById(R.id.p1);
        TextView p2 = (TextView) findViewById(R.id.p2);
        TextView p3 = (TextView) findViewById(R.id.p3);
        Button s1 = (Button) findViewById(R.id.s1);
        Button s2 = (Button) findViewById(R.id.s2);
        Button s3 = (Button) findViewById(R.id.s3);

        actual.setText(ActualQty);
        reorder.setText(ReorderLevel);
        reorderq.setText(ReorderQty);
        p1.setText(S1Price);
        p2.setText(S2Price);
        p3.setText(S3Price);

        s1.setText("Call: "+Supplier1);
        s2.setText("Call: "+Supplier2);
        s3.setText("Call: "+Supplier3);

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
                }                @Override
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

    public void calls1(View v) {
        Uri uri = Uri.parse("tel:"+S1Phone);
       Intent i = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(i);
    }

    public void calls2(View v) {
        Uri uri = Uri.parse("tel:"+S2Phone);
        Intent i = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(i);
    }

    public void calls3(View v) {
        Uri uri = Uri.parse("tel:"+S3Phone);
        Intent i = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(i);

    }

}
