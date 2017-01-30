package com.example.gogul.adandroid;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
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
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RetrievalAll extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String role;
    String userid;
    String max ;
    TextView headerRet;
    TextView space;
    String RetrievedQty;
    String ItemNo;
    String Status;
    LinearLayoutCompat h1;
    LinearLayoutCompat h2;
    String putinpicker;
    Button colt;
    Button bring;
    SharedPreferences pref;
    String toastupdate="Collected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieval_all);
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
        navigationView.getMenu().getItem(0).setChecked(true);

        Intent i = getIntent();
        View headerView = navigationView.getHeaderView(0);
        TextView navUserrole = (TextView) headerView.findViewById(R.id.roletx);
        role=i.getStringExtra("role");
        TextView navUserid = (TextView) headerView.findViewById(R.id.useridtx);
        navUserrole.setText(role);
        userid=i.getStringExtra("id");
        navUserid.setText(userid);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        headerRet= (TextView) findViewById(R.id.nameRE);

        String header=i.getStringExtra("ItemName");
        max = i.getStringExtra("RequestedQty");
        String RequestedQty = i.getStringExtra("RequestedQty");
        RetrievedQty = i.getStringExtra("RetrievedQty");
        ItemNo = i.getStringExtra("ItemNo");
        Status = i.getStringExtra("Status");

        TextView needed = (TextView)findViewById(R.id.needed);
        space = (TextView)findViewById(R.id.space);
        needed.setText(RequestedQty);

        setTitle(header);
        h1= (LinearLayoutCompat)findViewById(R.id.h1);
        h2= (LinearLayoutCompat)findViewById(R.id.h2);
        colt = (Button)findViewById(R.id.collectupdate);
        bring = (Button)findViewById(R.id.bring);


        if(Status.equals("Collected"))
        {
            toastupdate="Updated";
            colt.setText("update");
            headerRet.setText(RetrievedQty);
            putinpicker=RetrievedQty;
        }
        else
        {
            toastupdate="Collected";
            colt.setText("collect");
            headerRet.setText(max);
            putinpicker=max;
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
    String collected;
    public void submitretrival(View v){

     collected = headerRet.getText().toString();
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                return wcfRequisitionList.collected(params[0],params[1]);
            }
            @Override
            protected void onPostExecute(Void result) {
            }
        }.execute(collected,ItemNo);
        finish();
        Intent intent = new Intent(this, Store.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("role", role);
        intent.putExtra("id", userid);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), toastupdate, Toast.LENGTH_SHORT).show();
    }

    public void picker(View v)
    {
        show();
    }

    public void show()
    {
        final Dialog d = new Dialog(RetrievalAll.this);
        d.setTitle("Retrieved Quantity");
        d.setContentView(R.layout.dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(Integer.parseInt(max));
        np.setMinValue(0);
        np.setValue(Integer.parseInt(putinpicker));
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String actual = String.valueOf(np.getValue());
                headerRet.setText(actual);
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }

}

