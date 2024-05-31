import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
        dialog.setHeaderText("Introduza os Detalhes do Livro:");
    
        // Campos para inserir os detalhes do livro
        Label titleLabel = new Label("Título:");
        TextField titleField = new TextField();
        titleField.setPromptText("Introduza o Título do Livro");
    
        Label authorLabel = new Label("Autor:");
        TextField authorField = new TextField();
        authorField.setPromptText("Introduza o Nome do Autor");
    
        Label yearLabel = new Label("Ano de Publicação:");
        TextField yearField = new TextField();
        yearField.setPromptText("Apenas caracteres numéricos");
    
        // Restrição para permitir apenas números no campo de ano de publicação
        yearField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        }));
    
        Label genreLabel = new Label("Gênero:");
        ComboBox<String> genreComboBox = new ComboBox<>();
        genreComboBox.getItems().addAll("Ação", "Romance", "Terror", "Poesia", "Mistério");
    
        Label typeLabel = new Label("Tipo:");
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Físico", "Digital", "Ambos");
    
        Label quantityLabel = new Label("Quantidade:");
        TextField quantityField = new TextField();
        quantityField.setPromptText("Apenas caracteres numéricos");
    
        // Restrição para permitir apenas números no campo de quantidade
        quantityField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        }));
    
        // Botão para adicionar o livro
        ButtonType addButton = new ButtonType("Adicionar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);
    
        // Layout do conteúdo do diálogo
        VBox content = new VBox(10);
        content.getChildren().addAll(titleLabel, titleField, authorLabel, authorField, yearLabel, yearField, genreLabel, genreComboBox, typeLabel, typeComboBox, quantityLabel, quantityField);
        content.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(content);
    
        // Conversor de resultado do diálogo
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                try {
                    String title = titleField.getText();
                    String author = authorField.getText();
                    int year = Integer.parseInt(yearField.getText());
                    String genre = genreComboBox.getValue();
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
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro ao Adicionar Livro");
                    alert.setHeaderText(null);
                    alert.setContentText("Certifique-se de inserir um ano válido e uma quantidade válida.");
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });
    
        dialog.showAndWait();
    }
    
    

    private void removerLivro(Stage stage) {
        // Diálogo para remover um livro
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Remover Livro");
        dialog.setHeaderText("Selecione o título do livro que deseja remover:");
    
        // ComboBox para selecionar o título do livro
        ComboBox<String> titleComboBox = new ComboBox<>();
        for (Livro livro : biblioteca) {
            titleComboBox.getItems().add(livro.getTitulo() + " (" + livro.getTipo() + ")");
        }
    
        // Botão para remover o livro
        ButtonType removeButton = new ButtonType("Remover", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(removeButton, ButtonType.CANCEL);
        
        // Layout do conteúdo do diálogo
        VBox content = new VBox(10);
        content.getChildren().addAll(new Label("Título e Tipo:"), titleComboBox);
        content.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(content);
    
        // Conversor de resultado do diálogo
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == removeButton) {
                String selectedItem = titleComboBox.getValue();
                if (selectedItem != null) {
                    String[] parts = selectedItem.split(" \\(");
                    String title = parts[0];
                    String type = parts[1].substring(0, parts[1].length() - 1); // Remove the closing parenthesis
    
                    biblioteca.removeIf(livro -> livro.getTitulo().equals(title) && livro.getTipo().equals(type));
                    return "removido";
                }
            }
            return null;
        });
    
        dialog.showAndWait();
    }
    

    private void pesquisarLivro(Stage stage) {
        // Diálogo para pesquisar um livro
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Pesquisar Livro");
        dialog.setHeaderText("Selecione o título do livro que deseja pesquisar:");
    
        // ComboBox para selecionar o título do livro
        ComboBox<String> titleComboBox = new ComboBox<>();
        for (Livro livro : biblioteca) {
            titleComboBox.getItems().add(livro.getTitulo() + " (" + livro.getTipo() + ")");
        }
    
        // Botão para pesquisar o livro
        ButtonType searchButton = new ButtonType("Pesquisar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(searchButton, ButtonType.CANCEL);
    
        // Layout do conteúdo do diálogo
        VBox content = new VBox(10);
        content.getChildren().addAll(new Label("Título e Tipo:"), titleComboBox);
        content.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(content);
    
        // Conversor de resultado do diálogo
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == searchButton) {
                String selectedItem = titleComboBox.getValue();
                if (selectedItem != null) {
                    String[] parts = selectedItem.split(" \\(");
                    String title = parts[0];
                    String type = parts[1].substring(0, parts[1].length() - 1); // Remove the closing parenthesis
    
                    List<Livro> livrosEncontrados = new ArrayList<>();
                    for (Livro livro : biblioteca) {
                        if (livro.getTitulo().equals(title) && livro.getTipo().equals(type)) {
                            livrosEncontrados.add(livro);
                        }
                    }
    
                    // Alerta com os resultados da pesquisa
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
            }
            return null;
        });
    
        dialog.showAndWait();
    }
    
    

    private void gerenciarEmprestimos(Stage stage) {
        // Diálogo para gerenciar empréstimos
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Registrar Empréstimo", "Registrar Empréstimo", "Devolver Livro");
        dialog.setTitle("Gerenciar Empréstimos");
        dialog.setHeaderText("Escolha uma opção:");
    
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(option -> {
            if (option.equals("Registrar Empréstimo")) {
                // Implementação do registro de empréstimo
                Dialog<String> emprestimoDialog = new Dialog<>();
                emprestimoDialog.setTitle("Registrar Empréstimo");
                emprestimoDialog.setHeaderText("Digite os detalhes do empréstimo:");
    
                // Campos para inserir os detalhes do empréstimo
                Label userLabel = new Label("Usuário:");
                TextField userField = new TextField();
    
                Label bookLabel = new Label("Livro:");
                ComboBox<String> bookComboBox = new ComboBox<>();
                for (Livro livro : biblioteca) {
                    bookComboBox.getItems().add(livro.getTitulo() + " (" + livro.getTipo() + ")");
                }
    
                Label dateLabel = new Label("Data de Empréstimo:");
                DatePicker datePicker = new DatePicker();
    
                ButtonType confirmButton = new ButtonType("Confirmar", ButtonBar.ButtonData.OK_DONE);
                emprestimoDialog.getDialogPane().getButtonTypes().addAll(confirmButton, ButtonType.CANCEL);
    
                VBox content = new VBox(10);
                content.getChildren().addAll(userLabel, userField, bookLabel, bookComboBox, dateLabel, datePicker);
                content.setPadding(new Insets(10));
                emprestimoDialog.getDialogPane().setContent(content);
    
                emprestimoDialog.setResultConverter(dialogButton -> {
                    if (dialogButton == confirmButton) {
                        String user = userField.getText();
                        String bookTitle = bookComboBox.getValue();
                        LocalDate date = datePicker.getValue();
                        // Lógica para registrar o empréstimo
                        registrarEmprestimo(user, bookTitle, date);
    
                        // Mostrar pop-up de confirmação
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Empréstimo Registrado");
                        alert.setHeaderText(null);
                        alert.setContentText("O empréstimo foi registrado com sucesso!");
                        alert.showAndWait();
    
                        return "emprestado";
                    }
                    return null;
                });
    
                emprestimoDialog.showAndWait();
            } else if (option.equals("Devolver Livro")) {
                // Implementação da devolução de livro
                Dialog<String> devolucaoDialog = new Dialog<>();
                devolucaoDialog.setTitle("Devolver Livro");
                devolucaoDialog.setHeaderText("Digite os detalhes da devolução:");
    
                // Campos para inserir os detalhes da devolução
                Label userLabel = new Label("Usuário:");
                TextField userField = new TextField();
    
                Label bookLabel = new Label("Livro:");
                ComboBox<String> bookComboBox = new ComboBox<>();
                for (Livro livro : biblioteca) {
                    bookComboBox.getItems().add(livro.getTitulo() + " (" + livro.getTipo() + ")");
                }
    
                Label dateLabel = new Label("Data de Devolução:");
                DatePicker datePicker = new DatePicker();
    
                ButtonType confirmButton = new ButtonType("Confirmar", ButtonBar.ButtonData.OK_DONE);
                devolucaoDialog.getDialogPane().getButtonTypes().addAll(confirmButton, ButtonType.CANCEL);
    
                VBox content = new VBox(10);
                content.getChildren().addAll(userLabel, userField, bookLabel, bookComboBox, dateLabel, datePicker);
                content.setPadding(new Insets(10));
                devolucaoDialog.getDialogPane().setContent(content);
    
                devolucaoDialog.setResultConverter(dialogButton -> {
                    if (dialogButton == confirmButton) {
                        String user = userField.getText();
                        String bookTitle = bookComboBox.getValue();
                        LocalDate date = datePicker.getValue();
                        // Lógica para devolver o livro
                        devolverLivro(user, bookTitle, date);
    
                        // Mostrar pop-up de confirmação
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Devolução Registrada");
                        alert.setHeaderText(null);
                        alert.setContentText("A devolução foi registrada com sucesso!");
                        alert.showAndWait();
    
                        return "devolvido";
                    }
                    return null;
                });
    
                devolucaoDialog.showAndWait();
            }
        });
    }
    
    // Métodos fictícios para registrar empréstimo e devolução
    private void registrarEmprestimo(String user, String bookTitle, LocalDate date) {
        // Implementação do registro de empréstimo
        // Exemplo:
        // Emprestimo emprestimo = new Emprestimo(user, bookTitle, date);
        // listaEmprestimos.add(emprestimo);
    }
    
    private void devolverLivro(String user, String bookTitle, LocalDate date) {
        // Implementação da devolução de livro
        // Exemplo:
        // Emprestimo emprestimo = encontrarEmprestimo(user, bookTitle);
        // if (emprestimo != null) {
        //     listaEmprestimos.remove(emprestimo);
        // }
    }
    

    private void categorizarLivros(Stage stage) {
    // Diálogo para categorizar livros
    Dialog<Void> dialog = new Dialog<>();
    dialog.setTitle("Categorizar Livros");
    dialog.setHeaderText("Lista de Livros na Biblioteca:");

    // TextArea para exibir as informações dos livros
    TextArea textArea = new TextArea();
    textArea.setEditable(false);

    // Adicionar informações dos livros ao TextArea
    StringBuilder sb = new StringBuilder();
    for (Livro livro : biblioteca) {
        sb.append(livro.toString()).append("\n");
    }
    textArea.setText(sb.toString());

    // Layout do diálogo
    VBox content = new VBox(10);
    content.getChildren().add(textArea);
    dialog.getDialogPane().setContent(content);

    ButtonType closeButton = new ButtonType("Fechar", ButtonBar.ButtonData.CANCEL_CLOSE);
    dialog.getDialogPane().getButtonTypes().add(closeButton);

    dialog.showAndWait();
}

    
    public static void main(String[] args) {
        launch(args);
    }

}





