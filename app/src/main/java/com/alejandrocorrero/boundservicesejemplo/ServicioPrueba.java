package com.alejandrocorrero.boundservicesejemplo;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

public class ServicioPrueba extends Service{
    private LocalBinder mBinder;
    public static String ACTION_PRUEBA= "com.alejandrocorrero.boundservicesejemplo.action.test";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }
    public class LocalBinder extends Binder {
        // Retorna la instancia del servicio para que el cliente pueda
        // llamar a sus métodos públicos.
        public ServicioPrueba getService() {
            return ServicioPrueba.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        android.os.Debug.waitForDebugger();
        // Se crea el binder con el que se vinculará la actividad.
        mBinder = new LocalBinder();


        enviarBroadcast();
        //Acciones aqui
    }

    private void enviarBroadcast() {
        // Se envía un intent informativo a los receptores locales registrados
        // para la acción.
        Intent intentRespuesta = new Intent(ACTION_PRUEBA);
        LocalBroadcastManager gestor = LocalBroadcastManager.getInstance(this);
        gestor.sendBroadcast(intentRespuesta);
    }


     public void prueba(){
         Toast.makeText(this, "PRUEBA", Toast.LENGTH_SHORT).show();
     }

}
