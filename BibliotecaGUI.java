import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

        ButtonType searchButton = new ButtonType("Pesquisar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(searchButton, ButtonType.CANCEL);

        VBox content = new VBox(10);
        content.getChildren().addAll(titleField);
        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == searchButton) {
                String title = titleField.getText();
                for (Livro livro : biblioteca) {
                    if (livro.getTitulo().equals(title)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Livro Encontrado");
                        alert.setHeaderText(null);
                        alert.setContentText(livro.toString());
                        alert.showAndWait();
                        return "encontrado";
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

    private void gerenciarEmprestimos(Stage stage) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Gerenciar Empréstimos");
        dialog.setHeaderText("Escolha uma ação:");

        ButtonType emprestarButton = new ButtonType("Emprestar Livro", ButtonBar.ButtonData.OK_DONE);
        ButtonType devolverButton = new ButtonType("Devolver Livro", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(emprestarButton, devolverButton, ButtonType.CANCEL);

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
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Emprestar Livro");
        dialog.setHeaderText("Digite o título do livro que deseja emprestar:");

        TextField titleField = new TextField();

        ButtonType emprestarButton = new ButtonType("Emprestar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(emprestarButton, ButtonType.CANCEL);

        VBox content = new VBox(10);
        content.getChildren().addAll(titleField);
        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == emprestarButton) {
                String title = titleField.getText();
                for (Livro livro : biblioteca) {
                    if (livro.getTitulo().equals(title) && livro.isDisponivel()) {
                        livro.setDisponivel(false);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Livro Emprestado");
                        alert.setHeaderText(null);
                        alert.setContentText("O livro \"" + title + "\" foi emprestado com sucesso.");
                        alert.showAndWait();
                        return "emprestado";
                    }
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Livro Não Encontrado");
                alert.setHeaderText(null);
                alert.setContentText("Livro não encontrado ou não disponível.");
                alert.showAndWait();
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void realizarDevolucao(Stage stage) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Devolver Livro");
        dialog.setHeaderText("Digite o título do livro que deseja devolver:");

        TextField titleField = new TextField();

        ButtonType devolverButton = new ButtonType("Devolver", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(devolverButton, ButtonType.CANCEL);

        VBox content = new VBox(10);
        content.getChildren().addAll(titleField);
        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == devolverButton) {
                String title = titleField.getText();
                for (Livro livro : biblioteca) {
                    if (livro.getTitulo().equals(title) && !livro.isDisponivel()) {
                        livro.setDisponivel(true);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Livro Devolvido");
                        alert.setHeaderText(null);
                        alert.setContentText("O livro \"" + title + "\" foi devolvido com sucesso.");
                        alert.showAndWait();
                        return "devolvido";
                    }
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Livro Não Encontrado");
                alert.setHeaderText(null);
                alert.setContentText("Livro não encontrado ou já está disponível.");
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

