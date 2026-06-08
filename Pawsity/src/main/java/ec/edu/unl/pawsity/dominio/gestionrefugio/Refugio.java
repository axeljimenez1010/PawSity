package ec.edu.unl.pawsity.dominio.gestionrefugio;
import ec.edu.unl.pawsity.dominio.mascota.Mascota;
import ec.edu.unl.pawsity.excepciones.CapacidadRefugioExcedidaException;
import java.util.ArrayList;
import java.util.List;

public class Refugio {
    private String nombre;
    private int limiteMascotas;
    private List<Mascota> listaMascotas;

    public Refugio(String nombre, int limiteMascotas) {
        this.nombre = nombre;
        this.limiteMascotas = limiteMascotas;
        this.listaMascotas = new ArrayList<>();
    }

    public void registrarMascota(Mascota mascota) throws CapacidadRefugioExcedidaException {
        if (listaMascotas.size() >= limiteMascotas) {
            throw new CapacidadRefugioExcedidaException("No es posible registrar más ingresos. El refugio " + nombre + " ha alcanzado su límite de " + limiteMascotas + " mascotas.");
        }
        listaMascotas.add(mascota);
    }

    public List<Mascota> buscarMascota() {
        return listaMascotas;
    }
}