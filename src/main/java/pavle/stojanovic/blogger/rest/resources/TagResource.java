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
import pavle.stojanovic.blogger.domain.Tag;
import pavle.stojanovic.blogger.rest.app.ErrorMessage;
import pavle.stojanovic.blogger.rest.app.RESTResponse;
import pavle.stojanovic.blogger.rest.app.TagResponse;
import pavle.stojanovic.blogger.rest.app.TagsResponse;
import pavle.stojanovic.blogger.rest.dto.TagDTO;
import pavle.stojanovic.blogger.service.AppException;
import pavle.stojanovic.blogger.service.ArticleService;
import pavle.stojanovic.blogger.service.TagService;

@Path("tag")
public class TagResource {
	
	@EJB
	private TagService tagService;
	@EJB
	private ArticleService articleService;
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public TagResponse createTag(TagDTO tag) {
		
		TagResponse response = new TagResponse();
		
		try {
			long articleID = tag.getArticles().iterator().next().getId();
			Article a = articleService.getArticle(articleID);
			
			Tag t = new Tag(tag);
			t = tagService.createTag(t, a);
			
			response.setTag(new TagDTO(t));
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
	public TagsResponse getAll(@QueryParam("tag") String tagParam, 
			@QueryParam("like") boolean likeParam) {
		
		TagsResponse response = new TagsResponse();
		
		try {
			List<Tag> t = tagService.getAll(tagParam, likeParam);
			
			response.setTags(t);
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
	public TagResponse getTag(@PathParam("id") long id) {
		
		TagResponse response = new TagResponse();
		
		try {
			Tag t = tagService.getTag(id);
			
			TagDTO tDTO = null;
			if (t != null) {
				tDTO = new TagDTO(t);
			}
			
			response.setTag(tDTO);
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
	public TagResponse updateTag(@PathParam("id") long id, Tag tag) {
		
		TagResponse response = new TagResponse();
		
		try {
			Tag t = tagService.updateTag(id, tag);
			
			TagDTO tDTO = null;
			if (t != null) {
				tDTO = new TagDTO(t);
			}
			
			response.setTag(tDTO);
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
	public RESTResponse deleteTag(@PathParam("id") long id) {
		
		RESTResponse response = new RESTResponse();
		
		try {
			tagService.deleteTag(id);
			
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
