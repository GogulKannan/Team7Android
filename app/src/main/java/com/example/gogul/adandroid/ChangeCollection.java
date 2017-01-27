package com.example.gogul.adandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class ChangeCollection extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String role;
    String userid;
    String deptid;
    Spinner sItems;
    String current = "o";
    List<String> spinnerArray = new ArrayList<String>();
    String permission;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_collection);
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



        // List<String> spinnerArray =  new ArrayList<String>();

        spinnerArray.add("Stationery Store 09:30:00");
        spinnerArray.add("Management School 11:00:00");
        spinnerArray.add("Medical School 09:30:00");
        spinnerArray.add("Engineering School 11:00:00");
        spinnerArray.add("Science School 09:30:00");
        spinnerArray.add("University Hospital 11:00:00");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems = (Spinner) findViewById(R.id.spinner2);

        sItems.setAdapter(adapter);


        new AsyncTask<String, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(String... params) {

                return wcfRequisitionList.spinnercall(params[0]);
            }

            @Override
            protected void onPostExecute(List<String> result) {
                current = result.get(0);

                for (int t = 0; t < spinnerArray.size(); t++) {
                    if (current.equals(spinnerArray.get(t))) {
                        sItems.setSelection(t);
                    }
                }
            }
        }.execute(deptid);

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
//        getMenuInflater().inflate(R.menu.change_collection, menu);
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

    String selected;

    public void savechange(View v) {
        //get it from there
        selected = sItems.getSelectedItem().toString();
        new AlertDialog.Builder(this)
                .setTitle("Confirm Change?")
                .setMessage("\nNew Collection Point Changed to: "+selected)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialogSuccessfull();

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //   Toast.makeText(getApplicationContext(), getString(R.string.sayno), Toast.LENGTH_SHORT).show();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }



    public void dialogSuccessfull()
    {
        selected = sItems.getSelectedItem().toString();
        Long clptid=sItems.getSelectedItemId()+1;
        //Toast.makeText(getApplicationContext(), "Successfully changed to " + clptid + " . ", Toast.LENGTH_LONG).show();
        String cll=clptid.toString();
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return wcfRequisitionList.updatecollectionpt(params[0],params[1]);

            }

            @Override
            protected void onPostExecute(String result) {
                Toast.makeText(getApplicationContext(), "Successfully changed to " + selected + ".", Toast.LENGTH_SHORT).show();

            }
        }.execute(cll,deptid);

    }
}



