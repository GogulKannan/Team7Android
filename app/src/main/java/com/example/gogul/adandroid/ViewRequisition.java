package com.example.gogul.adandroid;

import android.app.NotificationManager;
import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewRequisition extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    String role;
    String userid;
    String deptid;
    String permission;
    TextView idcoll;
    List<wcfRequisitionList> forsorting = new ArrayList<wcfRequisitionList>();
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requisition);
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
        View headerView = navigationView.getHeaderView(0);
        TextView navUserrole = (TextView) headerView.findViewById(R.id.user1);
        TextView navUserid = (TextView) headerView.findViewById(R.id.id1);
        idcoll=(TextView)findViewById(R.id.idsorting);

        Intent i = getIntent();
        role = i.getStringExtra("role");
        userid = i.getStringExtra("id");
        deptid = i.getStringExtra("deptid");
        Menu nav_Menu = navigationView.getMenu();
        navUserrole.setText(role);
        navUserid.setText(userid);

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


               new AsyncTask<String, Void, List<wcfRequisitionList>>() {
            @Override
            protected List<wcfRequisitionList> doInBackground(String... params) {
                return wcfRequisitionList.reqlist(params[0]);
            }
            @Override
            protected void onPostExecute(List<wcfRequisitionList> result) {
                showlist(result);
                forsorting=result;
            }
        }.execute(deptid);
    }


    public void showlist(List<wcfRequisitionList> result)
    {
        final ListView lv = (ListView) findViewById(R.id.listviewreq);
        lv.setAdapter(new SimpleAdapter
                (this, result, R.layout.rowviewreq, new String[]{"Name","OrderDate" ,"status"},
                        new int[]{ R.id.text1,R.id.dateorder, R.id.text3}));

        if(result.size()==0)
        {
            idcoll.setVisibility(View.VISIBLE);
            idcoll.setText("No Items in this Category");
        }
        else idcoll.setVisibility(View.GONE);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                wcfRequisitionList listItem = (wcfRequisitionList) lv.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), ViewRequistionOne.class);
                String msg = listItem.get("Name");
                intent.putExtra("Name", msg);
                String qua = listItem.get("status");
                String orderdate = listItem.get("OrderDate");
                intent.putExtra("status", qua);
                intent.putExtra("deptid",deptid);
                intent.putExtra("orderdate",orderdate);

                String itemid = listItem.get("Id");
                intent.putExtra("itemid", itemid);
                intent.putExtra("role", role);
                intent.putExtra("id", userid);
                intent.putExtra("permission", permission);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_requisition, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.approve) {
            List<wcfRequisitionList> list = new ArrayList<wcfRequisitionList>();
            for (wcfRequisitionList b :forsorting  ) {
                if((b.get("status")).equals("Approved"))
                {
                    list.add(new wcfRequisitionList(b.get("Id"), b.get("Name"), b.get("status"), b.get("OrderDate")));
                }
            }
            showlist(list);
            return true;
        }

        if (id == R.id.processing) {
            List<wcfRequisitionList> list = new ArrayList<wcfRequisitionList>();
            for (wcfRequisitionList b :forsorting  ) {
                if((b.get("status")).equals("Processing"))
                {
                    list.add(new wcfRequisitionList(b.get("Id"), b.get("Name"), b.get("status"), b.get("OrderDate")));
                }
            }
            showlist(list);
            return true;
        }
        if (id == R.id.pending) {
            List<wcfRequisitionList> list = new ArrayList<wcfRequisitionList>();
            for (wcfRequisitionList b :forsorting  ) {
                if((b.get("status")).equals("Pending Approval"))
                {
                    list.add(new wcfRequisitionList(b.get("Id"), b.get("Name"), b.get("status"), b.get("OrderDate")));
                }
            }
            showlist(list);
            return true;
        }
        if (id == R.id.reject) {
            List<wcfRequisitionList> list = new ArrayList<wcfRequisitionList>();
            for (wcfRequisitionList b :forsorting  ) {
                if((b.get("status")).equals("Rejected"))
                {
                    list.add(new wcfRequisitionList(b.get("Id"), b.get("Name"), b.get("status"),b.get("OrderDate")));
                }
            }
            showlist(list);
            return true;
        }
        if (id == R.id.Outstanding) {
            List<wcfRequisitionList> list = new ArrayList<wcfRequisitionList>();
            for (wcfRequisitionList b :forsorting  ) {
                if((b.get("status")).equals("Outstanding"))
                {
                    list.add(new wcfRequisitionList(b.get("Id"), b.get("Name"), b.get("status"),b.get("OrderDate")));
                }
            }
            showlist(list);
            return true;
        }
        if (id == R.id.showall) {

            showlist(forsorting);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
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

            NotificationManager notifiManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notifiManager.cancelAll();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
