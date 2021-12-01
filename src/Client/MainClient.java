package Client;
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

import Common.IEmploye;
import Common.IIfShare;
import Common.IManageEmployes;
import Common.IProduct;

public class MainClient {
	
	static private Map<IEmploye, List<IProduct>> buyMap;
	static private List<IProduct> listPro;
	static private IEmploye employee = null;
	static private int loginAttempt = 3;
	static private IManageEmployes employeManager;
	
	public static void main(String[] args) throws RemoteException, NotBoundException, UnknownHostException, Exception {

		listPro = new ArrayList<IProduct>();
		employeManager = new ManageEmploye();
		
		employeManager.addEmploye(123, "password123", "user1", "firstname1");
		employeManager.addEmploye(234, "password145", "user2", "firstname2");
		employeManager.addEmploye(356, "password465", "user3", "firstname3");
		employeManager.addEmploye(843, "password656", "user5", "firstname5");
		employeManager.addEmploye(2135, "password644", "user4", "firstname4");
				
		// login
		IEmploye employee;
		try {
			employee = login();
			if (employee == null) {
				System.out.println("Sorry! You have reached the maximum of tries");
			} else {
				System.out.println("You successfuly logged in");
				System.out.println(employee.getFirstname() + " "+ employee.getLastname() + " " + employee.getId() + " " + employee.getBuyMap());
				buyMap = employee.getBuyMap();
				// connection to IfShare service
				String ip = Inet4Address.getLocalHost().getHostAddress();
				if (ip == null || ip == "") {
					ip = "localhost";
				}
				Registry r2 = LocateRegistry.getRegistry(ip, 1709);
				IIfShare service = (IIfShare) r2.lookup("//" + ip + "/IFShareService");	
				
				// get all the products from IfShare
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
				
				// buy a product
				System.out.println("Place your order");
				placeOrder(service, products);
								
				// Option menu
				optionMenu("buy", service, products);
				
			    // the client's products he bought
				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				for(Entry<IEmploye, List<IProduct>> e : buyMap.entrySet()) {
					IEmploye key = e.getKey();
					List<IProduct> lp =e.getValue();
					for(IProduct p : lp)
					{
						System.out.println("key: " + key.getId() + " Id Product: " + p.getId() + " type: " + p.getType() + " name: " + p.getName() + " price: " + p.getPrice() + " availible: " + p.isAvailable());
					}
				}
				
				System.out.println("/////////////////////////////");
				sellProduct(service);
				// Option menu
				// TODO offer the option only when the buyMap is not empty
				optionMenu("sell", service, products);
				
				// the client's bought product list
				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				for(Entry<IEmploye, List<IProduct>> e : buyMap.entrySet()) {
					IEmploye key = e.getKey();
					List<IProduct> lp =e.getValue();
					for(IProduct p : lp)
					{
						System.out.println("key: " + key.getId() + " Id Product: " + p.getId() + " type: " + p.getType() + " name: " + p.getName() + " price: " + p.getPrice() + " availible: " + p.isAvailable());
					}
				}
				
				System.out.println("**********************************");
				// get all the products from IfShare
				System.out.println("Here are all the products : ");
				for(IProduct p : products) {
					System.out.println("Id Product: " + p.getId() + " type: " + p.getType() + " name: " + p.getName() + " price: " + p.getPrice() + " availible: " + p.isAvailable() + " note: " + p.getNote() + " state: " + p.getState());
				}
				
				service.setFifo();
			}
			
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}
	
	public static IEmploye login() throws RemoteException {
		while(loginAttempt !=0 && employee == null) {
			System.out.println("Please enter your ID : ");
			Scanner scanner = new Scanner(System.in) ;
			int idClient = scanner.nextInt();
			System.out.println("Please enter your password : ");
			scanner = new Scanner(System.in);
			String password = scanner.nextLine();
			employee = employeManager.login(idClient, password);
			loginAttempt--;
			if (employee == null) {
				System.out.println("Wrong id or password, you have " + loginAttempt + " more tries");
			}
		}
		return employee;
	}
	
	public static void optionMenu(String action, IIfShare service, List<IProduct> products) throws RemoteException{
		boolean keepGoing = true;
		int response = 0;
		while (keepGoing) {
	    	System.out.println("Please select an option from the menu:");
		    System.out.println("1. Continue");
		    System.out.println("2. Finish");
			Scanner scanner = new Scanner(System.in);
			response = scanner.nextInt();
		    switch (response) {
		    	case 1:
		    		if (action.equals("buy")) {
		    			placeOrder(service, products);
		    		} else {
		    			sellProduct(service);
		    		}
		    		break;
		        case 2:
		        	keepGoing = false;
		            break;
		        default:
		            System.out.println("Unknown option");
		            break;
		    }
	    }
	}
	
	public static void placeOrder(IIfShare service, List<IProduct> products) throws RemoteException {
		System.out.println("Please select the product's id that you want to sell");
		Scanner scanner = new Scanner(System.in) ;
		String id = scanner.nextLine();
		buyProduct(id, service, products);
	}
	
	public static void buyProduct(String id, IIfShare service, List<IProduct> products) throws RemoteException {
		
		String result = service.buyProduct(id, employee);
		boolean b = false;
		
		for(IProduct p : products) {
			if (buyMap.isEmpty()) {
				if (p.getId().equals(result)) {
					listPro.add(p);
					buyMap.put(employee, listPro);
					System.out.println("Id Product: " + p.getId() + " type: " + p.getType() + " name: " + p.getName() + " price: " + p.getPrice() + " availible: " + p.isAvailable());
					b = true;
				}
			} else {
				if(p.getId().equals(result)) {
					if (buyMap.containsKey(employee)) {
						buyMap.get(employee).add(p);
					} else {
						listPro.add(p);
						buyMap.put(employee, listPro);
					}
					System.out.println("Id Product: " + p.getId() + " type: " + p.getType() + " name: " + p.getName() + " price: " + p.getPrice() + " availible: " + p.isAvailable());
					b = true;
				}
			}
			
		}
		if(!b) System.out.println(result);
	}
	
	public static void sellProduct(IIfShare service) throws RemoteException {
		
		System.out.println("Please select the product's id that you want to sell");
		Scanner scanner = new Scanner(System.in);
		String idProduct = scanner.nextLine();
		System.out.println("Give a rating to this product between 0 to 5");
		scanner = new Scanner(System.in);
		float note = scanner.nextFloat();
		System.out.println("What is the state of this product?");
		scanner = new Scanner(System.in);
		String state = scanner.nextLine();
		System.out.println("How much are you selling this product?");
		scanner = new Scanner(System.in);
		float price = scanner.nextFloat();
		IProduct p = service.sellProduct(idProduct, note, state, price);
		if (p != null) {
			// remove the sold product from the client's list
			buyMap.get(employee).remove(p);
		} else {
			System.out.println("Couldn't sell this product!");
		}
	}
	}

