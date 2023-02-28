package com.codecool.buyourstuff.model;

public class LineItem extends BaseModel {

    private final Product product;
    private final int cartId;
    private int quantity;

    public LineItem(Product product, int cartId, int quantity) {
        this.product = product;
        this.cartId = cartId;
        this.quantity = quantity;
    }

    public String toString() {
        return String.format("%1$s={quantity: %2$d, product: %3$s}",
                getClass().getSimpleName(),
                quantity,
                product
        );
    }

    public Product getProduct() {
        return product;
    }

    public int getCartId() {
        return cartId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
