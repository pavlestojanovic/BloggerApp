package pavle.stojanovic.blogger.domain.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import pavle.stojanovic.blogger.domain.Article;
import pavle.stojanovic.blogger.rest.app.ErrorMessage;
import pavle.stojanovic.blogger.service.AppException;

public class ArticleQueries {
	
	public static List<Article> getAll(EntityManager em, String article, boolean like) {
		
		if(like == true && article == null) {
			throw new AppException(ErrorMessage.invalid_query_string);
		}
		
		String q = "Select a from Article a";
		
		if(article != null && like == false) {
			q +=" where a.title = :title or a.content = :content";
		} else if(article != null && like == true) {
			q +=" where a.title like concat('%', :title, '%') or a.content like concat('%', :content, '%')";
		}
		
		TypedQuery<Article> query = em.createQuery(q, Article.class);
		
		if(article != null) {
			query.setParameter("title", article).setParameter("content", article);
		}
		
		return query.getResultList();
	}
	
	public static Article getArticle(EntityManager em, long id) {
		
		return em.find(Article.class, id);
	}

}
