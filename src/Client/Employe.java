package Client;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import Common.IEmploye;
import Common.IProduct;

@SuppressWarnings("serial")
public class Employe extends UnicastRemoteObject implements IEmploye {

	private int id;
	private String password;
	private String lastname;
	private String firstname;
	private Map<IEmploye, List<IProduct>> buyMap;
	
	public Employe() throws RemoteException {

	}

	public Employe(int id, String password, String lastname, String firstname) throws RemoteException {
		Objects.requireNonNull(password);
		Objects.requireNonNull(lastname);
		Objects.requireNonNull(firstname);

		if (id < 0) {
			throw new IllegalArgumentException("id can't be negative !");
		}

		this.id = id;
		this.password = password;
		this.lastname = lastname;
		this.firstname = firstname;
		this.buyMap = new HashMap<IEmploye, List<IProduct>>();
	}

	@Override
	public int getId() throws RemoteException {
		return id;
	}

	@Override
	public String getLastname() throws RemoteException {
		return lastname;
	}

	@Override
	public String getFirstname() throws RemoteException {
		return firstname;
	}

	@Override
	public void setPassword(String password) throws RemoteException {
		Objects.requireNonNull(password);
		this.password = password;
	}
	
	@Override
	public Map<IEmploye, List<IProduct>> getBuyMap() throws RemoteException {
		return this.buyMap;
	}

	@Override
	public void setBuyMap(Map<IEmploye, List<IProduct>> buyMap) throws RemoteException {
		this.buyMap = buyMap;
	}
	

	@Override
	public boolean verifIdentity(String password) throws RemoteException {
		Objects.requireNonNull(password);
		if (this.password.equals(password)) {
			return true;
		}
		return false;
	}

	public String toString() {
		return "ID : " + id + " Lastname : " + lastname + " Firstname : " + firstname;
	}

	@Override
	public void notifyEmployee(IProduct product) throws RemoteException {
		System.out.println("this is the new availible product "+ product.getId());
		buyProduct(product);
	}
	
	public void buyProduct(IProduct product) {
		if (!buyMap.isEmpty()) {
			buyMap.get(this).add(product);
		} else {
			List<IProduct> list = new ArrayList<IProduct>();
			list.add(product);
			buyMap.put(this, list);
		}
		
	}

}
