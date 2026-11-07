package domain;

public class DOPOSoccerException extends Exception {
    public static final String ESTADISTICA_FUERA_DE_RANGO = "El valor de una estadística debe estar entre 1 y 10";
    public static final String MINUTOS_INVALIDOS = "Los minutos del partido deben ser mayores a 0";
    public static final String GOLES_INVALIDOS = "Los goles para ganar deben ser mayores a 0";

    public DOPOSoccerException(String message) {
        super(message);
    }
}
