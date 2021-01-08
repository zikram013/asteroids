package com.example.asteroids;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Vector;

public class VistaJuego extends View {
    //Asteroides
    private final Vector<Grafico>asteroides;
    private int numAsteroides=5;
    private int numFragmentos=2;
    private Drawable drawableAsteroide[]=new Drawable[2];
    //Nave
    private Grafico nave;
    private int giroNave;
    private double fpsNave;
    private static final int MAX_VELOCIDAD=20;
    private static final int GIRO=5;
    private static final float ACELERACION=0.5f;
    //hilos para el movimiento
    private ThreadJuego hiloJuego= new ThreadJuego();
    private static int CAMBIOS=50;
    private long lastCambio=0;
    //Manejo nave
    private float mx=0;
    private float my=0;
    private boolean disparo=false;
    //misil
    private Grafico misil;
    private static int FPSMISIL=12;
    private boolean disparado=false;
    private int tiempoActivoMisil;
    //Puntuaciones
    private int puntuacion=0;
    private Activity juego;



    @SuppressLint("UseCompatLoadingForDrawables")
    public VistaJuego(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        Drawable drawableNave,drawableMisil;
        //drawableAsteroide=context.getResources().getDrawable(R.drawable.asteroide1);
        drawableAsteroide[0]=context.getResources().getDrawable(R.drawable.asteroide1);
        drawableAsteroide[1]=context.getResources().getDrawable(R.drawable.asteroide2);
        drawableNave=context.getResources().getDrawable(R.drawable.nave);
        nave=new Grafico(this,drawableNave);
        asteroides= new Vector<>();
        for (int i=0;i<numAsteroides;i++){
            Grafico asteroide=new Grafico(this,drawableAsteroide[0]);
            asteroide.setIncY(Math.random()*4-2);
            asteroide.setIncX(Math.random()*4-2);
            asteroide.setAngulo((int)(Math.random()*360));
            asteroide.setRotacion((int)(Math.random()*8-4));
            asteroides.add(asteroide);
        }
        /*ShapeDrawable dMisil=new ShapeDrawable(new RectShape());
        dMisil.getPaint().setColor(Color.WHITE);
        dMisil.getPaint().setStyle(Paint.Style.STROKE);
        dMisil.setIntrinsicWidth(15);
        dMisil.setIntrinsicHeight(3);
        drawableMisil=dMisil;*/
        drawableMisil=context.getResources().getDrawable(R.drawable.misil);
        misil=new Grafico(this,drawableMisil);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        for (Grafico asteroide:asteroides){
            do{
                asteroide.setCenX((int) (Math.random()*w));
                asteroide.setCenY((int)(Math.random()*h));
            }while(asteroide.distancia(nave)<(w+h)/5);

        }
        nave.setCenX(w/2);
        nave.setCenY(h/2);

        lastCambio=System.currentTimeMillis();
        hiloJuego.start();
    }

