package com.example.pc5.ventasyesi.Mapeo;
// Generated 08-nov-2015 14:48:03 by Hibernate Tools 4.3.1



/**
 * Listusu generated by hbm2java
 */
public class Listusu implements java.io.Serializable {


     private ListUsuId id;
     private Listas listas;
     private Usuario usuario;
     private int Id;
     private int Codl;
     private int Codusu;
     private String Cargo;

    public Listusu() {
    }

    public Listusu(ListUsuId id, Listas listas, Usuario usuario, int Id, int Codl, int Codusu, String Cargo) {
       this.id = id;
       this.listas = listas;
       this.usuario = usuario;
       this.Cargo = Cargo;
       this.Id=Id;
       this.Codl=Codl;
       this.Codusu=Codusu;
    }
   
    public ListUsuId getId() {
        return this.id;
    }
    
    public void setId(ListUsuId id) {
        this.id = id;
    }
    public Listas getListas() {
        return this.listas;
    }
    
    public void setListas(Listas listas) {
        this.listas = listas;
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public String getCargo() {
        return this.Cargo;
    }
    
    public void setCargo(String Cargo) {
        this.Cargo = Cargo;
    }

    public int getIds() {
        return this.Id;
    }

    public void setIds(int Id) {
        this.Id = Id;
    }

    public int getCodl() {
        return this.Codl;
    }

    public void setCodl(int Codl) {
        this.Codl = Codl;
    }

    public int getCodusu() {
        return this.Codusu;
    }

    public void setCodusu(int Codusu) {
        this.Codusu = Codusu;
    }



}

