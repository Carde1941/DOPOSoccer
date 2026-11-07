package domain;

public class PorTiempo extends TipoPartido {
    private static final int SEGUNDOS_POR_MINUTO = 60;

    private double segundosRestantes;

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

    public double getSegundosRestantes() {
        return segundosRestantes;
    }

    private boolean seAcaboElTiempo() {
        return segundosRestantes == 0;
    }
}