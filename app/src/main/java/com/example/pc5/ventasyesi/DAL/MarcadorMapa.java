package com.example.pc5.ventasyesi.DAL;

/**
 * Created by yesica on 16/08/2016.
 */
public class MarcadorMapa {
    double latitud;
            double longitud;
    String nombre;
    String Descripcion;

    public MarcadorMapa(double latitud, double longitud, String nombre, String descripcion) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombre = nombre;
        Descripcion = descripcion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
