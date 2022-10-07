package com.llorcabotas.juan.gestoralpha;

import android.app.Application;
import android.os.SystemClock;

public class MyApp extends Application {
    //Muestra durante 3 segundos la pantalla de inicio antes de abrir el main
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(3000);
    }
}
