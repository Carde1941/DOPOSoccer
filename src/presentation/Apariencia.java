package presentation;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.text.Normalizer;
import java.util.Map;

/**
 * Cómo se ve cada personaje: sus imágenes y el color de su aura. Las imágenes se buscan con el nombre
 * del personaje en minúsculas, sin tildes y con guiones; si un personaje no tiene las suyas, se usa
 * una genérica.
 */
public final class Apariencia {
    private static final Color AURA_POR_DEFECTO = Color.WHITE;
    private static final String GENERICA = "generico.png";
    private static final Map<String, Color> AURAS = Map.of(
        "Corea del Sur", new Color(70, 150, 255),
        "Camerún", new Color(255, 225, 60),
        "Nigeria", new Color(70, 230, 130),
        "Estados Unidos", new Color(195, 120, 255));

    private Apariencia() {
    }

    /**
     * El color del aura de un personaje.
     *
     * @param personaje el nombre del personaje, por ejemplo "Camerún"
     * @return su color, o blanco si no tiene uno
     */
    public static Color aura(String personaje) {
        return AURAS.getOrDefault(personaje, AURA_POR_DEFECTO);
    }

    /**
     * La imagen de cuerpo completo del personaje, la que sale en las tarjetas.
     *
     * @param personaje el nombre del personaje, por ejemplo "Camerún"
     * @return la imagen
     */
    public static BufferedImage cuerpo(String personaje) {
        return imagen("personajes/", personaje);
    }

    /**
     * La cabeza del personaje, la que se ve en la cancha.
     *
     * @param personaje el nombre del personaje, por ejemplo "Camerún"
     * @return la imagen
     */
    public static BufferedImage cabeza(String personaje) {
        return imagen("cabezas/", personaje);
    }

    /**
     * El guayo del personaje.
     *
     * @param personaje el nombre del personaje, por ejemplo "Camerún"
     * @return la imagen
     */
    public static BufferedImage guayo(String personaje) {
        return imagen("guayos/", personaje);
    }

    private static BufferedImage imagen(String carpeta, String personaje) {
        BufferedImage propia = Imagenes.get(carpeta + archivo(personaje));
        if (propia != null) {
            return propia;
        }
        return Imagenes.get(carpeta + GENERICA);
    }

    private static String archivo(String personaje) {
        String sinTildes = Normalizer.normalize(personaje, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return sinTildes.toLowerCase().replace(' ', '-') + ".png";
    }
}
