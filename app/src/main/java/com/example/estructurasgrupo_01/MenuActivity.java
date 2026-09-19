package com.example.estructurasgrupo_01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

public class MenuActivity extends AppCompatActivity {
    private Button modo1vs1;
    private Button modo1vsMaquina;
    private Button modo1vsMaquinaRandom;
    private Button modo1vsMaquinaInvincible;
    private Button activity_versus;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.menu);


        activity_versus = findViewById(R.id.modo1vs1);

        activity_versus.setOnClickListener(V -> {
            Intent intent = new Intent(MenuActivity.this, versusActivity.class);
            startActivity(intent);
        });


        modo1vsMaquina = findViewById(R.id.modo1vsMaquina); // Captura el click

        modo1vsMaquina.setOnClickListener(v -> { //Cambio de actividad (pantalla)
            Intent intent = new Intent(MenuActivity.this, TableroActivity.class);
            startActivity(intent);
        });


        modo1vsMaquinaRandom = findViewById(R.id.modo1vsMaquinaRandom);

        modo1vsMaquinaRandom.setOnClickListener(v -> { //Cambio de actividad (pantalla)
            Intent intent = new Intent(MenuActivity.this, TableroRandomActivity.class);
            startActivity(intent);
        });


        modo1vsMaquinaInvincible = findViewById(R.id.modo1vsMaquinaInvincible);

        modo1vsMaquinaInvincible.setOnClickListener(v -> { //Cambio de actividad (pantalla)
            Intent intent = new Intent(MenuActivity.this, TableroInvincibleActivity.class);
            startActivity(intent);
        });
    }
}
