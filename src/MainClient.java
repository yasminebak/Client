import java.lang.invoke.MethodHandles.Lookup;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

public class MainClient {
	
	static private Map<Integer, List<IProduct>> map;
	static private List<IProduct> listPro;
	
	public static void main(String[] args) throws RemoteException, NotBoundException, UnknownHostException, Exception {
		
		map = new HashMap<Integer, List<IProduct>>();
		listPro = new ArrayList();
		
		String ip = Inet4Address.getLocalHost().getHostAddress();
		if (ip == null || ip == "") {
			ip = "localhost";
		}
		Registry r = LocateRegistry.getRegistry(ip, 1708);
		IManageEmployes employeeManager = (IManageEmployes) r.lookup("//" + ip + "/EmployeeService");
		
		System.out.println("Please enter your ID : ");
		Scanner scID = new Scanner(System.in) ;
		int idClient = scID.nextInt();
		System.out.println("Please enter your password : ");
		Scanner scPassword = new Scanner(System.in);
		String password = scPassword.nextLine();
		boolean b = employeeManager.login(idClient, password);
		if (!b) {
			System.out.println("Wrong id or password");
		} else {
			System.out.println("You successfuly loged in");
			Registry r2 = LocateRegistry.getRegistry(ip, 1709);
			IIfShare service = (IIfShare) r2.lookup("//" + ip + "/IFShareService");	
			List<IProduct> products = service.getAllProduct();
			
			System.out.println("Here are all the products : ");
			for(IProduct p : products) {
				// TODO to delete
				p.setAvailable(true);
				System.out.println("Id Product: " + p.getId() + " type: " + p.getType() + " name: " + p.getName() + " price: " + p.getPrice() + " availible: " + p.isAvailable());
			}
						
			System.out.println("//////////////////////////////////////");
			// TODO
			// verify bank account
			
			System.out.println("Place your order");
			int response = 0;
			String type, nameProduct = null;
			
			// TODO ajouter les cas ou l'user saisi une reponse fausse
			do {
				System.out.println("Please state the type of your order : ");
				Scanner scType = new Scanner(System.in) ;
				type = scType.nextLine();
				System.out.println("Please state the name of the product : ");
				Scanner scName = new Scanner(System.in) ;
				nameProduct = scName.nextLine();
				buyProduct(type, nameProduct, idClient, service, products);
				
				System.out.println("to continue shopping press 1");
				System.out.println("to finalize your order press 2");
				Scanner scResponse = new Scanner(System.in);
				response = scResponse.nextInt();
			} while(response != 2);
			
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			for(Entry<Integer, List<IProduct>> e : map.entrySet()) {
				Integer key = e.getKey();
				List<IProduct> lp =e.getValue();
				for(IProduct p : lp)
				{
					System.out.println("key: " + key + " Id Product: " + p.getId() + " type: " + p.getType() + " name: " + p.getName() + " price: " + p.getPrice() + " availible: " + p.isAvailable());
				}
			}
		}
		
	}
	
	public static void buyProduct(String type, String productName, int idClient, IIfShare service, List<IProduct> products) throws RemoteException {
		
		String result = service.buyProduct(type, productName, idClient);
		boolean b = false;
		
		for(IProduct p : products) {
			if(p.getId().contains(result)) {
				if (map.containsKey(idClient)) {
					map.get(idClient).add(p);
				} else {
					listPro.add(p);
					map.put(idClient, listPro);
				}
				System.out.println("Id Product: " + p.getId() + " type: " + p.getType() + " name: " + p.getName() + " price: " + p.getPrice() + " availible: " + p.isAvailable());
				b = true;
			}
		}
		if(!b) System.out.println(result);
	}
	}

