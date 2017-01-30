package com.example.gogul.adandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class ReceiveRequisition extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String role;
    String userid;
    String reqid;
    TextView timetv;
    String deptid;
    SharedPreferences pref;
    String permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_requisition);
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
        navigationView.getMenu().getItem(3).setChecked(true); View headerView = navigationView.getHeaderView(0);
        TextView navUserrole = (TextView) headerView.findViewById(R.id.textView1);
        TextView navUserid = (TextView) headerView.findViewById(R.id.textView);


        Intent i = getIntent();
        role=i.getStringExtra("role");
        userid=i.getStringExtra("id");
        Menu nav_Menu = navigationView.getMenu();
        navUserrole.setText(role);
        navUserid.setText(userid);
        String name = i.getStringExtra("cloc");
        String time = i.getStringExtra("time");
        reqid = i.getStringExtra("reqid");
        deptid = i.getStringExtra("deptid");
        setTitle(name);
        timetv= (TextView)findViewById(R.id.timetv);
        timetv.setText(time);
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



        new AsyncTask<String, Void, List<wcfTodayCollectionDetail>>() {
            @Override
            protected List<wcfTodayCollectionDetail> doInBackground(String... params) {
                return wcfTodayCollectionDetail.getTodayCollectionDetail(params[0],params[1]);
            }

            @Override
            protected void onPostExecute(List<wcfTodayCollectionDetail> result) {
                showlist(result);
            }
        }.execute(deptid,reqid);

    }


    public void showlist(List<wcfTodayCollectionDetail> result)
    {
        final ListView lv = (ListView) findViewById(R.id.listreciver);

        lv.setAdapter(new SimpleAdapter
                (this, result, R.layout.rowrecive, new String[]{"cloc", "time","reqid"},
                        new int[]{ R.id.text1, R.id.text2,R.id.text3}));

//        lv.setAdapter(new SimpleAdapter
//                (this, students, R.layout.rowrecive, new String[]{"name", "total","avi"},
//                        new int[]{ R.id.text1, R.id.text2,R.id.text3}));

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
//        getMenuInflater().inflate(R.menu.receive_requisition, menu);
//        return true;
//    }
//
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

//    public void callclerkp(View v)
//    {
//        Uri uri = Uri.parse("tel:92345678");
//        Intent i = new Intent(Intent.ACTION_DIAL, uri);
//        startActivity(i);
//    }

    public void Acceptrecive(View v)
    {
        new AsyncTask<String, Void, String >() {
            @Override
            protected String  doInBackground(String... params) {
                return wcfTodayCollection.AcceptCollection(params[0]);
            }

            @Override
            protected void onPostExecute(String result) {
                Intent intent = new Intent(getApplicationContext(), ViewCollectionDetails.class);
                intent.putExtra("role", role);
                intent.putExtra("id", userid);
                intent.putExtra("deptid", deptid);
                intent.putExtra("permission", permission);
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finishAffinity();

            }
        }.execute(reqid);


    }
}
