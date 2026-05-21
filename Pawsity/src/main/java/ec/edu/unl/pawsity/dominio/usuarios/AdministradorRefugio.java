package ec.edu.unl.pawsity.dominio.usuarios;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class AdministradorRefugio extends Usuario {

    private String codigoEmpleado;

    public AdministradorRefugio() {
        super();
    }

    public AdministradorRefugio(int idUsuario, String nombreCompleto, String email, String contrasena, Date fechaRegistro,
                                String codigoEmpleado) {
        super(idUsuario, nombreCompleto, email, contrasena, fechaRegistro);
        this.codigoEmpleado = codigoEmpleado;
    }

    public void validarRegistroSistema() {
        System.out.println("Verificando credenciales de acceso interno.");
    }

    // TODO: Reemplazar Object por la clase HistorialAdopcion cuando esté implementada
    public List<Object> monitorearAdopciones() {
        System.out.println("Generando reporte de adopciones en curso...");
        return new ArrayList<>();
    }

    // Getters y Setters
    public String getCodigoEmpleado() { return codigoEmpleado; }
    public void setCodigoEmpleado(String codigoEmpleado) { this.codigoEmpleado = codigoEmpleado; }
}