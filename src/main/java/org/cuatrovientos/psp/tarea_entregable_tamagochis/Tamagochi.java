package org.cuatrovientos.psp.tarea_entregable_tamagochis;

import java.util.Random;
import java.util.Scanner;

public class Tamagochi extends Thread {

    public enum Estado {
        LIBRE,      
        COMIENDO,
        JUGANDO,
        LIMPIANDOSE,    
        MUERTO
    }

    private int id;
    private String nombre;
    private Estado estado;
    private int suciedad;

    public Tamagochi(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.suciedad = 0;
        this.estado = Estado.LIBRE;
    }

    public int getTamagochiPorId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getSuciedad() {
        return suciedad;
    }

    public Estado getEstado() {
        return estado;
    }

    @Override
    public void run() {
        int segundosVividos = 0;

        while (estado != Estado.MUERTO) {
            try {
                Thread.sleep(1000);
                segundosVividos++;

                if (segundosVividos % 20 == 0) {
                    suciedad++;
                    
                    if (suciedad == AppConfiguration.AVISO_SUCIEDAD) {
                        System.out.println(nombre + ": Estoy empezando a estar sucio, deberías limpiarme.");
                    } else if (suciedad >= AppConfiguration.SUCIEDAD_MAXIMA) {
                        System.out.println(nombre + " ha muerto debido a la suciedad acumulada.");
                        morir();
                        break; 
                    }
                }

                if (segundosVividos >= (AppConfiguration.TIEMPO_DE_VIDA / 1000)) { 
                    System.out.println(nombre + " ha llegado a su máximo de vida y ha muerto de viejo.");
                    morir();
                    break;
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    } 

    public void morir() {
        estado = Estado.MUERTO;
    }

    public void alimentarTamagochi() {
        if (estado != Estado.LIBRE) {
            System.out.println(nombre + ": No puedo comer ahora, estoy " + estado);
            return;
        }

        estado = Estado.COMIENDO;
        System.out.println(nombre + " ha empezado a comer.");

        Random random = new Random();
        int tiempoComiendo = random.nextInt(AppConfiguration.TIEMPO_MAXIMO_COMER - AppConfiguration.TIEMPO_MINIMO_COMER + 1) + AppConfiguration.TIEMPO_MINIMO_COMER;

        try {
            Thread.sleep(tiempoComiendo);
            System.out.println(nombre + " ha terminado de comer.");
            estado = Estado.LIBRE; 
        } catch (InterruptedException e) {
            System.out.println(nombre + " fue interrumpido mientras comía.");
            Thread.currentThread().interrupt();
            morir();
        }
    }

    public void limpiarTamagochi() {
        if (estado != Estado.LIBRE) {
            System.out.println(nombre + ": No puedo limpiarme ahora, estoy " + estado);
            return;
        }

        estado = Estado.LIMPIANDOSE;
        System.out.println(nombre + " ha empezado a limpiarse.");

        try {
            Thread.sleep(5000); 
            suciedad = 0; 
            System.out.println(nombre + " se ha limpiado y ahora está limpio.");
            estado = Estado.LIBRE; 
        } catch (InterruptedException e) {
            System.out.println(nombre + " fue interrumpido mientras se limpiaba.");
            Thread.currentThread().interrupt();
            morir();
        }
    }

    public void jugarTamagochi() {
        if (estado != Estado.LIBRE) {
            System.out.println(nombre + ": No puedo jugar ahora, estoy " + estado);
            return;
        }

        estado = Estado.JUGANDO;
        System.out.println(nombre + " ha empezado a jugar.");

        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int numero1, numero2, resultadoSuma;

        do {
            numero1 = random.nextInt(9) + 1;
            numero2 = random.nextInt(9) + 1;
            resultadoSuma = numero1 + numero2;
        } while (resultadoSuma >= 10);

        boolean estajugando = true;

        while (estajugando && estado != Estado.MUERTO) {
            System.out.print("Suma: " + numero1 + " + " + numero2 + "= ");

            if (scanner.hasNextInt()) {
                int respuestaCuidador = scanner.nextInt();

                if (respuestaCuidador == resultadoSuma) {
                    System.out.println("Correcto, juego terminado.");
                    estajugando = false;
                } else {
                    System.out.println("Resultado incorrecto, sigues jugando.");
                }
            } else {
                System.out.println("Por favor, introduce un número válido.");
                scanner.next();
            }
        }

        estado = Estado.LIBRE;
    }

    public void destruirTamagochi() {
        if (estado != Estado.LIBRE) {
            System.out.println(nombre + ": No puedo ser destruido ahora, estoy " + estado);
            return;
        }

        System.out.println(nombre + " ha sido destruido por el cuidador.");
        morir();
        this.interrupt();
    }
}