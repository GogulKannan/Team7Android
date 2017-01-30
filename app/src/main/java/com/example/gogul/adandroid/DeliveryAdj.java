package com.example.gogul.adandroid;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class DeliveryAdj extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    TextView tv;
    TextView needed;
    TextView tvremark;
    EditText etremark;
    int  DisbQtyno;
    int  PreQtyno;
    String Ddid;
    String role;
    String userid;
    String PreQty;
    String titlename;
    String RepName;
    String RepPhone;
    String Remarks;
    String DisListID;
    String CollectionPoint;
    String DeliveryDatetime;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_adj);
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


        //menu hiding
        Intent i = getIntent();
        role=i.getStringExtra("role");
        userid=i.getStringExtra("id");
        String name =i.getStringExtra("name");
        Remarks =i.getStringExtra("Remarks");
        Log.e("rem",Remarks);
        Ddid =i.getStringExtra("Ddid");
        PreQty =i.getStringExtra("PreQty");
        PreQtyno=Integer.parseInt(PreQty);
        String DisbQty =i.getStringExtra("DisbQty");

        titlename= i.getStringExtra("Name");
        CollectionPoint= i.getStringExtra("CollectionPoint");
        RepName= i.getStringExtra("RepName");
        RepPhone= i.getStringExtra("RepPhone");
        DisListID= i.getStringExtra("DisListID");
        DeliveryDatetime= i.getStringExtra("DeliveryDatetime");



        DisbQtyno=Integer.parseInt(DisbQty);
        setTitle(name);
        Menu nav_Menu = navigationView.getMenu();
        navUserrole.setText(role);
        navUserid.setText(userid);
        tvremark = (TextView) findViewById(R.id.tvremark);
        etremark = (EditText) findViewById(R.id.etremark);
        if(!PreQty.equals(DisbQty))
        {
            tvremark.setVisibility(View.VISIBLE);
            etremark.setVisibility(View.VISIBLE);
            Log.e("redsm",Remarks);
            etremark.setText(Remarks);
        }
        else
        {
            tvremark.setVisibility(View.GONE);
            etremark.setVisibility(View.GONE);

        }
        tv = (TextView) findViewById(R.id.delno);
        needed = (TextView) findViewById(R.id.neededno);
        tv.setText(DisbQty);
        needed.setText(PreQty);






    }

    public void show()
    {

        final Dialog d = new Dialog(DeliveryAdj.this);
        d.setTitle("Delivered");
        d.setContentView(R.layout.dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);

        np.setMaxValue(PreQtyno); // max value 100

        np.setMinValue(0);   // min value 0
      // np.setWrapSelectorWheel(true);

        np.setValue(DisbQtyno);//set here da

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String actual = String.valueOf(np.getValue());
                tv.setText(actual); //set the value to textview
                d.dismiss();
                if(!actual.equals(PreQty)) /// from coming list
                {
                    tvremark.setVisibility(View.VISIBLE);
                    etremark.setVisibility(View.VISIBLE);
                }
                else
                {
                    tvremark.setVisibility(View.GONE);
                    etremark.setVisibility(View.GONE);
                    etremark.getText().clear();                }

            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss(); // dismiss the dialog
            }
        });
        d.show();


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
//        getMenuInflater().inflate(R.menu.delivery_adj, menu);
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
            Intent intent = new Intent(this,UnfulfilledRequisitions.class);
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
    public void picker(View v)
    {
        show();
    }
    public void changed(View v)
    {

        String rem= etremark.getText().toString();

        String dv= tv.getText().toString();
        wcfDisbursementListDetail c = new wcfDisbursementListDetail();

            c.put("Remarks",rem);
        c.put("DisbQty",dv);
        c.put("Ddid",Ddid);


        new AsyncTask<wcfDisbursementListDetail, Void, Void>() {
            @Override
            protected Void doInBackground(wcfDisbursementListDetail... params) {
                wcfDisbursementListDetail.updatedqun(params[0]);
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                finish();
                Intent intent = new Intent(getApplicationContext(), DisbursementList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("CollectionPoint", CollectionPoint);
                intent.putExtra("DisListID", DisListID);
                intent.putExtra("DeliveryDatetime", DeliveryDatetime);
                intent.putExtra("Name", titlename);
                intent.putExtra("RepPhone", RepPhone);
                intent.putExtra("RepName", RepName);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Confirmed", Toast.LENGTH_SHORT).show();
            }
        }.execute(c);
    }

}
