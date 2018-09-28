package com.example.mauro.proyectotp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ListadoActivity extends AppCompatActivity {

    // DECLARAMOS VARIABLES

    ListView listview;
    ArrayList<Alumno> arraylist_alumnos;
    ListadoAdapter nuestro_adapter;
    Button crear_alumno;


    //MAIN

    @Override
    // DENTRO DEL ON CREATE
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        //IGUALAMOS VARIABLES CON EL ACTIVITY_LISTADO

        listview = (ListView) findViewById(R.id.listview);
        crear_alumno = (Button) findViewById(R.id.crear_alumno);

        // AHORA CONFIGURAMOS EL FUNCIONAMIENTO DE DICHO BOTON "CREAR_ALUMNO"

        crear_alumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Abrir pantalla CrearAlumno

                Intent intent = new Intent(getApplicationContext(), CrearAlumnoActivity.class);
                startActivity(intent);

            }
        });

        arraylist_alumnos = new ArrayList<Alumno>(); //inicializar Arraylist de alumnos

        //Instancia del adapter que hemos creado
        //Utilizamos el array de alumnos.

        nuestro_adapter = new ListadoAdapter(this, R.layout.fila, arraylist_alumnos);

        listview.setAdapter(nuestro_adapter);  //Asignar adaptador al listView

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Abrir pantalla Detalle Alumno.
                Intent intent = new Intent(getApplicationContext(), DetalleActivity.class);
                intent.putExtra("identificador", arraylist_alumnos.get(position).getId());
                startActivity(intent);
            }
        });


        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Eliminar(position);

                return true; // ponemos true para que pregunte y no se ejecute el OnItemClick
            }
        });


    }  // fin ON CREATE

    // ON RESUME ES PARA VISUALIZAR LOS DATOS CREADOS, SIN NECESIDAD DE TENER QUE SALIR Y ENTRAR DE NUEVO EN LA APP
    @Override
    protected void onResume() {
        super.onResume();

        arraylist_alumnos.clear(); // ELIMINA LOS ALUMNOS EN EL ARRAYLIST ANTERIORMENTE.
        arraylist_alumnos.addAll(Alumno.listAll(Alumno.class)); // se agregan los alumnos al arraylist
        nuestro_adapter.notifyDataSetChanged(); // le avisa al programa que se ha realizado modificacion
    }

//ELIMINA EL ALUMNO DEL ARRAY LIST Y DE LA BASE DE DATOS

    public void EliminarAlumno(int position) {
        Alumno p = arraylist_alumnos.get(position); //coger objeto en la posición
        p.delete(); //Borrar de la base de datos
        arraylist_alumnos.remove(position); //Borrar del ArrayList
        //Avisar al adapter que hemos modificado el listado.
        nuestro_adapter.notifyDataSetChanged();
    }

    //MENSAJE DE BORRAR Y ELIMINACION DEFINITIVA USANDO LA FUNCION ANTERIOR

    public void Eliminar(final int p) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("¿Estás seguro de que quieres eliminar a este alumno?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Proceder a eliminar el registro
                        EliminarAlumno(p);

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss(); // Quitar la alerta sin hacer nada
                    }
                });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    /*

    //Función que comprueba si el dispositivo tiene conexión a internet.
    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


    public static String GET(String sUrl) {
        URL url;
        String result = "";
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(sUrl);
            urlConnection = (HttpURLConnection) url.openConnection(); //abrir la conexión

            //Leer el resultado
            InputStream in = urlConnection.getInputStream();
            result = convertInputStreamToString(in);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        //Devuelve el resultado (lo recogeremos en el onPostExecute)
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }*/

} // fin fin
