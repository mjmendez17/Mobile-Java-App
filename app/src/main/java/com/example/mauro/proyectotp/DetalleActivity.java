package com.example.mauro.proyectotp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class DetalleActivity extends AppCompatActivity {

    // DECLARACION VARIABLES

    // CREO LOS TEXT VIEW EXACTAMENTE CON LOS MISMOS NOMBRES QUE ESTAN EN "ACTIVITY_DETALLE"

    Alumno alumno_detalle;

    int p;

    TextView tv_detallenombre;
    TextView tv_detalleapellido;
    TextView tv_detalleedad;
    TextView tv_detalledireccion;
    TextView tv_detalleestudios;
    TextView tv_detalleemail;
    TextView tv_detalletelefono;
    ImageView imagen_alumno;


    String string_foto;

    Button editar_alumno;
    Button eliminar_alumno;


    //int VAR_SELECT_IMAGEN = 2;
    //ImageView imagen_alumno;


    //funcion para volver atras con la flecha EN LA VISUALIZACION

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }


    // MAIN
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Comparamos y llamamos las variables con el layout de activity_detalle

        tv_detallenombre = (TextView) findViewById(R.id.tv_detallenombre);
        //tv_detalleapellido = (TextView) findViewById(R.id.tv_detalleapellido);
        tv_detalleedad = (TextView) findViewById(R.id.tv_detalleedad);
        tv_detalledireccion = (TextView) findViewById(R.id.tv_detalledireccion);
        tv_detalleestudios = (TextView) findViewById(R.id.tv_detalleestudios);
        tv_detalleemail = (TextView) findViewById(R.id.tv_detalleemail);
        tv_detalletelefono = (TextView) findViewById(R.id.tv_detalletelefono);



        eliminar_alumno = (Button) findViewById(R.id.btn_Eliminar);
        editar_alumno = (Button) findViewById(R.id.btn_Editar);


        imagen_alumno = (ImageView) findViewById(R.id.imagen_alumno); // AGREGADO PARA FOTO


        //creamos el log de indentificacion para que se abra en otra pantalla el detalle seleccionado

        // 0 es el valor por defecto


        long identificador = getIntent().getLongExtra("identificador", 0);
        // seleccionamos el alumno con id = identificador de la tabla Alumno

        alumno_detalle = Alumno.findById(Alumno.class, identificador);

        // Asignamos los valores de los atributos de Persona en los del TextView de la pantalla


        tv_detallenombre.setText(alumno_detalle.getNombre() + " " + alumno_detalle.getApellido());
        tv_detalleedad.setText("Edad: " + String.valueOf(alumno_detalle.getEdad())); // HAGO UN STRING VALUE OF PORQUE ES UN VALOR ENTERO
        tv_detalledireccion.setText("Direccion: " + alumno_detalle.getDireccion());
        tv_detalleestudios.setText("Estudios: " + alumno_detalle.getEstudios());
        tv_detalleemail.setText("Email: " + alumno_detalle.getEmail());
        tv_detalletelefono.setText("Telefono: " + alumno_detalle.getTelefono()); // HAGO UN STRING VALUE OF PORQUE ES UN VALOR ENTERO

        // PARA DAR VISUALIZAR LA IMAGEN CUANDO SE ACCEDA AL DETALLE DEL ALUMNO.
        string_foto = alumno_detalle.getFoto();

        showImageFromBase64(imagen_alumno, string_foto);


        //CONFIGURAMOS EL BOTON DE EDITAR/MODIFICAR

        editar_alumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Modificar(p);

            }
        });


        //CONFIGURAMOS EL BOTON DE ELIMINAR ALUMNO

        eliminar_alumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // creo una variable de tipo int p, declarada arriba xq en la funcion eliminar declarada debajo del main, utiliza dicho dato
                Eliminar(p);

            }
        });


    }// fin main


    // FUNCION ELIMINAR, PARA ELIMINAR ALUMNO DENTRO DE DETALLE

    public void Eliminar(final int p) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("¿Estás seguro de que quieres eliminar a este alumno?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Proceder a eliminar el registro
                        alumno_detalle.delete();
                        finish(); // es para terminar la actividad y volver hacia atras
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

    public void Modificar(final int p) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setMessage("¿Estás seguro de que quieres modificar a este alumno?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //Proceder a modificar el registro


                        long identificador = getIntent().getLongExtra("identificador", 0);

                        Intent intent = new Intent(getApplicationContext(), CrearAlumnoActivity.class); // se dirige a la pantalla crear alumno
                        intent.putExtra("identificador", identificador);
                        startActivity(intent);


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


    public void showImageFromBase64(ImageView imagen_alumno, String foto) {
        byte[] decodedString = Base64.decode(foto, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imagen_alumno.setImageBitmap(decodedByte);
    }


}// fin fin



