package pavle.stojanovic.blogger.jsf.backing;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import org.primefaces.component.api.UIData;

import pavle.stojanovic.blogger.domain.Article;
import pavle.stojanovic.blogger.domain.Tag;
import pavle.stojanovic.blogger.domain.User;
import pavle.stojanovic.blogger.rest.app.ErrorMessage;
import pavle.stojanovic.blogger.service.AppException;
import pavle.stojanovic.blogger.service.ArticleService;
import pavle.stojanovic.blogger.service.UserService;

@Named
@RequestScoped
public class ArticleBean implements Serializable {
	
	private String title;
	private String content;
	private LocalDateTime created;
	private int ratingCounter;
	private String keyword;
	private double averageCounter;
	
	private UIData dataTable;
	
	@Inject
	private LoginBean loginBean;
	
	@EJB
	private UserService userService;
	@EJB
	private ArticleService articleService;
	
	public String createArticle() {
		
		Article article = new Article();
		
		long id = loginBean.getLoggedUser().getId();
		User user = userService.getUser(id);
		
		article.setTitle(title);
		article.setContent(content);
		article.setCreated(created);
		article.getKeywords().add(keyword);
		
		try {
			articleService.createArticle(article, user);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ErrorMessage.ok.getMessage()));
			
			return "allArticles?faces-redirect=true";
			
		} catch(PersistenceException pe) {
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ErrorMessage.db_problem.getMessage()));
			
			return "";
			
		} catch(AppException ae) {
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ae.getError().getMessage()));
	
			return "";
		}		
	}
	
	public List<Article> getAllArticles() {
		
		return articleService.getAll(null, false);
	}
	
	public Article getArticle() {
		
		String articleId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("articleId");
		
		long id = Long.parseLong(articleId);
		
		return articleService.getArticle(id);
	}
	
	public String updateArticle() {
		
		String articleId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("articleId");
		
		long id = Long.parseLong(articleId);
		
		Article article = new Article();
		
		article.setTitle(title);
		article.setContent(content);
		article.getKeywords().add(keyword);
		
		try {
			articleService.updateArticle(id, article);
			
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
	
	public List<String> getTags() {
		
		List<String> tagValue = new ArrayList<>();
		
		String articleId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("articleId");
		long id = Long.parseLong(articleId);
		Article article = articleService.getArticle(id);
		
		for(Tag t : article.getTags()) {
			tagValue.add(t.getValue());
		}
		
		return tagValue;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public int getRatingCounter() {
		return ratingCounter;
	}

	public void setRatingCounter(int ratingCounter) {
		this.ratingCounter = ratingCounter;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public double getAverageCounter() {
		return averageCounter;
	}

	public void setAverageCounter(double averageCounter) {
		this.averageCounter = averageCounter;
	}
	
	public UIData getDataTable() {
		return dataTable;
	}

	public void setDataTable(UIData dataTable) {
		this.dataTable = dataTable;
	}

	private static final long serialVersionUID = 1L;
	
}
