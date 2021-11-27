import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IProduct extends Remote {
	public String getId() throws RemoteException;

	public String getType() throws RemoteException;

	public void setType(String type) throws RemoteException;

	public String getName() throws RemoteException;

	public float getPrice() throws RemoteException;

	public void setPrice(float price) throws RemoteException;
	
	public float getNote() throws RemoteException;

	public void setNote(float noteProduct) throws RemoteException;

	public String getState() throws RemoteException;

	public void setState(String statProduct) throws RemoteException;

	public boolean isAvailable() throws RemoteException;
	
	public void setAvailable(boolean bool) throws RemoteException;
	
}
