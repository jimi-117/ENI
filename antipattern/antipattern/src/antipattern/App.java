package antipattern;


import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("=== BadShop Console v0.1 (anti-pattern demo) ===");
        Scanner sc = new Scanner(System.in);
        DataStore.initSampleData();

        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1) Lister produits");
            System.out.println("2) Créer client");
            System.out.println("3) Créer commande");
            System.out.println("4) Afficher commande");
            System.out.println("5) Envoyer notifications");
            System.out.println("6) Générer rapport");
            System.out.println("0) Quitter");
            System.out.print("Choix: ");
            String line = sc.nextLine().trim();
            try {
                int choice = Integer.parseInt(line);
                switch (choice) {
                    case 1:
                        OrderManager.listProducts();
                        break;
                    case 2:
                        System.out.print("Nom du client: ");
                        String name = sc.nextLine();
                        System.out.print("Email: ");
                        String email = sc.nextLine();
                        System.out.print("Téléphone: ");
                        String phone = sc.nextLine();
                        OrderManager.createCustomerQuick(name, email, phone);
                        break;
                    case 3:
                        System.out.print("Id client: ");
                        String cid = sc.nextLine();
                        OrderManager.createOrderInteractive(cid, sc);
                        break;
                    case 4:
                        System.out.print("Id commande: ");
                        String oid = sc.nextLine();
                        OrderManager.printOrderDetails(oid);
                        break;
                    case 5:
                        System.out.println("Envoi massif (email + sms)...");
                        NotificationService.sendBulkNotifications();
                        break;
                    case 6:
                        System.out.print("Nom fichier rapport: ");
                        String fname = sc.nextLine();
                        ReportGenerator.generateAllOrdersReport(fname);
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Choix inconnu.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide.");
            } catch (Exception e) {
                System.out.println("Erreur: " + e.getMessage());
                e.printStackTrace();
            }
        }

        sc.close();
        System.out.println("Bye.");
    }
}
