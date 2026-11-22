package presentation;

import java.awt.Color;

/**
 * Cómo se ve cada personaje del Anexo 1: imágenes, color del aura y la ficha de su poder.
 * Las reglas del poder viven en el dominio (domain.Personaje); aquí se buscan por el nombre.
 */
public enum Pais {
    COREA("Corea del Sur", "Aura Azul", "Recto y horizontal, a alta velocidad.",
        "Empuje: el rival es lanzado hacia su propio arco.", 1.5, 40, new Color(70, 150, 255)),
    CAMERUN("Camerún", "Rayo", "Recto, cubierto de electricidad.",
        "Electrocutado: no puede saltar y se mueve al 50 % de su velocidad.", 3, 60, new Color(255, 225, 60)),
    NIGERIA("Nigeria", "Tornado", "Remolino a ras de suelo que rebota rápidamente.",
        "Inconsciente: es lanzado al aire y no puede moverse, saltar ni patear.", 3, 40, new Color(70, 230, 130)),
    ESTADOS_UNIDOS("Estados Unidos", "Ilusión", "El balón se divide en 8 balones idénticos; 7 son falsos.",
        "Confusión: el rival no sabe cuál es el balón real.", 2.5, 30, new Color(195, 120, 255));

    private final String nombre;
    private final String poder;
    private final String tiro;
    private final String efecto;
    private final double duracion;
    private final int dano;
    private final Color aura;

    Pais(String nombre, String poder, String tiro, String efecto, double duracion, int dano, Color aura) {
        this.nombre = nombre;
        this.poder = poder;
        this.tiro = tiro;
        this.efecto = efecto;
        this.duracion = duracion;
        this.dano = dano;
        this.aura = aura;
    }

    /** El país del personaje que dice el dominio; si el dominio trae uno nuevo sin dibujos, se usa Nigeria. */
    public static Pais desdeNombre(String nombre) {
        for (Pais pais : values()) {
            if (pais.nombre.equals(nombre)) {
                return pais;
            }
        }
        return NIGERIA;
    }

    public String getNombre() { return nombre; }
    public Color getAura() { return aura; }
    public String getPoder() { return poder; }
    public String getTiro() { return tiro; }
    public String getEfecto() { return efecto; }
    public double getDuracion() { return duracion; }
    public int getDano() { return dano; }

    /** Personaje de cuerpo completo dentro de imagenes/, por ejemplo personajes/corea.png. */
    public String getImagen() { return "personajes/" + name().toLowerCase() + ".png"; }

    /** Archivo de la cabeza dentro de imagenes/, por ejemplo cabezas/corea.png. */
    public String getCabeza() { return "cabezas/" + name().toLowerCase() + ".png"; }

    /** Archivo del guayo dentro de imagenes/, por ejemplo guayos/corea.png. */
    public String getGuayo() { return "guayos/" + name().toLowerCase() + ".png"; }

    @Override
    public String toString() { return nombre; }
}