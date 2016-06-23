package com.example.pablo.practica1.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.pablo.practica1.objetos.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pablo on 13/6/16.
 */
public class Control extends SQLiteOpenHelper {

    private String creaTUsers = "CREATE TABLE USUARIOS (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, cont TEXT)";
    private String creaTFotos = "CREATE TABLE FOTOS (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, galeria INTEGER)";
    private String creaGalerias = "CREATE TABLE GALERIAS(id INTEGER PRIMARY KEY, nombre TEXT)";
    private String creaTfavs = "CREATE TABLE FAVS(idFav INTEGER PRIMARY KEY AUTOINCREMENT, idUsuario INTEGER, idFoto INTEGER)";

    private static Control miControl;

    private Control(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    //Usamos el patrón singleton para que no pueda repetirse

    public static Control getControl(Context c, String n){
        if (miControl==null){
            miControl=new Control(c, n,null,1);
        }
        return miControl;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(creaGalerias);
        db.execSQL(creaTFotos);
        Log.d("Base de datos","Base de datos de fotos creada");
        db.execSQL(creaTUsers);
        db.execSQL(creaTfavs);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void cerrarBD(SQLiteDatabase db) {
        db.close();
    }

//metodos de galerias

    public void insertaGaleria(Galeria g){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlGal = "INSERT INTO GALERIAS (id, nombre) VALUES ('"+g.getIdentificador()+"','"+g.getNombre()+"')";
        db.execSQL(sqlGal);
        this.cerrarBD(db);
    }

    public List<String> devolverNGalerias(){

        List<String> l = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL = "Select nombre from GALERIAS";

        Cursor c = (Cursor) db.rawQuery(SQL, null);

        if (!c.moveToFirst()){
            Log.d("Base de datos", "El cursor está vacio");
        }else{

            int i = c.getColumnIndex("nombre");
            Log.d("Base de datos", "Obtenemos el indice de la columna nombre (1)");
            String s = c.getString(i);
            Log.d("Base de datos", "Asignamos el valor de la colunma nombre (1):"+s );
            if (l.add( (String) s)) {
                Log.d("Base de datos", "Añadimos el nombre a la lista(1)");
            }
            while (c.moveToNext()){
                s=c.getString(i);
                Log.d("Base de datos", "Asignamos el valor de la colunma nombre (2):"+s );
                if (l.add(s))
                    Log.d("Base de datos", "Añadimos el nombre a la lista(2)");
            }}
        this.cerrarBD(db);
        return l;
    }


// METODOS DE USUARIO

    public void insertaUsuario(Usuario user) {
        String sqlUsuario = "INSERT INTO USUARIOS (nombre, cont) VALUES ('" +user.getUser()+ "', '" +user.getPasswd()+ "')";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlUsuario);
        this.cerrarBD(db);
    }

    public boolean comprobarPasswd(String user, String pass){
        SQLiteDatabase db = this.getReadableDatabase();
        boolean b=false;
        System.out.println(user);
        System.out.println(pass);
        String contra= "SELECT cont FROM USUARIOS WHERE NOMBRE='"+user+"'";
        System.out.println(contra);
        Cursor c = db.rawQuery(contra,null);
        int i=c.getColumnCount();
        System.out.println(i);
        if (c==null){
            Log.d("Error en DB", "No se encuentra ningun usuario con ese nombre");
        }
        else {
            c.moveToFirst();
            int s=c.getColumnIndex("cont");
            System.out.println(s);
            String password = c.getString(s);
            if (pass.equals(password)) {
                b = true;
                Log.d("Seguridad", "Acceso admitido");

            }

        }
        return b;
    }
    public Usuario devuelveUsuario(String nom){
        Usuario user = new Usuario();

        SQLiteDatabase db = this.getReadableDatabase();
        String sqlUser = "SELECT * FROM USUARIOS WHERE NOMBRE='"+nom+"'";
        System.out.println(sqlUser);
        Log.d("Base de datos", "Vamos a crear el cursor");
        Cursor c = (Cursor) db.rawQuery(sqlUser, null);
        Log.d("Base de datos", "Cursor Creado");
        int i;
        if (!c.moveToFirst()){

            Log.d("Base de datos", "Error en la query");}
        else{
            Log.d("Base de datos", "Query correcta: Total "+c.getColumnCount()+" columnas");
            if (c.moveToFirst()){

                Log.d("Base de datos", "Cursor a primera posición");
                user.setUser(nom);
                Log.d("Base de datos", "Asignamos valor al nombre del objeto");

                i=(c.getColumnIndex("cont"));
                Log.d("Base de datos", "Obtenemos el indice de la columna de la contraseña. El indice es:"+i);
                String pass = c.getString(i);
                Log.d("Base de datos", "Obtenemos el valor de la columna de la contraseña. El valor es:"+pass);
                user.setPasswd(pass);


                List<Foto> f=null;
                Log.d("Base de datos", "Creamos el objeto que representa la lista de Fotos favoritas");

                f=this.favoritos(user);
                Log.d("Base de datos", "Obtenemos el objeto que representa la lista de Fotos favoritas");
                user.setFavs(f);
                Log.d("Base de datos", "Asignamos la lista de Fotos favoritas al usuario");
                user.setId(c.getInt(c.getColumnIndex("id")));
                Log.d("Base de datos", "Asignamos el identificador al usuario");
            }}
        this.cerrarBD(db);
        return user;
    }


    public void favorito(Usuario u, Foto f) {


        Log.d("Base de datos","Vamos a insertar el usuario:"+u.getId()+" y la foto " +f.getId());
        String sqlfav = "INSERT INTO FAVS (idUsuario, idFoto) VALUES (" + u.getId() + ", " + f.getId() + ")";
        Log.d("Base de datos","Creada sentencia SQL");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlfav);
        this.cerrarBD(db);
    }
    public List<Foto> favoritos(Usuario u){

        //TODO COMPROBAR METODO
        String sqlfavs="SELECT * FROM FAVS WHERE idUsuario="+u.getId();
        Log.d("Base de datos", "Seleccionamos todos los datos del usuario:"+sqlfavs);
        SQLiteDatabase db = this.getReadableDatabase();
        List<Foto> lf=null;


        Cursor c = db.rawQuery(sqlfavs, null);

        if (c!=null || c.getCount()<=0){
            if (!c.moveToFirst()){
                Log.d("Base de datos", "Lista de favoritos vacia");
            }else{
                ArrayList<Foto> alf = new ArrayList<Foto>(c.getCount());
                int idFoto= c.getInt(2);
                boolean comp = lf.add(buscaFoto(idFoto));
                if (!comp){
                    Log.d("Error DB","No se ha añadido la foto a favoritos");
                }
                while (c.moveToNext()){
                    idFoto=c.getInt(2);
                    comp=lf.add(buscaFoto(idFoto));
                    if (!comp){
                        Log.d("Error DB","No se ha añadido la foto"+idFoto+" a favoritos");
                    }
                }
            }}
        this.cerrarBD(db);
        return lf;
    }

