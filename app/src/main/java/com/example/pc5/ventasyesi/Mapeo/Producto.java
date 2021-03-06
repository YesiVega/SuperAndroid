package com.example.pc5.ventasyesi.Mapeo;
// Generated 08-nov-2015 14:48:03 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Producto generated by hbm2java
 */
public class Producto  implements java.io.Serializable {


     private int Idprod;
     private String Codp;
     private String Nombre;
     private double Precio;
     private String Imagen;
     private String Coduni;
     private int Codsup;
     private Set detalleLists = new HashSet(0);
    private Super misuper;
    private int cantidad;

    public Producto() {
        misuper=null;
        cantidad=0;
    }

    public Super getMisuper() {
        return misuper;
    }

    public void setMisuper(Super misuper) {
        this.misuper = misuper;
    }

    public Producto(int Idprod, String Codp, String Nombre, double Precio, String Imagen,String Coduni,int Codsup) {
       this.Idprod= Idprod;
        this.Codp = Codp;
        this.Nombre = Nombre;
        this.Precio = Precio;
        this.Imagen = Imagen;
        this.Coduni=Coduni;
        this.Codsup=Codsup;
        cantidad=0;
    }
    public Producto(int Idprod,String Codp, String Nombre, double Precio, String Imagen,String Coduni,int Codsup, Set detalleLists) {
        this.Idprod= Idprod;
       this.Codp = Codp;
       this.Nombre = Nombre;
       this.Precio = Precio;
       this.Imagen = Imagen;
        this.Coduni=Coduni;
        this.Codsup=Codsup;
        cantidad=0;
       this.detalleLists = detalleLists;
    }

    public int getIdprod() {
        return this.Idprod;
    }

    public void setIdprod(int Idprod) {
        this.Idprod = Idprod;
    }
    public String getCodp() {
        return this.Codp;
    }
    
    public void setCodp(String Codp) {
        this.Codp = Codp;
    }
    public String getNombre() {
        return this.Nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    public double getPrecio() {
        return this.Precio;
    }
    
    public void setPrecio(double Precio) {
        this.Precio = Precio;
    }
    public String getImagen() {
        return this.Imagen;
    }
    
    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }
    public Set getDetalleLists() {
        return this.detalleLists;
    }
    
    public void setDetalleLists(Set detalleLists) {
        this.detalleLists = detalleLists;
    }

    public String getCoduni() {
        return this.Coduni;
    }

    public void setCoduni(String Coduni) {
        this.Coduni = Coduni;
    }

    public int getCodsup() {
        return this.Codsup;
    }

    public void setCodsup(int Codsup) {
        this.Codsup = Codsup;
    }


}


