package pavle.stojanovic.blogger.rest.resources;

import java.util.List;

import javax.ejb.EJB;
import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import pavle.stojanovic.blogger.domain.User;
import pavle.stojanovic.blogger.rest.app.ErrorMessage;
import pavle.stojanovic.blogger.rest.app.RESTResponse;
import pavle.stojanovic.blogger.rest.app.UserResponse;
import pavle.stojanovic.blogger.rest.app.UsersResponse;
import pavle.stojanovic.blogger.rest.dto.UserDTO;
import pavle.stojanovic.blogger.service.AppException;
import pavle.stojanovic.blogger.service.ArticleService;
import pavle.stojanovic.blogger.service.UserService;

@Path("user")
public class UserResource {
	
	@EJB
	private UserService userService;
	@EJB
	private ArticleService articleService;
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public UserResponse createUser(UserDTO user) {
		
		UserResponse response = new UserResponse();
		
		try {		
			User u = new User(user);
			u = userService.createUser(u);
			
			response.setUser(new UserDTO(u, true));
			response.setErrorCode(ErrorMessage.ok);
			return response;
			
		} catch(PersistenceException pe) {
			
			response.setErrorCode(ErrorMessage.db_problem);
			return response;
			
		} catch(AppException ae) {
			
			 response.setErrorCode(ae.getError());
			 return response;
		}	
	}
	
	@GET
	@Produces("application/json")
	public UsersResponse getAll(@QueryParam("user") String userParam, 
			@QueryParam("like") boolean likeParam) {
		
		UsersResponse response = new UsersResponse();
		
		try {
			List<User> u = userService.getAll(userParam, likeParam);

			response.setUsers(u);
			response.setErrorCode(ErrorMessage.ok);
			return response;
			
		} catch(PersistenceException pe) {
			
			response.setErrorCode(ErrorMessage.db_problem);
			return response;
			
		} catch(AppException ae) {
			
			response.setErrorCode(ae.getError());
			return response; 
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public UserResponse getUser(@PathParam("id") long id) {
		
		UserResponse response = new UserResponse();
		
		try {
			User u = userService.getUser(id);
			
			UserDTO uDTO = null;
			if (u != null) {
				uDTO = new UserDTO(u);
			}
			
			response.setUser(uDTO);
			response.setErrorCode(ErrorMessage.ok);
			return response;
			
		} catch(PersistenceException pe) {
			
			response.setErrorCode(ErrorMessage.db_problem);
			return response;
			
		} catch(AppException ae) {
			
			response.setErrorCode(ae.getError());
			return response; 
		}
	}
	
	@PUT
	@Path("/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public UserResponse updateUser(@PathParam("id") long id, User user) {
		
		UserResponse response = new UserResponse();
		
		try {
			User u = userService.updateUser(id, user);
			
			UserDTO uDTO = null;
			if (u != null) {
				uDTO = new UserDTO(u);
			}
			
			response.setUser(uDTO);
			response.setErrorCode(ErrorMessage.ok);
			return response;
			
		} catch(PersistenceException pe) {
			
			response.setErrorCode(ErrorMessage.db_problem);
			return response;
			
		} catch(AppException ae) {
			
			response.setErrorCode(ae.getError());
			return response; 
		}	
	}
	
	@DELETE
	@Path("/{id}")
	public RESTResponse deleteUser(@PathParam("id") long id) {
		
		RESTResponse response = new RESTResponse();
		
		try {
			userService.deleteUser(id);
			
			response.setErrorCode(ErrorMessage.ok);
			return response;
			
		} catch(PersistenceException pe) {
			
			response.setErrorCode(ErrorMessage.db_problem);
			return response;
			
		} catch(AppException ae) {
			
			response.setErrorCode(ae.getError());
			return response; 
		}	
	}

}
