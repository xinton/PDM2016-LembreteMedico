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

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(context)
                .setContentTitle("New mail from " + "test@gmail.com")
                .setContentText(intent.getStringExtra("MEDICO")).setSmallIcon(R.drawable.ic_local_hospital)
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
