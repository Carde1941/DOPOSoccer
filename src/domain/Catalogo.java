package domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * La lista de todo lo que se puede escoger en el juego: personajes, máquinas y tipos de partido. Cada
 * nombre tiene guardada la forma de crear su objeto. Para agregar uno nuevo basta con registrarlo
 * aquí.
 */
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
        MAQUINAS.put("Ofensiva", Ofensiva::new);
        MAQUINAS.put("Defensiva", Defensiva::new);
        MAQUINAS.put("Experta", Experta::new);
        TIPOS.put("Por tiempo", new CreadorPorTiempo());
        TIPOS.put("Por goles", new CreadorPorGoles());
    }

    private Catalogo(){
    }

    /**
     * Los nombres de los personajes, en el orden en que se registraron.
     *
     * @return la lista de nombres
     */
    public static List<String> getPersonajes(){
        return new ArrayList<>(PERSONAJES.keySet());
    }

    /**
     * Crea un personaje nuevo a partir de su nombre.
     *
     * @param nombre el nombre del personaje
     * @return el personaje
     * @throws DOPOSoccerException si no hay ningún personaje con ese nombre
     */
    public static Personaje crearPersonaje(String nombre) throws DOPOSoccerException{
        Supplier<Personaje> creador = PERSONAJES.get(nombre);
        if (creador==null){
            throw new DOPOSoccerException(DOPOSoccerException.PERSONAJE_DESCONOCIDO);
        }
        return creador.get();
    }

    /**
     * Los nombres de las máquinas que existen.
     *
     * @return la lista de nombres
     */
    public static List<String> getMaquinas(){
        return new ArrayList<>(MAQUINAS.keySet());
    }

    /**
     * Crea una máquina nueva a partir de su nombre. Cada partido recibe la suya.
     *
     * @param nombre el nombre de la máquina, por ejemplo "Experta"
     * @return la máquina
     * @throws DOPOSoccerException si no hay ninguna máquina con ese nombre
     */
    public static Controlador crearMaquina(String nombre) throws DOPOSoccerException{
        Supplier<Controlador> creador = MAQUINAS.get(nombre);
        if (creador==null){
            throw new DOPOSoccerException(DOPOSoccerException.MAQUINA_DESCONOCIDA);
        }
        return creador.get();
    }

    /**
     * Los nombres de los tipos de partido que existen.
     *
     * @return la lista de nombres
     */
    public static List<String> getTiposPartido(){
        return new ArrayList<>(TIPOS.keySet());
    }

    /**
     * Crea un tipo de partido a partir de su nombre y su cantidad.
     *
     * @param nombre el nombre del tipo de partido
     * @param cantidad los minutos o los goles, según el tipo
     * @return el tipo de partido
     * @throws DOPOSoccerException si el tipo no existe o la cantidad no sirve
     */
    public static TipoPartido crearTipoPartido(String nombre, int cantidad) throws DOPOSoccerException{
        return creadorDe(nombre).crear(cantidad);
    }

    /**
     * Las cantidades que se pueden escoger para un tipo de partido.
     *
     * @param tipo el nombre del tipo de partido, por ejemplo "Por tiempo"
     * @return la lista de cantidades
     * @throws DOPOSoccerException si el tipo no existe
     */
    public static List<Integer> getCantidades(String tipo) throws DOPOSoccerException{
        return creadorDe(tipo).getCantidades();
    }

    /**
     * El texto que se muestra para una cantidad, por ejemplo "2 minutos".
     *
     * @param tipo el nombre del tipo de partido, por ejemplo "Por tiempo"
     * @param cantidad los minutos o los goles
     * @return el texto
     * @throws DOPOSoccerException si el tipo no existe
     */
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
