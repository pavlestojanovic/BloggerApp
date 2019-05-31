package pavle.stojanovic.blogger.jsf.backing;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import pavle.stojanovic.blogger.domain.Article;
import pavle.stojanovic.blogger.domain.Tag;
import pavle.stojanovic.blogger.rest.app.ErrorMessage;
import pavle.stojanovic.blogger.service.AppException;
import pavle.stojanovic.blogger.service.ArticleService;
import pavle.stojanovic.blogger.service.TagService;

@Named
@RequestScoped
public class TagBean implements Serializable {
	
	private String value;
	
	@EJB
	private ArticleService articleService;
	@EJB
	private TagService tagService;
	
	
	public String createTag() {
		
		Tag tag = new Tag();
		
		String articleId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("articleId");
		long articleid = Long.parseLong(articleId);
		Article article = articleService.getArticle(articleid);
		
		tag.setValue(value);
		
		try {
			tagService.createTag(tag, article);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ErrorMessage.ok.getMessage()));
			
			return "articleDetail";
			
		} catch(PersistenceException pe) {
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ErrorMessage.db_problem.getMessage()));
			
			return "";
			
		} catch(AppException ae) {
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ae.getError().getMessage()));
	
			return "";
		}		
	}


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	private static final long serialVersionUID = 1L;

}
