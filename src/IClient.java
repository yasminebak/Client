import java.rmi.Remote;
import java.rmi.RemoteException;



public interface IClient extends Remote {
	public void notifyAvailableProduct(String notification) throws RemoteException;

	public int getClientId() throws RemoteException;

	public String getClientPassword() throws RemoteException;
}
