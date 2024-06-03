abstract class Livro {
    protected String titulo; // Título do livro
    protected String autor; // Autor do livro
    protected int anoPublicacao; // Ano de publicação do livro
    protected String genero; // Género do livro
    protected int quantidade; // Quantidade de exemplares disponíveis
    protected boolean emprestado; // Indica se o livro está emprestado
    protected String nomeEmprestimo; // Nome da pessoa que emprestou o livro
    protected String tipo; // Tipo do livro (e.g., Físico, Digital)

    // Construtor da classe Livro
    public Livro(String titulo, String autor, int anoPublicacao, String genero, int quantidade, String tipo) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.genero = genero;
        this.quantidade = quantidade;
        this.emprestado = false; // Inicializa como não emprestado
        this.nomeEmprestimo = null; // Inicializa sem nome de empréstimo
        this.tipo = tipo; // Inicializa o tipo do livro
    }

    // Métodos getter para obter os valores dos atributos

    public String getTitulo() {
        return titulo; // Retorna o título do livro
    }

    public String getAutor() {
        return autor; // Retorna o autor do livro
    }

    public int getAnoPublicacao() {
        return anoPublicacao; // Retorna o ano de publicação do livro
    }

    public String getGenero() {
        return genero; // Retorna o género do livro
    }

    public int getQuantidade() {
        return quantidade; // Retorna a quantidade de exemplares disponíveis
    }

    public boolean isEmprestado() {
        return emprestado; // Retorna se o livro está emprestado ou não
    }

    public String getNomeEmprestimo() {
        return nomeEmprestimo; // Retorna o nome da pessoa que emprestou o livro
    }

    public String getTipo() {
        return tipo; // Retorna o tipo do livro
    }

    // Método para registar um empréstimo
    public void registrarEmprestimo(String nomeEmprestimo) {
        if (!emprestado && quantidade > 0) { // Verifica se o livro não está emprestado e há exemplares disponíveis
            this.emprestado = true; // Marca o livro como emprestado
            this.nomeEmprestimo = nomeEmprestimo; // Define o nome da pessoa que emprestou
            quantidade--; // Decrementa a quantidade de exemplares disponíveis
        }
    }

    // Método para devolver um livro
    public void devolverLivro() {
        if (emprestado) { // Verifica se o livro está emprestado
            this.emprestado = false; // Marca o livro como não emprestado
            this.nomeEmprestimo = null; // Remove o nome da pessoa que emprestou
            quantidade++; // Incrementa a quantidade de exemplares disponíveis
        }
    }

    // Método toString para retornar uma representação em string do objeto Livro
    @Override
    public String toString() {
        return "Título: " + titulo +
                ", Autor: " + autor +
                ", Ano de Publicação: " + anoPublicacao +
                ", Gênero: " + genero +
                ", Quantidade: " + quantidade +
                ", Tipo: " + tipo + 
                ", Emprestado: " + (emprestado ? "Sim" : "Não") +
                (emprestado ? ", Nome do Empréstimo: " + nomeEmprestimo : "");
    }
}
