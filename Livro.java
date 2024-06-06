import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

abstract class Livro {
    protected String titulo; 
    protected String autor; 
    protected int anoPublicacao; 
    protected String genero; 
    protected int quantidade; 
    protected boolean emprestado; 
    protected List<String> nomeEmprestimo; 
    protected String tipo; 

    public Livro(String titulo, String autor, int anoPublicacao, String genero, int quantidade, String tipo) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.genero = genero;
        this.quantidade = quantidade;
        this.emprestado = false; 
        this.nomeEmprestimo = new ArrayList<>(); 
        this.tipo = tipo; 
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

    public void setQuantidade(int newValue) {
        this.quantidade = newValue;    
    }

    public boolean isEmprestado() {
        return emprestado; 
    }

    public List<String> getNomeEmprestimo() {
        return nomeEmprestimo; 
    }

    public String getTipo() {
        return tipo; 
    }

    public void registarEmprestimo(String nomeEmprestimo) {
        if (quantidade > 0) { 
            this.emprestado = true; 
            this.nomeEmprestimo.add(nomeEmprestimo);
            quantidade--; 
        }
    }

    public void devolverLivro(String nomeEmprestimo) {
        if (this.nomeEmprestimo.contains(nomeEmprestimo)) { 
            this.nomeEmprestimo.remove(nomeEmprestimo);
            if (this.nomeEmprestimo.isEmpty()) {
                this.emprestado = false; 
            }
            quantidade++; 
        }
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ");
        for (String nome : nomeEmprestimo) {
            joiner.add(nome);
        }
        return  "[ Titulo: " + titulo + " ][ Autor: " + autor + " ][ Ano Publicado: " + anoPublicacao + " ][ Gênero: " + genero + " ][ Numero de Livros: " + quantidade + " ][ Versão: " + tipo + " ][ Emprestado: " + emprestado + " ][ Nomes: " + joiner.toString() + " ]";
    }
    public static Livro fromString(String str) {
        try {
            String titulo = str.substring(str.indexOf("Titulo: ") + 8, str.indexOf(" ][ Autor: "));
            String autor = str.substring(str.indexOf("Autor: ") + 7, str.indexOf(" ][ Ano Publicado: "));
            int anoPublicacao = Integer.parseInt(str.substring(str.indexOf("Ano Publicado: ") + 15, str.indexOf(" ][ Gênero: ")));
            String genero = str.substring(str.indexOf("Gênero: ") + 8, str.indexOf(" ][ Numero de Livros: "));
            int quantidade = Integer.parseInt(str.substring(str.indexOf("Numero de Livros: ") + 18, str.indexOf(" ][ Versão: ")));
            String tipo = str.substring(str.indexOf("Versão: ") + 8, str.indexOf(" ][ Emprestado: "));
            boolean emprestado = Boolean.parseBoolean(str.substring(str.indexOf("Emprestado: ") + 12, str.indexOf(" ][ Nomes: ")));

            Livro livro = new LivroConcreta(titulo, autor, anoPublicacao, genero, quantidade, tipo);

            String nomesEmprestimoStr = str.substring(str.indexOf("Nomes: ") + 7, str.lastIndexOf(" ]"));
            if (!nomesEmprestimoStr.isEmpty()) {
                String[] nomesEmprestimo = nomesEmprestimoStr.split(", ");
                for (String nome : nomesEmprestimo) {
                    livro.nomeEmprestimo.add(nome);
                }
                livro.emprestado = true;
            }
            return livro;
        } catch (Exception e) {
            throw new IllegalArgumentException("String de entrada inválida: " + str, e);
        }
    }
}

class LivroConcreta extends Livro {
    public LivroConcreta(String titulo, String autor, int anoPublicacao, String genero, int quantidade, String tipo) {
        super(titulo, autor, anoPublicacao, genero, quantidade, tipo);
    }
}