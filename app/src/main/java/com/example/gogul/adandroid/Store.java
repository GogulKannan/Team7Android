package com.example.gogul.adandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInstaller;
import android.graphics.Color;
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

public class Store extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {



    String role;
    String userid;
    Button bring;
    TextView idcoll;
    ListView lv;
    List<wcfRetrivalList> forsorting = new ArrayList<wcfRetrivalList>();
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        //code here
        Intent i = getIntent();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUserrole = (TextView) headerView.findViewById(R.id.storerole);
        role=i.getStringExtra("role");
        TextView navUserid = (TextView) headerView.findViewById(R.id.storeusername);
        navUserrole.setText(role);
        userid=i.getStringExtra("id");
        navUserid.setText(i.getStringExtra("id"));
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        bring = (Button)findViewById(R.id.bring);
        idcoll=(TextView)findViewById(R.id.idcollected);
        lv = (ListView) findViewById(R.id.listall);


        new AsyncTask<String, Void, List<wcfRetrivalList>>() {
            @Override
            protected List<wcfRetrivalList> doInBackground(String... params) {
                Log.i("sdsadasdasdsad","?");
                    return wcfRetrivalList.getRetrivalList();

            }

            @Override
            protected void onPostExecute(List<wcfRetrivalList> result) {
                forsorting=result;
                showlist(result);



            }
        }.execute();


    }


    public void showlist(List<wcfRetrivalList> result)
    {
       // final ListView lv = (ListView) findViewById(R.id.listall);
        lv.setAdapter(new SimpleAdapter
                (this, result, R.layout.row, new String[]{"ItemName", "RequestedQty","Status"},
                        new int[]{ R.id.text1, R.id.text2,R.id.text3}));


        allcatebuttonhide();

        if(result.size()==0)
        {
            idcoll.setVisibility(View.VISIBLE);
            idcoll.setText("No Items in this Category");
            bring.setVisibility(View.GONE);

        }
        else idcoll.setVisibility(View.GONE);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                wcfRetrivalList listItem = (wcfRetrivalList) lv.getItemAtPosition(position);


                    Intent intent = new Intent(getApplicationContext(), RetrievalAll.class);
                    String ItemName = listItem.get("ItemName");
                    intent.putExtra("ItemName", ItemName);
                    String RequestedQty = listItem.get("RequestedQty");
                    String RetrievedQty = listItem.get("RetrievedQty");
                    intent.putExtra("RequestedQty", RequestedQty);
                    String itemid = listItem.get("ItemNo");
                    String Status = listItem.get("Status");
                    intent.putExtra("ItemNo", itemid);
                    intent.putExtra("Status", Status);

                    intent.putExtra("RetrievedQty", RetrievedQty);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.store, menu);
        return true;
    }
    public void allcatebuttonhide()
    {
        int flag=0;
        Log.i("forsorting",forsorting.toString());
        for (wcfRetrivalList t:forsorting ) {
            Log.e("king", t.get("Status"));
            if(t.get("Status").equals("Not Collected"))
            {
                flag=1;
            }
        }
//        Log.e("king", String.valueOf(flag));
        if(flag==0)
        {
            bring.setVisibility(View.VISIBLE);


        }

        else if(flag==1)
        {
            bring.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_Collected) {
            List<wcfRetrivalList> list = new ArrayList<wcfRetrivalList>();
            for (wcfRetrivalList b :forsorting  ) {
                if((b.get("Status")).equals("Collected"))
                {
                    list.add(new wcfRetrivalList(b.get("ItemName"), b.get("RequestedQty"),
                            b.get("RetrievedQty"), b.get("Status"), b.get("ItemNo")));
                }
            }
            showlist(list);
            return true;

        }

        if (id == R.id.nav_not) {
            List<wcfRetrivalList> list = new ArrayList<wcfRetrivalList>();
            for (wcfRetrivalList b :forsorting  ) {
                if((b.get("Status")).equals("Not Collected"))
                {
                    list.add(new wcfRetrivalList(b.get("ItemName"), b.get("RequestedQty"),
                            b.get("RetrievedQty"), b.get("Status"), b.get("ItemNo")));
                }
            }
            showlist(list);
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
            SharedPreferences.Editor editor = pref.edit();
            role="null";
            editor.putString("userid", role);
            editor.putString("role", role);
            editor.commit();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finishAffinity();
            Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();

            //TODO proper logout

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //TODO MAKE IT BETTER PLS.


    public void allocatebutton()
    {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {

                return wcfallocate.getallocate();

            }

            @Override
            protected void onPostExecute(String result) {

                Intent intent = new Intent(getApplicationContext(), UnfulfilledRequisitions.class);
                intent.putExtra("role", role);
                intent.putExtra("id", userid);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                finishAffinity();

            }
        }.execute();
    }
        public void bringlisth(View v)
        {


            new AlertDialog.Builder(this)
                    .setTitle("Confirm Allocation")
                    .setMessage("Once allocated, updates can only be made on desktop.\nConfirm allocation?")
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            allocatebutton();

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

}
