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
    public static AlmacenPuntuaciones almacenPuntuaciones=new AlmacenPuntuacionesLista();
    @SuppressLint("StaticFieldLeak")
    public static AlmacenPuntuacionesPreferencias puntosPreferences;
    Button mostrarPuntuaciones,jugar,help;
    public static PersistenciaDatosPuntuaciones rankingPuntos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rankingPuntos = new PersistenciaDatosPuntuaciones(this);
        puntosPreferences=new AlmacenPuntuacionesPreferencias(this);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ACTIV_JUEGO && resultCode==RESULT_OK && data!=null){
            int puntuacion=data.getExtras().getInt("puntuacion");
            String nombre="yo";
            almacenPuntuaciones.guardarPuntuacion(puntuacion,nombre,System.currentTimeMillis());
            listaMejoresPuntuaciones(null);
        }

    }
}