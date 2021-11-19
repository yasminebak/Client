import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class MainClient {

	public static void main(String[] args) throws RemoteException, NotBoundException {
		if (args.length < 2) {
			System.out.println("Missing arguments");
			System.exit(1);
		}
		int id = Integer.valueOf(args[0]);
		String password = args[1];

		IClient client = new Client(id, password);

		String ip = "localhost";

		Registry r = LocateRegistry.getRegistry(ip, 1706);
		IApplicationShareProducts buyProduct = (IApplicationShareProducts) r.lookup("//" + ip + "/ApplicationShareProductServices");
		System.out.println("Hello ! ");
		

		System.out.println(buyProduct.subscribe(client));
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			String line = sc.nextLine();
			String[] decomposeLine = line.split(" ");
			if (line.startsWith("products")) {
				System.out.println(buyProduct.getAvailableProducts());

			} else if (line.startsWith("all products")) {
				System.out.println(buyProduct.getAllProducts());

			

			} else if (line.startsWith("type")) {
				String type = line.split(" ")[1];
				System.out.println(buyProduct.getTypeProducts(type));

			
			
			} else {
				System.out.println("Unknown command. Please retry");
			}
		}
		sc.close();

	}
	}

