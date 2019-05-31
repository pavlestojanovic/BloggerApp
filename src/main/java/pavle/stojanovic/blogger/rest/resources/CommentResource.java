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
import pavle.stojanovic.blogger.domain.Comment;
import pavle.stojanovic.blogger.domain.User;
import pavle.stojanovic.blogger.rest.app.CommentResponse;
import pavle.stojanovic.blogger.rest.app.CommentsResponse;
import pavle.stojanovic.blogger.rest.app.ErrorMessage;
import pavle.stojanovic.blogger.rest.app.RESTResponse;
import pavle.stojanovic.blogger.rest.dto.CommentDTO;
import pavle.stojanovic.blogger.service.AppException;
import pavle.stojanovic.blogger.service.ArticleService;
import pavle.stojanovic.blogger.service.CommentService;
import pavle.stojanovic.blogger.service.UserService;

@Path("comment")
public class CommentResource {
	
	@EJB
	private CommentService commentService;
	
	@EJB
	private UserService userService;
	
	@EJB
	private ArticleService articleService;
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public CommentResponse createComment(CommentDTO comment) {
		
		CommentResponse response = new CommentResponse();
		
		try {
			long userID = comment.getUser().getId();
			User u = userService.getUser(userID);
			
			long articleID = comment.getArticle().getId();
			Article a = articleService.getArticle(articleID);
			
			Comment c = new Comment(comment);
			c = commentService.createComment(c, u, a);
			
			response.setComment(new CommentDTO(c));
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
	public CommentsResponse getAll(@QueryParam("comment") String commentParam, 
			@QueryParam("like") boolean likeParam) {
		
		CommentsResponse response = new CommentsResponse();
		
		try {
			List<Comment> c = commentService.getAll(commentParam, likeParam);
			
			response.setComments(c);
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
	public CommentResponse getComment(@PathParam("id") long id) {
		
		CommentResponse response = new CommentResponse();
		
		try {
			Comment c = commentService.getComment(id);
			
			CommentDTO cDTO = null;
			if (c != null) {
				cDTO = new CommentDTO(c);
			}
			
			response.setComment(cDTO);
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
	public CommentResponse updateComment(@PathParam("id") long id, Comment comment) {
		
		CommentResponse response = new CommentResponse();
		
		try {
			Comment c = commentService.updateComment(id, comment);
			
			CommentDTO cDTO = null;
			if (c != null) {
				cDTO = new CommentDTO(c);
			}
			
			response.setComment(cDTO);
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
	public RESTResponse deleteComment(@PathParam("id") long id) {
		
		RESTResponse response = new RESTResponse();
		
		try {
			commentService.deleteComment(id);
			
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
