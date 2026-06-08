package ec.edu.unl.pawsity;

import ec.edu.unl.pawsity.dominio.gestionrefugio.Refugio;
import ec.edu.unl.pawsity.dominio.gestionrefugio.SolicitudDeAdopcion;
import ec.edu.unl.pawsity.dominio.usuarios.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Refugio refugio = new Refugio("PawSity", 15);

        // Memoria volátil para las solicitudes mientras el programa se ejecuta
        List<SolicitudDeAdopcion> sistemaDeSolicitudes = new ArrayList<>();

        // Base de datos simulada de usuarios
        Usuario admin = new Administrador("admin@mail.com", "123", "Laura", "Díaz", "Jefa");
        Usuario vet = new Veterinario("vet@mail.com", "123", "Dr. Mario", "Gómez", "Cirujano", "LIC-01");
        Usuario adoptante = new Adoptante("carlos@mail.com", "123", "Carlos", "Pérez", "099", "Centro", "Profesor");

        Usuario[] baseDeDatosUsuarios = {admin, vet, adoptante};

        int opcionLogin = 0;
        do {
            System.out.println("\n=========================================");
            System.out.println("   🐾 SISTEMA CENTRAL PAWSITY 🐾   ");
            System.out.println("=========================================");
            System.out.println("Simular inicio de sesión rápido como:");
            System.out.println("1. Administrador (Laura)");
            System.out.println("2. Veterinario (Dr. Mario)");
            System.out.println("3. Adoptante (Carlos)");
            System.out.println("4. Apagar Sistema");
            System.out.print("Seleccione: ");

            try {
                opcionLogin = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("[!] Ingrese un número válido.");
                continue;
            }

            if (opcionLogin >= 1 && opcionLogin <= 3) {
                Usuario usuarioActivo = baseDeDatosUsuarios[opcionLogin - 1];
                // Aplicación de Polimorfismo puro: El sistema no sabe qué clase hija es,
                // pero cada una sabe exactamente qué menú abrir.
                usuarioActivo.redireccionarPanel(scanner, refugio, sistemaDeSolicitudes);
            }

        } while (opcionLogin != 4);

        System.out.println("Apagando servidores de PawSity... ¡Hasta pronto!");
        scanner.close();
    }
}
