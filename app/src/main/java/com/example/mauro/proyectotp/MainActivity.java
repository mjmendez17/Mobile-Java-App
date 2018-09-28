package com.example.mauro.proyectotp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.List;
import java.util.ArrayList;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.lang.IllegalStateException;
import java.lang.StringBuilder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    //Variables

    //DECLARACION DE VARIABLES

    Button btn_gps;  // me va a visualizar la ubicacion de la escuela
    Button btn_listado; // boton listado

    SharedPreferences prefs;


    @Override

    //Main
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DECLARAMOS CADA BOTON O PROCEDIMIENTO PARA BUSCARLO EN EL LAYOUT

        btn_gps = (Button) findViewById(R.id.btn_gps);
        btn_listado = (Button) findViewById(R.id.btn_listado);
        ////////////////////////////////////////////////////////////////////////////

        prefs = getSharedPreferences("preferencias", MODE_PRIVATE);


        //CONFIGURAMOS BOTON LISTADO

        btn_listado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListadoActivity.class);
                startActivity(intent);

            }
        });


        //CONFIGURAMOS BOTON GPS

        btn_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intentgps = new Intent(Intent.ACTION_VIEW);

                //Uri gmmIntentUri = Uri.parse("geo:41.388104,2.179900");

                //Codigo para localizacion exacta sabiendo las coordenadas

                Uri gmmIntentUri = Uri.parse("geo:<41.388104>,<2.179900>?z=<zoom>&q=<41.388104>,<2.179900>(<Escuela Formacion Barcelona Activa>)");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
                //intentgps.setData(Uri.parse("http://maps.google.com/"));
                //startActivity(intentgps);
            }
        });




    }//fin main










}//public class







