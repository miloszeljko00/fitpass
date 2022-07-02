package controllers.implementations;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.models.Guestbook;
import services.implementations.ContextInitService;
import services.interfaces.IGuestbookService;

@Path("/GuestbookController")
public class GuestbookController {


	@Context
	ServletContext ctx;
	
	public GuestbookController() {}

	@PostConstruct
	public void init() {
		ContextInitService.initGuesbookService(ctx);
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Guestbook> getAll(){
		IGuestbookService guestbookService = (IGuestbookService) ctx.getAttribute("GuestbookService");
		
		return guestbookService.getAll();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Guestbook get(@PathParam("id") long id) {
		IGuestbookService guestbookService = (IGuestbookService) ctx.getAttribute("GuestbookService");
		
		return guestbookService.get(id);
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Guestbook create(Guestbook guestbook) {
		IGuestbookService guestbookService = (IGuestbookService) ctx.getAttribute("GuestbookService");
		
		return guestbookService.create(guestbook);
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean update(Guestbook guestbook) {
		IGuestbookService guestbookService = (IGuestbookService) ctx.getAttribute("GuestbookService");
		
		return guestbookService.update(guestbook);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean delete(@PathParam("id") long id) {
		IGuestbookService guestbookService = (IGuestbookService) ctx.getAttribute("GuestbookService");
		
		return guestbookService.delete(id);
	}
}
