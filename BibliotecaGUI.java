import java.time.LocalDate; // Importação da classe LocalDate para lidar com datas
import java.util.ArrayList; // Importação da classe ArrayList para armazenar os livros
import java.util.List; // Importação da interface List para manipular listas
import java.util.Optional; // Importação da classe Optional para tratamento de valores opcionais
import java.time.Year; // Importação da classe Year para obter o ano atual

import javafx.application.Application; // Importação da classe Application para criar aplicativos JavaFX
import javafx.geometry.Insets; // Importação da classe Insets para definir margens em layouts
import javafx.scene.Scene; // Importação da classe Scene para representar uma cena gráfica
import javafx.scene.control.Alert; // Importação da classe Alert para exibir caixas de diálogo de alerta
import javafx.scene.control.Button; // Importação da classe Button para criar botões
import javafx.scene.control.ButtonBar; // Importação da classe ButtonBar para definir botões em uma barra
import javafx.scene.control.ButtonType; // Importação da classe ButtonType para definir tipos de botões
import javafx.scene.control.ChoiceDialog; // Importação da classe ChoiceDialog para exibir uma lista de escolha
import javafx.scene.control.ComboBox; // Importação da classe ComboBox para criar menus de seleção
import javafx.scene.control.DatePicker; // Importação da classe DatePicker para seleção de datas
import javafx.scene.control.Dialog; // Importação da classe Dialog para criar diálogos personalizados
import javafx.scene.control.Label; // Importação da classe Label para criar rótulos
import javafx.scene.control.Menu; // Importação da classe Menu para criar menus
import javafx.scene.control.MenuBar; // Importação da classe MenuBar para criar barras de menu
import javafx.scene.control.MenuItem; // Importação da classe MenuItem para criar itens de menu
import javafx.scene.control.TextArea; // Importação da classe TextArea para criar áreas de texto
import javafx.scene.control.TextField; // Importação da classe TextField para criar campos de texto
import javafx.scene.control.TextFormatter; // Importação da classe TextFormatter para formatar texto
import javafx.scene.layout.BorderPane; // Importação da classe BorderPane para criar layouts de contêiner
import javafx.scene.layout.VBox; // Importação da classe VBox para criar layouts de contêiner verticais
import javafx.stage.Stage; // Importação da classe Stage para representar uma janela de aplicativo


public class BibliotecaGUI extends Application {
    private List<Livro> biblioteca = new ArrayList<>(); // Lista para armazenar os livros da biblioteca

