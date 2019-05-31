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

import pavle.stojanovic.blogger.domain.Article;
import pavle.stojanovic.blogger.domain.Rating;
import pavle.stojanovic.blogger.domain.User;
import pavle.stojanovic.blogger.rest.app.ErrorMessage;
import pavle.stojanovic.blogger.rest.app.RESTResponse;
import pavle.stojanovic.blogger.rest.app.RatingResponse;
import pavle.stojanovic.blogger.rest.app.RatingsResponse;
import pavle.stojanovic.blogger.rest.dto.RatingDTO;
import pavle.stojanovic.blogger.service.AppException;
import pavle.stojanovic.blogger.service.ArticleService;
import pavle.stojanovic.blogger.service.RatingService;
import pavle.stojanovic.blogger.service.UserService;

@Path("rating")
public class RatingResource {
	
	@EJB
	private RatingService ratingService;
	
	@EJB
	private UserService userService;
	
	@EJB
	private ArticleService articleService;
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public RatingResponse createRating(RatingDTO rating) {
		
		RatingResponse response = new RatingResponse();
		
		try {
			long userID = rating.getUser().getId();
			User u = userService.getUser(userID);
			
			long articleID = rating.getArticle().getId();
			Article a = articleService.getArticle(articleID);
			
			Rating r = new Rating(rating);
			r = ratingService.createRating(r, u, a);
			
			response.setRating(new RatingDTO(r));
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
	public RatingsResponse getAll(@QueryParam("tag") String ratingParam, 
			@QueryParam("like") boolean likeParam) {
		
		RatingsResponse response = new RatingsResponse();
		
		try {
			List<Rating> r = ratingService.getAll(ratingParam, likeParam);
			
			response.setRatings(r);
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
	public RatingResponse getTag(@PathParam("id") long id) {
		
		RatingResponse response = new RatingResponse();
		
		try {
			Rating r = ratingService.getRating(id);
			
			RatingDTO rDTO = null;
			if (r != null) {
				rDTO = new RatingDTO(r);
			}
			
			response.setRating(rDTO);
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
	public RatingResponse updateRating(@PathParam("id") long id, Rating rating) {
		
		RatingResponse response = new RatingResponse();
		
		try {
			Rating r = ratingService.updateRating(id, rating);
			
			RatingDTO rDTO = null;
			if (r != null) {
				rDTO = new RatingDTO(r);
			}
			
			response.setRating(rDTO);;
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
	public RESTResponse deleteRating(@PathParam("id") long id) {
		
		RESTResponse response = new RESTResponse();
		
		try {
			ratingService.deleteRating(id);
			
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
