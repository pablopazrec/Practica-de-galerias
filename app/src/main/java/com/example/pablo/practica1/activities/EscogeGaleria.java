package com.example.pablo.practica1.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pablo.practica1.R;
import com.example.pablo.practica1.dao.Control;
import com.example.pablo.practica1.objetos.Usuario;

import java.util.List;

public class EscogeGaleria extends AppCompatActivity {

    Button verGal;
    Spinner menuGal;
    int galElegida;
    Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escoge_galeria);

        verGal = (Button) findViewById(R.id.VerGal);
        menuGal = (Spinner) findViewById(R.id.menuBienvenida);

        Control c = Control.getControl(this, "midbgalerias1");
        List ll = c.devolverNGalerias();
        cargarSpinner(ll);
        user = c.devuelveUsuario(getIntent().getStringExtra("usuario"));

        menuGal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int id, long l) {
                galElegida=id+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        verGal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent verGals = new Intent(EscogeGaleria.this,MostrarGaleria.class);
                verGals.putExtra("galeria",galElegida);
                verGals.putExtra("usuario",user.getUser());
                startActivity(verGals);
             //   Toast.makeText(EscogeGaleria.this, "La galeria a mostrar será:"+galElegida, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void cargarSpinner(List<String> ll){
        ArrayAdapter<String> spinAdap = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ll);
        menuGal.setAdapter(spinAdap);

    }
}