    @Override
    public void start(Stage primaryStage) {
        // Configuração do layout principal usando BorderPane
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10)); // Adiciona um espaço de preenchimento de 10 pixels em todos os lados do layout

        // Criação da barra de menu
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Opções");

        // Criação dos itens de menu e atribuição de ações aos eventos
        MenuItem adicionarMenuItem = new MenuItem("Adicionar Livro");
        adicionarMenuItem.setOnAction(e -> adicionarLivro(primaryStage));
        MenuItem removerMenuItem = new MenuItem("Remover Livro");
        removerMenuItem.setOnAction(e -> removerLivro(primaryStage));
        MenuItem pesquisarMenuItem = new MenuItem("Pesquisar Livro");
        pesquisarMenuItem.setOnAction(e -> pesquisarLivro(primaryStage));
        MenuItem gerirMenuItem = new MenuItem("Gerir Empréstimos");
        gerirMenuItem.setOnAction(e -> gerirEmprestimos(primaryStage));
        MenuItem categorizarMenuItem = new MenuItem("Categorizar Livros");
        categorizarMenuItem.setOnAction(e -> categorizarLivros(primaryStage));

        // Adiciona os itens de menu ao menu principal
        menu.getItems().addAll(adicionarMenuItem, removerMenuItem, pesquisarMenuItem, gerirMenuItem, categorizarMenuItem);
        menuBar.getMenus().add(menu); // Adiciona o menu à barra de menu
        root.setTop(menuBar); // Define a barra de menu como o componente superior do layout

        // Configuração do layout dos botões
        VBox buttonsBox = new VBox(10); // VBox para organizar os botões verticalmente, com um espaço de 10 pixels entre eles
        buttonsBox.setPadding(new Insets(10)); // Adiciona um espaço de preenchimento de 10 pixels em todos os lados do VBox

        // Criação dos botões e atribuição de ações aos eventos
        Button adicionarButton = new Button("Adicionar Livro");
        adicionarButton.setOnAction(e -> adicionarLivro(primaryStage));
        adicionarButton.getStyleClass().add("button"); // Adiciona uma classe CSS aos botões para estilização

        Button removerButton = new Button("Remover Livro");
        removerButton.setOnAction(e -> removerLivro(primaryStage));
        removerButton.getStyleClass().add("button");

        Button pesquisarButton = new Button("Pesquisar Livro");
        pesquisarButton.setOnAction(e -> pesquisarLivro(primaryStage));
        pesquisarButton.getStyleClass().add("button");

        Button gerirEmprestimosButton = new Button("Gerir Empréstimos");
        gerirEmprestimosButton.setOnAction(e -> gerirEmprestimos(primaryStage));
        gerirEmprestimosButton.getStyleClass().add("button");

        Button categorizarButton = new Button("Categorizar Livros");
        categorizarButton.setOnAction(e -> categorizarLivros(primaryStage));
        categorizarButton.getStyleClass().add("button");

        // Adiciona os botões ao VBox
        buttonsBox.getChildren().addAll(adicionarButton, removerButton, pesquisarButton, gerirEmprestimosButton, categorizarButton);
        root.setCenter(buttonsBox); // Define o VBox como o componente central do layout

        // Configuração da cena principal
        Scene scene = new Scene(root, 400, 300); // Cria uma cena com o layout principal, de largura 400 e altura 300
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm()); // Adiciona uma folha de estilo CSS à cena
        primaryStage.setTitle("Sistema de Gerenciamento de Biblioteca"); // Define o título da janela
        primaryStage.setScene(scene); // Define a cena como a cena principal da janela
        primaryStage.show(); // Exibe a janela
    }
    
    private void adicionarLivro(Stage stage) {
        // Criação do diálogo para adicionar um novo livro
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Adicionar Livro");
        dialog.setHeaderText("Introduza os Detalhes do Livro:");
    
        // Definição dos campos para inserir os detalhes do livro
        Label titleLabel = new Label("Título:");
        TextField titleField = new TextField();
        titleField.setPromptText("Introduza o Título do Livro");
    
        Label authorLabel = new Label("Autor:");
        TextField authorField = new TextField();
        authorField.setPromptText("Introduza o Nome do Autor");
    
        // Restrição para permitir apenas caracteres não numéricos no campo de autor
        authorField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[^\\d]*")) {
                return change;
            }
            return null;
        }));
    
        Label yearLabel = new Label("Ano de Publicação:");
        TextField yearField = new TextField();
        yearField.setPromptText("Apenas caracteres numéricos");
    
        // Restrição para permitir apenas números no campo de ano de publicação e até 4 dígitos
        int currentYear = Year.now().getValue();
        yearField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,4}")) {
                return change;
            }
            return null;
        }));
    
        Label genreLabel = new Label("Gênero:");
        ComboBox<String> genreComboBox = new ComboBox<>();
        genreComboBox.setPromptText("Selecione um estilo...");
        genreComboBox.getItems().addAll("Ação", "Romance", "Terror", "Poesia", "Mistério");
    
        Label typeLabel = new Label("Tipo:");
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.setPromptText("Selecione uma versão ...");
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
                    if (year < 0 || year > currentYear) {
                        throw new NumberFormatException("Ano inválido");
                    }
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
                    // Exibe um alerta de erro se os campos de ano e quantidade não forem preenchidos corretamente
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
    
        dialog.showAndWait(); // Exibe o diálogo e espera pelo resultado
    }
    

    private void removerLivro(Stage stage) {
        // Criação do diálogo para remover um livro
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Remover Livro");
        dialog.setHeaderText("Selecione o título do livro que deseja remover:");
    
        // ComboBox para selecionar o título do livro
        ComboBox<String> titleComboBox = new ComboBox<>();
        titleComboBox.setPromptText("Selecione um livro..."); // Texto fantasma
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
                    // Separa o título e o tipo do livro
                    String[] parts = selectedItem.split(" \\(");
                    String title = parts[0];
                    String type = parts[1].substring(0, parts[1].length() - 1); // Remove the closing parenthesis
    
                    // Remove o livro da biblioteca
                    biblioteca.removeIf(livro -> livro.getTitulo().equals(title) && livro.getTipo().equals(type));
                    return "removido";
                }
            }
            return null;
        });
    
        dialog.showAndWait(); // Exibe o diálogo e espera pelo resultado
    }
    

    private void pesquisarLivro(Stage stage) {
        // Criação do diálogo para pesquisar um livro
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Pesquisar Livro");
        dialog.setHeaderText("Selecione o título do livro que deseja pesquisar:");
    
        // ComboBox para selecionar o título do livro
        ComboBox<String> titleComboBox = new ComboBox<>();
        titleComboBox.setPromptText("Selecione um livro..."); // Texto fantasma
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
                    // Separa o título e o tipo do livro
                    String[] parts = selectedItem.split(" \\(");
                    String title = parts[0];
                    String type = parts[1].substring(0, parts[1].length() - 1); // Remove the closing parenthesis
    
                    // Lista para armazenar os livros encontrados
                    List<Livro> livrosEncontrados = new ArrayList<>();
                    // Percorre a biblioteca em busca do livro com o título e tipo selecionados
                    for (Livro livro : biblioteca) {
                        if (livro.getTitulo().equals(title) && livro.getTipo().equals(type)) {
                            livrosEncontrados.add(livro);
                        }
                    }
    
                    // Cria um alerta para exibir os resultados da pesquisa
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
                    alert.showAndWait(); // Exibe o alerta com os resultados da pesquisa
                    return "pesquisado";
                }
            }
            return null;
        });
    
        dialog.showAndWait(); // Exibe o diálogo e aguarda o resultado
    }
    

