package antipattern;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataStore {
	public static List<Product> products = new ArrayList<>();
	public static List<Customer> customers = new ArrayList<>();
	public static List<Order> orders = new ArrayList<>();

	public static void initSampleData() {
		// produits
		products.add(new Product("P100", "Console GX", "Console de jeu portable", 299.99));
		products.add(new Product("P101", "Manette Pro", "Manette bluetooth", 59.90));
		products.add(new Product("P102", "Jeu: Space Battles", "Jeu d'action", 49.99));
		products.add(new Product("P103", "Casque VR", "Casque réalité virtuelle", 199.0));
		// clients
		customers.add(new Customer(randomId(), "Alice", "alice@ex.com", "+33123456789"));
		customers.add(new Customer(randomId(), "Bob", "bob@ex.com", "+33699887766"));
		// une commande sample
		Order o = new Order(randomId(), customers.get(0).id);
		o.addItem(products.get(0), 1);
		o.addItem(products.get(2), 2);
		orders.add(o);
	}

	public static String randomId() {
		return UUID.randomUUID().toString().substring(0, 8);
	}

	public static Product findProductById(String id) {
		for (Product p : products) {
			if (p.id.equals(id))
				return p;
		}
		return null;
	}

	public static Customer findCustomerById(String id) {
		for (Customer c : customers) {
			if (c.id.equals(id))
				return c;
		}
		return null;
	}

	public static Order findOrderById(String id) {
		for (Order o : orders) {
			if (o.id.equals(id))
				return o;
		}
		return null;
	}
}
