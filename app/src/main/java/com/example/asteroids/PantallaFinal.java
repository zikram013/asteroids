package com.example.asteroids;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class PantallaFinal extends AppCompatActivity {
    private static final String FICHERO="puntuaciones.txt";
    private final int MAXSCORES=10;
    Button menuPrincipal,guardarPuntuacion;
    Bundle puntos;
    TextView mostrarDatos;
    EditText player;
    private Intent recibe;
    private int damePuntos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fin);
        mostrarDatos=findViewById(R.id.mostrarPuntos);
        //player=findViewById(R.id.nombrePlayer);
        recibe=getIntent();
        puntos=recibe.getExtras();
        assert puntos!=null;
        damePuntos=recibe.getIntExtra("puntuacion",0);
        mostrarDatos.setText("Puntos: "+damePuntos);
        menuPrincipal=findViewById(R.id.init);
        guardarPuntuacion=findViewById(R.id.save);
    }


    public void saveScore(View v) throws IOException {
        ArrayList<Integer>puntFichero=readFile();
        if(puntFichero==null || puntFichero.isEmpty()){
            ArrayList<Integer>punt=new ArrayList<>();
            punt.add(damePuntos);
            guardar(punt);
        }else{
            for (int i=0;i<puntFichero.size();i++){
                System.out.println("puntuacion: "+i+" "+puntFichero.get(i));
            }
            puntFichero.add(damePuntos);
            Collections.sort(puntFichero,Collections.reverseOrder());
            guardar(puntFichero);
        }

        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public ArrayList<Integer> readFile(){
        ArrayList<Integer>rank =new ArrayList<Integer>();
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
        }catch (Exception e){
            System.out.println("No se carga el fichero");
        }
        assert fis != null;
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rank;
    }

    public void inicio(View v){
        Intent i= new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void guardar(ArrayList<Integer>punt) throws IOException {
        FileOutputStream fos=null;
        try{
            fos=openFileOutput(FICHERO,MODE_PRIVATE);
            for(int i = 0; i < punt.size(); i++){
                //writer.println(punt.get(i));
                System.out.println(punt.get(i));
                fos.write(String.valueOf(punt.get(i)+"\n").getBytes());
            }
            fos.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 }

