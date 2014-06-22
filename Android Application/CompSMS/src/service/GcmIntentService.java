package service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.roy.compsms.MainActivity;
import com.roy.compsms.R;

public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
           if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // This loop represents the service doing some work.
            	String number = extras.getString("num");
            //	String number="5558";
                String message = extras.getString("msg");
                if (number != null && number.length() > 0 && message != null && message.length() > 0) {
                	Log.i(MainActivity.TAG, "num: "+number+" msg: "+message);
                try {
               SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number, null, message, null, null);

                String result = number + ": " + message;
                Log.i(MainActivity.TAG, result);

                ContentValues values = new ContentValues();
                values.put("address", number);
               values.put("body", message);
                getApplicationContext().getContentResolver().insert(Uri.parse("content://sms/sent"), values);
                }
               catch (Exception ex) {
              Log.e(MainActivity.TAG, ex.toString());
               }
                }
                Log.i(MainActivity.TAG, "Completed work @ " + SystemClock.elapsedRealtime());
                // Post notification of received message.
                sendNotification(number, message);
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }


    private void sendNotification(String number, String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent sendIntent = new Intent(Intent.ACTION_MAIN);         
        sendIntent.addCategory(Intent.CATEGORY_DEFAULT);
        sendIntent.setType("vnd.android-dir/mms-sms");
        
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
        		sendIntent, 0);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
        .setAutoCancel(true)
        .setSmallIcon(R.drawable.ic_sms)
        .setContentTitle("Sms Sent")
        .setStyle(new NotificationCompat.BigTextStyle()
        .bigText(number+" : "+msg))
        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}