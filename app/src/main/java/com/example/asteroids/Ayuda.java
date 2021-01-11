package com.example.asteroids;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;

public class Ayuda extends AppCompatActivity {

    Button retroceder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ayuda);
        retroceder=findViewById(R.id.volver);
    }

    public void menuPrincipal(View v){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

}
