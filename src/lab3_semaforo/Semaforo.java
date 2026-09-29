package lab3_semaforo;

enum Semaforo {
    VERDE(30, "Avance"), AMARILLO(5, "Precaución"), ROJO(35, "Pare");

    private final int segundos;
    private final String accion;

    Semaforo(int segundos, String accion) { this.segundos = segundos; this.accion = accion; }

    public int getSegundos() { return segundos; }
    public String getAccion() { return accion; }

    public Semaforo siguiente() {
        return switch (this) {
            case VERDE -> AMARILLO;
            case AMARILLO -> ROJO;
            case ROJO -> VERDE;
        };
    }

    public static int duracionCiclo() {
        int total = 0;
        for (Semaforo s : values()) total += s.segundos;
        return total;
    }
}
