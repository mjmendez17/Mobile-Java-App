package com.example.mauro.proyectotp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;


public class CrearAlumnoActivity extends AppCompatActivity {

    //Declaración DE VARIABLES


    Button crearAlumno;
    EditText et_nombre;
    EditText et_apellido;
    EditText et_edad;
    EditText et_direccion;
    EditText et_estudios;
    EditText et_email;
    EditText et_telefono;

    int VAR_SELECT_IMAGEN = 2;
    ImageView imagen_alumno;

    Alumno alumno_detalle;

    long identificador;


    //Main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_alumno);

        // igualo los ids con el LAYOUT

        crearAlumno = (Button) findViewById(R.id.crearAlumno);
        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_apellido = (EditText) findViewById(R.id.et_apellido);
        et_edad = (EditText) findViewById(R.id.et_edad);

        et_direccion = (EditText) findViewById(R.id.et_direccion);
        et_estudios = (EditText) findViewById(R.id.et_estudios);
        et_email = (EditText) findViewById(R.id.et_email);
        et_telefono = (EditText) findViewById(R.id.et_telefono);
        imagen_alumno = (ImageView) findViewById(R.id.imagen_alumno);


        //CONFIGURAMOS PONER LA IMAGEN DESDE GALERIA
        imagen_alumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Selecciona imagen"), VAR_SELECT_IMAGEN);
                // SIGUE DEBAJO
            }
        });


        //Click en el botón DE CREAR Alumno Y APARECE UNA NUEVA VISTA

        crearAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {


                    String string_foto = getBase64FromImageView(imagen_alumno); //nombre del ImageView declarado
                    String nombre = et_nombre.getText().toString();
                    String apellido = et_apellido.getText().toString();
                    int edad = Integer.parseInt(et_edad.getText().toString()); // paso a texto la variable entera
                    String direccion = et_direccion.getText().toString();
                    String estudios = et_estudios.getText().toString();
                    String email = et_email.getText().toString();
                    String telefono = et_telefono.getText().toString(); // paso a texto la variable entera

                    if (identificador > 0) {
                        alumno_detalle.setFoto(string_foto);
                        alumno_detalle.setNombre(nombre);
                        alumno_detalle.setApellido(apellido);
                        alumno_detalle.setEdad(edad);
                        alumno_detalle.setDireccion(direccion);
                        alumno_detalle.setEstudios(estudios);
                        alumno_detalle.setEmail(email);
                        alumno_detalle.setTelefono(telefono);

                        alumno_detalle.save();

                    }
                    //Recoger los datos de la vista en variables:
                    //Boton crear Alumno

                    else {
                        Alumno nuevo_alumno = new Alumno(nombre, apellido, edad, direccion, estudios, email, telefono, string_foto);


                        //Guardar:
                        nuevo_alumno.save();
                    }
                    finish(); // cerrar la pantalla despues de guardar el empleado creado


                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error: Debe completar todos los datos antes de finalizar... ", Toast.LENGTH_LONG).show();
                }


            }
        });


        ///////////////////////////////////////////////////////////////////////////////

        // QUEREMOS VISUALIZAR TODOS LOS DATOS PARA PODER MODIFICARLOS.
        identificador = getIntent().getLongExtra("identificador", 0);
        if (identificador > 0) {

            alumno_detalle = Alumno.findById(Alumno.class, identificador);


            // PONEMOS SET TEXT XQ NECESITAMOS MODIFICARLAS UNA VEZ MOSTRADAS(EJEMPLO: GET.XXX)
            et_nombre.setText(alumno_detalle.getNombre());
            et_apellido.setText(alumno_detalle.getApellido());
            et_edad.setText(String.valueOf(alumno_detalle.getEdad())); // ponemos value of por que es un entero y lo queremos pasar a texto
            et_direccion.setText(alumno_detalle.getDireccion());
            et_estudios.setText(alumno_detalle.getEstudios());
            et_email.setText(alumno_detalle.getEmail());
            et_telefono.setText(alumno_detalle.getTelefono());
            showImageFromBase64(imagen_alumno, alumno_detalle.getFoto()); // PARA MOSTRAR LA IMAGEN TENEMOS QUE MOSTRARLA SI O SI CON LA FUNCION DECLARADA FUERA DEL MAIN


        }

        //////////////////////////////////////////////////////////////////////////////

    } // fin main on create


    public void showImageFromBase64(ImageView imagen_alumno, String foto) {
        byte[] decodedString = Base64.decode(foto, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imagen_alumno.setImageBitmap(decodedByte);
    }


    //FINALIZAMOS FUERA DEL ON CREATE PARA TERMINAR DE PONER IMAGEN DE GALERIA

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VAR_SELECT_IMAGEN && resultCode == RESULT_OK) {
            try {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    imagen_alumno.setImageURI(selectedImageUri); //asignar al ImageView
                }
            } catch (Exception e) {
                e.printStackTrace(); //Mostrar errores en la consola del Android Studio
                Toast.makeText(getApplicationContext(), "Error al seleccionr imagen", Toast.LENGTH_LONG).show();
            }

        }

    }

    // Función Guardar ImageView como String Y QUE SE PUEDA VISUALIZAR UNA VEZ GUARDADO EN LA BD


    public String getBase64FromImageView(ImageView imagen_alumno) {


        imagen_alumno.buildDrawingCache();
        Bitmap bitmap = imagen_alumno.getDrawingCache();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image = stream.toByteArray();
        System.out.println("byte array:" + image);

        String string_imagen = Base64.encodeToString(image, 0);
        return string_imagen;
    }

}// fin fin
