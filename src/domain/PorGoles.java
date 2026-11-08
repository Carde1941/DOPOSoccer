package domain;

public class PorGoles extends TipoPartido {
    private int golesParaGanar;

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
