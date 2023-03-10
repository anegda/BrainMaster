package com.example.brainmaster;

import java.util.ArrayList;

public class ClaseBotonesJuego {
    private ArrayList<Integer> secuencia;
    private int puntuacion;
    private int ronda;

    public ClaseBotonesJuego(){
        this.secuencia = new ArrayList<Integer>();
        this.puntuacion = 0;
        this.ronda=0;
    }

    public ArrayList<Integer> getSecuencia(){
        if(puntuacion==ronda) {
            int numero = (int) (Math.random() * 9 + 1);
            secuencia.add(numero);
            this.ronda++;
        }
        return secuencia;
    }

    public boolean comparar(ArrayList<Integer> resultado){
        if(secuencia.equals(resultado)){
            puntuacion = puntuacion+1;
            return true;
        }
        else{
            return false;
        }
    }

    public int getPuntos(){
        return this.puntuacion;
    }
}