    @Override
    protected  void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        nave.dibujarGrafico(canvas);
        if (disparado=true){
            misil.dibujarGrafico(canvas);
        }
        synchronized (asteroides){
            for(Grafico asteroide:asteroides){
                asteroide.dibujarGrafico(canvas);
            }
        }

    }

    protected  void actualizarFisica(){
        long ahora=System.currentTimeMillis();
        if(lastCambio+CAMBIOS>ahora){
            return;
        }
        double factorMovimiento=(ahora-lastCambio)/CAMBIOS;
        lastCambio=ahora;
        nave.setAngulo((int)(nave.getAngulo()+giroNave*factorMovimiento));
        double nIncX=nave.getIncX()+fpsNave*Math.cos(Math.toRadians(nave.getAngulo()))*factorMovimiento;
        double nIncY=nave.getIncY()+fpsNave*Math.sin(Math.toRadians(nave.getAngulo()))*factorMovimiento;
        if(Math.hypot(nIncX,nIncY)<=MAX_VELOCIDAD){
            nave.setIncX(nIncX);
            nave.setIncY(nIncY);
        }
        nave.incrementaPosicion(factorMovimiento);
        for (Grafico asteroide:asteroides){
            asteroide.incrementaPosicion(factorMovimiento);
        }
        if (disparado){
            misil.incrementaPosicion(factorMovimiento);
            tiempoActivoMisil-=factorMovimiento;
            if(tiempoActivoMisil<0){
                disparado=false;
            }else{
                for (int i=0;i<asteroides.size();i++)
                    if(misil.colisiona(asteroides.elementAt(i))){
                        colisionConAsteroide(i);
                        disparado=true;
                        break;
                    }

            }
        }
        for (Grafico asteroide:asteroides){
            if(asteroide.colisiona(nave)){
                salir();
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        float x= event.getX();
        float y=event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                disparo=true;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx=Math.abs(x-mx);
                float dy=Math.abs(y-my);
                if (dy<6 && dx>6){
                    giroNave=Math.round((x-mx)/2);
                    disparo=false;
                }else if(dx<6 && dy>6){
                    fpsNave=Math.round((my-y)/21.5);
                    disparo=false;
                }
                break;
            case MotionEvent.ACTION_UP:
                giroNave=0;
                fpsNave=0;
                if(disparo){
                    activaMisil();
                }
                break;
        }
        mx=x;
        my=y;
        return true;
    }

    private void colisionConAsteroide(int aste){
        if(asteroides.get(aste).getDrawable()!=drawableAsteroide[1]){
            for (int i=0;i<numFragmentos;i++){
                Grafico asteroide=new Grafico(this,drawableAsteroide[1]);
                asteroide.setCenX(asteroides.get(aste).getCenX());
                asteroide.setCenY(asteroides.get(aste).getCenY());
                asteroide.setIncY(Math.random()*7-2-1);
                asteroide.setIncX(Math.random()*7-2-1);
                asteroide.setAngulo((int)(Math.random()*360));
                asteroide.setRotacion((int)(Math.random()*8-4));
                asteroides.add(asteroide);
                puntuacion+=500;
            }
        }
        synchronized (asteroides){
            asteroides.remove(aste);
            disparado=false;
            this.postInvalidate();
            puntuacion+=1000;
        }
        if (asteroides.isEmpty()){
            salir();
        }
    }

    private void activaMisil(){
        misil.setCenX(nave.getCenX());
        misil.setCenY(nave.getCenY());
        misil.setAngulo(nave.getAngulo());
        misil.setIncX(Math.cos(Math.toRadians(misil.getAngulo()))*FPSMISIL);
        misil.setIncY(Math.sin(Math.toRadians(misil.getAngulo()))*FPSMISIL);
        tiempoActivoMisil=(int)Math.min(this.getWidth()/Math.abs(misil.getIncX()),this.getHeight()/Math.abs(misil.getIncY()))-2;
        disparado=true;
    }

    private void salir(){
        Intent i=new Intent(juego,PantallaFinal.class);
        //Bundle bundle=new Bundle();
        //bundle.putInt("puntuacion",puntuacion);
        i.putExtra("puntuacion",puntuacion);
        juego.startActivity(i);
    }

    public ThreadJuego getHiloJuego() {
        return hiloJuego;
    }
    public void setJuego(Activity juego){
        this.juego=juego;
    }

    public class ThreadJuego extends Thread {
        private boolean pausa,corriendo;

        public synchronized void pausar(){
            pausa=true;
        }

        public synchronized void reanudar(){
            pausa=false;
            notify();
        }

        public void detener(){
            corriendo=false;
            if(pausa){
                reanudar();
            }
        }

        @Override
        public void run() {
            corriendo = true;
            while (corriendo){
                actualizarFisica();
                synchronized (this){
                    while (pausa){
                        try{
                            wait();
                        }catch (Exception e){
                            System.out.println("Algo ha fallado");
                        }
                    }
                }
            }
        }
    }
}
