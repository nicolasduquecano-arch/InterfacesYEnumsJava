package lab1_playlist;

class Cancion implements Comparable<Cancion> {
    private final String titulo;
    private final String artista;
    private final int duracionSeg;

    Cancion(String titulo, String artista, int duracionSeg) {
        this.titulo = titulo;
        this.artista = artista;
        this.duracionSeg = duracionSeg;
    }

    public String getTitulo() { return titulo; }
    public String getArtista() { return artista; }
    public int getDuracionSeg() { return duracionSeg; }

    public String duracion() {
        return String.format("%d:%02d", duracionSeg / 60, duracionSeg % 60);
    }

    @Override
    public int compareTo(Cancion otra) { return titulo.compareTo(otra.titulo); }
}
