package com.example.estructurasgrupo_01;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Game.Table;
import TDA.Tree;

public class TableroRandomActivity extends AppCompatActivity {
    private ImageView fondorandomizer;
    private ImageView circuloR1 ;
    private ImageView diamanteR2;
    private ImageView estrellaR2;

    private ImageView diamanteR3;
    private ImageView estrellaR1;
    private ImageView cuadradoR1;

    private ImageView cuadradoR2;
    private ImageView circuloR2 ;
    private ImageView diamanteR1;
    private final Handler handler =
            new Handler(Looper.getMainLooper());
    private Button btn00;
    private Button btn01;
    private Button btn02;
    private Button btn10;
    private Button btn11;
    private Button btn12;
    private Button btn20;
    private Button btn21;
    private Button btn22;
    private Table table;
    private char simboloJugador;
    private char simboloMaquina;
    private boolean empiezaMaquina;
    private AnimatorSet animacionesFiguras;
    private boolean animacionesActivas = false;

    // El siguiente método sirve para manejar la animación del fondo de Randomizer

    private final Runnable animacionFondo = new Runnable() {
        @Override
        public void run() {
            fondorandomizer.animate().alpha(0f);
            fondorandomizer.animate().setDuration(1000);
            fondorandomizer.animate().withEndAction(() -> {
                        fondorandomizer.animate();
                        fondorandomizer.animate().alpha(1f);
                        fondorandomizer.animate().setDuration(1000);
                        fondorandomizer.animate().withEndAction(() ->
                                        handler.postDelayed(this, 5000)
                                );
                        fondorandomizer.animate().start();
                    });
            fondorandomizer.animate().start();
        }
    };

    // El siguiente método sirve para personalizar el texto de la pantalla emergente
    private void mostrarResultado(String mensaje){

        LinearLayout panelResultado = findViewById(R.id.panelResultado);
        TextView txtResultado = findViewById(R.id.txtResultado);
        Button btnVolverMenu = findViewById(R.id.btnVolverMenu);

        txtResultado.setText(mensaje);

        btn00.setEnabled(false);
        btn01.setEnabled(false);
        btn02.setEnabled(false);
        btn10.setEnabled(false);
        btn11.setEnabled(false);
        btn12.setEnabled(false);
        btn20.setEnabled(false);
        btn21.setEnabled(false);
        btn22.setEnabled(false);

        panelResultado.setVisibility(View.VISIBLE);

        btnVolverMenu.setOnClickListener(v -> {
            finish();
        });
    }

    // Método para imágenes de las jugadas

    private void mostrarSimbolo(int posx, int posy, char simbolo){

        ImageView icono = null;

        if(posx == 0 && posy == 0){

            if(simbolo == 'x') icono = findViewById(R.id.equis00);
            else icono = findViewById(R.id.circulo00);

        }else if(posx == 0 && posy == 1){

            if(simbolo == 'x') icono = findViewById(R.id.equis01);
            else icono = findViewById(R.id.circulo01);

        }else if(posx == 0 && posy == 2){

            if(simbolo == 'x') icono = findViewById(R.id.equis02);
            else icono = findViewById(R.id.circulo02);

        }else if(posx == 1 && posy == 0){

            if(simbolo == 'x') icono = findViewById(R.id.equis10);
            else icono = findViewById(R.id.circulo10);

        }else if(posx == 1 && posy == 1){

            if(simbolo == 'x') icono = findViewById(R.id.equis11);
            else icono = findViewById(R.id.circulo11);

        }else if(posx == 1 && posy == 2){

            if(simbolo == 'x') icono = findViewById(R.id.equis12);
            else icono = findViewById(R.id.circulo12);

        }else if(posx == 2 && posy == 0){

            if(simbolo == 'x') icono = findViewById(R.id.equis20);
            else icono = findViewById(R.id.circulo20);

        }else if(posx == 2 && posy == 1){

            if(simbolo == 'x') icono = findViewById(R.id.equis21);
            else icono = findViewById(R.id.circulo21);

        }else if(posx == 2 && posy == 2){

            if(simbolo == 'x') icono = findViewById(R.id.equis22);
            else icono = findViewById(R.id.circulo22);

        }

        if(icono != null) icono.setVisibility(View.VISIBLE);
    }

