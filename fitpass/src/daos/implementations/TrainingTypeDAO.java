package daos.implementations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import beans.models.TrainingType;
import daos.interfaces.IDAO;
import repositories.interfaces.IRepository;

public class TrainingTypeDAO implements IDAO<TrainingType> {

	private Map<String, TrainingType> trainingTypes = new HashMap<String, TrainingType>();
	private IRepository<TrainingType> trainingTypeRepository;
	
	public TrainingTypeDAO(IRepository<TrainingType> trainingTypeRepository) {
		super();
		this.trainingTypeRepository = trainingTypeRepository;
		trainingTypes = trainingTypeRepository.load();
	}

	@Override
	public Collection<TrainingType> getAll() {
		Collection<TrainingType> retVal = new ArrayList<TrainingType>(trainingTypes.values());
		retVal.removeIf(x -> (x.getIsDeleted()));
		return retVal;
	}

	@Override
	public TrainingType get(String id) {
		if(trainingTypes.containsKey(id)) {
			if(trainingTypes.get(id).getIsDeleted() == false){
				return trainingTypes.get(id);
			}
		}
		return null;	
	}

	@Override
	public TrainingType create(TrainingType trainingType) {
		long maxId = 0;
		for (String id : trainingTypes.keySet()) {
			long idNum = Long.parseLong(id);
			if (idNum > maxId) {
				maxId = idNum;
			}
		}
		maxId++;
		
		trainingType.setId(maxId);
		trainingTypes.put(String.valueOf(trainingType.getId()), trainingType);
		trainingTypeRepository.save(trainingTypes);
		return trainingType;
	}

	@Override
	public boolean update(TrainingType trainingType) {
		if(trainingTypes.put(String.valueOf(trainingType.getId()), trainingType) != null) {
			trainingTypeRepository.save(trainingTypes);
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(String id) {
		if(trainingTypes.containsKey(id)) {
			trainingTypes.get(id).setIsDeleted(true);
			trainingTypeRepository.save(trainingTypes);
			return true;
		}
		return false;
	}

}
