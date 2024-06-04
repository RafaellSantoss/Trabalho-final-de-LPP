public class ComparadorDeLivros {
    private Livro livro1;
    private Livro livro2;

    public ComparadorDeLivros(Livro livro1, Livro livro2) {
        this.livro1 = livro1;
        this.livro2 = livro2;
    }

    public String comparar() {
        StringBuilder comparacao = new StringBuilder();
        comparacao.append("Comparação entre os livros:\n");
        comparacao.append("Livro 1: ").append(livro1.getTitulo()).append(" por ").append(livro1.getAutor()).append("\n");
        comparacao.append("Livro 2: ").append(livro2.getTitulo()).append(" por ").append(livro2.getAutor()).append("\n\n");

        comparacao.append("Título: ").append(compararCampos(livro1.getTitulo(), livro2.getTitulo())).append("\n");
        comparacao.append("Autor: ").append(compararCampos(livro1.getAutor(), livro2.getAutor())).append("\n");
        comparacao.append("Ano de Publicação: ").append(compararCampos(livro1.anoPublicacao, livro2.anoPublicacao)).append("\n");
        comparacao.append("Gênero: ").append(compararCampos(livro1.getGenero(), livro2.getGenero())).append("\n");
        comparacao.append("Tipo: ").append(compararCampos(livro1.getTipo(), livro2.getTipo())).append("\n");
        comparacao.append("Quantidade: ").append(compararCampos(livro1.getQuantidade(), livro2.getQuantidade())).append("\n");

        return comparacao.toString();
    }

    private String compararCampos(Object campo1, Object campo2) {
        if (campo1.equals(campo2)) {
            return "Igual (" + campo1 + ")";
        } else {
            return "Diferente (Livro 1: " + campo1 + ", Livro 2: " + campo2 + ")";
        }
    }
}