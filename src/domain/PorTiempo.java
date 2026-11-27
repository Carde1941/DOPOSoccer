package domain;

/**
 * Partido que dura unos minutos. Si se acaba el tiempo y van empatados, sigue en muerte súbita: gana
 * el siguiente gol.
 */
public class PorTiempo extends TipoPartido {
    private static final int SEGUNDOS_POR_MINUTO = 60;

    private double segundosRestantes;

    /**
     * Crea el partido.
     *
     * @param minutos cuántos minutos dura
     * @throws DOPOSoccerException si los minutos son menos de 1
     */
    public PorTiempo(int minutos) throws DOPOSoccerException {
        if (minutos < 1) {
            throw new DOPOSoccerException(DOPOSoccerException.MINUTOS_INVALIDOS);
        }
        segundosRestantes = minutos * SEGUNDOS_POR_MINUTO;
    }

    @Override
    public void actualizar(double segundos) {
        segundosRestantes = Math.max(segundosRestantes - segundos, 0);
    }

    @Override
    public boolean haTerminado(int golesIzquierdo, int golesDerecho) {
        return seAcaboElTiempo() && golesIzquierdo != golesDerecho;
    }

    /**
     * Dice si se acabó el tiempo con el marcador empatado.
     *
     * @param golesIzquierdo los goles del jugador de la izquierda
     * @param golesDerecho los goles del jugador de la derecha
     * @return true si está en muerte súbita
     */
    public boolean enMuerteSubita(int golesIzquierdo, int golesDerecho) {
        return seAcaboElTiempo() && golesIzquierdo == golesDerecho;
    }

    @Override
    public String getInformacion(int golesIzquierdo, int golesDerecho){
        if(enMuerteSubita(golesIzquierdo, golesDerecho)){
            return "Muerte súbita";
        }
        int segundos = (int) Math.ceil(segundosRestantes);
        int minutos = segundos / SEGUNDOS_POR_MINUTO;
        int resto = segundos % SEGUNDOS_POR_MINUTO;
        return String.format("%d:%02d", minutos, resto);
    }

    /**
     * Cuánto tiempo queda.
     *
     * @return los segundos que faltan
     */
    public double getSegundosRestantes() {
        return segundosRestantes;
    }

    private boolean seAcaboElTiempo() {
        return segundosRestantes == 0;
    }
}