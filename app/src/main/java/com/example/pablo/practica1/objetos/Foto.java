package com.example.pablo.practica1.objetos;

/**
 * Created by Pablo on 13/6/16.
 */
public class Foto{

    private int id;
    private String nombre;
    private int gal;//Suponemos que solo puede estar en una galería


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getGal() {
        return gal;
    }

    public void setGal(int gal) {
        this.gal = gal;
    }

    public Foto(){
        id=0;
        nombre="";
        gal=0;
    }

    public Foto(int id, String nombre, int gal){
        this.id=id;
        this.nombre=nombre;
        this.gal=gal;
    }

    public Foto(String nombre, int gal){

        this.nombre=nombre;
        this.gal=gal;
    }
    public String toString(){
        String s = "Fotografía con id:"+id+ "de nombre "+nombre+" en la galeria "+gal;
        return s;
    }



}
