

package com.example.mauro.proyectotp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000; // tiempo de ejecucion


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences pref = getSharedPreferences("Preferencias", MODE_PRIVATE); // valor para guardar el resultado
                boolean te_login = pref.getBoolean("te_login", false); // boolean para saber si hace login o no

                if(te_login){ // preguntar si el login es exitoso con user y pass
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class); //luego de mostrar el splash , muestra la pantalla principal del main
                    startActivity(intent); // ejecutar intent anterior
                }
                else{
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent); // ejecutar intent anterior
                }

                finish();
            }
        }, SPLASH_TIME_OUT);

        getSupportActionBar().hide(); // OCULTA LA BARRA SUPERIOR DEL SPLASH PARA MEJOR VISUALIZACION
    }
}
