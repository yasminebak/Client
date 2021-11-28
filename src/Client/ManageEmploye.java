package Client;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import Common.IEmploye;
import Common.IManageEmployes;

public class ManageEmploye extends UnicastRemoteObject implements IManageEmployes {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<Integer, Employe> employes;

	public ManageEmploye() throws RemoteException {
		super();
		employes = new HashMap<Integer, Employe>();
	}

	@Override
	public void addEmploye(int id, String password, String lastname, String firstname)
			throws RemoteException {
		Objects.requireNonNull(password);
		Objects.requireNonNull(lastname);
		Objects.requireNonNull(firstname);

		Employe e = new Employe(id, password, lastname, firstname);
		employes.put(id, e);
	}

	@Override
	public void deleteEmploye(int id) throws RemoteException {
		if (employes.containsKey(id)) {
			employes.remove(id);
		}
	}

	@Override
	public boolean existEmploye(int id) throws RemoteException {
		return employes.containsKey(id);
	}

	@Override
	public List<IEmploye> getAllEmployes() throws RemoteException {
		List<IEmploye> returnEmployes = new ArrayList<IEmploye>();
		for (Entry<Integer, Employe> entry : employes.entrySet()) {
			Employe e = entry.getValue();
			returnEmployes.add(e);
		}
		return returnEmployes;
	}

	@Override
	public IEmploye getEmploye(int id) throws RemoteException {
		if (employes.containsKey(id)) {
			return employes.get(id);
		}
		return null;
	}

	@Override
	public IEmploye login(int id, String password) throws RemoteException {
		if(!existEmploye(id)) return null;
		if(!getEmploye(id).verifIdentity(password)) return null;
		return employes.get(id);
	}

}
