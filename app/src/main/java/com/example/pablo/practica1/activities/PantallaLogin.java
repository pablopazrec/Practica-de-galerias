package com.example.pablo.practica1.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pablo.practica1.R;
import com.example.pablo.practica1.dao.Control;

/**
 * Created by Pablo on 13/6/16.
 */
public class PantallaLogin extends AppCompatActivity {

    private Button mLogin;
    private EditText user;
    private EditText passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_login);


        mLogin=(Button) findViewById(R.id.entrar);
        user=(EditText) findViewById(R.id.Usuario);
        passwd = (EditText) findViewById(R.id.passwd);
        final String u1=user.getText().toString();
        final String p1=passwd.getText().toString();



        final Control c = Control.getControl(this, "midbGalerias1");
        c.cargarDatos();

        //insertamos el listener del boton
        mLogin.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final String u1=user.getText().toString();
                final String p1=passwd.getText().toString();
                boolean res= false;
                if (u1.length()==0 || p1.length()==0){
                    Toast.makeText(PantallaLogin.this, "Alguno de los valores está vacío", Toast.LENGTH_SHORT).show();
                } else
                    res =c.comprobarPasswd(u1,p1);
                if (res) {
                    Toast.makeText(PantallaLogin.this, "Acceso permitido", Toast.LENGTH_SHORT).show();

                    //Asignamos el paso del activity
                    //Intent i = new Intent(PantallaLogin.this, MostrarGaleria.class);
                    Intent i = new Intent(PantallaLogin.this, EscogeGaleria.class);
                    //insertamos los valores a pasar

                    i.putExtra("usuario",u1);

                    //Lanzamos el Activity
                    startActivity(i);

                }else Toast.makeText(PantallaLogin.this, "Usuario o contraseña no encontrados", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public boolean compruebaContrasenya(EditText u, EditText p){
        boolean res=false;
        String u1 = u.toString();
        String p1= p.toString();

        return res;
    }

}
