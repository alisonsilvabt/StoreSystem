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

public class StoreModeGUI {
    private JFrame frame;
    private JPanel panel;
    private JButton addButton;
    private JButton removeButton;
    private JTextArea cartTextArea;
    private JLabel titleLabel;
    private JLabel productLabel;
    private JLabel quantityLabel;
    private JTextField quantityTextField;
    private JTextField searchTextField;
    private JList<String> productList;
    private DefaultListModel<String> productListModel;
    private JLabel priceLabel;
    private JList<String> cartList; // Lista para o carrinho de compras
    private DefaultListModel<String> cartListModel; // Modelo da lista
    private Map<String, Product> productMap; // Mapeamento de nomes para produtos
    private Map<String, Integer> cartContents; // Conteúdo do carrinho (nome do produto e quantidade)
    private double totalPrice;

    private List<Product> availableProducts;

    public StoreModeGUI() {
        frame = new JFrame("Store Mode");
        panel = new JPanel();
        addButton = new JButton("Add to Cart");
        removeButton = new JButton("Remove from Cart");
        cartTextArea = new JTextArea(10, 30);
        titleLabel = new JLabel("Welcome to Our Store!");
        productLabel = new JLabel("Product:");
        quantityLabel = new JLabel("Quantity:");
        quantityTextField = new JTextField(5);
        searchTextField = new JTextField(15);
        productListModel = new DefaultListModel<>();
        productList = new JList<>(productListModel);
        priceLabel = new JLabel("");
        cartListModel = new DefaultListModel<>(); // Modelo da lista para o carrinho
        cartList = new JList<>(cartListModel);
        productMap = new HashMap<>();
        cartContents = new HashMap<>();
        totalPrice = 0.0;

        availableProducts = new ArrayList<>();
        availableProducts.add(new ProductImpl(1, "Product 1", 10.0, "Description 1"));
        availableProducts.add(new ProductImpl(2, "Product 2", 20.0, "Description 2"));
        availableProducts.add(new ProductImpl(3, "Product 3", 30.0, "Description 3"));

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
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedProductInfo = productList.getSelectedValue();
                if (selectedProductInfo != null) {
                    Product selectedProduct = productMap.get(selectedProductInfo);
                    int quantity = Integer.parseInt(quantityTextField.getText());

                    // Adicione o produto ao carrinho
                    addToCart(selectedProduct, quantity);

                    // Atualize a lista do carrinho
                    updateCartList();

                    // Atualize o preço total
                    updateTotalPrice();
                }
            }
        });

        // Ação ao clicar no botão "Remove from Cart"
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedCartItem = cartList.getSelectedValue();
                if (selectedCartItem != null) {
                    // Remova o produto do carrinho
                    removeFromCart(selectedCartItem);

                    // Atualize a lista do carrinho
                    updateCartList();

                    // Atualize o preço total
                    updateTotalPrice();
                }
            }
        });

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
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


    private void updateProductList() {
        String searchText = searchTextField.getText().toLowerCase();
        productListModel.clear(); // Limpa o modelo da lista

        for (Product product : availableProducts) {
            String productName = product.getName().toLowerCase();

            // Verifica se o nome do produto contém o texto de pesquisa
            if (productName.contains(searchText)) {
                String productInfo = product.getName() + " - $" + product.getPrice();
                productListModel.addElement(productInfo); // Adiciona o produto ao modelo da lista
            }
        }
    }

            // Método para adicionar um produto ao carrinho
    private void addToCart(Product product, int quantity) {
        String productName = product.getName();
        if (cartContents.containsKey(productName)) {
            int existingQuantity = cartContents.get(productName);
            cartContents.put(productName, existingQuantity + quantity);
        } else {
            cartContents.put(productName, quantity);
        }
    }

    // Método para remover um produto do carrinho
    private void removeFromCart(String cartItem) {
        cartContents.remove(cartItem);
    }

    // Método para atualizar a lista do carrinho
    private void updateCartList() {
        cartListModel.clear();
        for (Map.Entry<String, Integer> entry : cartContents.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            String cartItemInfo = productName + " - Quantity: " + quantity + " - Total Price: $" + (productMap.get(productName).getPrice() * quantity);
            cartListModel.addElement(cartItemInfo);
        }
    }

    // Método para atualizar o preço total
    private void updateTotalPrice() {
        totalPrice = 0.0;
        for (Map.Entry<String, Integer> entry : cartContents.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            totalPrice += productMap.get(productName).getPrice() * quantity;
        }
        // Atualize o rótulo de preço total
        priceLabel.setText("Total Price: $" + totalPrice);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StoreModeGUI();
            }
        });
    }
}
