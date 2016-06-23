package com.example.pablo.practica1.objetos;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Pablo on 13/6/16.
 */
public class Galeria {
    private int identificador;
    private List<Foto> imagenes;
    private String nombre;
    private int total;


    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public List<Foto> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Foto> imagenes) {
        this.imagenes = imagenes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Galeria(){
        this.identificador=0;
        imagenes= new ArrayList<Foto>();
        this.nombre="";
        this.total=0;
    }
    public Galeria(int id, String nombre){
        identificador=id;
        this.nombre=nombre;
        imagenes= new ArrayList<Foto>();
        total=0;
    }

    public void addFoto(Foto f){
        if (imagenes.add(f)){
            total= total+1;}else Log.d("Base de datos", "Error de carga de foto en la galeria");

    }
    public void removeFoto(Foto f){
        imagenes.remove(f);
        total=total-1;

    }
    public void limpiar(){

        imagenes.clear();
    }

    public ArrayList<String> devolverNombres(){
        ArrayList<String> lista = new ArrayList<String>();
        Iterator it = imagenes.iterator();
        int pos = 0;
        while (it.hasNext()){
            Foto f= (Foto) it.next();
            //Ahora devuelve una lista de nombres, no de fotos
            lista.add(f.getNombre());

            pos=pos+1;
        }
        return lista;
    }

    public Foto devolverFoto(int i){
        Foto f = imagenes.get(i);

        System.out.println("Devolvemos la foto: "+f.toString()+"y cursor:"+i);
        return f;
    }


}
