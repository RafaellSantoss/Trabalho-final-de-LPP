public class LivroFisico extends Livro {
    private int numeroPaginas;

    public LivroFisico(String titulo, String autor, int anoPublicacao, String genero, int numeroPaginas) {
        super(titulo, autor, anoPublicacao, genero);
        this.numeroPaginas = numeroPaginas;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    @Override
    public String toString() {
        return super.toString() + ", Número de Páginas: " + numeroPaginas;
    }
}
