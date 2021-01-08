package com.example.asteroids;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class PersistenciaDatosPuntuaciones implements AlmacenPuntuaciones {

    private static String FICHERO="puntuaciones.txt";
    private Context context;

    public PersistenciaDatosPuntuaciones(Context context){
        this.context=context;
    }

    @Override
    public void guardarPuntuacion(int puntos, String nombre, long fecha) {
        try{
            FileOutputStream fos=context.openFileOutput(FICHERO,Context.MODE_APPEND);
            String texto=puntos +" " +nombre+"\n";
            fos.write(texto.getBytes());
            fos.close();
        }catch (Exception e){
            Log.e("Asteroids",e.getMessage(),e);
        }
    }

    @Override
    public Vector<String> listaPuntuaciones(int cantidad) {
        Vector<String>listado=new Vector<String>();
        try{
            FileInputStream fis=context.openFileInput(FICHERO);
            BufferedReader br=new BufferedReader(new InputStreamReader(fis));
            int contadorLineas=0;
            String lineaPuntuacion;
            do{
                lineaPuntuacion=br.readLine();
                if (lineaPuntuacion!=null){
                    listado.add(lineaPuntuacion);
                    contadorLineas++;
                }
            }while (contadorLineas<cantidad && lineaPuntuacion!=null);
            fis.close();
        }catch (Exception e){
            Log.e("Asteroids",e.getLocalizedMessage(),e);
        }
        return listado;
    }
}
