package com.example.asteroids;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class Puntuaciones extends AppCompatActivity {

    private static final String FICHERO="puntuaciones.txt";
    private ArrayList<Integer>rank= new ArrayList<>();
    TextView lista;
    Button menuPrincipal;
    ListView sublista;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.puntuaciones);
            lista=findViewById(R.id.nombrePlayer);
            menuPrincipal=findViewById(R.id.init);
            sublista=findViewById(R.id.listado);
            FileInputStream fis=null;
            try{
                fis=openFileInput(FICHERO);
                InputStreamReader isr=new InputStreamReader(fis);
                BufferedReader br=new BufferedReader(isr);
                String ranking;
                StringBuilder stringBuilder=new StringBuilder();
                while ((ranking=br.readLine())!=null){
                    stringBuilder.append(ranking).append("\n");
                    rank.add(Integer.parseInt(ranking));
                }
                /*if(lista!=null){
                    lista.setText(ranking);
                }else{
                    lista.setText("No hay datos");
                }*/

            }catch (Exception e){
                System.out.println("No se carga el fichero");
            }
        assert fis != null;
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!rank.isEmpty()){
            Collections.sort(rank,Collections.reverseOrder());
            ArrayAdapter adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,rank);
            sublista.setAdapter(adapter);
        }else{
            lista.setText("No hay puntuaciones para mostrar en el ranking");
        }


    }
    public void inicio(View v){
        Intent i= new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
