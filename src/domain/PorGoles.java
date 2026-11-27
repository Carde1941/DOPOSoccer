package domain;

/** Partido que gana el primero en llegar a cierta cantidad de goles. */
public class PorGoles extends TipoPartido {
    private int golesParaGanar;

    /**
     * Crea el partido.
     *
     * @param golesParaGanar los goles que hay que meter para ganar
     * @throws DOPOSoccerException si los goles son menos de 1
     */
    public PorGoles(int golesParaGanar) throws DOPOSoccerException{
        if(golesParaGanar < 1){
            throw new DOPOSoccerException(DOPOSoccerException.GOLES_INVALIDOS);
        }
        this.golesParaGanar = golesParaGanar;
    }

    @Override 
    public boolean haTerminado(int golesIzquierdo, int golesDerecho){
        return golesIzquierdo >= golesParaGanar || golesDerecho >= golesParaGanar;
    }

    @Override 
    public String getInformacion(int golesIzquierdo, int golesDerecho){
        return "A " + golesParaGanar + " goles";
    }


}
