package Common;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface IIfShare extends Remote {
	public IProduct getProduct(String id) throws RemoteException;

	public List<IProduct> getAllProduct() throws RemoteException;
	
	public List<IProduct> getSoldProduct() throws RemoteException;

	public void addProduct(String type, String nameProduct, float price) throws RemoteException;

	public void delete(String id) throws RemoteException;
	
	public List<IProduct> lookProductType(String type) throws RemoteException;
	
	public List<IProduct> lookProductTypeName(String type, String nameProduct) throws RemoteException;

	//getAvailableproduct c'est les produit disponibles achetable
	public List<IProduct> getAvailableProduct() throws RemoteException;
	
	public String buyProduct(String id, IEmploye employee) throws RemoteException;
	
	public void setFifo() throws RemoteException;
	
	public IProduct sellProduct(String id, float note, String state, float price) throws RemoteException;
	
	/*
	public void setAvailableProduct(String id, boolean bool) throws RemoteException;
	
	public String getStatProduct(String id) throws RemoteException;

	public void setStatProduct(String id, String statProduct) throws RemoteException;
	*/	

}