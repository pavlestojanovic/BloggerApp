package pavle.stojanovic.blogger.domain.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import pavle.stojanovic.blogger.domain.Rating;
import pavle.stojanovic.blogger.rest.app.ErrorMessage;
import pavle.stojanovic.blogger.service.AppException;

public class RatingQueries {
	
public static List<Rating> getAll(EntityManager em, String rating, boolean like) {
		
		if(like == true && rating == null) {
			throw new AppException(ErrorMessage.invalid_query_string);
		}
		
		String q = "Select r from Rating r";
		
		if(rating != null && like == false) {
			q +=" where r.rating = :rating";
		} else if(rating != null && like == true) {
			q +=" where r.rating like concat('%', :rating, '%')";
		}
		
		TypedQuery<Rating> query = em.createQuery(q, Rating.class);
		
		if(rating != null) {
			query.setParameter("rating", rating);
		}
		
		return query.getResultList();
	}
	
	public static Rating getRating(EntityManager em, long id) {
		
		return em.find(Rating.class, id);
	}

}
