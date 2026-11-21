package presentation;

import java.awt.Color;
import java.awt.Font;

public final class Estilo {
    public static final Color FONDO = new Color(28, 16, 56);
    public static final Color PANEL = new Color(52, 30, 92);
    public static final Color TEXTO = Color.WHITE;
    public static final Color ROSADO = new Color(255, 70, 140);
    public static final Color AMARILLO = new Color(255, 205, 40);
    public static final Color CIAN = new Color(70, 205, 255);
    public static final Color PELIGRO = new Color(214, 48, 49);
    public static final Color DESHABILITADO = new Color(105, 96, 135);
    public static final Color CONTORNO = new Color(22, 10, 38);

    public static final Color PRIMARIO = ROSADO;
    public static final Color SECUNDARIO = CIAN;

    public static final Font TITULO = Fuentes.juego(40f);
    public static final Font BOTON = Fuentes.juego(18f);
    public static final Font NORMAL = new Font("SansSerif", Font.BOLD, 15);

    private Estilo() {
    }
}