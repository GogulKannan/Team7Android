package com.example.gogul.adandroid;

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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ApproveRequisition extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String role;
    String userid;
    String deptid;
    SharedPreferences pref;
    ListView listapprove;
    TextView msg;
    String permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_requisition);
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
        listapprove= (ListView) findViewById(R.id.listapprove);
        msg= (TextView) findViewById(R.id.msg);

        //menu hiding
        Intent i = getIntent();
        role=i.getStringExtra("role");
        userid=i.getStringExtra("id");
        deptid=i.getStringExtra("deptid");
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



        // getData();

        new AsyncTask<String, Void, List<wcfApproveRequisitions>>() {
            @Override
            protected List<wcfApproveRequisitions> doInBackground(String... params) {
                return wcfApproveRequisitions.getApproveRequisitions(params[0]);
            }

            @Override
            protected void onPostExecute(List<wcfApproveRequisitions> result) {
                showlist(result);
            }
        }.execute(deptid);


    }


    public void showlist(List<wcfApproveRequisitions> result)
    {
        Log.i("p",result.toString());
        final ListView lv = (ListView) findViewById(R.id.listapprove);
        lv.setAdapter(new SimpleAdapter
                (this, result, R.layout.rowapprove, new String[]{"EmpName","ReqDate"},
                        new int[]{ R.id.text1,R.id.text2}));
        if(result.size()==0)
        {
            listapprove.setVisibility(View.GONE);
            msg.setVisibility(View.VISIBLE);
            msg.setText("All Requisitions are processed.");


        }
        else
        {       msg.setVisibility(View.GONE);
                listapprove.setVisibility(View.VISIBLE);
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                wcfApproveRequisitions listItem = (wcfApproveRequisitions) lv.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), ApproveOneReq.class);
                String msg=listItem.get("EmpName");
                intent.putExtra("name", msg);
                String qua=listItem.get("ReqDate");
                String ReqID=listItem.get("ReqID");
                intent.putExtra("Desc", qua);

                intent.putExtra("ReqID", ReqID);
                intent.putExtra("deptid", deptid);


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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.approve_requisition, menu);
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
}
