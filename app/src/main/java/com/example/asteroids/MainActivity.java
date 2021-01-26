package com.example.asteroids;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    static final int ACTIV_JUEGO=0;

    @SuppressLint("StaticFieldLeak")

    Button mostrarPuntuaciones,jugar,help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
         mostrarPuntuaciones=findViewById(R.id.ranking);
         help=findViewById(R.id.help);
         jugar=findViewById(R.id.play);
    }

    public void iniciarJuego(View v){
        Intent i = new Intent(MainActivity.this,Juego.class);
        startActivityForResult(i,ACTIV_JUEGO);
    }

    public void listaMejoresPuntuaciones(View v){
        Intent i = new Intent(MainActivity.this,Puntuaciones.class);
        startActivity(i);
    }

    public void mostrarAyuda(View v){
        Intent i = new Intent(MainActivity.this,Ayuda.class);
        startActivity(i);
    }



}