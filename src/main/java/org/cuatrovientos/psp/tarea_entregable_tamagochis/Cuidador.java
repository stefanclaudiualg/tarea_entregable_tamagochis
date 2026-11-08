package org.cuatrovientos.psp.tarea_entregable_tamagochis;

import java.util.ArrayList;

public class Cuidador {

    private ArrayList<Tamagochi> tamagotchis = new ArrayList<>();

    public Cuidador() {
        for (int i = 1; i <= AppConfiguration.NUMERO_TAMAGOCHIS; i++) {
            Tamagochi tamagochi = new Tamagochi(i, "Tamagotchi" + i);
            tamagotchis.add(tamagochi);
            tamagochi.start();
        }
    }

    public Tamagochi buscarTamagochiPorId(int id) {
        for (Tamagochi tamagochi : tamagotchis) {
            if (tamagochi.getTamagochiPorId() == id) {
                return tamagochi;
            }
        }
        return null;
    }

    public void verEstadosTodosLosTamagotchis() {
        System.out.println("Estados de todos los Tamagotchis:");
        for (Tamagochi tamagochi : tamagotchis) {
            System.out.println("ID Tamagochi: " + tamagochi.getTamagochiPorId() + 
            ", Estado del tamagochi: " + tamagochi.getEstado());}
    }
}