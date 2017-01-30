package com.example.gogul.adandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class UnfulfilledRequisitions extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    LinearLayoutCompat listhead;
    ListView unfulfillmentlist;
    TextView msg;
    TextView space;
    Button btngenerste;
    Button btnviewlist;
    Button btnclear;
    SharedPreferences pref;
    String userid;
    String role;
    String deptid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unfulfilled_requisitions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Intent i = getIntent();
        View headerView = navigationView.getHeaderView(0);
        TextView navUserrole = (TextView) headerView.findViewById(R.id.textView1);
        role=i.getStringExtra("role");
        TextView navUserid = (TextView) headerView.findViewById(R.id.textView);
        navUserrole.setText(role);
        userid=i.getStringExtra("id");
        navUserid.setText(i.getStringExtra("id"));
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        setTitle("View Requisitions");

        listhead =(LinearLayoutCompat) findViewById(R.id.listhead1);
        unfulfillmentlist = (ListView)findViewById(R.id.unfulfilllist);
        space = (TextView) findViewById(R.id.spacebtwbtn);
        btngenerste = (Button)findViewById(R.id.btngenerate);
        btnviewlist = (Button)findViewById(R.id.btnviewlist);
        btnclear = (Button)findViewById(R.id.btnclear);
        msg = (TextView) findViewById(R.id.msg);

        if(getIntent().getExtras().containsKey("showdialog"))
        {
          if(i.getStringExtra("showdialog").equals("yes"))
            dialogmsg(i.getStringExtra("cloc"));
        }
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return wcfStoreRequisitions.getBtnReqList();
            }
            @Override
            protected void onPostExecute(String result) {
                if(result.equals("view"))
                {
                    btngenerste.setVisibility(View.GONE);
                    btnviewlist.setVisibility(View.VISIBLE);
                    btnclear.setVisibility(View.VISIBLE);
                    space.setVisibility(View.GONE);
                }
                else
                {
                    btngenerste.setVisibility(View.VISIBLE);
                    btnviewlist.setVisibility(View.GONE);
                    btnclear.setVisibility(View.GONE);
                    space.setVisibility(View.VISIBLE);
                }
            }
        }.execute();


        new AsyncTask<String, Void, List<wcfStoreRequisitions>>() {
            @Override
            protected List<wcfStoreRequisitions> doInBackground(String... params) {
                return wcfStoreRequisitions.getStoreRequisitions();
            }
            @Override
            protected void onPostExecute(List<wcfStoreRequisitions> result) {
            showlist(result);
            }
        }.execute();
    }


    public void showlist(List<wcfStoreRequisitions> result)
    {
        final ListView lv = (ListView) findViewById(R.id.unfulfilllist);
        lv.setAdapter(new SimpleAdapter
                (this, result, R.layout.rowstorereq, new String[]{"DeptName","ReqStatus"},
                        new int[]{ R.id.text1,R.id.text2}));
        if(result.size()==0)
        {
            listhead.setVisibility(View.GONE);
            unfulfillmentlist.setVisibility(View.GONE);
            btngenerste.setVisibility(View.GONE);
            msg.setVisibility(View.VISIBLE);
            msg.setText("All requisitions have been processed.\n\nAuto-allocation is done based on requisition order. Manual re-allocation must be performed on desktop before:\n"+
                    "(1) a new retrieval list is generated. \n"+
                    "(2) the disbursement has been collected.\n");
        }
        else
        {
            listhead.setVisibility(View.VISIBLE);
            unfulfillmentlist.setVisibility(View.VISIBLE);
            msg.setVisibility(View.GONE);
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
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, StockCard.class);
            intent.putExtra("role", role);
            intent.putExtra("id", userid);
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

            NotificationManager notifiManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notifiManager.cancelAll();

        }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

    }

    public void GenerateRetrival(View  v)
    {
        new AlertDialog.Builder(this)
                .setTitle("Generate Retrieval list")
                .setMessage("Auto-allocation is done based on requisition order. Manual re-allocation must be performed on desktop before: \n" +
                        "(1) a new retrieval list is generated \n" +
                        "(2) the disbursement has been collected\nGenerate List?")
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        GenerateRetrivalconfirm();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    public void GenerateRetrivalconfirm()
    {

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return wcfStoreRequisitions.GenerateBtnOk();
            }
            @Override
            protected void onPostExecute(String result) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                            }
        }.execute();
        Intent intent = new Intent(this, UnfulfilledRequisitions.class);
        intent.putExtra("role", role);
        intent.putExtra("id", userid);
        startActivity(intent);
        finishAffinity();
    }

    public void clearRetrival(View v)
    {

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return wcfStoreRequisitions.ClearbtnOk();
            }
            @Override
            protected void onPostExecute(String result) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        }.execute();
        Intent intent = new Intent(this, UnfulfilledRequisitions.class);
        intent.putExtra("role", role);
        intent.putExtra("id", userid);
        startActivity(intent);
        finishAffinity();
    }
    public void ViewRetrival(View v)
    {
        Intent intent = new Intent(this, Store.class);
        intent.putExtra("role", role);
        intent.putExtra("id", userid);
        startActivity(intent);
    }

    public void dialogmsg(String deptname)
    {

        final Dialog d = new Dialog(this);
        d.setTitle(deptname);
        d.setContentView(R.layout.successchange);
        d.setCancelable(false);
        TextView msgread= (TextView)d.findViewById(R.id.messagedia);
        msgread.setText("have accepted the Disbursement.");
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
