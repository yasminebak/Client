import java.rmi.RemoteException;

public interface IApplicationShareProducts {
	public String subscribe(IClient client) throws RemoteException;

	public String getAllProducts() throws RemoteException;

	public String getAvailableProducts() throws RemoteException;

	public String getTypeProducts(String type) throws RemoteException;

	public String buyProduct(String matricule, IClient client) throws RemoteException;

}
