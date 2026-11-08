package org.cuatrovientos.psp.tarea_entregable_tamagochis;

import java.util.ArrayList;
import java.util.Scanner;

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

    public void menuInteraccionTamagochis() {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        while (opcion != 6) {
            System.out.println("MENU DEL CUIDADOR DE TAMAGOCHIS");
            System.out.println("1. Ver estados de todos los tamagotchis");
            System.out.println("2. Alimentar un tamagotchi");
            System.out.println("3. Limpiar un tamagotchi");
            System.out.println("4. Jugar con un tamagotchi");
            System.out.println("5. Matar un tamagotchi");
            System.out.println("6. Finalizar el programa");
            System.out.print("Opcion seleccionada: ");
            opcion = scanner.nextInt();
            if (opcion == 1) {
                verEstadosTodosLosTamagotchis();
            } else if (opcion >= 2 && opcion <= 5) {
                System.out.print("Introduce el ID del tamagotchi: ");
                int id = scanner.nextInt();
                Tamagochi t = buscarTamagochiPorId(id);
                if (t == null) {
                    System.out.println("Tamagotchi no encontrado.");
                    continue;
                }
                switch (opcion) {
                    case 2:
                        new Thread(() -> t.alimentarTamagochi()).start();
                        break;
                    case 3:
                        new Thread(() -> t.limpiarTamagochi()).start();
                        break;
                    case 4:
                        t.jugarTamagochi();
                        break;
                    case 5:
                        t.destruirTamagochi();
                        break;
                }
            }
        }
        System.out.println("Programa cerrado con exito.");
    }
}