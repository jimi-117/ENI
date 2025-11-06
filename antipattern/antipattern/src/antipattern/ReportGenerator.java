package antipattern;


import java.io.FileWriter;
import java.io.IOException;

public class ReportGenerator {

    public static void generateAllOrdersReport(String filename) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Rapport commandes BadShop ===\n\n");
        for (Order o : DataStore.orders) {
            Customer c = DataStore.findCustomerById(o.customerId);
            sb.append("Commande: " + o.id + "\n");
            sb.append("Client: " + (c != null ? c.name : "inconnu") + "\n");
            double raw = 0.0;
            for (int i = 0; i < o.capacity; i++) {
                if (o.productIds[i] == null) continue;
                Product p = DataStore.findProductById(o.productIds[i]);
                if (p == null) continue;
                int q = o.quantities[i];
                sb.append("  - " + p.name + " x" + q + " => " + Utils.formatPrice(p.price * q) + "\n");
                raw += p.price * q;
            }
            double discount = DiscountCalculator.computeDiscount(o); // separated but used here
            double tva = raw * 0.20;
            double total = raw - discount + tva;
            sb.append("Sous-total : " + Utils.formatPrice(raw) + "\n");
            sb.append("Remise : " + Utils.formatPrice(discount) + "\n");
            sb.append("TVA : " + Utils.formatPrice(tva) + "\n");
            sb.append("Total : " + Utils.formatPrice(total) + "\n");
            sb.append("\n");
        }

        try (FileWriter fw = new FileWriter(filename + ".txt")) {
            fw.write(sb.toString());
            System.out.println("Rapport écrit : " + filename + ".txt");
        } catch (IOException e) {
            System.out.println("Erreur écriture rapport: " + e.getMessage());
        }
    }
}
