package com.example.pc5.ventasyesi.Mapeo;
// Generated 08-nov-2015 14:48:03 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Usuario generated by hbm2java
 */
public class Usuario  implements java.io.Serializable {


     private int Codusu;
     private String Nick;
     private String Telef;
     private Set listUsus = new HashSet(0);

    public Usuario() {
    }

	
    public Usuario(int Codusu, String Nick, String Telef) {
        this.Codusu = Codusu;
        this.Nick = Nick;
        this.Telef = Telef;
    }
    public Usuario(int Codusu, String Nick, String Telef, Set listUsus) {
       this.Codusu = Codusu;
       this.Nick = Nick;
       this.Telef = Telef;
       this.listUsus = listUsus;
    }
   
    public int getCodusu() {
        return this.Codusu;
    }
    
    public void setCodusu(int Codusu) {
        this.Codusu = Codusu;
    }
    public String getNick() {
        return this.Nick;
    }
    
    public void setNick(String Nick) {
        this.Nick = Nick;
    }
    public String getTelef() {
        return this.Telef;
    }
    
    public void setTelef(String Telef) {
        this.Telef = Telef;
    }
    public Set getListUsus() {
        return this.listUsus;
    }
    
    public void setListUsus(Set listUsus) {
        this.listUsus = listUsus;
    }




}


