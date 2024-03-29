package daos.implementations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import beans.models.SportsFacilityType;
import daos.interfaces.IDAO;
import repositories.interfaces.IRepository;

public class SportsFacilityTypeDAO implements IDAO<SportsFacilityType> {

	private Map<String, SportsFacilityType> sportsFacilityTypes = new HashMap<String, SportsFacilityType>();
	private IRepository<SportsFacilityType> sportsFacilityTypeRepository;
	
	public SportsFacilityTypeDAO(IRepository<SportsFacilityType> sportsFacilityTypeRepository) {
		super();
		this.sportsFacilityTypeRepository = sportsFacilityTypeRepository;
		this.sportsFacilityTypes = sportsFacilityTypeRepository.load();
	}

	@Override
	public Collection<SportsFacilityType> getAll() {
		Collection<SportsFacilityType> retVal = new ArrayList<SportsFacilityType>(sportsFacilityTypes.values());
		retVal.removeIf(x -> (x.getIsDeleted()));
		return retVal;
	}

	@Override
	public SportsFacilityType get(String id) {
		if(sportsFacilityTypes.containsKey(id)) {
			if(sportsFacilityTypes.get(id).getIsDeleted() == false){
				return sportsFacilityTypes.get(id);
			}
		}
		return null;	
	}

	@Override
	public SportsFacilityType create(SportsFacilityType sportsFacilityType) {
		long maxId = 0;
		for (String id : sportsFacilityTypes.keySet()) {
			long idNum = Long.parseLong(id);
			if (idNum > maxId) {
				maxId = idNum;
			}
		}
		maxId++;
		
		sportsFacilityType.setId(maxId);
		sportsFacilityTypes.put(String.valueOf(sportsFacilityType.getId()), sportsFacilityType);
		sportsFacilityTypeRepository.save(sportsFacilityTypes);
		return sportsFacilityType;
	}

	@Override
	public boolean update(SportsFacilityType sportsFacilityType) {
		if(sportsFacilityTypes.put(String.valueOf(sportsFacilityType.getId()), sportsFacilityType) != null) {
			sportsFacilityTypeRepository.save(sportsFacilityTypes);
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(String id) {
		if(sportsFacilityTypes.containsKey(id)) {
			sportsFacilityTypes.get(id).setIsDeleted(true);
			sportsFacilityTypeRepository.save(sportsFacilityTypes);
			return true;
		}
		return false;
	}

}
