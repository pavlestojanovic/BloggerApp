package pavle.stojanovic.blogger.domain.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import pavle.stojanovic.blogger.domain.Comment;
import pavle.stojanovic.blogger.rest.app.ErrorMessage;
import pavle.stojanovic.blogger.service.AppException;

public class CommentQueries {
	
	public static List<Comment> getAll(EntityManager em, String comment, boolean like) {
		
		if(like == true && comment == null) {
			throw new AppException(ErrorMessage.invalid_query_string);
		}
		
		String q = "Select c from Comment c";
		
		if(comment != null && like == false) {
			q +=" where c.text = :text";
		} else if(comment != null && like == true) {
			q +=" where c.text like concat('%', :text, '%')";
		}
		
		TypedQuery<Comment> query = em.createQuery(q, Comment.class);
		
		if(comment != null) {
			query.setParameter("text", comment);
		}
		
		return query.getResultList();
	}
	
	public static Comment getComment(EntityManager em, long id) {
		
		return em.find(Comment.class, id);
	}

}
