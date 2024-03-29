package daos.implementations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import beans.models.SportsFacility;
import daos.interfaces.IDAO;
import repositories.interfaces.IRepository;

public class SportsFacilityDAO implements IDAO<SportsFacility>{

	private Map<String, SportsFacility> sportsFacilities = new HashMap<String, SportsFacility>();
	private IRepository<SportsFacility> sportsFacilityRepository;
	
	public SportsFacilityDAO(IRepository<SportsFacility> sportsFacilityRepository) {
		super();
		this.sportsFacilityRepository = sportsFacilityRepository;
		sportsFacilities = sportsFacilityRepository.load();
	}

	@Override
	public Collection<SportsFacility> getAll() {
		Collection<SportsFacility> retVal = new ArrayList<SportsFacility>(sportsFacilities.values());
		retVal.removeIf(x -> (x.getIsDeleted()));
		return retVal;
	}

	@Override
	public SportsFacility get(String id) {
		if(sportsFacilities.containsKey(id)) {
			if(sportsFacilities.get(id).getIsDeleted() == false){
				return sportsFacilities.get(id);
			}
		}
		return null;	
	}

	@Override
	public SportsFacility create(SportsFacility sportsFacility) {
		long maxId = 0;
		for (String id : sportsFacilities.keySet()) {
			long idNum = Long.parseLong(id);
			if (idNum > maxId) {
				maxId = idNum;
			}
		}
		maxId++;
		
		sportsFacility.setId(maxId);
		sportsFacilities.put(String.valueOf(sportsFacility.getId()), sportsFacility);
		sportsFacilityRepository.save(sportsFacilities);
		return sportsFacility;
	}

	@Override
	public boolean update(SportsFacility sportsFacility) {
		if(sportsFacilities.put(String.valueOf(sportsFacility.getId()), sportsFacility) != null) {
			sportsFacilityRepository.save(sportsFacilities);
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(String id) {
		if(sportsFacilities.containsKey(id)) {
			sportsFacilities.get(id).setIsDeleted(true);
			sportsFacilityRepository.save(sportsFacilities);
			return true;
		}
		return false;
	}
}
