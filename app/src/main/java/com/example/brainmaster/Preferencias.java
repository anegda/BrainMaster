package com.example.brainmaster;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceFragmentCompat;

import java.util.Locale;

public class Preferencias extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{
    @Override
    public void onCreatePreferences (Bundle savedInstanceState, String rootKey){
        addPreferencesFromResource(R.xml.pref_config);
    }

    @Override
    public void onSharedPreferenceChanged (SharedPreferences sharedPreferences, String s){
        switch (s) {
            case "switch":
                Boolean notis = sharedPreferences.getBoolean("switch",false);
                if(notis){
                    //NOTIFICACIÓN PERIÓDICA
                    //https://www.youtube.com/watch?v=nl-dheVpt8o
                    //https://developer.android.com/training/scheduling/alarms?hl=es-419
                    //https://www.tutorialspoint.com/how-to-create-everyday-notifications-at-certain-time-in-android
                    Intent intent = new Intent(getContext(), ReminderBroadcast.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(getContext().ALARM_SERVICE);

                    long timeAtButtonClick = System.currentTimeMillis();

                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeAtButtonClick,1000*60*60*24, pendingIntent);
                }else{
                    //CANCELAMOS LA NOTIFICACIÓN DIARIA
                    Intent intent = new Intent(getContext(), ReminderBroadcast.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(getContext().ALARM_SERVICE);

                    alarmManager.cancel(pendingIntent);
                }
                break;
            case "temapref":
                //NOTIFICACIÓN POR CAMBIO DE TEMA
                NotificationManager elManager = (NotificationManager) getContext().getSystemService(getContext().NOTIFICATION_SERVICE);
                NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(getContext(),"notifySimple");
                elBuilder.setSmallIcon(R.drawable.logo)
                        .setContentTitle(getString(R.string.noti1Titulo))
                        .setContentText(getString(R.string.noti1Contenido))
                        .setVibrate(new long[] {0,500,100,1000})
                        .setAutoCancel(true);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    NotificationChannel elCanal = new NotificationChannel("notifySimple","Canal notificación simple",NotificationManager.IMPORTANCE_DEFAULT);
                    elCanal.setDescription("Canal notificación simple");
                    elCanal.enableLights(true);
                    elCanal.setLightColor(Color.RED);
                    elCanal.setVibrationPattern(new long[]{0, 500, 100, 1000});
                    elCanal.enableVibration(true);
                    elManager.createNotificationChannel(elCanal);
                }
                elManager.notify(11, elBuilder.build());
                startActivity(new Intent(getContext(), Menu.class));
                getActivity().finish();
                break;
            case "idiomapref":
                String idioma = sharedPreferences.getString("idiomapref","es");

                Log.d("DAS",idioma);
                Locale nuevaloc = new Locale(idioma);
                Locale.setDefault(nuevaloc);
                Configuration configuration = getContext().getResources().getConfiguration();
                configuration.setLocale(nuevaloc);
                configuration.setLayoutDirection(nuevaloc);

                Context context = getContext().createConfigurationContext(configuration);
                getContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
                startActivity(getActivity().getIntent());
                getActivity().finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

}
