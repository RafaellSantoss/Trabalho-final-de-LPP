public class LivroDigital extends Livro {
    // Construtor da classe LivroDigital
    public LivroDigital(String titulo, String autor, int anoPublicacao, String genero, int quantidade) {
        // Chama o construtor da superclasse Livro com o tipo "Digital"
        super(titulo, autor, anoPublicacao, genero, quantidade, "Digital");
    }
}