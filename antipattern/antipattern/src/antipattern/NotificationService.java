package antipattern;


public class NotificationService {

    public static void sendBulkNotifications() {
        for (Customer c : DataStore.customers) {
            String msg = generateMessageForCustomer(c);
            // email
            if (c.email != null && !c.email.isEmpty()) {
                // "simulation" d'envoi
                System.out.println("[EMAIL to " + c.email + "] " + msg);
            } else {
                System.out.println("[EMAIL SKIP] client sans email: " + c.name);
            }
            // sms pour certains numéros qui semblent valides
            if (c.phone != null && c.phone.startsWith("+33")) {
                System.out.println("[SMS to " + c.phone + "] " + msg);
            } else {
                System.out.println("[SMS SKIP] numéro invalide: " + c.phone);
            }
            // logique supplémentaire: si client VIP (email contient vip) on fait un envoi spécial
            if (c.email != null && c.email.toLowerCase().contains("vip")) {
                System.out.println("[SPECIAL VIP] message personnalisé pour " + c.name);
            }
        }
    }

    public static String generateMessageForCustomer(Customer c) {
        StringBuilder sb = new StringBuilder();
        sb.append("Bonjour ");
        if (c != null && c.name != null) {
            sb.append(c.name);
        } else {
            sb.append("client");
        }
        sb.append(", ");
        sb.append("merci pour votre confiance. ");
        Order recent = null;
        for (Order o : DataStore.orders) {
            if (o.customerId.equals(c.id)) {
                recent = o;
                break;
            }
        }
        if (recent != null) {
            sb.append("Nous avons enregistré une commande (id=");
            sb.append(recent.id);
            sb.append(") passée le ");
            sb.append(recent.createdAt.toString());
            sb.append(". ");
        } else {
            sb.append("Vous n'avez pas de commande récente. ");
        }
        sb.append("Cordialement, BadShop.");
        return sb.toString();
    }
}
