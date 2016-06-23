package com.example.pablo.practica1.objetos;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.pablo.practica1.dao.Control;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pablo on 13/6/16.
 */
public class Usuario {



    private int id;
    private String user;
    private String passwd;
    private List<Foto> favs;


    public Usuario(){
        this.user="";
        this.passwd="";
        this.favs=new ArrayList<Foto>();
        this.id=0;
    }

    public Usuario(int id, String user, String passwd, List<Foto> favs){
        this.id=id;
        this.user=user;
        this.passwd=passwd;
        this.favs=favs;
    }

    public Usuario(int id, String user, String pass){
        this.id=id;
        this.user=user;
        passwd=pass;
        favs=new ArrayList<Foto>();
    }
    public Usuario(String user, String pass){
        this.user=user;
        passwd=pass;
        favs=new ArrayList<Foto>();
    }
    public List<Foto> getFavs() {
        return favs;
    }

    public void setFavs(List<Foto> favs) {
        this.favs = favs;
    }



    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }



    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id=id;
    }

    public String toString(){
        String var = "mi nombre es"+this.getUser()+ " y mi id es " +this.getId();
        return var;
    }

    public void addFav(Foto f){
        if(this.favs == null){
            this.favs = new ArrayList<Foto>();
        }
        this.favs.add(f);

    }
    public Foto addFav(Context con, String n){
        Control c = Control.getControl(con,"midbgalerias1");

        Foto f = c.buscaFoto(n);
        addFav(f);
        Log.d("Clase Usuario","Añadida la Foto a la lista de la clase");
        c.favorito(this,f);



        return f;
    }

}
