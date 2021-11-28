import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IManageEmployes extends Remote {
	
	public void addEmploye(int id, String password, String lastname, String firstname)
			throws RemoteException;

	public void deleteEmploye(int id) throws RemoteException;

	public boolean existEmploye(int id) throws RemoteException;

	public List<IEmploye> getAllEmployes() throws RemoteException;

	public IEmploye getEmploye(int id) throws RemoteException;
	
	public IEmploye login(int id, String password) throws RemoteException;
}
