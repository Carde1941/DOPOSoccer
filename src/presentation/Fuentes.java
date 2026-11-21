package presentation;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public final class Fuentes {
    private static final String ARCHIVO = "fuentes/Bungee-Regular.ttf";
    private static final Logger LOG = Logger.getLogger(Fuentes.class.getName());
    private static Font base;

    private Fuentes() {
    }

    public static Font juego(float tamano) {
        if (base == null) {
            base = cargar();
        }
        return base.deriveFont(tamano);
    }

    private static Font cargar() {
        try (InputStream entrada = Fuentes.class.getResourceAsStream(ARCHIVO)) {
            if (entrada != null) {
                return Font.createFont(Font.TRUETYPE_FONT, entrada);
            }
            LOG.warning("No se encontró " + ARCHIVO + "; se usará SansSerif.");
        } catch (IOException | FontFormatException e) {
            LOG.warning("No se pudo leer " + ARCHIVO + ": " + e.getMessage());
        }
        return new Font("SansSerif", Font.BOLD, 12);
    }
}