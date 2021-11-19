import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;





@SuppressWarnings("serial")
public class Client extends UnicastRemoteObject implements IClient {
	private final int id;
	private String password;

	public Client(int id, String password) throws RemoteException {

		this.id = id;
		this.password = password;
	}

	@Override
	public int getClientId() throws RemoteException {
		if (id < 0) {
			throw new NumberFormatException("ID can't be negative !");
		}
		return this.id;
	}

	@Override
	public String getClientPassword() throws RemoteException {
		if (password == null) {
			return "error";
		}
		return this.password;
	}

	public void setPassword(String password) {
		if (password == null) {
			System.out.println("password can't be null, You must put a password");
		}
		this.password = password;
	}

	@Override
	public void notifyAvailableProduct(String notification) throws RemoteException {
		System.out.println(notification);

	}

}
