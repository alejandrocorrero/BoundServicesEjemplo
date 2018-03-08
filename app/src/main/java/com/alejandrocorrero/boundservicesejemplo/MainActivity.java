package com.alejandrocorrero.boundservicesejemplo;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    private LocalBroadcastManager mGestor;
    private BroadcastReceiver mReceptor;
    private IntentFilter mFiltro;
    private Intent intent;
    private boolean mVinculado;
    private ServicioPrueba mServicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGestor = LocalBroadcastManager.getInstance(this);
        // Se crea el receptor de mensajes desde el servicio.
        mReceptor = new BroadcastReceiver() {
            // Cuando se recibe el intent.
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "Me ha llegao algo", Toast.LENGTH_SHORT).show();
               // actualizarIU();
            }
        };
        // Se crea el filtro para al receptor.
        mFiltro = new IntentFilter(ServicioPrueba.ACTION_PRUEBA);
        // Se vincula el servicio.
        vincularServicio();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mGestor.registerReceiver(mReceptor, mFiltro);
        //actualizarIU();
    }

    // Vincula la actividad con el servicio.
    private void vincularServicio() {
        // Se crea el intent de vinculación con el servicio.
        intent = new Intent(getApplicationContext(), ServicioPrueba.class);
        // Se inicia el servicio (para que se pare automáticamente al
        // desvincular).
        getApplicationContext().startService(intent);
        // Se vincula el servicio.
        getApplicationContext().bindService(intent, this,
                Context.BIND_AUTO_CREATE);


        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        // Se desregistra el receptor del gestor de receptores locales.
        mGestor.unregisterReceiver(mReceptor);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // Se desvincula el servicio.
        desvincularServicio();
        super.onDestroy();
    }

    // Desvincula la actividad del servicio.
    private void desvincularServicio() {
        // Se desvincula del servicio.
        if (mVinculado) {
            getApplicationContext().unbindService(this);
            mVinculado = false;
            mServicio = null;
        }
    }

    // Cuando se desconecta el servicio porque se ha producido un error.
    @Override
    public void onServiceDisconnected(ComponentName arg0) {
        mVinculado = false;
        mServicio = null;
    }

    // Cuando se completa la conexión.
    @Override
    public void onServiceConnected(ComponentName className, IBinder binder) {
        mVinculado = true;
        // Se obtiene la referencia al servicio a partir del binder recibido.
        mServicio = ((ServicioPrueba.LocalBinder) binder).getService();

        //actualizarIU();

    }

}
