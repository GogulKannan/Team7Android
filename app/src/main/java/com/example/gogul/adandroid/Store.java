package com.example.gogul.adandroid;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.content.Context;
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
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Store extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {



    String role;
    String userid;
    Button bring;
    TextView idcoll;
    ListView lv;
    List<wcfRetrivalList> forsorting = new ArrayList<wcfRetrivalList>();
    SharedPreferences pref;
    Date sending;
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

        getMenuInflater().inflate(R.menu.store, menu);
        return true;
    }
    public void allcatebuttonhide()
    {
        int flag=0;
        for (wcfRetrivalList t:forsorting ) {
            if(t.get("Status").equals("Not Collected"))
            {
                flag=1;
            }
        }
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

        int id = item.getItemId();
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

        if (id == R.id.nav_all) {

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



    public void allocatebutton(String sending)
    {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return wcfallocate.getallocate(params[0]);
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
        }.execute(sending);
    }
        public void bringlisth(View v)
        {

            dateshow();

        }

    public void confirm(String  sending)
    {
        final  String se= sending;
        new AlertDialog.Builder(this)
                .setTitle("Confirm Allocation & Delivery Date")
                .setMessage("Once allocated, updates can only be made on desktop.\nConfirm allocation?")
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        allocatebutton(se);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }







public void dateshow()
{

    String myFormat = "MM/dd/yyyy"; //In which you need put here
    final  SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    final  Calendar myCalendar = Calendar.getInstance();
    final Calendar today=Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);



           String sending= sdf.format(myCalendar.getTime());
            if(myCalendar.getTime().before(today.getTime()) )
            {
                Toast.makeText(getApplicationContext(), "Delivery date cant be before today.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                confirm(sending);
            }


        }
    };

    new DatePickerDialog(Store.this, date, myCalendar
            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)).show();


}



}
