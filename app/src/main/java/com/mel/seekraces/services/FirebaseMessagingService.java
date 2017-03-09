package com.mel.seekraces.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mel.seekraces.R;
import com.mel.seekraces.activities.detailRace.DetailRaceActivity;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.deserializers.UserDeserializer;
import com.mel.seekraces.entities.Race;
import com.mel.seekraces.entities.User;

/**
 * Created by moha on 16/01/17.
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {

            if (SharedPreferencesSingleton.getInstance(this).containValue(Constantes.KEY_USER)){
                Race race = new Race();
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(User.class, new UserDeserializer());
                Gson gson = gsonBuilder.create();
                User user = gson.fromJson(remoteMessage.getData().get("user"), User.class);
                race.setUser(user);
                race.setDescription(remoteMessage.getData().get("desciption"));
                race.setImageName(remoteMessage.getData().get("imageName"));
                race.setWeb(remoteMessage.getData().get("web"));
                race.setName(remoteMessage.getData().get("name"));
                race.setPlace(remoteMessage.getData().get("place"));
                race.setDistance(Integer.parseInt(remoteMessage.getData().get("distance")));
                race.setDate_time_init(remoteMessage.getData().get("date_time_init"));
                showNotification(race);
            }
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    private void showNotification(Race race) {

        Intent i = new Intent(this, DetailRaceActivity.class);
        //Intent i = new Intent("BroadcastReceiver");
        /*Bundle bundle=new Bundle();
        bundle.putString("race",new Gson().toJson(race));*/
        i.putExtra("race", new Gson().toJson(race));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        TaskStackBuilder taskStackBuilder=TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(DetailRaceActivity.class);
        taskStackBuilder.addNextIntent(i);

        //i.putExtra("broadcasting", true);
        //i.putExtra("bundle",bundle);

        //PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(),i , PendingIntent.FLAG_UPDATE_CURRENT);
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,i , PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent =taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(race.getName())
                .setContentText(race.getUser().getUsername().concat(" ha publicado la carrera "+race.getName()))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        manager.notify(0, builder.build());
    }
}
