abstract class Livro {
    protected String titulo;
    protected String autor;
    protected int anoPublicacao;
    protected String genero;
    protected int quantidade;
    protected boolean emprestado;
    protected String nomeEmprestimo;

    public Livro(String titulo, String autor, int anoPublicacao, String genero, int quantidade) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.genero = genero;
        this.quantidade = quantidade;
        this.emprestado = false;
        this.nomeEmprestimo = null;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public String getGenero() {
        return genero;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public boolean isEmprestado() {
        return emprestado;
    }

    public String getNomeEmprestimo() {
        return nomeEmprestimo;
    }

    public void registrarEmprestimo(String nomeEmprestimo) {
        if (!emprestado && quantidade > 0) {
            this.emprestado = true;
            this.nomeEmprestimo = nomeEmprestimo;
            quantidade--;
        }
    }

    public void devolverLivro() {
        if (emprestado) {
            this.emprestado = false;
            this.nomeEmprestimo = null;
            quantidade++;
        }
    }

    @Override
    public String toString() {
        return "Título: " + titulo +
                ", Autor: " + autor +
                ", Ano de Publicação: " + anoPublicacao +
                ", Gênero: " + genero +
                ", Quantidade: " + quantidade +
                ", Emprestado: " + (emprestado ? "Sim" : "Não") +
                (emprestado ? ", Nome do Empréstimo: " + nomeEmprestimo : "");
    }
}