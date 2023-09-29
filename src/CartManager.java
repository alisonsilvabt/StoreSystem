import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private List<CartItem> cartItems; // Uma lista de itens no carrinho

    public CartManager() {
        this.cartItems = new ArrayList<>();
    }

    // Método para adicionar um produto ao carrinho
    public void addItemToCart(Product product, int quantity) {
        CartItem newItem = new CartItem(product, quantity);
        cartItems.add(newItem);
    }

    // Método para remover um produto do carrinho
    public void removeItemFromCart(CartItem item) {
        cartItems.remove(item);
    }

    // Método para calcular o valor total da compra
    public double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (CartItem item : cartItems) {
            totalPrice += item.getSubtotal();
        }
        return totalPrice;
    }

    // Método para confirmar a compra
    public void confirmPurchase() {
        // Implemente a lógica para gerar uma nota fiscal e realizar a transação
        // Pode incluir atualizações de estoque, registro de compra, etc.
    }

    // Outros métodos relacionados ao gerenciamento do carrinho, como listar itens, esvaziar o carrinho, etc.
}
