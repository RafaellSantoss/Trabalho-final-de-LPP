import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BibliotecaGUI extends Application {
    private List<Livro> biblioteca = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Opções");

        MenuItem adicionarMenuItem = new MenuItem("Adicionar Livro");
        adicionarMenuItem.setOnAction(e -> adicionarLivro(primaryStage));
        MenuItem removerMenuItem = new MenuItem("Remover Livro");
        removerMenuItem.setOnAction(e -> removerLivro(primaryStage));
        MenuItem pesquisarMenuItem = new MenuItem("Pesquisar Livro");
        pesquisarMenuItem.setOnAction(e -> pesquisarLivro(primaryStage));
        MenuItem gerenciarMenuItem = new MenuItem("Gerenciar Empréstimos");
        gerenciarMenuItem.setOnAction(e -> gerenciarEmprestimos(primaryStage));
        MenuItem categorizarMenuItem = new MenuItem("Categorizar Livros");
        categorizarMenuItem.setOnAction(e -> categorizarLivros(primaryStage));

        menu.getItems().addAll(adicionarMenuItem, removerMenuItem, pesquisarMenuItem, gerenciarMenuItem, categorizarMenuItem);
        menuBar.getMenus().add(menu);
        root.setTop(menuBar);

        VBox buttonsBox = new VBox(10);
        buttonsBox.setPadding(new Insets(10));

        Button adicionarButton = new Button("Adicionar Livro");
        adicionarButton.setOnAction(e -> adicionarLivro(primaryStage));
        adicionarButton.getStyleClass().add("button");

        Button removerButton = new Button("Remover Livro");
        removerButton.setOnAction(e -> removerLivro(primaryStage));
        removerButton.getStyleClass().add("button");

        Button pesquisarButton = new Button("Pesquisar Livro");
        pesquisarButton.setOnAction(e -> pesquisarLivro(primaryStage));
        pesquisarButton.getStyleClass().add("button");

        Button gerenciarEmprestimosButton = new Button("Gerenciar Empréstimos");
        gerenciarEmprestimosButton.setOnAction(e -> gerenciarEmprestimos(primaryStage));
        gerenciarEmprestimosButton.getStyleClass().add("button");

        Button categorizarButton = new Button("Categorizar Livros");
        categorizarButton.setOnAction(e -> categorizarLivros(primaryStage));
        categorizarButton.getStyleClass().add("button");

        buttonsBox.getChildren().addAll(adicionarButton, removerButton, pesquisarButton, gerenciarEmprestimosButton, categorizarButton);
        root.setCenter(buttonsBox);

        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setTitle("Sistema de Gerenciamento de Biblioteca");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void adicionarLivro(Stage stage) {
        // Diálogo para adicionar um novo livro
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Adicionar Livro");
        dialog.setHeaderText("Digite os detalhes do livro:");

        // Campos para inserir os detalhes do livro
        Label titleLabel = new Label("Título:");
        TextField titleField = new TextField();
        Label authorLabel = new Label("Autor:");
        TextField authorField = new TextField();
        Label yearLabel = new Label("Ano de Publicação:");
        TextField yearField = new TextField();
        Label genreLabel = new Label("Gênero:");
        TextField genreField = new TextField();
        Label typeLabel = new Label("Tipo:");
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Físico", "Digital", "Ambos");
        Label quantityLabel = new Label("Quantidade:");
        TextField quantityField = new TextField();

        // Botão para adicionar o livro
        ButtonType addButton = new ButtonType("Adicionar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        // Layout do conteúdo do diálogo
        VBox content = new VBox(10);
        content.getChildren().addAll(titleLabel, titleField, authorLabel, authorField, yearLabel, yearField, genreLabel, genreField, typeLabel, typeComboBox, quantityLabel, quantityField);
        content.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(content);

        // Conversor de resultado do diálogo
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                String title = titleField.getText();
                String author = authorField.getText();
                int year = Integer.parseInt(yearField.getText());
                String genre = genreField.getText();
                String type = typeComboBox.getValue();
                int quantity = Integer.parseInt(quantityField.getText());
                if (type.equals("Físico") || type.equals("Ambos")) {
                    Livro livroFisico = new LivroFisico(title, author, year, genre, quantity);
                    biblioteca.add(livroFisico);
                }
                if (type.equals("Digital") || type.equals("Ambos")) {
                    Livro livroDigital = new LivroDigital(title, author, year, genre, quantity);
                    biblioteca.add(livroDigital);
                }
                return "adicionado";
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void removerLivro(Stage stage) {
        // Diálogo para remover um livro
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Remover Livro");
        dialog.setHeaderText("Digite o título do livro que deseja remover:");

        // Campo para inserir o título do livro
        TextField titleField = new TextField();
        Label typeLabel = new Label("Tipo:");
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Físico", "Digital", "Ambos");

        // Botão para remover o livro
        ButtonType removeButton = new ButtonType("Remover", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(removeButton, ButtonType.CANCEL);

        // Layout do conteúdo do diálogo
        VBox content = new VBox(10);
        content.getChildren().addAll(new Label("Título:"), titleField, typeLabel, typeComboBox);
        content.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(content);

        // Conversor de resultado do diálogo
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == removeButton) {
                String title = titleField.getText();
                String type = typeComboBox.getValue();
                biblioteca.removeIf(livro -> livro.getTitulo().equals(title) && (
                        type.equals("Ambos") ||
                        (type.equals("Físico") && livro instanceof LivroFisico) ||
                        (type.equals("Digital") && livro instanceof LivroDigital)
                ));
                return "removido";
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void pesquisarLivro(Stage stage) {
        // Diálogo para pesquisar um livro
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Pesquisar Livro");
        dialog.setHeaderText("Digite o título do livro que deseja pesquisar:");

        // Campos para inserir o título e tipo do livro
        TextField titleField = new TextField();
        Label typeLabel = new Label("Tipo:");
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Físico", "Digital", "Ambos");

        // Botão para pesquisar o livro
        ButtonType searchButton = new ButtonType("Pesquisar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(searchButton, ButtonType.CANCEL);

        // Layout do conteúdo do diálogo
        VBox content = new VBox(10);
        content.getChildren().addAll(new Label("Título:"), titleField, typeLabel, typeComboBox);
        content.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(content);

        // Conversor de resultado do diálogo
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == searchButton) {
                String title = titleField.getText();
                String type = typeComboBox.getValue();
                List<Livro> livrosEncontrados = new ArrayList<>();
                for (Livro livro : biblioteca) {
                    if (livro.getTitulo().equals(title) && (
                            type.equals("Ambos") ||
                            (type.equals("Físico") && livro instanceof LivroFisico) ||
                            (type.equals("Digital") && livro instanceof LivroDigital)
                    )) {
                        livrosEncontrados.add(livro);
                    }
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if (livrosEncontrados.isEmpty()) {
                    alert.setTitle("Livro Não Encontrado");
                    alert.setHeaderText(null);
                    alert.setContentText("Livro não encontrado na biblioteca.");
                } else {
                    alert.setTitle("Livros Encontrados");
                    alert.setHeaderText(null);
                    StringBuilder contentText = new StringBuilder();
                    for (Livro livro : livrosEncontrados) {
                        contentText.append(livro.toString()).append("\n");
                    }
                    alert.setContentText(contentText.toString());
                }
                alert.showAndWait();
                return "pesquisado";
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void gerenciarEmprestimos(Stage stage) {
        // Diálogo para gerenciar empréstimos
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Gerenciar Empréstimos");
        dialog.setHeaderText("Escolha uma ação:");

        // Botões para emprestar ou devolver livros
        ButtonType emprestarButton = new ButtonType("Emprestar Livro", ButtonBar.ButtonData.OK_DONE);
        ButtonType devolverButton = new ButtonType("Devolver Livro", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(emprestarButton, devolverButton, ButtonType.CANCEL);

        // Conversor de resultado do diálogo
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == emprestarButton) {
                realizarEmprestimo(stage);
            } else if (dialogButton == devolverButton) {
                realizarDevolucao(stage);
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void realizarEmprestimo(Stage stage) {
        List<LivroFisico> livrosDisponiveis = new ArrayList<>();
        for (Livro livro : biblioteca) {
            if (livro instanceof LivroFisico && livro.getQuantidade() > 0) {
                livrosDisponiveis.add((LivroFisico) livro);
            }
        }

        List<String> titulosDisponiveis = new ArrayList<>();
        for (LivroFisico livro : livrosDisponiveis) {
            titulosDisponiveis.add(livro.getTitulo() + " (" + livro.getQuantidade() + " disponíveis)");
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, titulosDisponiveis);
        dialog.setTitle("Emprestar Livro");
        dialog.setHeaderText("Escolha um livro físico disponível para emprestar:");
        dialog.setContentText("Título:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(selectedTitle -> {
            for (LivroFisico livro : livrosDisponiveis) {
                if (selectedTitle.startsWith(livro.getTitulo())) {
                    livro.setQuantidade(livro.getQuantidade() - 1);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Livro Emprestado");
                    alert.setHeaderText(null);
                    alert.setContentText("O livro \"" + livro.getTitulo() + "\" foi emprestado com sucesso. Quantidade restante: " + livro.getQuantidade());
                    alert.showAndWait();
                    break;
                }
            }
        });
    }

    private void realizarDevolucao(Stage stage) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Devolver Livro");
        dialog.setHeaderText("Digite o título do livro que deseja devolver:");

        TextField titleField = new TextField();

        ButtonType devolverButton = new ButtonType("Devolver", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(devolverButton, ButtonType.CANCEL);

        VBox content = new VBox(10);
        content.getChildren().addAll(new Label("Título:"), titleField);
        content.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == devolverButton) {
                String title = titleField.getText();
                for (Livro livro : biblioteca) {
                    if (livro.getTitulo().equals(title)) {
                        livro.setQuantidade(livro.getQuantidade() + 1);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Livro Devolvido");
                        alert.setHeaderText(null);
                        alert.setContentText("O livro \"" + title + "\" foi devolvido com sucesso. Quantidade atual: " + livro.getQuantidade());
                        alert.showAndWait();
                        return "devolvido";
                    }
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Livro Não Encontrado");
                alert.setHeaderText(null);
                alert.setContentText("Livro não encontrado na biblioteca.");
                alert.showAndWait();
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void categorizarLivros(Stage stage) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Categorizar Livros");
        dialog.setHeaderText("Livros disponíveis na biblioteca:");

        StringBuilder contentText = new StringBuilder();
        for (Livro livro : biblioteca) {
            contentText.append(livro.toString()).append("\n");
        }

        Label contentLabel = new Label(contentText.toString());
        dialog.getDialogPane().setContent(contentLabel);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        dialog.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Livro {
    private String titulo;
    private String autor;
    private int ano;
    private String genero;
    private int quantidade;

    public Livro(String titulo, String autor, int ano, String genero, int quantidade) {
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.genero = genero;
        this.quantidade = quantidade;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAno() {
        return ano;
    }

    public String getGenero() {
        return genero;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return titulo + " - " + autor + " (" + ano + "), " + genero + " - Quantidade: " + quantidade;
    }
}

class LivroFisico extends Livro {
    public LivroFisico(String titulo, String autor, int ano, String genero, int quantidade) {
        super(titulo, autor, ano, genero, quantidade);
    }

    @Override
    public String toString() {
        return "[Físico] " + super.toString();
    }
}

class LivroDigital extends Livro {
    public LivroDigital(String titulo, String autor, int ano, String genero, int quantidade) {
        super(titulo, autor, ano, genero, quantidade);
    }

    @Override
    public String toString() {
        return "[Digital] " + super.toString();
    }
}
