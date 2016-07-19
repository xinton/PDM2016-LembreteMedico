package br.edu.ifpb.si.pdm.lembretemedico;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import junit.framework.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by home on 12/07/2016.
 */

public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        String action = intent.getAction();
        Log.i("Receiver", "Broadcast received: " + action);
       Log.i("Receiver", ":: " + intent.getStringExtra("MEDICO"));

        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent2 = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent2, 0);




        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date d = new Date();
        d.setTime(intent.getLongExtra("DATA", -1));
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String data = df.format(d);

        // Build notification
        Notification noti = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_local_hospital_white)
                .setContentTitle("Consulta com " + intent.getStringExtra("MEDICO"))
                .setContentText( "Data: " + data )
                .setContentIntent(pIntent)/*
                .addAction(R.drawable.ic_menu_send, "Call", pIntent)
                .addAction(R.drawable.ic_menu_send, "More", pIntent)
                .addAction(R.drawable.ic_menu_send, "And more", pIntent)*/
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify((int) (new Date()).getTime()-1, noti);
    }

}
