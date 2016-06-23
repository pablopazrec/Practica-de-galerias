package com.example.pablo.practica1.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pablo.practica1.R;
import com.example.pablo.practica1.dao.Control;
import com.example.pablo.practica1.objetos.Foto;

import java.util.List;

/**
 * Created by Pablo on 13/6/16.
 */
public class MostrarFavs extends AppCompatActivity {

    int idUsuario,actual,totalFavs;
    ImageView im;
    List<Foto> favoritos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_favs);

        idUsuario=getIntent().getIntExtra("IdUsuario",0);
        if (idUsuario==0){
            Toast.makeText(MostrarFavs.this, "Error en la carga del usuario", Toast.LENGTH_SHORT).show();
        }else {
            Control c = Control.getControl(this, "midbgalerias1");
            favoritos = c.favoritos(idUsuario);

            if (favoritos==null){
                Toast.makeText(MostrarFavs.this, "Este usuario no tiene favoritos", Toast.LENGTH_SHORT).show();
                mostrarFoto(0);

            }else{

                totalFavs=favoritos.size();
                Button pre = (Button) findViewById(R.id.ant);
                Button sig = (Button) findViewById(R.id.next);
                im =(ImageView) findViewById(R.id.imagenCentral);

                actual =0;
                mostrarFoto(favoritos.get(0).getId());

                //Listener Boton atrás
                pre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (actual==0){
                            Toast.makeText(MostrarFavs.this, "No hay más favoritos", Toast.LENGTH_SHORT).show();

                        }else{
                            actual--;
                            mostrarFoto(favoritos.get(actual).getId());
                        }
                    }
                });

                //Listener boton delante
                sig.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (actual==(totalFavs-1)){
                            Toast.makeText(MostrarFavs.this, "No hay más favoritos", Toast.LENGTH_SHORT).show();

                        }else{
                            actual++;
                            mostrarFoto(favoritos.get(actual).getId());
                        }
                    }
                });
            }



        }
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