    public List<Foto> favoritos(int idUsuario){

        //TODO COMPROBAR METODO
        String sqlfavs="SELECT * FROM FAVS WHERE idUsuario="+idUsuario;
        Log.d("Base de datos", "Seleccionamos todos los datos del usuario:"+sqlfavs);
        SQLiteDatabase db = this.getReadableDatabase();
        List<Foto> lf=null;


        Cursor c = db.rawQuery(sqlfavs, null);

        if (c!=null || c.getCount()<=0){
            if (!c.moveToFirst()){
                Log.d("Base de datos", "Lista de favoritos vacia");
            }else{
                lf = new ArrayList<Foto>(c.getCount());
                int idFoto= c.getInt(2);
                boolean comp = lf.add(buscaFoto(idFoto));
                if (!comp){
                    Log.d("Error DB","No se ha añadido la foto a favoritos");
                }
                while (c.moveToNext()){
                    idFoto=c.getInt(2);
                    comp=lf.add(buscaFoto(idFoto));
                    if (!comp){
                        Log.d("Error DB","No se ha añadido la foto"+idFoto+" a favoritos");
                    }
                }
            }}
        this.cerrarBD(db);
        return lf;
    }

    private Foto buscaFoto(int id){
        //TODO Revisar codigo
        Foto f = new Foto();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c= db.rawQuery("Select * FROM FOTOS WHERE ID="+id, null);
        Log.d("Base de datos","Creada consulta de Fotografía :"+id);
        if (!c.moveToFirst()){
            Log.d("Base de datos","Cursor de búsqueda de Fotografía por identificador"+id+" vacio");
        }else{
            Log.d("Base de datos","Procedemos a almacenar la foto por identificador"+id);
            f.setId(id);
            f.setNombre(c.getString(1));
            f.setGal(2);
            Log.d("Base de datos","Devolvemos la foto por identificador:"+id);
        }
        this.cerrarBD(db);
        return f;
    }

