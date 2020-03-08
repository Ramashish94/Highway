package com.highway.common.base.firebaseService;

/**
 * Created by santhosh@appoets.com on 21-05-2018.
 */

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.highway.BuildConfig;
import com.highway.R;
import com.highway.common.base.activity.DashBoardActivity;
import com.highway.utils.Constants;
import com.highway.utils.HighwayPrefs;
import com.highway.utils.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import static com.highway.utils.Constants.COMPLETED;
import static com.highway.utils.Constants.DROPPED;
import static com.highway.utils.Constants.INVOICE;
import static com.highway.utils.Constants.PICKEDUP;
import static com.highway.utils.Constants.RATING;
import static com.highway.utils.Constants.SEARCHING;
import static com.highway.utils.Constants.TRIP_STARTED;

public class MyFirebaseServiceMessaging extends FirebaseMessagingService {
    private LocalBroadcastManager broadcaster;
    int notificationId = 0;
    OnMessageRecievedFromPush onMessageRecievedFromPush;
    private final String TAG = getClass().getSimpleName();
    public static final String INTENT_FILTER = "INTENT_FILTER" + BuildConfig.APPLICATION_ID;

    @Override
    public void onNewToken(String s) {
        @SuppressLint("HardwareIds")
        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        Log.d("DEVICE_ID: ", deviceId);
        Log.d("FCM_TOKEN", s);

        HighwayPrefs.putString(this, "device_token", s);
        HighwayPrefs.putString(this, "device_id", deviceId);
    }


