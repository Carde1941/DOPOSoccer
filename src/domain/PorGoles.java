package domain;

public class PorGoles extends TipoPartido {
    private int golesParaGanar;

    public PorGoles(int golesParaGanar) throws DOPOSoccerException{
        if(golesParaGanar < 1){
            throw new DOPOSoccerException(DOPOSoccerException.GOLES_INVALIDOS);
        }
        this.golesParaGanar = golesParaGanar;
    }

    public boolean haTerminado(int golesIzquierdo, int golesDerecho){
        return golesIzquierdo >= golesParaGanar || golesDerecho >= golesParaGanar;
    }


}
