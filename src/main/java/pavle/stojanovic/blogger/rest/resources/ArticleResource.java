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
import pavle.stojanovic.blogger.domain.User;
import pavle.stojanovic.blogger.rest.app.ArticleResponse;
import pavle.stojanovic.blogger.rest.app.ArticlesResponse;
import pavle.stojanovic.blogger.rest.app.ErrorMessage;
import pavle.stojanovic.blogger.rest.app.RESTResponse;
import pavle.stojanovic.blogger.rest.dto.ArticleDTO;
import pavle.stojanovic.blogger.service.AppException;
import pavle.stojanovic.blogger.service.ArticleService;
import pavle.stojanovic.blogger.service.TagService;
import pavle.stojanovic.blogger.service.UserService;

@Path("article")
public class ArticleResource {
	
	@EJB
	private ArticleService articleService;
	@EJB
	private TagService tagService;
	@EJB
	private UserService userService;
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public ArticleResponse createArticle(ArticleDTO article) {
		
		ArticleResponse response = new ArticleResponse();
		
		try {
			long userID = article.getUser().getId();
			User u = userService.getUser(userID);
			
			Article a = new Article(article);
			a = articleService.createArticle(a, u);
			
			response.setArticle(new ArticleDTO(a, true));
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
	public ArticlesResponse getAll(@QueryParam("article") String articleParam, 
			@QueryParam("like") boolean likeParam) {
		
		ArticlesResponse response = new ArticlesResponse();
		
		try {
			List<Article> a = articleService.getAll(articleParam, likeParam);
			
			response.setArticles(a);
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
	public ArticleResponse getArticle(@PathParam("id") long id) {
		
		ArticleResponse response = new ArticleResponse();
		
		try {
			Article a = articleService.getArticle(id);
			
			ArticleDTO aDTO = null;
			if (a != null) {
				aDTO = new ArticleDTO(a);
			}
			
			response.setArticle(aDTO);
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
	public ArticleResponse updateArticle(@PathParam("id") long id, Article article) {
		
		ArticleResponse response = new ArticleResponse();
		
		try {
			Article a = articleService.updateArticle(id, article);
			
			ArticleDTO aDTO = null;
			if (a != null) {
				aDTO = new ArticleDTO(a);
			}
			
			response.setArticle(aDTO);
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
	public RESTResponse deleteArticle(@PathParam("id") long id) {
		
		RESTResponse response = new RESTResponse();
		
		try {
			articleService.deleteArticle(id);
			
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
