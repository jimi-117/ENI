package antipattern;


public class DiscountCalculator {

    public static double computeDiscount(Order o) {
        double raw = o.computeTotalRaw();
        double discount = 0.0;

        // règle 1 : si commande > 1000 et contient casque VR -> 20%
        boolean hasVR = false;
        for (int i = 0; i < o.capacity; i++) {
            if (o.productIds[i] == null) continue;
            Product p = DataStore.findProductById(o.productIds[i]);
            if (p != null && p.name.toLowerCase().contains("vr")) {
                hasVR = true;
            }
        }
        if (raw > 1000 && hasVR) {
            discount = raw * 0.20;
            return discount;
        }

        // règle 2 : si plus de 3 copies du même jeu -> remise par article
        for (int i = 0; i < o.capacity; i++) {
            if (o.productIds[i] == null) continue;
            Product p = DataStore.findProductById(o.productIds[i]);
            if (p != null && p.name.toLowerCase().contains("jeu")) {
                int q = o.quantities[i];
                if (q >= 3) {
                    discount += p.price * 0.05 * q; // 5% par jeu
                }
            }
        }

        // règle 3 : client VIP (déterminé par email contenant vip)
        Customer c = DataStore.findCustomerById(o.customerId);
        if (c != null && c.email != null && c.email.toLowerCase().contains("vip")) {
            discount += 30.0;
        }

        // règle 4 : si total > 300 appliquer palier fixe
        if (raw > 300) {
            discount += 20.0;
        }

        // règle 5 : limit cap
        if (discount > raw * 0.5) {
            discount = raw * 0.5;
        }

        return discount;
    }
}
