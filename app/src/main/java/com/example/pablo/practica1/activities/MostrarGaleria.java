package com.example.pablo.practica1.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pablo.practica1.R;
import com.example.pablo.practica1.dao.Control;
import com.example.pablo.practica1.objetos.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Pablo on 13/6/16.
 */
public class MostrarGaleria extends AppCompatActivity {

    private Button pre,sig, fav,verFav;
    private Spinner menu;
    private TextView bienvenida;
    ImageView im;
    int actual,total;
    Galeria g = new Galeria();
    ArrayList<String> lista;
    Usuario user;
    int flag; //usamos la variable para comprobar las iteraciones sobre las galerias.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_galeria);
        menu = (Spinner) findViewById(R.id.menu);

        //Asignamos los objetos del layout
        pre = (Button) findViewById(R.id.pre);
        sig = (Button) findViewById(R.id.sig);
        fav =  (Button) findViewById(R.id.Fav);
        verFav=(Button) findViewById(R.id.verFavs);
        bienvenida = (TextView) findViewById(R.id.bienvenida);
        im = (ImageView) findViewById(R.id.imageView);

        String nombre= getIntent().getStringExtra("usuario");
        String mensaje = "Bienvenido " +nombre;
        int galDev= getIntent().getIntExtra("galeria",0);
        flag=0;

        bienvenida.setText(mensaje);


        Control c = Control.getControl(this, "midbgalerias1");

        //Creamos el listado de nombres

        List listaSpinner = c.devolverNGalerias();
        cargarSpinner(listaSpinner);
        System.out.println("Vamos a cargar el usuario");
        user = c.devuelveUsuario(nombre);
        Log.d("Base de datos", "Devuelto el usuario");






       //Comprueba si ha recibido de forma correcta los valores del intent
        if (galDev==0){
        im.setImageResource(R.drawable.oops);
            Toast.makeText(MostrarGaleria.this, "Error al cargar la Galeria", Toast.LENGTH_SHORT).show();
        }else {
            g=cargaGaleria(galDev);
            actual = 0;
            total = g.getTotal();
            mostrarFoto(g.devolverFoto(actual).getId());
        }



        //Listeners botones
        //Listener Botón ver Favoritos


        verFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pase = new Intent(MostrarGaleria.this,MostrarFavs.class);
                pase.putExtra("IdUsuario",user.getId());
                startActivity(pase);
            }
        });

        //Listener botón previo
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Comprobamos si no nos hemos pasado del máximo antes de dibujar

                if (actual>0){
                    actual--;
                    //intentamos cargar el identificador de la imagen

                    int i= g.devolverFoto(actual).getId();
                    String mensaje1= "Vamos por la foto:"+i;
                    mostrarFoto(i);
                    //String mensaje1= "Vamos por la foto:"+actual;
                    Toast.makeText(MostrarGaleria.this, mensaje1, Toast.LENGTH_SHORT).show();
                }else Toast.makeText(MostrarGaleria.this, "No hay más imágenes", Toast.LENGTH_SHORT).show();
            }
        });

        //Listener botón siguiente
        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (actual<(g.getTotal()-1)){
                    actual++;
                    //intentamos cargar el identificador de la imagen

                    int i= g.devolverFoto(actual).getId();
                    String mensaje1= "Vamos por la foto:"+i;
                    mostrarFoto(i);
                    //String mensaje1= "Vamos por la foto:"+actual;
                    Toast.makeText(MostrarGaleria.this, mensaje1, Toast.LENGTH_SHORT).show();

                }else Toast.makeText(MostrarGaleria.this, "No hay mas imágenes", Toast.LENGTH_SHORT).show();
            }

        });

        //Listener botón favoritos

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                System.out.println("Vamos por el :"+actual);
               //Añade el favorito al usuario actual.
                favorito(lista.get(actual));
                Toast.makeText(MostrarGaleria.this, "Foto añadida a favoritos", Toast.LENGTH_SHORT).show();
            }
        });


        //Listener del Spinner

        menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int id, long l) {
                //Cargamos la galeria que seleccionamos con el spinner Hay que sumarle 1 porque el ID en las tablas empieza en 1

                if (flag==0){

                }else {
                    g=cargaGaleria(id+1);
                    lista=g.devolverNombres();
                    actual=0;
                //Mostramos siempre la primera foto
                    mostrarFoto(g.devolverFoto(actual).getId());
                    Toast.makeText(MostrarGaleria.this, "Seleccionada la galeria:"+g.getNombre(), Toast.LENGTH_SHORT).show();

                }flag=1;



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
    private void favorito(String nombreF){
        user.addFav(this,nombreF);
    }

    private void cargarSpinner(List<String> ll){
        ArrayAdapter<String> spinAdap = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ll);
        menu.setAdapter(spinAdap);

    }


    public Galeria cargaGaleria(int id){
        Control c = Control.getControl(this, "midbgalerias1");
        Galeria gal = new Galeria();
        List<Foto> lista = new ArrayList<Foto>();
        lista = c.devolverFotos(id);
        Iterator i = lista.iterator();
        int num=0;
        while (i.hasNext()) {
            gal.addFoto((Foto) i.next());
            num++;
            Log.d("Base de datos", "Iteracion de carga:" + id);
        }
        gal.setNombre(c.devolverNombreGaleria(id));

        return gal;
    }

    public int IDFoto(int idF){
        int identificador=0;

        return identificador;
    }
    public void mostrarFoto(int identificador){

        switch(identificador) {

            case 1: im.setImageResource(R.drawable.eurofighter);
                break;
            case 2: im.setImageResource(R.drawable.f18);
                break;
            case 3: im.setImageResource(R.drawable.barco1);
                break;
            case 4: im.setImageResource(R.drawable.f182);
                break;
            case 5: im.setImageResource(R.drawable.barco2);
                break;
            case 6: im.setImageResource(R.drawable.mig29);
                break;
            case 7: im.setImageResource(R.drawable.barco3);
                break;
            case 8: im.setImageResource(R.drawable.solar2);
                break;
            case 9: im.setImageResource(R.drawable.barco4);
                break;
            default: im.setImageResource(R.drawable.oops);

        }

    }

}
