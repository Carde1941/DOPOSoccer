package domain;

/** Receta para crear un tipo de partido a partir de una cantidad: minutos, goles... */
public interface CreadorTipoPartido {
    TipoPartido crear(int cantidad) throws DOPOSoccerException;
}
