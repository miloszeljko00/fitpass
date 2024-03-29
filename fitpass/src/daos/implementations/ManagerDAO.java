package daos.implementations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import beans.models.Manager;
import daos.interfaces.IDAO;
import repositories.interfaces.IRepository;

public class ManagerDAO implements IDAO<Manager> {

	private Map<String, Manager> managers = new HashMap<String, Manager>();
	private IRepository<Manager> managerRepository;
	
	public ManagerDAO(IRepository<Manager> managerRepository) {
		super();
		this.managerRepository = managerRepository;
		managers = managerRepository.load();
	}

	@Override
	public Collection<Manager> getAll() {
		Collection<Manager> retVal = new ArrayList<Manager>(managers.values());
		retVal.removeIf(x -> (x.getIsDeleted()));
		return retVal;
	}

	@Override
	public Manager get(String id) {
		if(managers.containsKey(id)) {
			if(managers.get(id).getIsDeleted() == false){
				return managers.get(id);
			}
		}
		return null;	
	}

	@Override
	public Manager create(Manager manager) {
		long maxId = 0;
		for (String id : managers.keySet()) {
			long idNum = Long.parseLong(id);
			if (idNum > maxId) {
				maxId = idNum;
			}
		}
		maxId++;
		
		manager.setId(maxId);
		managers.put(String.valueOf(manager.getId()), manager);
		managerRepository.save(managers);
		return manager;
	}

	@Override
	public boolean update(Manager manager) {
		if(managers.put(String.valueOf(manager.getId()), manager) != null) {
			managerRepository.save(managers);
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(String id) {
		if(managers.containsKey(id)) {
			managers.get(id).setIsDeleted(true);
			managerRepository.save(managers);
			return true;
		}
		return false;
	}

}