private void gerirEmprestimos(Stage stage) {
    // Diálogo para gerir empréstimos
    ChoiceDialog<String> dialog = new ChoiceDialog<>("Registrar Empréstimo", "Registrar Empréstimo", "Devolver Livro");
    dialog.setTitle("Gerir Empréstimos");
    dialog.setHeaderText("Escolha uma opção:");

    Optional<String> result = dialog.showAndWait();
    result.ifPresent(option -> {
        if (option.equals("Registrar Empréstimo")) {
            // Implementação do registro de empréstimo
            Dialog<String> emprestimoDialog = new Dialog<>();
            emprestimoDialog.setTitle("Registrar Empréstimo");
            emprestimoDialog.setHeaderText("Digite os detalhes do empréstimo:");

            // Campos para inserir os detalhes do empréstimo
            Label userLabel = new Label("Utilizador:");
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

                    // Remover o livro da lista de categorias
                    removerLivroDaLista(bookComboBox, bookTitle);

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
            Label userLabel = new Label("Utilizador:");
            TextField userField = new TextField();

            Label bookLabel = new Label("Livro:");
            ComboBox<String> bookComboBox = new ComboBox<>();
            for (Livro livro : biblioteca) {
                bookComboBox.getItems().add(livro.getTitulo() + " (" + livro.getTipo() + ")");
            }

            ButtonType confirmButton = new ButtonType("Confirmar", ButtonBar.ButtonData.OK_DONE);
            devolucaoDialog.getDialogPane().getButtonTypes().addAll(confirmButton, ButtonType.CANCEL);

            VBox content = new VBox(10);
            content.getChildren().addAll(userLabel, userField, bookLabel, bookComboBox);
            content.setPadding(new Insets(10));
            devolucaoDialog.getDialogPane().setContent(content);

            devolucaoDialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmButton) {
                    String user = userField.getText();
                    String bookTitle = bookComboBox.getValue();
                    // Lógica para devolver o livro
                    devolverLivro(user, bookTitle);

                    // Remover o livro da lista de categorias
                    removerLivroDaLista(bookComboBox, bookTitle);

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
private void registrarEmprestimo(String user, String bookTitle, LocalDate date) { // Implementação do registro de empréstimo
}

private void devolverLivro(String user, String bookTitle) { // Implementação da devolução de livro
}

private void removerLivroDaLista(ComboBox<String> bookComboBox, String bookTitle) {
    bookComboBox.getItems().remove(bookTitle);
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





