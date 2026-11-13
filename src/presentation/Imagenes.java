package presentation;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public final class Imagenes {
    private static final String CARPETA = "imagenes/";
    private static final Logger LOG = Logger.getLogger(Imagenes.class.getName());
    private static final Map<String, BufferedImage> CACHE = new HashMap<>();

    private Imagenes() {
    }

    public static BufferedImage get(String nombre) {
        if (CACHE.containsKey(nombre)) {
            return CACHE.get(nombre);
        }
        BufferedImage imagen = null;
        try (InputStream entrada = Imagenes.class.getResourceAsStream(CARPETA + nombre)) {
            if (entrada == null) {
                LOG.warning("No se encontró la imagen " + CARPETA + nombre + "; se usará el fondo por defecto.");
            } else {
                imagen = ImageIO.read(entrada);
            }
        } catch (IOException e) {
            LOG.warning("No se pudo leer la imagen " + CARPETA + nombre + ": " + e.getMessage());
        }
        CACHE.put(nombre, imagen);
        return imagen;
    }
}