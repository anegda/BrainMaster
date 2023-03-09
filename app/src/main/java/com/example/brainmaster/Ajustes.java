package com.example.brainmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import java.util.Locale;

public class Ajustes extends AppCompatActivity {
    int tema=1;
    String pIdioma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //MANTENER IDIOMA EN HORIZONTAL
        if (savedInstanceState != null) {
            pIdioma = savedInstanceState.getString("idiomaAct");
            cambiarIdioma(pIdioma);
        }
        else{
            Locale locale = getResources().getConfiguration().getLocales().get(0);
            pIdioma = locale.getLanguage();
            getIntent().putExtra("idiomaAct",pIdioma);
        }

        //ESTABLECER TEMA UTILIZANDO PREFERENCIAS
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String tema = prefs.getString("temapref","1");
        if(tema.equals("1")) {
            Log.d("DAS",tema+" 1");
            setTheme(R.style.Theme_BrainMaster);
        }
        else if(tema.equals("2")){
            Log.d("DAS",tema+" 2");
            setTheme(R.style.Theme_BrainMasterSummer);
        }
        else if(tema.equals("3")){
            Log.d("DAS",tema+" 3");
            setTheme(R.style.Theme_BrainMasterPunk);
        }
        else{
            Log.d("DAS",tema+" 4");
            setTheme(R.style.Theme_BrainMaster);
        }

        //CREAR INTERFAZ
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        String idiomaAct = getIntent().getStringExtra("idiomaAct");
        savedInstanceState.putString("idiomaAct", idiomaAct);
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String idiomaAct = savedInstanceState.getString("idiomaAct");
        pIdioma = idiomaAct;
    }

    protected void cambiarIdioma(String idioma){
        Log.d("DAS",idioma);
        Locale nuevaloc = new Locale(idioma);
        Locale.setDefault(nuevaloc);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.setLocale(nuevaloc);
        configuration.setLayoutDirection(nuevaloc);

        Context context = getBaseContext().createConfigurationContext(configuration);
        getBaseContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }
}