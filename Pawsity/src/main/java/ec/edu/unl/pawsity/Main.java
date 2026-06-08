package ec.edu.unl.pawsity;

import ec.edu.unl.pawsity.dominio.gestionrefugio.*;
import ec.edu.unl.pawsity.dominio.mascota.*;
import ec.edu.unl.pawsity.dominio.usuarios.*;
import ec.edu.unl.pawsity.excepciones.CapacidadRefugioExcedidaException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Refugio refugio = new Refugio("PawSity", 2); // Capacidad intencionalmente baja para la simulación

        // Usuarios precargados en la base de datos simulada
        Usuario admin = new Administrador("admin@mail.com", "123", "Laura", "Díaz", "Jefa");
        Usuario adoptante = new Adoptante("carlos@mail.com", "123", "Carlos", "Pérez", "099", "Centro", "Profesor");
        Usuario vet = new Veterinario("vet@mail.com", "123", "Dr. Mario", "Gómez", "Cirujano", "LIC-01");

        Usuario[] sistemaUsuarios = {admin, adoptante, vet};

        int opcion = 0;
        do {
            System.out.println("\n========================================");
            System.out.println("   🐾 SISTEMA DE GESTIÓN PAWSITY 🐾   ");
            System.out.println("========================================");
            System.out.println("1. Acceder a los paneles de usuario");
            System.out.println("2. Registrar ingreso de nueva mascota");
            System.out.println("3. Procesar solicitud de adopción");
            System.out.println("4. Ver reporte estadístico del refugio");
            System.out.println("5. Cerrar sesión y salir");
            System.out.print("Seleccione una opción: ");

            // Validación para evitar que el programa colapse si se ingresa una letra
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
            } else {
                System.out.println("\n[!] Por favor, ingrese un número válido.");
                scanner.next(); // Limpia el error
                continue; // Vuelve a mostrar el menú
            }

            switch (opcion) {
                case 1:
                    System.out.println("\n--- AUTENTICACIÓN DE USUARIOS ---");
                    System.out.println("Simulando inicio de sesión en el sistema...");
                    for (Usuario u : sistemaUsuarios) {
                        System.out.print("Bienvenido/a, " + u.getNombres() + ". ");
                        u.redireccionarPanel();
                    }
                    break;

                case 2:
                    System.out.println("\n--- FORMULARIO DE INGRESO AL REFUGIO ---");
                    Mascota m1 = new Mascota("Max", "Canino", 2.0, "Mediano", "Macho", "Dorado", EstadoMascota.DISPONIBLE);
                    Mascota m2 = new Mascota("Luna", "Felino", 1.5, "Pequeño", "Hembra", "Blanco", EstadoMascota.DISPONIBLE);
                    Mascota m3 = new Mascota("Rocky", "Canino", 4.0, "Grande", "Macho", "Negro", EstadoMascota.DISPONIBLE);

                    try {
                        System.out.println("Procesando admisión de: " + m1.getNombre() + "...");
                        refugio.registrarMascota(m1);
                        System.out.println(" [OK] " + m1.getNombre() + " ha sido ingresado al sistema.");

                        System.out.println("Procesando admisión de: " + m2.getNombre() + "...");
                        refugio.registrarMascota(m2);
                        System.out.println(" [OK] " + m2.getNombre() + " ha sido ingresado al sistema.");

                        System.out.println("Procesando admisión de: " + m3.getNombre() + "...");
                        refugio.registrarMascota(m3);
                    } catch (CapacidadRefugioExcedidaException e) {
                        System.out.println("\n[ALERTA DEL SISTEMA] " + e.getMessage());
                        System.out.println(">> Acción requerida: Derive a la mascota a un refugio aliado.");
                    }
                    break;

                case 3:
                    System.out.println("\n--- MÓDULO DE ADOPCIONES ---");
                    if (!refugio.buscarMascota().isEmpty()) {
                        Mascota mascotaElegida = refugio.buscarMascota().get(0);
                        System.out.println("Generando contrato de adopción para el solicitante " + adoptante.getNombres() + " por la mascota '" + mascotaElegida.getNombre() + "'...");

                        SolicitudDeAdopcion solicitud = new SolicitudDeAdopcion((Adoptante) adoptante, mascotaElegida);
                        solicitud.aprobar();

                        System.out.println(">> ¡Trámite finalizado! El estado actual de " + mascotaElegida.getNombre() + " es: " + mascotaElegida.getEstado());
                    } else {
                        System.out.println("[INFO] No hay mascotas registradas en el refugio en este momento.");
                    }
                    break;

                case 4:
                    System.out.println("\n--- DASHBOARD ESTADÍSTICO ---");
                    System.out.println(" Total de rescates históricos registrados: " + Mascota.getTotalMascotasRescatadas());
                    System.out.println(" Ocupación actual de instalaciones: " + refugio.buscarMascota().size() + " mascotas hospedadas.");
                    break;

                case 5:
                    System.out.println("\nCerrando el Sistema PawSity. ¡Buen turno!");
                    break;

                default:
                    System.out.println("\n[!] Opción no reconocida. Intente de nuevo.");
            }
        } while (opcion != 5);

        scanner.close();
    }
}
