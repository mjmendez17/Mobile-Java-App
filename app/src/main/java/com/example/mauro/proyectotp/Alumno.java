package com.example.mauro.proyectotp;

import com.orm.SugarRecord;

/**
 * Created by Mauro on 29/11/2016.
 */

public class Alumno extends SugarRecord { // ponemos el extends para que trabaje con la base de datos

    //Atributos

    private String nombre;
    private String apellido;
    private int edad;
    private String direccion;
    private String estudios;
    private String email;
    private String telefono;
    private String foto; // AGREGADO

    public Alumno() {}

    //CONSTRUCTOR

    public Alumno(String nombre, String apellido, int edad, String direccion, String estudios, String email, String telefono, String foto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.direccion = direccion;
        this.estudios = estudios;
        this.email = email;
        this.telefono = telefono;
        this.foto = foto;
    }

    //SET Y GET

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstudios() {
        return estudios;
    }

    public void setEstudios(String estudios) {
        this.estudios = estudios;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // nuevos


    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
