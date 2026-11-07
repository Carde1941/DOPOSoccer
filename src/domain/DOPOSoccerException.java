package domain;

public class DOPOSoccerException extends Exception {
    public static final String ESTADISTICA_FUERA_DE_RANGO = "El valor de una estadística debe estar entre 1 y 10";

    public DOPOSoccerException(String message) {
        super(message);
    }
}
