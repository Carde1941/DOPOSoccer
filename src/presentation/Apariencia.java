package presentation;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.text.Normalizer;
import java.util.Map;

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

    public static Color aura(String personaje) {
        return AURAS.getOrDefault(personaje, AURA_POR_DEFECTO);
    }

    public static BufferedImage cuerpo(String personaje) {
        return imagen("personajes/", personaje);
    }

    public static BufferedImage cabeza(String personaje) {
        return imagen("cabezas/", personaje);
    }

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
