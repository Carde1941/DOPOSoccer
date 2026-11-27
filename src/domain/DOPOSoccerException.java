package domain;

/** Los errores que el usuario puede ver. Cada constante es un mensaje listo para mostrar. */
public class DOPOSoccerException extends Exception {
    public static final String ESTADISTICA_FUERA_DE_RANGO = "El valor de una estadística debe estar entre 1 y 10";
    public static final String NO_HAY_PARTIDO = "No hay ningun partido en curso para guardar";
    public static final String ERROR_AL_GUARDAR = "No se pudo guardar la partida.";
    public static final String MINUTOS_INVALIDOS = "Los minutos del partido deben ser mayores a 0";
    public static final String GOLES_INVALIDOS = "Los goles para ganar deben ser mayores a 0";
    public static final String ERROR_AL_ABRIR = "No se pudo abrir la partida.";
    public static final String ARCHIVO_NO_ES_PARTIDA = "El archivo no es una partida de DOPOSoccer.";
    public static final String MAQUINA_DESCONOCIDA = "El perfil de maquina escogido no existe.";
    public static final String TIPO_DESCONOCIDO = "El tipo de partido elegido no existe.";
    public static final String PERSONAJE_DESCONOCIDO = "El personaje elegido no existe.";
    public static final String ESTADISTICA_DESCONOCIDA = "La estadística elegida no existe.";
    public static final String ESTADISTICA_AL_MAXIMO = "Esa estadística ya está en el nivel máximo (10).";
    public static final String PUNTOS_INSUFICIENTES = "No tienes puntos suficientes para esa mejora.";
    public static final String ERROR_AL_GUARDAR_PROGRESO = "No se pudo guardar el progreso de los jugadores.";
    public static final String ERROR_AL_ABRIR_PROGRESO = "No se pudo abrir el progreso de los jugadores.";
    public static final String ARCHIVO_NO_ES_PROGRESO = "El archivo no tiene el progreso de DOPOSoccer.";

    /**
     * Crea el error con su mensaje.
     *
     * @param message el mensaje que va a ver el usuario
     */
    public DOPOSoccerException(String message) {
        super(message);
    }

    /**
     * Crea el error con su mensaje y guarda el error original que lo causó.
     *
     * @param message el mensaje que va a ver el usuario
     * @param causa el error original que lo provocó, para poder revisarlo después en el registro
     */
    public DOPOSoccerException(String message, Throwable causa) {
        super(message, causa);
    }
}
