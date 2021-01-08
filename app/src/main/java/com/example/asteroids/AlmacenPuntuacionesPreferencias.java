package com.example.asteroids;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Vector;

public class AlmacenPuntuacionesPreferencias implements AlmacenPuntuaciones{

    private static String PREFERENCES="puntuaciones";
    private Context context;

    public AlmacenPuntuacionesPreferencias(Context context){
        this.context=context;
    }

    @Override
    public void guardarPuntuacion(int puntos, String nombre, long fecha) {
        SharedPreferences preferences=context.getSharedPreferences(PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=preferences.edit();
        for (int n=9;n>=1;n--){
            edit.putString("puntuacion"+n,preferences.getString("puntuacion"+(n-1),""));
        }
        edit.putString("puntuacion0",puntos+""+nombre);
        edit.apply();
    }

    @Override
    public Vector<String> listaPuntuaciones(int cantidad) {
        Vector<String>list=new Vector<String>();
        SharedPreferences preferences=context.getSharedPreferences(PREFERENCES,Context.MODE_PRIVATE);
        for (int n=0;n<=9;n++){
            String puntos=preferences.getString("puntuacion"+n,"");
            if(!puntos.isEmpty()){
                list.add(puntos);
            }
        }
        return list;
    }
}
