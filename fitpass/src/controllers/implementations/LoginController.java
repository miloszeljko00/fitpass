package controllers.implementations;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.models.Administrator;
import beans.models.Buyer;
import beans.models.Coach;
import beans.models.Manager;
import beans.models.User;
import beans.dtos.UserLoginDTO;
import beans.dtos.UserToken;
import controllers.interfaces.ILoginController;
import services.implementations.ContextInitService;
import services.interfaces.IAdministratorService;
import services.interfaces.IBuyerService;
import services.interfaces.ICoachService;
import services.interfaces.ILoginService;
import services.interfaces.IManagerService;

@Path("/LoginController")
public class LoginController implements ILoginController {
	
	@Context
	ServletContext ctx;
	
	public LoginController() {}

	@PostConstruct
	public void init() {
		ContextInitService.initBuyerService(ctx);
		ContextInitService.initCoachService(ctx);
		ContextInitService.initManagerService(ctx);
		ContextInitService.initAdministratorService(ctx);
		ContextInitService.initLoginService(ctx);
	}
	
	@Override
	@GET
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean logout(@Context HttpServletRequest request) {
		ILoginService loginService = (ILoginService) ctx.getAttribute("LoginService");
		return loginService.logout(request);
	}
	
	@Override
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public UserToken login(@Context HttpServletRequest request, UserLoginDTO userLoginDTO) {
		UserToken retVal = null;
		IBuyerService buyerService = (IBuyerService) ctx.getAttribute("BuyerService");
		ICoachService coachService = (ICoachService) ctx.getAttribute("CoachService");
		IManagerService managerService = (IManagerService) ctx.getAttribute("ManagerService");
		IAdministratorService administratorService = (IAdministratorService) ctx.getAttribute("AdministratorService");
		ILoginService loginService = (ILoginService) ctx.getAttribute("LoginService");
		retVal = (UserToken)request.getSession().getAttribute("userToken");
		if (retVal == null) {
			Collection<Buyer> buyers = buyerService.getAll();
			Collection<Coach> coaches = coachService.getAll();
			Collection<Manager> managers = managerService.getAll();
			Collection<Administrator> administrators = administratorService.getAll();
			Collection<User> users = new ArrayList<User>();
			for(Buyer b: buyers) {
				users.add(b);
			}
			for(Coach c: coaches) {
				users.add(c);
			}
			for(Manager m: managers) {
				users.add(m);
			}
			for(Administrator a: administrators) {
				users.add(a);
			}
			User user = loginService.login(userLoginDTO, users);
			if(user == null) return null;
			UserToken userToken = new UserToken();
			userToken.setId(user.getId());
			userToken.setUsername(user.getUsername());
			userToken.setRole(user.getRole());
			request.getSession().setAttribute("userToken", userToken);
			retVal = userToken;
		}
		return retVal;
	}

	
}
