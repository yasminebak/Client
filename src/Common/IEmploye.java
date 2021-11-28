package Common;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface IEmploye extends Remote {
	
	public int getId() throws RemoteException;

	public String getLastname() throws RemoteException;

	public String getFirstname() throws RemoteException;

	public void setPassword(String password) throws RemoteException;
	
	public Map<IEmploye, List<IProduct>> getBuyMap() throws RemoteException;
	
	public void setBuyMap(Map<IEmploye, List<IProduct>> buyMap) throws RemoteException;

	public boolean verifIdentity(String password) throws RemoteException;
	
	public void notifyEmployee(IProduct product) throws RemoteException;
	
}