    @Override
    public void onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (!HighwayPrefs.getBoolean(this, Constants.LOGGED_IN)) {
            return;
        }

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Fromdata: " + remoteMessage.getData());
        JSONObject jsonObject = new JSONObject();

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//            sendNotification(remoteMessage.getData().get("message"));
            try {
                String type = remoteMessage.getData().get("type");

                switch (type) {
                    case Constants.TRIP_NEW:
                        jsonObject.put(Constants.PUSH_TYPE, type);
                        jsonObject.put(Constants.CUSTOMER_NAME, remoteMessage.getData().get("customer"));
                        jsonObject.put(Constants.PUSH_MOBILE, remoteMessage.getData().get("mobile"));
                        jsonObject.put(Constants.TRIP_ID, remoteMessage.getData().get("tripId"));
                        jsonObject.put(Constants.SOURCE, remoteMessage.getData().get("source"));
                        jsonObject.put(Constants.DESTINATEION, remoteMessage.getData().get("destination"));

//                        Intent mainIntent = new Intent(this, DashBoardActivity.class);
//                        mainIntent.putExtra(Constants.PUSH_NEW_BOOKING_TRIP_DATA_KEY, jsonObject.toString());
//                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(mainIntent);
                        Intent intent = new Intent("MyData");
                        intent.putExtra(Constants.PUSH_NEW_BOOKING_TRIP_DATA_KEY, jsonObject.toString());

                        broadcaster.sendBroadcast(intent);
                        if (onMessageRecievedFromPush != null) {
                            onMessageRecievedFromPush.onPushData(jsonObject);
                        }
                        break;

                    case SEARCHING:
                        break;
                    case TRIP_STARTED:
                        break;
                    case Constants.ARRIVED:
                        break;
                    case PICKEDUP:
                        break;
                    case DROPPED:
                        break;
                    case COMPLETED:
                        break;
                    case RATING:
                        break;
                    case INVOICE:
                        break;


                }


                handleDataMessage(jsonObject);
            } catch (Exception e) {
                Log.e("Exception: ", e.getMessage());
            }
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
    private void scheduleJob() {
        // [START dispatch_job]
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                //.setService(MyJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */


    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void sendNotification(String messageBody) {

        if (!Utilities.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Utilities.printV(TAG, "foreground");
        } else {
            Utilities.printV(TAG, "background");
            // app is in background, show the notification in notification tray
//            if (messageBody.equalsIgnoreCase("New Incoming Ride")) {
//
//                Intent mainIntent = new Intent(this, DashBoardActivity.class);
//                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                mainIntent.putExtra(Constants.PUSH_NEW_BOOKING_TRIP_DATA_KEY, messageBody);
//                startActivity(mainIntent);
//            }
        }

        // NotificationPushData data = BaseUtil.objectFromString(messageBody, NotificationPushData.class);
        Intent intent = new Intent(getApplicationContext(), DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //intent.putExtra(Constants.PUSH_NEW_BOOKING_TRIP_DATA_KEY, data);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

//        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
//        Intent localIntent = new Intent("CUSTOM_ACTION");
//        // Send local broadcast
//        localBroadcastManager.sendBroadcast(localIntent);
//        localBroadcastManager.sendBroadcast(intent);
//        PushNavigateReceiver receiver = new PushNavigateReceiver();
//        IntentFilter intentFilter = new IntentFilter("custom.notification.navigation");
//        registerReceiver(receiver, intentFilter);
        Intent filter = new Intent("custom.notification.navigation");
        sendBroadcast(filter);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "PUSH");
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine(messageBody);

        long when = System.currentTimeMillis();         // notification time


        // Sets an ID for the notification, so it can be updated.
        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "Channel human readable title";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;


        Notification notification;
        notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker(getString(R.string.app_name)).setWhen(when)
//                .setAutoCancel(true)
                .setContentTitle(getString(R.string.app_name))
                .setContentIntent(pendingIntent)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                .setWhen(when)
                .setSmallIcon(getNotificationIcon(mBuilder))
                .setContentText(messageBody)
                .setChannelId(CHANNEL_ID)
                .setDefaults(Notification.DEFAULT_VIBRATE
                        | Notification.DEFAULT_LIGHTS
                )
                .build();

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            android.app.NotificationChannel mChannel = new android.app.NotificationChannel(CHANNEL_ID, name, importance);
            // Create a notification and set the notification channel.
            notificationManager.createNotificationChannel(mChannel);
        }

        notificationManager.notify(notifyID, notification);
    }

    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            return R.drawable.ic_stat_ic_notification;
        } else {
            return R.mipmap.ic_launcher;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void handleDataMessage(JSONObject json) {
        try {
//            JSONObject data = json.getJSONObject("");
            switch (json.getString("type")) {

                case Constants.NOTIFICATION_TYPE_TRIP_NEW:
//                    String mobile = json.getString("mobile");
//                    String message = json.getString("message");
//                    String destination = json.getString("destination");
//                    String tripId = json.getString("tripId");
//                    String source = json.getString("source");
//                    String type = json.getString("type");
//                    String customer = json.getString("customer");
//                    System.out.println(mobile + message + destination + tripId + source + type + customer);
//                    NotificationPushData data = BaseUtil.objectFromString(message, NotificationPushData.class);
                    sendNotification(json.getString("type"));
                    break;

                case Constants.NOTIFICATION_TYPE_TRIP_CANCEL:
                    break;

                case Constants.NOTIFICATION_TYPE_TRIP_START:
                    break;

                case Constants.NOTIFICATION_TYPE_TRIP_END:
                    break;

                case Constants.NOTIFICATION_TYPE_TRIP_ACCEPTED:
                    break;

                case Constants.NOTIFICATION_TYPE_TRIP_REJECTED:
                    break;

                case Constants.NOTIFICATION_TYPE_TRIP_NORESPONCE:
                    break;
            }
        } catch (JSONException e) {
            Log.e("Json Exception: ", e.getMessage());
        } catch (Exception e) {
            Log.e("Exception: ", e.getMessage());
        }
    }

    public void setPushListener(OnMessageRecievedFromPush onMessageRecievedFromPush) {
        this.onMessageRecievedFromPush = onMessageRecievedFromPush;
    }


    public interface OnMessageRecievedFromPush {
        void onPushData(JSONObject jsonObject);
    }

}
