class LivroFisico extends Livro {
    private int quantidade;

    public LivroFisico(String titulo, String autor, int ano, String genero, int quantidade) {
        super(titulo, autor, ano, genero, quantidade);
        this.quantidade = quantidade;
    }

    // Métodos getter e setter para quantidade
    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "[Físico] " + super.toString() + " - Quantidade: " + quantidade;
    }
}
