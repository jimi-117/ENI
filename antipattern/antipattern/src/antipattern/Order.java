package antipattern;

import java.util.Date;
import java.util.Arrays;

public class Order {
    public String id;
    public String customerId;
    public String[] productIds; 
    public int[] quantities;
    public int capacity = 4; // capacité initiale
    public Date createdAt;
    public boolean shipped = false;
    public String shippingNote = "";

    public Order(String id, String customerId) {
        this.id = id;
        this.customerId = customerId;
        this.productIds = new String[capacity];
        this.quantities = new int[capacity];
        this.createdAt = new Date();
    }

    public void addItem(Product p, int qty) {
        // chercher première case vide
        for (int i = 0; i < capacity; i++) {
            if (productIds[i] == null) {
                productIds[i] = p.id;
                quantities[i] = qty;
                return;
            }
        }
        // si plein, on "ré-alloue" à la main
        int newCap = capacity * 2;
        String[] newP = new String[newCap];
        int[] newQ = new int[newCap];
        // copie manuelle
        for (int i = 0; i < capacity; i++) {
            newP[i] = productIds[i];
            newQ[i] = quantities[i];
        }
        // remplir
        newP[capacity] = p.id;
        newQ[capacity] = qty;
        productIds = newP;
        quantities = newQ;
        capacity = newCap;
    }

    public double computeTotalRaw() {
        double sum = 0.0;
        for (int i = 0; i < capacity; i++) {
            if (productIds[i] == null) continue;
            Product p = DataStore.findProductById(productIds[i]);
            if (p != null) {
                sum += p.price * quantities[i];
            }
        }
        return sum;
    }

    public String toString() {
        return "Order#" + id + " customer=" + customerId + " items=" + Arrays.toString(productIds);
    }
}
