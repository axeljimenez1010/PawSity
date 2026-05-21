
// CÓDIG BIEN ESCRITO
public void aprobarAdopcion(Usuario adoptante, Mascota mascota) throws AdopcionInvalidaException {
    if (mascota.estaEnTratamientoMedico()) {
        throw new AdopcionInvalidaException("La mascota aún se está recuperando y no puede ser adoptada.");
    }

    if (adoptante.tieneMultasPendientes()) {
        throw new AdopcionInvalidaException("El usuario debe pagar sus multas antes de adoptar.");
    }

    // ... proceso de adopción normal y limpio ...
}
