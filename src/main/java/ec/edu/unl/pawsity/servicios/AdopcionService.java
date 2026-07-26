package ec.edu.unl.pawsity.servicios;

import ec.edu.unl.pawsity.dominio.gestionrefugio.EstadoSolicitud;
import ec.edu.unl.pawsity.dominio.gestionrefugio.SolicitudDeAdopcion;
import ec.edu.unl.pawsity.dominio.mascota.EstadoMascota;
import ec.edu.unl.pawsity.dominio.mascota.Mascota;
import ec.edu.unl.pawsity.dominio.usuarios.Adoptante;
import ec.edu.unl.pawsity.repositorios.MascotaRepository;
import ec.edu.unl.pawsity.repositorios.SolicitudRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class AdopcionService {

    @Inject
    private SolicitudRepository solicitudRepository;

    @Inject
    private MascotaRepository mascotaRepository;

    @Transactional
    public SolicitudDeAdopcion crearSolicitud(Adoptante adoptante, Mascota mascota, String tipoVivienda, boolean tieneOtrasMascotas, String experienciaPrevia, String motivoAdopcion) {
        Objects.requireNonNull(adoptante, "El adoptante no puede ser nulo");
        Objects.requireNonNull(mascota, "La mascota no puede ser nula");

        Mascota mascotaActual = mascotaRepository.buscarPorId(mascota.getId());
        if (mascotaActual == null || mascotaActual.getEstado() != EstadoMascota.DISPONIBLE) {
            throw new IllegalStateException("Lo sentimos, esta mascota ya no está disponible para adopción.");
        }

        SolicitudDeAdopcion nuevaSolicitud = new SolicitudDeAdopcion(adoptante, mascotaActual);
        nuevaSolicitud.registrarFormulario(tipoVivienda, tieneOtrasMascotas, experienciaPrevia, motivoAdopcion);

        nuevaSolicitud.setFechaSolicitud(LocalDate.now());

        solicitudRepository.guardar(nuevaSolicitud);
        return nuevaSolicitud;
    }

    @Transactional
    public void aprobarSolicitud(Long idSolicitud) {
        Objects.requireNonNull(idSolicitud, "El ID de la solicitud es requerido");

        SolicitudDeAdopcion solicitud = solicitudRepository.buscarPorId(idSolicitud);
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud especificada no existe en el sistema.");
        }

        if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden aprobar solicitudes que estén en estado PENDIENTE.");
        }

        Mascota mascota = solicitud.getMascota();
        if (mascota.getEstado() != EstadoMascota.DISPONIBLE) {
            throw new IllegalStateException("No se puede aprobar: la mascota '" + mascota.getNombre() + "' ya fue adoptada por otra persona.");
        }

        solicitud.aprobar();

        solicitudRepository.actualizar(solicitud);
        mascotaRepository.actualizar(mascota);

        rechazarSolicitudesRivales(mascota.getId(), solicitud.getId());
    }


    @Transactional
    public void rechazarSolicitud(Long idSolicitud) {
        Objects.requireNonNull(idSolicitud, "El ID de la solicitud es requerido");

        SolicitudDeAdopcion solicitud = solicitudRepository.buscarPorId(idSolicitud);
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud especificada no existe en el sistema.");
        }

        solicitud.rechazar();
        solicitudRepository.actualizar(solicitud);
    }


    public List<SolicitudDeAdopcion> obtenerSolicitudesPendientes() {
        return solicitudRepository.buscarPendientes();
    }

    public List<SolicitudDeAdopcion> obtenerHistorialPorAdoptante(Long idAdoptante) {
        return solicitudRepository.buscarPorAdoptante(idAdoptante);
    }

    private void rechazarSolicitudesRivales(Long idMascotaAdoptada, Long idSolicitudGanadora) {
        List<SolicitudDeAdopcion> pendientes = solicitudRepository.buscarPendientes();
        for (SolicitudDeAdopcion sol : pendientes) {
            if (sol.getMascota().getId().equals(idMascotaAdoptada) && !sol.getId().equals(idSolicitudGanadora)) {
                sol.rechazar();
                solicitudRepository.actualizar(sol);
            }
        }
    }
}
