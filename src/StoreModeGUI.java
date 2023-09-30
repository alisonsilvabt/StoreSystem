import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.border.EmptyBorder;

public class StoreModeGUI {
    private JButton backButton;
    private JFrame frame;
    private JButton addProductButton;
    private JButton listProduct;
    private JPanel productListPanel;
    private JButton searchButton;
    private JTextField quantityTextField;
    private JTextField searchTextField;
    private JList<String> productList;
    private DefaultListModel<String> productListModel;
    private JLabel priceLabel;
    private Map<String, Product> productMap; // Mapeamento de nomes para produtos


    private List<Product> availableProducts;

    /**
     * This class represents the graphical user interface for the store mode of the application.
     * It contains buttons to add and list products, a product list panel, a search bar, and a cart list.
     * It also has a register product screen for adding new products to the store.
     */
    public StoreModeGUI() {
        frame = new JFrame("Store Mode");
        JFrame registerFrame = new JFrame("Cadastrar produto");
        JFrame listProductFrame = new JFrame("Listar produto");
        JPanel mainScreen = new JPanel(new GridBagLayout());
        JPanel buttonColumn = new JPanel();
        addProductButton = new JButton("Cadastrar produto");
        listProduct = new JButton("Listar produtos");
        productListPanel = new JPanel(new BorderLayout());
        searchButton = new JButton("Search");
        quantityTextField = new JTextField(5);
        searchTextField = new JTextField(15);
        productListModel = new DefaultListModel<>();
        productList = new JList<>(productListModel);
        priceLabel = new JLabel("");
        productMap = new HashMap<>();
        JPanel registerProductScreen = new JPanel();

        availableProducts = new ArrayList<>();
        availableProducts.add(new ProductImpl(1, "Camiseta Nike Dri-FIT", 59.90, "Camiseta", 10));
        availableProducts.add(new ProductImpl(2, "Camiseta Nike Court Dri-FIT", 89.90, "Camiseta", 15));
        availableProducts.add(new ProductImpl(3, "Camiseta Diamond Block Branco", 64.90, "Camiseta", 4));

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchTextField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        productListPanel.add(new JScrollPane(productList), BorderLayout.CENTER);
        productListPanel.add(searchPanel, BorderLayout.NORTH);
        
        backButton = new JButton("Voltar");
        backButton.setPreferredSize(new Dimension(200, 40));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                productListPanel.setVisible(false); // Oculta a lista de produtos
                frame.setVisible(true); // Mostra a tela inicial novamente
            }
        });
        productListPanel.add(backButton, BorderLayout.SOUTH);

        listProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                productListPanel.setVisible(true); // Exibe a lista de produtos
                updateProductList(); // Atualiza a lista de produtos com base na pesquisa
            }
        });

        for (Product product : availableProducts) {
            String productInfo = product.getName() + " - $" + product.getPrice();
            productListModel.addElement(productInfo);
        }

        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateProductList();
            }

            public void removeUpdate(DocumentEvent e) {
                updateProductList();
            }

            public void changedUpdate(DocumentEvent e) {
                updateProductList();
            }
        });

        // Ação ao selecionar um item da lista de produtos (o restante do código permanece inalterado)
        productList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                updatePriceLabel();
            }
        });

        // Ação ao clicar no botão "Add to Cart"
        addProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                registerFrame.setVisible(true);
            }
        });

        // Ação ao clicar no botão "Remove from Cart"
        listProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                listProductFrame.setVisible(true);
                updateProductList();
            }
        });


        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0; // Coluna
        gridBagConstraints.gridy = 0; // Linha
        gridBagConstraints.fill = GridBagConstraints.BOTH; // Preenchimento em ambas as direções
        gridBagConstraints.weightx = 1.0; // Peso horizontal
        gridBagConstraints.weighty = 1.0; // Peso vertical


        Dimension buttonSize = new Dimension(200, 40);
        addProductButton.setPreferredSize(buttonSize);
        listProduct.setPreferredSize(buttonSize);
        listProduct.setMinimumSize(buttonSize);

        buttonColumn.setLayout(new BoxLayout(buttonColumn, BoxLayout.Y_AXIS));
        listProduct.setMargin(new Insets(10, 10, 10, 10));
        JPanel padding = new JPanel();
        padding.setBorder(new EmptyBorder(10, 10, 10, 10));
        buttonColumn.add(addProductButton);
        buttonColumn.add(padding);
        buttonColumn.add(listProduct);
        mainScreen.add(buttonColumn);
 
        // Configuration of Register Product Screen
        registerProductScreen.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
        JPanel registerContent = new JPanel();
        registerContent.setLayout(new BoxLayout(registerContent, BoxLayout.Y_AXIS));
        JLabel registerScreenTitle = new JLabel("Cadastrar produto");
        registerContent.add(registerScreenTitle);
        registerProductScreen.add(registerContent);
        gridBagConstraints2.gridx = 0; // Coluna
        gridBagConstraints2.gridy = 0; // Linha
        gridBagConstraints2.fill = GridBagConstraints.BOTH; // Preenchimento em ambas as direções
        gridBagConstraints2.weightx = 1.0; // Peso horizontal
        gridBagConstraints2.weighty = 1.0; // Peso vertical

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10)); // Layout de grade para organizar os campos

        JLabel nameLabel = new JLabel("Nome do Produto:");
        JTextField nameTextField = new JTextField();

        JLabel quantityLabel = new JLabel("Quantidade:");
        JTextField quantityTextField = new JTextField();

        JLabel codeLabel = new JLabel("Código do Produto:");
        JTextField codeTextField = new JTextField();

        JLabel priceLabel = new JLabel("Preço:");
        JTextField priceTextField = new JTextField();

        formPanel.add(nameLabel);
        formPanel.add(nameTextField);
        formPanel.add(priceLabel);
        formPanel.add(priceTextField);
        formPanel.add(quantityLabel);
        formPanel.add(quantityTextField);
        formPanel.add(codeLabel);
        formPanel.add(codeTextField);


        JButton submitButton = new JButton("Cadastrar"); // Botão para enviar o formulário


        submitButton.addActionListener(e -> {
            try {
                String productName = nameTextField.getText();
                String quantity = quantityTextField.getText();
                String productCode = codeTextField.getText();
                double price = Double.parseDouble(priceTextField.getText());

                if (productName.isEmpty() || quantity.isEmpty() || productCode.isEmpty() || priceTextField.getText().isEmpty()) {
                    throw new Exception("Todos os campos devem ser preenchidos!");
                }

                // verify if product already exists
                for (Product product : availableProducts) {
                    if (product.getId() == (Integer.parseInt(productCode))) {
                        throw new Exception("Produto já cadastrado!");
                    }
                }

                ProductImpl newProduct = new ProductImpl(
                        Integer.parseInt(productCode), productName, 
                        price,"",
                        Integer.parseInt(quantity));
                availableProducts.add(newProduct);

                // Você pode fazer algo com os valores, como exibi-los em uma janela de diálogo
                String message = "Produto cadastrado com sucesso!";
                JOptionPane.showMessageDialog(frame, message);

            }
            catch (Exception ex) {
                String message= "";
                if (ex instanceof NumberFormatException) {
                    message = "Os campos 'Quantidade' e 'Código do Produto' devem ser numéricos!";
                } 
                message = "Ocorreu um erro ao cadastrar o produto!" + "\n" + ex.getMessage();
                JOptionPane.showMessageDialog(frame, message);
                ex.printStackTrace();
            }
        


            // Limpe os campos após o envio
            nameTextField.setText("");
            quantityTextField.setText("");
            codeTextField.setText("");
        });

        // Listar produtos Screen
        registerContent.add(formPanel, BorderLayout.CENTER);
        registerContent.add(Box.createRigidArea(new Dimension(0, 10)));
        registerContent.add(submitButton, BorderLayout.SOUTH);
        
        JButton backToMainScreenButton = new JButton("Voltar");
        backToMainScreenButton.addActionListener((ActionEvent event) -> {
            frame.setVisible(true);
            registerFrame.setVisible(false);
        });

        registerContent.add(backToMainScreenButton);

        frame.add(mainScreen);
        registerFrame.add(registerProductScreen);
        listProductFrame.add(productListPanel);
        
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); 

        registerFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        registerFrame.setLocationRelativeTo(null);

        listProductFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        listProductFrame.setLocationRelativeTo(null);

    }

    

    private void updateProductList() {
        String searchText = searchTextField.getText().toLowerCase();
        productListModel.clear(); // Limpa o modelo da lista

        // Adicione rótulos para Nome, Preço e Quantidade com espaçamento adequado
        String header = String.format("%-40s %-12s %-12s", "Nome", "Preço", "Quantidade");
        productListModel.addElement(header);

        for (Product product : availableProducts) {
            String productName = product.getName().toLowerCase();

            // Verifica se o nome do produto contém o texto de pesquisa
            if (productName.contains(searchText)) {
                // Use formatação para alinhar corretamente os valores nas colunas
                String productInfo = String.format("%-40s $%-12.2f %-12d", product.getName(), product.getPrice(),
                        product.getQuantity());
                productListModel.addElement(productInfo); // Adiciona o produto ao modelo da lista
            }
        }
    }

    // Método para atualizar o rótulo de preço com base no produto selecionado
    private void updatePriceLabel() {
        int selectedIndex = productList.getSelectedIndex();
        if (selectedIndex >= 0) {
            String selectedProductInfo = productListModel.getElementAt(selectedIndex);
            String productName = selectedProductInfo.split(" - ")[0]; // Extrai o nome do produto
            Product selectedProduct = productMap.get(productName);

            // Obtém a quantidade do campo de texto
            int quantity = 0;
            try {
                quantity = Integer.parseInt(quantityTextField.getText());
            } catch (NumberFormatException e) {
                // Lida com a entrada inválida da quantidade (pode ser tratado de acordo com sua lógica)
            }

            double productPrice = selectedProduct.getPrice() * quantity;
            priceLabel.setText("Price: $" + productPrice);
        } else {
            priceLabel.setText(""); // Limpa o rótulo se nenhum produto estiver selecionado
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StoreModeGUI();
            }
        });
    }
}