    // Método para mostrar líneas

    private void mostrarLineaVictoria(Table tabla){

        View icono = null;
        String tipo = tabla.getTipoVictoria();

        if (tipo.equals("f0")){

            icono = findViewById(R.id.filaVictoria0);

        }else if(tipo.equals("f1")){

            icono = findViewById(R.id.filaVictoria1);

        }else if(tipo.equals("f2")){

            icono = findViewById(R.id.filaVictoria2);

        }else if(tipo.equals("c0")){

            icono = findViewById(R.id.columnaVictoria0);

        }else if(tipo.equals("c1")){

            icono = findViewById(R.id.columnaVictoria1);

        }else if(tipo.equals("c2")){

            icono = findViewById(R.id.columnaVictoria2);

        }else if(tipo.equals("d0")){

            icono = findViewById(R.id.diagonalVictoria0);

        }else if(tipo.equals("d1")){

            icono = findViewById(R.id.diagonalVictoria1);

        }

        if(icono != null) icono.setVisibility(View.VISIBLE);

    }







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tablero_random);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Instancia del tablero con el que se va a jugar
        table = new Table();

        fondorandomizer = findViewById(R.id.fondorandomizer);

        circuloR1 = findViewById(R.id.circuloR1);
        diamanteR2 = findViewById(R.id.diamanteR2);
        estrellaR2 = findViewById(R.id.estrellaR2);

        diamanteR3 = findViewById(R.id.diamanteR3);
        estrellaR1 = findViewById(R.id.estrellaR1);
        cuadradoR1 = findViewById(R.id.cuadradoR1);

        cuadradoR2 = findViewById(R.id.cuadradoR2);
        circuloR2 = findViewById(R.id.circuloR2);
        diamanteR1 = findViewById(R.id.diamanteR1);

        FrameLayout panelConfiguracion = findViewById(R.id.panelConfiguracion);

        RadioButton opcionX = findViewById(R.id.opcionX);

        RadioButton opcionO = findViewById(R.id.opcionO);

        RadioButton opcionJugador = findViewById(R.id.opcionJugador);

        RadioButton opcionMaquina = findViewById(R.id.opcionMaquina);

        Button btnComenzar = findViewById(R.id.btnComenzar);

        TextView pensamiento = findViewById(R.id.pensamiento);

        btnComenzar.setOnClickListener(v -> {


            if(opcionX.isChecked()){

                simboloJugador = 'x';
                simboloMaquina = 'o';

            }else if(opcionO.isChecked()){

                simboloJugador = 'o';
                simboloMaquina = 'x';

            }else{
                return;
            }

            if(opcionJugador.isChecked()){

                empiezaMaquina = false;

            }else if(opcionMaquina.isChecked()){

                empiezaMaquina = true;

            }else{
                return;
            }


            panelConfiguracion.setVisibility(View.GONE);

            if (empiezaMaquina) {

                btn00.setEnabled(false);
                btn01.setEnabled(false);
                btn02.setEnabled(false);
                btn10.setEnabled(false);
                btn11.setEnabled(false);
                btn12.setEnabled(false);
                btn20.setEnabled(false);
                btn21.setEnabled(false);
                btn22.setEnabled(false);

                pensamiento.setText("pensando");
                pensamiento.setVisibility(View.VISIBLE);

                handler.postDelayed(() -> {
                    pensamiento.setText("pensando.");
                }, 500);

                handler.postDelayed(() -> {
                    pensamiento.setText("pensando..");
                }, 1000);

                handler.postDelayed(() -> {
                    pensamiento.setText("pensando...");
                }, 1500);

                handler.postDelayed(() -> {
                    btn00.setEnabled(true);
                    btn01.setEnabled(true);
                    btn02.setEnabled(true);
                    btn10.setEnabled(true);
                    btn11.setEnabled(true);
                    btn12.setEnabled(true);
                    btn20.setEnabled(true);
                    btn21.setEnabled(true);
                    btn22.setEnabled(true);

                    pensamiento.setVisibility(View.GONE);
                    pensamiento.setText("pensando");

                    int[] coord = Table.obtenerPosicionRandom(table, simboloMaquina, simboloJugador);

                    if (coord != null) {
                        table.insert(coord[0], coord[1], simboloMaquina);
                        mostrarSimbolo(coord[0], coord[1], simboloMaquina);

                    }
                }, 2000);
            }

        });


        // Obtener botones
        btn00 = findViewById(R.id.btn_00);
        btn01 = findViewById(R.id.btn_01);
        btn02 = findViewById(R.id.btn_02);
        btn10 = findViewById(R.id.btn_10);
        btn11 = findViewById(R.id.btn_11);
        btn12 = findViewById(R.id.btn_12);
        btn20 = findViewById(R.id.btn_20);
        btn21 = findViewById(R.id.btn_21);
        btn22 = findViewById(R.id.btn_22);


        // Un solo ClickListener para todos los botones
        View.OnClickListener listener = v -> {

            boolean jugadaValida = false;

            // TURNO DEL JUGADOR
            if (v.getId() == R.id.btn_00) {

                jugadaValida = table.insert(0, 0, simboloJugador);

                if(!jugadaValida) return;

                mostrarSimbolo(0, 0, simboloJugador);

            } else if (v.getId() == R.id.btn_01) {
                jugadaValida = table.insert(0, 1, simboloJugador);

                if(!jugadaValida) return;

                mostrarSimbolo(0, 1, simboloJugador);


            } else if (v.getId() == R.id.btn_02) {
                jugadaValida = table.insert(0, 2, simboloJugador);

                if(!jugadaValida) return;

                mostrarSimbolo(0, 2, simboloJugador);


            } else if (v.getId() == R.id.btn_10) {
                jugadaValida = table.insert(1, 0, simboloJugador);

                if(!jugadaValida) return;

                mostrarSimbolo(1, 0, simboloJugador);

            } else if (v.getId() == R.id.btn_11) {
                jugadaValida = table.insert(1, 1, simboloJugador);

                if(!jugadaValida) return;

                mostrarSimbolo(1, 1, simboloJugador);

            } else if (v.getId() == R.id.btn_12) {
                jugadaValida = table.insert(1, 2, simboloJugador);

                if(!jugadaValida) return;

                mostrarSimbolo(1, 2, simboloJugador);

            } else if (v.getId() == R.id.btn_20) {
                jugadaValida = table.insert(2, 0, simboloJugador);

                if(!jugadaValida) return;

                mostrarSimbolo(2, 0, simboloJugador);

            } else if (v.getId() == R.id.btn_21) {
                jugadaValida = table.insert(2, 1, simboloJugador);

                if(!jugadaValida) return;

                mostrarSimbolo(2, 1, simboloJugador);


            } else if (v.getId() == R.id.btn_22) {
                jugadaValida = table.insert(2, 2, simboloJugador);

                if(!jugadaValida) return;

                mostrarSimbolo(2, 2, simboloJugador);

            }



            // Verificar si el jugador terminó la partida
            if (table.isWinner(simboloJugador)) {
                mostrarLineaVictoria(table);
                mostrarResultado("¡Felicidades!");
                return;
            }

            if (table.isDraw()) {
                mostrarResultado("Empate...");
                return;
            }


            // TURNO DE LA MÁQUINA

            btn00.setEnabled(false);
            btn01.setEnabled(false);
            btn02.setEnabled(false);
            btn10.setEnabled(false);
            btn11.setEnabled(false);
            btn12.setEnabled(false);
            btn20.setEnabled(false);
            btn21.setEnabled(false);
            btn22.setEnabled(false);

            pensamiento.setText("pensando");
            pensamiento.setVisibility(View.VISIBLE);

            handler.postDelayed(() -> {
                pensamiento.setText("pensando.");
            }, 500);

            handler.postDelayed(() -> {
                pensamiento.setText("pensando..");
            }, 1000);

            handler.postDelayed(() -> {
                pensamiento.setText("pensando...");
            }, 1500);

            handler.postDelayed(() -> {

                btn00.setEnabled(true);
                btn01.setEnabled(true);
                btn02.setEnabled(true);
                btn10.setEnabled(true);
                btn11.setEnabled(true);
                btn12.setEnabled(true);
                btn20.setEnabled(true);
                btn21.setEnabled(true);
                btn22.setEnabled(true);

                pensamiento.setVisibility(View.GONE);
                pensamiento.setText("pensando");


                int[] coord = Table.obtenerPosicionRandom(table, simboloMaquina, simboloJugador);

                if (coord != null) {
                    table.insert(coord[0], coord[1], simboloMaquina);
                    mostrarSimbolo(coord[0], coord[1], simboloMaquina);
                }

                // Verificar si la máquina terminó la partida
                if (table.isWinner(simboloMaquina)) {
                    mostrarLineaVictoria(table);
                    mostrarResultado("¡Oops! Perdiste ante el mejor.");
                    return;
                }

                if (table.isDraw()) {
                    mostrarResultado("EMPATEEEE");
                }
            }, 2000);
        };


        // Asignar el mismo listener a los 9 botones
        btn00.setOnClickListener(listener);
        btn01.setOnClickListener(listener);
        btn02.setOnClickListener(listener);
        btn10.setOnClickListener(listener);
        btn11.setOnClickListener(listener);
        btn12.setOnClickListener(listener);
        btn20.setOnClickListener(listener);
        btn21.setOnClickListener(listener);
        btn22.setOnClickListener(listener);

    }

    // Animación de las figuras en el rostro (¿eso es un rostro?) de Randomizer
    private void animarFiguras(){

        // Si la Activity ya no está activa, no empezamos otra vuelta
        if(!animacionesActivas){
            return;
        }

        // Reiniciar figuras

        circuloR1.setAlpha(0f);
        diamanteR2.setAlpha(0f);
        estrellaR2.setAlpha(0f);

        diamanteR3.setAlpha(0f);
        estrellaR1.setAlpha(0f);
        cuadradoR1.setAlpha(0f);

        cuadradoR2.setAlpha(0f);
        circuloR2.setAlpha(0f);
        diamanteR1.setAlpha(0f);


        long duracionCambio = 200;


        // Primera cara

        ObjectAnimator apareceCirculoR1 = ObjectAnimator.ofFloat(circuloR1, "alpha", 0f, 1f);

        ObjectAnimator apareceDiamanteR3 =ObjectAnimator.ofFloat(diamanteR3, "alpha", 0f, 1f);

        ObjectAnimator apareceCuadradoR2 = ObjectAnimator.ofFloat(cuadradoR2, "alpha", 0f, 1f);

        apareceCirculoR1.setStartDelay(0);
        apareceDiamanteR3.setStartDelay(0);
        apareceCuadradoR2.setStartDelay(0);

        apareceCirculoR1.setDuration(duracionCambio);
        apareceDiamanteR3.setDuration(duracionCambio);
        apareceCuadradoR2.setDuration(duracionCambio);

        // Segunda cara

        ObjectAnimator desapareceCirculoR1 =ObjectAnimator.ofFloat(circuloR1, "alpha", 1f, 0f);

        ObjectAnimator desapareceDiamanteR3 = ObjectAnimator.ofFloat(diamanteR3, "alpha", 1f, 0f);

        ObjectAnimator desapareceCuadradoR2 = ObjectAnimator.ofFloat(cuadradoR2, "alpha", 1f, 0f);

        ObjectAnimator apareceDiamanteR2 = ObjectAnimator.ofFloat(diamanteR2, "alpha", 0f, 1f);

        ObjectAnimator apareceEstrellaR1 = ObjectAnimator.ofFloat(estrellaR1, "alpha", 0f, 1f);

        ObjectAnimator apareceCirculoR2 = ObjectAnimator.ofFloat(circuloR2, "alpha", 0f, 1f);

        desapareceCirculoR1.setStartDelay(1000);
        desapareceDiamanteR3.setStartDelay(1000);
        desapareceCuadradoR2.setStartDelay(1000);

        apareceDiamanteR2.setStartDelay(1000);
        apareceEstrellaR1.setStartDelay(1000);
        apareceCirculoR2.setStartDelay(1000);

        desapareceCirculoR1.setDuration(duracionCambio);
        desapareceDiamanteR3.setDuration(duracionCambio);
        desapareceCuadradoR2.setDuration(duracionCambio);

        apareceDiamanteR2.setDuration(duracionCambio);
        apareceEstrellaR1.setDuration(duracionCambio);
        apareceCirculoR2.setDuration(duracionCambio);

        // Tercera cada

        ObjectAnimator desapareceDiamanteR2 = ObjectAnimator.ofFloat(diamanteR2, "alpha", 1f, 0f);

        ObjectAnimator desapareceEstrellaR1 = ObjectAnimator.ofFloat(estrellaR1, "alpha", 1f, 0f);

        ObjectAnimator desapareceCirculoR2 = ObjectAnimator.ofFloat(circuloR2, "alpha", 1f, 0f);

        ObjectAnimator apareceEstrellaR2 = ObjectAnimator.ofFloat(estrellaR2, "alpha", 0f, 1f);

        ObjectAnimator apareceCuadradoR1 = ObjectAnimator.ofFloat(cuadradoR1, "alpha", 0f, 1f);

        ObjectAnimator apareceDiamanteR1 = ObjectAnimator.ofFloat(diamanteR1, "alpha", 0f, 1f);

        desapareceDiamanteR2.setStartDelay(2000);
        desapareceEstrellaR1.setStartDelay(2000);
        desapareceCirculoR2.setStartDelay(2000);

        apareceEstrellaR2.setStartDelay(2000);
        apareceCuadradoR1.setStartDelay(2000);
        apareceDiamanteR1.setStartDelay(2000);

        desapareceDiamanteR2.setDuration(duracionCambio);
        desapareceEstrellaR1.setDuration(duracionCambio);
        desapareceCirculoR2.setDuration(duracionCambio);

        apareceEstrellaR2.setDuration(duracionCambio);
        apareceCuadradoR1.setDuration(duracionCambio);
        apareceDiamanteR1.setDuration(duracionCambio);

        ValueAnimator esperaFinal = ValueAnimator.ofFloat(0f, 1f);

        esperaFinal.setStartDelay(2200);
        esperaFinal.setDuration(800);

        animacionesFiguras = new AnimatorSet();

        animacionesFiguras.playTogether(


                apareceCirculoR1,
                apareceDiamanteR3,
                apareceCuadradoR2,


                desapareceCirculoR1,
                desapareceDiamanteR3,
                desapareceCuadradoR2,

                apareceDiamanteR2,
                apareceEstrellaR1,
                apareceCirculoR2,


                desapareceDiamanteR2,
                desapareceEstrellaR1,
                desapareceCirculoR2,

                apareceEstrellaR2,
                apareceCuadradoR1,
                apareceDiamanteR1,


                esperaFinal
        );

        // Repetir

        animacionesFiguras.addListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if(animacionesActivas) animarFiguras();
                    }
                }
        );

        animacionesFiguras.start();

    }


    // Activar animació al entrar a la Activity
    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(animacionFondo, 5000);
        animacionesActivas = true;
        animarFiguras();
    }

    // Desactivar animación al salida de la Activity. Detener la animación.
    @Override
    protected void onPause() {
        animacionesActivas = false;
        // Quitar listener antes de desactivar
        if(animacionesFiguras != null){
            animacionesFiguras.removeAllListeners();
            animacionesFiguras.cancel();
        }
        super.onPause();
        handler.removeCallbacks(animacionFondo);
        fondorandomizer.animate().cancel();
    }
}