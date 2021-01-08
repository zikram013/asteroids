package com.example.asteroids;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class Juego extends Activity {
    private VistaJuego vistaJuego;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.juego);
        vistaJuego=(VistaJuego)findViewById(R.id.VistaJuego);
        vistaJuego.setJuego(this);
    }

    @Override
    protected void onPause() {
        vistaJuego.getHiloJuego().pausar();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vistaJuego.getHiloJuego().reanudar();
    }

    @Override
    protected void onDestroy() {
        vistaJuego.getHiloJuego().detener();
        super.onDestroy();
    }
}
