public class LivroDigital extends Livro {
    private int tamanhoMB;

    public LivroDigital(String titulo, String autor, int anoPublicacao, String genero, int tamanhoMB) {
        super(titulo, autor, anoPublicacao, genero);
        this.tamanhoMB = tamanhoMB;
    }

    public int getTamanhoMB() {
        return tamanhoMB;
    }

    public void setTamanhoMB(int tamanhoMB) {
        this.tamanhoMB = tamanhoMB;
    }

    @Override
    public String toString() {
        return super.toString() + ", Tamanho (MB): " + tamanhoMB;
    }
}
