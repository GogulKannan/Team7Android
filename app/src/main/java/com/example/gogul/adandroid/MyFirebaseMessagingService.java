package com.example.gogul.adandroid;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.EditText;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by e0046709 on 1/25/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    String intentname;
    String id;
    SharedPreferences pref;
    String userid;
    String role;
    String deptid;
    String permission;
    String extraDetail;
    String pagehead;
    int iconnoti=0;
    String showdialog="no";
    int SEPARATE_INT_VALUE=0;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: "+ remoteMessage.getFrom());
        if(remoteMessage.getData().size()>0){
            Log.d(TAG, "Message Data: "+remoteMessage.getData());
        }
        if(remoteMessage.getNotification() != null)
        {
                getallPre();
                intentname= remoteMessage.getData().get("intent");
                id= remoteMessage.getData().get("id");
                pagehead = remoteMessage.getData().get("pageHeader");
                extraDetail=remoteMessage.getData().get("extraDetail");
                sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }
    }

    public void getallPre()
    {
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        role=pref.getString("role", "null");
        userid=pref.getString("userid", "null");
        deptid=pref.getString("deptid", "null");
        permission=pref.getString("permission", "null");
    }

    public void sendNotification(String title,String body){

        int notificationNumber = pref.getInt("notificationNumber", 0);
        SEPARATE_INT_VALUE = pref.getInt("SEPARATE_INT_VALUE", 0);
        Intent intent ;
        if(intentname.equals("ReceiveRequisition"))
        { intent= new Intent(getApplicationContext(),ReceiveRequisition.class);iconnoti=R.mipmap.pngtoday;}
        else if(intentname.equals("StockCard"))
        { intent= new Intent(getApplicationContext(),StockCard.class);iconnoti=R.mipmap.png3;}
        else if(intentname.equals("DisbursementList"))
        { intent= new Intent(getApplicationContext(),DisbursementMainmenu.class);iconnoti=R.mipmap.png5;showdialog="yes";}
        else if(intentname.equals("ApproveRequisition"))
        { intent= new Intent(getApplicationContext(),ApproveRequisition.class);iconnoti=R.mipmap.pngaprove;}
        else if(intentname.equals("reqaccepted"))
        { intent= new Intent(getApplicationContext(),UnfulfilledRequisitions.class);iconnoti=R.mipmap.png5;showdialog="yes";}
        else if(intentname.equals("UnfulfilledRequisitions"))
        { intent= new Intent(getApplicationContext(),UnfulfilledRequisitions.class);iconnoti=R.mipmap.png5;}
        else
        { intent= new Intent(getApplicationContext(),MainActivity.class);}

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("cloc", pagehead);
        intent.putExtra("time", extraDetail);
        intent.putExtra("reqid", id);
        intent.putExtra("deptid", deptid);
        intent.putExtra("role", role);
        intent.putExtra("id", userid);
        intent.putExtra("showdialog", showdialog);
        intent.putExtra("permission", permission);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,SEPARATE_INT_VALUE,intent,PendingIntent.FLAG_ONE_SHOT);

        SharedPreferences.Editor editor = pref.edit();
        SEPARATE_INT_VALUE++;
        editor.putInt("SEPARATE_INT_VALUE", SEPARATE_INT_VALUE);
        editor.commit();
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notifiBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(iconnoti)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);
        NotificationManager notifiManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notifiManager.notify(notificationNumber, notifiBuilder.build());

        SharedPreferences.Editor editor1 = pref.edit();
        notificationNumber++;
        editor1.putInt("notificationNumber", notificationNumber);
        editor1.commit();
    }

    public void clearallnotification()
    {
        NotificationManager notifiManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notifiManager.cancelAll();
    }

}