package domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class Catalogo {

    private static final Map<String, Supplier<Controlador>> MAQUINAS = new LinkedHashMap<>();
    private static final Map<String, CreadorTipoPartido> TIPOS = new LinkedHashMap<>();
    private static final Map<String, Supplier<Personaje>> PERSONAJES = new LinkedHashMap<>();

    static {
        PERSONAJES.put("Corea del Sur", Corea::new);
        PERSONAJES.put("Camerún", Camerun::new);
        PERSONAJES.put("Nigeria", Nigeria::new);
        PERSONAJES.put("Estados Unidos", EstadosUnidos::new);
        MAQUINAS.put("Torpe", Torpe::new);
        TIPOS.put("Por tiempo", new CreadorPorTiempo());
        TIPOS.put("Por goles", new CreadorPorGoles());
    }

    private Catalogo(){
    }

    public static List<String> getPersonajes(){
        return new ArrayList<>(PERSONAJES.keySet());
    }

    public static Personaje crearPersonaje(String nombre) throws DOPOSoccerException{
        Supplier<Personaje> creador = PERSONAJES.get(nombre);
        if (creador==null){
            throw new DOPOSoccerException(DOPOSoccerException.PERSONAJE_DESCONOCIDO);
        }
        return creador.get();
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

    public static List<String> getTiposPartido(){
        return new ArrayList<>(TIPOS.keySet());
    }

    public static TipoPartido crearTipoPartido(String nombre, int cantidad) throws DOPOSoccerException{
        return creadorDe(nombre).crear(cantidad);
    }

    public static List<Integer> getCantidades(String tipo) throws DOPOSoccerException{
        return creadorDe(tipo).getCantidades();
    }

    public static String describirCantidad(String tipo, int cantidad) throws DOPOSoccerException{
        return creadorDe(tipo).describir(cantidad);
    }

    private static CreadorTipoPartido creadorDe(String nombre) throws DOPOSoccerException{
        CreadorTipoPartido creador = TIPOS.get(nombre);
        if (creador==null){
            throw new DOPOSoccerException(DOPOSoccerException.TIPO_DESCONOCIDO);
        }
        return creador;
    }
}