    public Foto buscaFoto(String n){
        Foto f = new Foto();
        String s = "SELECT * FROM FOTOS WHERE nombre='"+n+"'";
        Log.d("Base de datos","Creada consulta de Fotografía :"+n);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(s,null);
        if (!c.moveToFirst()){
            Log.d("Base de datos","Cursor de búsqueda de Fotografía por nombre"+n+" vacio");
        }else{
            f.setId(c.getInt(0));
            f.setNombre(n);
            f.setGal(c.getInt(2));
            Log.d("Base de datos", "Fotografía:" +n+" creada y devuelta");
        }
        return f;
    }


//Metodos de las galerias

    public void insertarFoto(Foto f){
        String SqlFoto= "Insert INTO FOTOS (nombre, galeria) VALUES ('"+f.getNombre()+"', '"+f.getGal()+"')";
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(SqlFoto);
        this.cerrarBD(db);
    }


    public List<Foto> devolverFotos(int gal){
        String sqlGaleria="Select * FROM Fotos WHERE galeria="+gal;
        SQLiteDatabase db= this.getReadableDatabase();
        List<Foto> galeria= new ArrayList<Foto>();
        Cursor c = db.rawQuery(sqlGaleria,null);
        if (c.getCount()==0 || c==null){
            Log.d("Base de datos", "Error en la devolución de fotos de la galeria"+gal);
        }else{
            Log.d("Base de datos", "Consulta galeria correcta");
            Foto f = new Foto();
            if (!c.moveToFirst()){
                Log.d("Base de datos", "Error en el cursor de la devolución de fotos de la galeria"+gal);
            }
            f.setId(c.getInt(0));
            f.setGal(c.getInt(2));
            f.setNombre(c.getString(1));
            if (galeria.add(f)){
                Log.d("Base de datos", "Añadida foto a al galeria"+gal);
            }

            while (c.moveToNext()){
                f = new Foto();
                f.setId(c.getInt(0));
                f.setNombre(c.getString(1));
                f.setGal(c.getInt(2));
                if (galeria.add(f)){
                    Log.d("Base de datos", "Añadida foto a al galeria"+gal);
                };
            }


        }
        this.cerrarBD(db);
        return galeria;}

    public String devolverNombreGaleria(int gal){
        String sqlGaleria="Select * FROM GALERIAS WHERE id="+gal;
        System.out.println(sqlGaleria);
        SQLiteDatabase db= this.getReadableDatabase();
        String nombreGal = null;
        Cursor c = db.rawQuery(sqlGaleria,null);
        if (!c.moveToFirst()){Log.d("Base de datos", "No es posible cargar el nombre de la galeria");}
        else{
            nombreGal = c.getString(1);
            Log.d("Base de datos", "Nombre de la galeria recuperado:"+nombreGal);}
        return nombreGal;

    }



    //UTILS

    public void cargarDatos(){


        SQLiteDatabase db = this.getReadableDatabase();
        String sqlPrueba = "SELECT COUNT(*) FROM USUARIOS";
        Cursor c = db.rawQuery(sqlPrueba,null);
        if (!c.moveToFirst()){
            Log.d("Base de datos","Error al comprobar la integridad de la Base de datos");
        }else {
                Log.d("Base de datos", "El cursor no está vacío");
            int i = c.getInt(0);
            if (i == 0) {

                Log.d("Base de datos","Base de datos vacia, se procede a la carga");
                Usuario u1 = new Usuario("Pablo", "zeus");
                Usuario u2 = new Usuario("Carmen", "ares");
                Usuario u3 = new Usuario("Chema", "hermes");

                Foto f1 = new Foto("1", 1);
                Foto f2 = new Foto("2", 1);
                Foto f3 = new Foto("3", 2);
                Foto f4 = new Foto("4", 1);
                Foto f5 = new Foto("5", 2);
                Foto f6 = new Foto("6", 1);
                Foto f7 = new Foto("7", 2);
                Foto f8 = new Foto("8", 1);
                Foto f9 = new Foto("9", 2);

                this.insertaUsuario(u1);
                this.insertaUsuario(u2);
                this.insertaUsuario(u3);

                this.insertarFoto(f1);
                this.insertarFoto(f2);
                this.insertarFoto(f3);
                this.insertarFoto(f4);
                this.insertarFoto(f5);
                this.insertarFoto(f6);
                this.insertarFoto(f7);
                this.insertarFoto(f8);
                this.insertarFoto(f9);

                Galeria g1 = new Galeria(1, "Galeria de Aviones");
                Galeria g2 = new Galeria(2, "Galeria de Barcos");
                this.insertaGaleria(g1);
                this.insertaGaleria(g2);

                Log.d("Base de datos","Carga de datos terminada");
            }

        }
        this.cerrarBD(db);
    }
}
