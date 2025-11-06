package antipattern;


import java.util.Scanner;

public class OrderManager {

    // liste de méthodes publiques lourdes et redondantes
    public static void listProducts() {
        System.out.println("\n--- Catalogue produits ---");
        for (Product p : DataStore.products) {
            System.out.println(p.id + " | " + p.name + " | " + p.description + " | " + Utils.formatPrice(p.price));
        }
    }

    public static void createCustomerQuick(String name, String email, String phone) {
        String id = DataStore.randomId();
        Customer c = new Customer(id, name, email, phone);
        DataStore.customers.add(c);
        System.out.println("Client créé id=" + id + " nom=" + name);
    }

    public static void createOrderInteractive(String customerId, Scanner sc) {
        Customer c = DataStore.findCustomerById(customerId);
        if (c == null) {
            System.out.println("Client introuvable.");
            return;
        }
        String oid = DataStore.randomId();
        Order order = new Order(oid, customerId);
        boolean adding = true;
        while (adding) {
            System.out.print("Id produit à ajouter (ou ok pour finir): ");
            String pid = sc.nextLine().trim();
            if (pid.equalsIgnoreCase("ok")) break;
            Product p = DataStore.findProductById(pid);
            if (p == null) {
                System.out.println("Produit inconnu.");
                continue;
            }
            System.out.print("Quantité: ");
            String ql = sc.nextLine().trim();
            int q = 1;
            try { q = Integer.parseInt(ql); } catch (Exception e) {}
            order.addItem(p, q);
            System.out.println("Ajouté " + q + " x " + p.name);
        }
        // appliquer réduction "magique" de façon non factorisée
        double raw = order.computeTotalRaw();
        double reduction = 0.0;
        // règles de remise codées ici, mélange de logique et affichage
        if (raw > 500) {
            reduction = raw * 0.10; // 10% si > 500
            System.out.println("Application remise 10% (commande > 500)");
        } else if (raw > 200) {
            reduction = 15.0; // remise fixe 15€
            System.out.println("Application remise fixe 15€ (commande > 200)");
        } else {
            System.out.println("Pas de remise automatique");
        }
        // taxe
        double tva = raw * 0.20;
        double total = raw - reduction + tva;
        // stockage
        DataStore.orders.add(order);
        System.out.println("Commande créée id=" + oid + " total=" + Utils.formatPrice(total));
    }

    public static void printOrderDetails(String orderId) {
        Order o = DataStore.findOrderById(orderId);
        if (o == null) {
            System.out.println("Commande introuvable.");
            return;
        }
        Customer c = DataStore.findCustomerById(o.customerId);
        System.out.println("\n--- Détails commande " + o.id + " ---");
        System.out.println("Client: " + (c != null ? c.name : "inconnu"));
        double raw = 0.0;
        for (int i = 0; i < o.capacity; i++) {
            if (o.productIds[i] == null) continue;
            Product p = DataStore.findProductById(o.productIds[i]);
            if (p == null) continue;
            int q = o.quantities[i];
            System.out.println("- " + p.name + " x" + q + " = " + Utils.formatPrice(p.price * q));
            raw += p.price * q;
        }
        // recompute discount (duplicated logic from createOrderInteractive)
        double discount = 0.0;
        if (raw > 500) {
            discount = raw * 0.10;
        } else if (raw > 200) {
            discount = 15.0;
        }
        double tva = raw * 0.20;
        double total = raw - discount + tva;
        System.out.println("Sous-total: " + Utils.formatPrice(raw));
        System.out.println("Remise: " + Utils.formatPrice(discount));
        System.out.println("TVA: " + Utils.formatPrice(tva));
        System.out.println("Total: " + Utils.formatPrice(total));
        System.out.println("Expédiée: " + (o.shipped ? "oui" : "non"));
        if (o.shipped) {
            System.out.println("Note expédition: " + o.shippingNote);
        }
    }
}
