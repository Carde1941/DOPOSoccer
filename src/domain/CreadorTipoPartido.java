package domain;

public interface CreadorTipoPartido {
    TipoPartido crear(int cantidad) throws DOPOSoccerException;
}
