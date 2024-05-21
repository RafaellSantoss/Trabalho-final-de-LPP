import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BibliotecaGUI extends Application {
    private ArrayList<Livro> biblioteca = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Button adicionarButton = new Button("Adicionar Livro");
        adicionarButton.setOnAction(e -> adicionarLivro(primaryStage));

        Button removerButton = new Button("Remover Livro");
        removerButton.setOnAction(e -> removerLivro(primaryStage));

        Button pesquisarButton = new Button("Pesquisar Livro");
        pesquisarButton.setOnAction(e -> pesquisarLivro(primaryStage));

        Button gerenciarEmprestimosButton = new Button("Gerenciar Empréstimos");
        gerenciarEmprestimosButton.setOnAction(e -> gerenciarEmprestimos(primaryStage));

        Button categorizarButton = new Button("Categorizar Livros");
        categorizarButton.setOnAction(e -> categorizarLivros(primaryStage));

        root.getChildren().addAll(adicionarButton, removerButton, pesquisarButton, gerenciarEmprestimosButton, categorizarButton);

        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Sistema de Gerenciamento de Biblioteca");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void adicionarLivro(Stage stage) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Adicionar Livro");
        dialog.setHeaderText("Digite os detalhes do livro:");

        Label titleLabel = new Label("Título:");
        TextField titleField = new TextField();

        Label authorLabel = new Label("Autor:");
        TextField authorField = new TextField();

        Label yearLabel = new Label("Ano de Publicação:");
        TextField yearField = new TextField();

        Label genreLabel = new Label("Gênero:");
        TextField genreField = new TextField();

        ButtonType addButton = new ButtonType("Adicionar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        VBox content = new VBox(10);
        content.getChildren().addAll(titleLabel, titleField, authorLabel, authorField, yearLabel, yearField, genreLabel, genreField);
        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                String title = titleField.getText();
                String author = authorField.getText();
                int year = Integer.parseInt(yearField.getText());
                String genre = genreField.getText();
                Livro livro = new LivroFisico(title, author, year, genre);
                biblioteca.add(livro);
                return "adicionado";
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void removerLivro(Stage stage) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Remover Livro");
        dialog.setHeaderText("Digite o título do livro que deseja remover:");

        TextField titleField = new TextField();

        ButtonType removeButton = new ButtonType("Remover", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(removeButton, ButtonType.CANCEL);

        VBox content = new VBox(10);
        content.getChildren().addAll(titleField);
        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == removeButton) {
                String title = titleField.getText();
                biblioteca.removeIf(livro -> livro.getTitulo().equals(title));
                return "removido";
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void pesquisarLivro(Stage stage) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Pesquisar Livro");
        dialog.setHeaderText("Digite o título do livro que deseja pesquisar:");

        TextField titleField = new TextField();

        ButtonType searchButton = new But
