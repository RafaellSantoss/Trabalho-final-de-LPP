public class LivroFisico extends Livro {
    // Construtor da classe LivroFisico
    public LivroFisico(String titulo, String autor, int anoPublicacao, String genero, int quantidade) {
        // Chama o construtor da superclasse Livro com o tipo "Físico"
        super(titulo, autor, anoPublicacao, genero, quantidade, "Físico");
    }
}