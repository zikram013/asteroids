package com.example.asteroids;

import java.util.Vector;

public class AlmacenPuntuacionesLista implements AlmacenPuntuaciones{
    private Vector<String>puntuaciones;

    public AlmacenPuntuacionesLista(){
        puntuaciones=new Vector<>();
        puntuaciones.add("12300 Marcos Sanchez");
        puntuaciones.add("52000 Camila Belen");
        puntuaciones.add("255 Miguel Ruiz");
    }
    @Override
    public void guardarPuntuacion(int puntos, String nombre, long fecha) {
        puntuaciones.add(0,puntos+" "+nombre);
    }

    @Override
    public Vector<String> listaPuntuaciones(int cantidad) {
        return puntuaciones;
    }
}
