package presentation;

/** Los cuatro personajes del Anexo 1. Temporal: en el paso 5 pasan al dominio con Factory Method. */
public enum Pais {
    COREA("Corea del Sur", "Aura Azul", "Recto y horizontal, a alta velocidad.",
        "Empuje: el rival es lanzado hacia su propio arco.", 1.5, 40),
    CAMERUN("Camerún", "Rayo", "Recto, cubierto de electricidad.",
        "Electrocutado: no puede saltar y se mueve al 50 % de su velocidad.", 3, 60),
    NIGERIA("Nigeria", "Tornado", "Remolino a ras de suelo que rebota rápidamente.",
        "Inconsciente: es lanzado al aire y no puede moverse, saltar ni patear.", 3, 40),
    ESTADOS_UNIDOS("Estados Unidos", "Ilusión", "El balón se divide en 8 balones idénticos; 7 son falsos.",
        "Confusión: el rival no sabe cuál es el balón real.", 2.5, 30);

    private final String nombre;
    private final String poder;
    private final String tiro;
    private final String efecto;
    private final double duracion;
    private final int dano;

    Pais(String nombre, String poder, String tiro, String efecto, double duracion, int dano) {
        this.nombre = nombre;
        this.poder = poder;
        this.tiro = tiro;
        this.efecto = efecto;
        this.duracion = duracion;
        this.dano = dano;
    }

    public String getPoder() { return poder; }
    public String getTiro() { return tiro; }
    public String getEfecto() { return efecto; }
    public double getDuracion() { return duracion; }
    public int getDano() { return dano; }

        /** Archivo de la cabeza dentro de imagenes/, por ejemplo cabezas/corea.png. */
    public String getCabeza() { return "cabezas/" + name().toLowerCase() + ".png"; }

    /** Archivo del guayo dentro de imagenes/, por ejemplo guayos/corea.png. */
    public String getGuayo() { return "guayos/" + name().toLowerCase() + ".png"; }

    @Override
    public String toString() { return nombre; }
}