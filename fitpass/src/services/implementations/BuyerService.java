package services.implementations;

import java.util.ArrayList;
import java.util.Collection;

import beans.models.Buyer;
import daos.interfaces.IDAO;
import services.interfaces.IBuyerService;

public class BuyerService implements IBuyerService {

	private IDAO<Buyer> buyerDAO;
	
	
	
	public BuyerService(IDAO<Buyer> buyerDAO) {
		super();
		this.buyerDAO = buyerDAO;
	}

	@Override
	public Collection<Buyer> getAll() {
		return buyerDAO.getAll();
	}

	@Override
	public Buyer get(long id) {
		return buyerDAO.get(String.valueOf(id));
	}

	@Override
	public Buyer create(Buyer buyer) {
		return buyerDAO.create(buyer);
	}

	@Override
	public boolean update(Buyer buyer) {
		return buyerDAO.update(buyer);
	}

	@Override
	public boolean delete(long id) {
		return buyerDAO.delete(String.valueOf(id));
	}

	@Override
	public Buyer getByUsername(String username) {
		Collection<Buyer> buyers = buyerDAO.getAll();
		for(Buyer b : buyers) {
			if(b.getUsername().compareTo(username) == 0) {
				return b;
			}
		}
		return null;
	}

	@Override
	public Collection<Buyer> getBuyersWhoVisitedCertainSportFacility(long id) {
		Collection<Buyer> buyers = buyerDAO.getAll();
		Collection<Buyer> buyersWhoVisitedCertainSportFacility = new ArrayList<Buyer>();
		for(Buyer b : buyers) {
			for(long vsf : b.getVisitedSportsFacilitiesIds()) {
				if(vsf == id) {
					buyersWhoVisitedCertainSportFacility.add(b);
					break;
				}
			}
		}
		return buyersWhoVisitedCertainSportFacility;
	}

	@Override
	public long invalidateMembershipIfExists(long buyerId) {
		long retVal = -1;
		Buyer buyer = buyerDAO.get(String.valueOf(buyerId));
		if(buyer.getMembershipId() != -1) {
			retVal = buyer.getMembershipId();
			buyer.setMembershipId(-1);
		}
		return retVal;
	}

	@Override
	public Buyer updateBuyerStatus(long buyerId, double pointsForUpdate) {
		Buyer buyer = buyerDAO.get(String.valueOf(buyerId));
		double points = buyer.getNumberOfCollectedPoints() + pointsForUpdate;
		
		if(points < 500) {
			buyer.setBuyerTypeId(1);
			buyer.setNumberOfCollectedPoints(points);
		}
		else if(points < 1000) {
			buyer.setBuyerTypeId(2);
			buyer.setNumberOfCollectedPoints(points);
		}
		else if(points > 1000) {
			buyer.setBuyerTypeId(3);
			buyer.setNumberOfCollectedPoints(points);
		}
		
		if(points < 0) { 
			points = 0;
			buyer.setNumberOfCollectedPoints(points);
		}
		
		if(buyerDAO.update(buyer)) {
			return buyer;
		} return null;
		
	}

}
