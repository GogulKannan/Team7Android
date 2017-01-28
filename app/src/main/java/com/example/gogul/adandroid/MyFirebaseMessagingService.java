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
    int icon;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: "+ remoteMessage.getFrom());

        //check if msg contain data
        if(remoteMessage.getData().size()>0){
            Log.d(TAG, "Message Data: "+remoteMessage.getData());
//            Log.e("data..",remoteMessage.getData().get("intent").toString());
//            Log.e("data..",remoteMessage.getData().get("deptId").toString());
        }
//Log.e("log",remoteMessage.getNotification().toString());
        //check if msg contain notification
        if(remoteMessage.getNotification() != null){

            Log.d(TAG,"Message Body: "+remoteMessage.getNotification().getBody());

                Log.e("ttile : ",remoteMessage.getNotification().getBody());
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
    /*display notification
     * @param body
     * */
    private void sendNotification(String title,String body){

        int notificationNumber = pref.getInt("notificationNumber", 0);
        Intent intent ;
        if(intentname.equals("ReceiveRequisition"))
        { intent= new Intent(getApplicationContext(),ReceiveRequisition.class);icon=R.mipmap.pngtoday;}
        else if(intentname.equals("StockCard"))
        { intent= new Intent(getApplicationContext(),StockCard.class);icon=R.mipmap.png3;}
        else if(intentname.equals("DisbursementList"))
        { intent= new Intent(getApplicationContext(),DisbursementMainmenu.class);
            icon=R.mipmap.png5;
        }
        else if(intentname.equals("ApproveRequisition"))
        { intent= new Intent(getApplicationContext(),ApproveRequisition.class);
            icon=R.mipmap.pngaprove;
        }
        else if(intentname.equals("UnfulfilledRequisitions"))
        { intent= new Intent(getApplicationContext(),UnfulfilledRequisitions.class);
            icon=R.mipmap.png5;
        }
        else
        { intent= new Intent(getApplicationContext(),MainActivity.class);}



        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("cloc", pagehead);
        intent.putExtra("time", extraDetail);
        intent.putExtra("reqid", id);
        intent.putExtra("deptid", deptid);
        intent.putExtra("role", role);
        intent.putExtra("id", userid);
        intent.putExtra("permission", permission);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0/*request code*/,intent,PendingIntent.FLAG_ONE_SHOT);
        //set notification sound
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notifiBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);
        NotificationManager notifiManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notifiManager.notify(notificationNumber, notifiBuilder.build());


        SharedPreferences.Editor editor = pref.edit();
        notificationNumber++;
       // Log.e("dsfsfffffffffffffff",Integer.toString(notificationNumber));
        editor.putInt("notificationNumber", notificationNumber);
        editor.commit();
    }

}