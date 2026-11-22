package domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class Catalogo {

    private static final Map<String, Supplier<Controlador>> MAQUINAS = new LinkedHashMap<>();

    static {
        MAQUINAS.put("Torpe", Torpe::new);
    }

    private Catalogo(){
    }

    public static List<String> getMaquinas(){
        return new ArrayList<>(MAQUINAS.keySet());
    }

    public static Controlador crearMaquina(String nombre) throws DOPOSoccerException{
        Supplier<Controlador> creador = MAQUINAS.get(nombre);
        if (creador==null){
            throw new DOPOSoccerException(DOPOSoccerException.MAQUINA_DESCONOCIDA);
        }
        return creador.get();
    }








    
}
