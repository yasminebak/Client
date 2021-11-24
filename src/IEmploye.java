import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IEmploye extends Remote {
	
	public int getId() throws RemoteException;

	public String getLastname() throws RemoteException;

	public String getFirstname() throws RemoteException;

	public void setPassword(String password) throws RemoteException;

	public boolean verifIdentity(String password) throws RemoteException;
	
}
