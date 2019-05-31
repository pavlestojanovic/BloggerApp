package pavle.stojanovic.blogger.service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pavle.stojanovic.blogger.domain.Article;
import pavle.stojanovic.blogger.domain.Rating;
import pavle.stojanovic.blogger.domain.Tag;
import pavle.stojanovic.blogger.domain.User;
import pavle.stojanovic.blogger.domain.jpa.ArticleQueries;
import pavle.stojanovic.blogger.rest.app.ErrorMessage;

@Stateless
public class ArticleService {
	
	@PersistenceContext
	private EntityManager em;
	
	public Article createArticle(Article article, User user) {
		
		if(article.getTitle().length() > 75) {
			throw new AppException(ErrorMessage.invalid_title);
		}
		
		if(article.getContent().length() > 5000) {
			throw new AppException(ErrorMessage.invalid_content);
		}
		
		article.setCreated(LocalDateTime.now());
		
		article.setRatingCounter(article.getRatingCounter());
		
		em.persist(article);
		
		user = em.merge(user);
		
		article.setUser(user);
		user.getArticles().add(article);
		
		return article;
	}
	
	public List<Article> getAll(String article, boolean like) {
		
		return ArticleQueries.getAll(em, article, like);
	}
	
	public Article getArticle(long id) {
		
		Article a = ArticleQueries.getArticle(em, id);
		
		if(a == null) {
			throw new AppException(ErrorMessage.article_does_not_exists);
		}
		
		int totalRate = 0;
		if(a.getRatings() != null) {
			for(Rating r : a.getRatings()) {
				totalRate += r.getRating();
			}
		}
		
		if(a.getRatingCounter() != 0) {
			double rate = (double) totalRate/a.getRatingCounter();
			
			NumberFormat formatter = new DecimalFormat("#0.00");
			
			rate = Double.parseDouble(formatter.format(rate));
			
			a.setAverageCounter(rate);
		}
		
		Set<Tag> tag = new HashSet<>();
		for(Tag t : a.getTags()) {
			tag.add(t);
		}
		a.setTags(tag);
		
		return a;
	}
	
	public Article updateArticle(long id, Article article) {
		
		if(article.getTitle() != null && article.getTitle().length() > 75) {
			throw new AppException(ErrorMessage.invalid_title);
		}
		
		if(article.getContent() != null && article.getContent().length() > 5000) {
			throw new AppException(ErrorMessage.invalid_content);
		}
		
		Article a = ArticleQueries.getArticle(em, id);
		
//		if(a == null) {
//			return createArticle(article);
//		}
		
		if(article.getTitle() == null) {
			a.getTitle();
		} else { 
			a.setTitle(article.getTitle());
		}
		
		if(article.getContent() == null) {
			a.getContent();
		} else {
			a.setContent(article.getContent());
		}
		
		a.setKeywords(article.getKeywords());
		
		return a;
	}
	
	public void deleteArticle(long id) {
		
		Article a = ArticleQueries.getArticle(em, id);
		
		if(a == null) {
			throw new AppException(ErrorMessage.article_does_not_exists);
		}
		
		em.remove(a);
	}

}
