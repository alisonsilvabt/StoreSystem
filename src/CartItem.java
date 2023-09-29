public class CartItem {
    private Product product; // O produto associado ao item do carrinho
    private int quantity; // A quantidade desse produto no carrinho

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Método para obter o produto associado a este item do carrinho
    public Product getProduct() {
        return product;
    }

    // Método para obter a quantidade deste produto no carrinho
    public int getQuantity() {
        return quantity;
    }

    // Método para calcular o subtotal deste item (preço do produto multiplicado pela quantidade)
    public double getSubtotal() {
        return product.getPrice() * quantity;
    }

    // Outros métodos relacionados a itens do carrinho, se necessário
}
