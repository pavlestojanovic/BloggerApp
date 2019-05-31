package pavle.stojanovic.blogger.jsf.backing;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import pavle.stojanovic.blogger.domain.Article;
import pavle.stojanovic.blogger.domain.Rating;
import pavle.stojanovic.blogger.domain.User;
import pavle.stojanovic.blogger.rest.app.ErrorMessage;
import pavle.stojanovic.blogger.service.AppException;
import pavle.stojanovic.blogger.service.ArticleService;
import pavle.stojanovic.blogger.service.RatingService;
import pavle.stojanovic.blogger.service.UserService;

@Named
@RequestScoped
public class RatingBean implements Serializable {
	
	private int rating;
	
	@EJB
	private UserService userService;
	@EJB
	private ArticleService articleService;
	@EJB
	private RatingService ratingService;
	
	@Inject
	private LoginBean loginBean;
	
	public String createRating() {
		
		Rating rating = new Rating();
		
		long userId = loginBean.getLoggedUser().getId();
		User user = userService.getUser(userId);
		
		String articleId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("articleId");
		long articleid = Long.parseLong(articleId);
		Article article = articleService.getArticle(articleid);
		
		rating.setRating(this.rating);
		
		try {
			ratingService.createRating(rating, user, article);
			
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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
	private static final long serialVersionUID = 1L;

}
