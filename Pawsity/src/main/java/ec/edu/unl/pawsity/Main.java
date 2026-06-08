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
        Refugio refugio = new Refugio("PawSity", 2);

        List<SolicitudDeAdopcion> sistemaDeSolicitudes = new ArrayList<>();

        Usuario admin = new Administrador("admin@mail.com", "123", "Laura", "Díaz", "Administrador");
        Usuario vet = new Veterinario("vet@mail.com", "123", "Dr. Mario", "Gómez", "Cirujano Veterinario", "LIC-01");
        Usuario adoptante = new Adoptante("carlos@mail.com", "123", "Carlos", "Pérez", "099", "Centro", "Docente");

        Usuario[] baseDeDatosUsuarios = {admin, vet, adoptante};

        int opcionLogin = 0;
        do {
            System.out.println("\n=========================================");
            System.out.println("        SISTEMA CENTRAL PAWSITY        ");
            System.out.println("=========================================");
            System.out.println("Seleccione el perfil de acceso:");
            System.out.println("1. Acceder como Administrador");
            System.out.println("2. Acceder como Veterinario");
            System.out.println("3. Acceder como Adoptante");
            System.out.println("4. Finalizar y salir");
            System.out.print("Opción: ");

            try {
                opcionLogin = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
                continue;
            }

            if (opcionLogin >= 1 && opcionLogin <= 3) {
                Usuario usuarioActivo = baseDeDatosUsuarios[opcionLogin - 1];
                usuarioActivo.redireccionarPanel(scanner, refugio, sistemaDeSolicitudes);
            }

        } while (opcionLogin != 4);

        System.out.println("Cerrando el sistema PawSity. Hasta luego.");
        scanner.close();
    }
}
