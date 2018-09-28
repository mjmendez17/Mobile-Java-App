package com.example.mauro.proyectotp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListadoAdapter extends ArrayAdapter<Alumno> {

    //VARIABLES
    ArrayList<Alumno> objects; //Corresponde al array de cosas del ListadoActivity.
    Context context; //Referencia al ListadoActivity
    int resource; //identificador del layout fila

    //CONSTRUCTOR

    public ListadoAdapter(Context context, int resource, ArrayList<Alumno> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.context = context;
        this.resource = resource;

    }

    //main
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;// ES LA FILA DEL ESTADO.

        // TRABAJAMOS E IGUALAMOS CON LOS DATOS DEL LAYOUT PERTENECIENTE A ADAPTER, LLAMADO "FILA"

        //INFLATER ES PARA CARGAR LA VISTA(VIEW)
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(resource, parent, false);

        TextView tv_nombre = (TextView) row.findViewById(R.id.tv_nombre);
        tv_nombre.setText(objects.get(position).getNombre());

        TextView tv_apellido = (TextView) row.findViewById(R.id.tv_apellido);
        tv_apellido.setText(objects.get(position).getApellido());

        TextView tv_estudios = (TextView) row.findViewById(R.id.tv_estudios);
        tv_estudios.setText(objects.get(position).getEstudios());

        TextView tv_email = (TextView) row.findViewById(R.id.tv_email);
        tv_email.setText(objects.get(position).getEmail());


        return row;
    }


}