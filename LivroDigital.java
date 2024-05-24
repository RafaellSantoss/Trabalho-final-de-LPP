class LivroDigital extends Livro {
    public LivroDigital(String titulo, String autor, int ano, String genero, int quantidade) {
        super(titulo, autor, ano, genero, quantidade);
    }

    @Override
    public String toString() {
        return "[Digital] " + super.toString();
    }
}
