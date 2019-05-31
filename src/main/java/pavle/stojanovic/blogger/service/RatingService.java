package pavle.stojanovic.blogger.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pavle.stojanovic.blogger.domain.Article;
import pavle.stojanovic.blogger.domain.Rating;
import pavle.stojanovic.blogger.domain.User;
import pavle.stojanovic.blogger.domain.jpa.RatingQueries;
import pavle.stojanovic.blogger.rest.app.ErrorMessage;

@Stateless
public class RatingService {
	
	@PersistenceContext
	private EntityManager em;
	
	public Rating createRating(Rating rating, User user, Article article) {
		
		if(rating.getRating() < 0 || rating.getRating() > 5) {
			throw new AppException(ErrorMessage.invalid_rating);
		}
		
		article.setRatingCounter(article.getRatingCounter() + 1);
		
		em.persist(rating);
		
		user = em.merge(user);
		article = em.merge(article);
		
		rating.setUser(user);
		rating.setArticle(article);
		user.getRatings().add(rating);
		article.getRatings().add(rating);
		
		return rating;
	}
	
	public List<Rating> getAll(String rating, boolean like) {
		
		return RatingQueries.getAll(em, rating, like);
	}
	
	public Rating getRating(long id) {
		
		Rating r = RatingQueries.getRating(em, id);
		
		if(r == null) {
			throw new AppException(ErrorMessage.rating_does_not_exists);
		}
		
		return r;
	}
	
	public Rating updateRating(long id, Rating rating) {
		
		Rating r = RatingQueries.getRating(em, id);
		
//		if(r == null) {
//			return createRating(rating);
//		}
		
		r.setRating(rating.getRating());
		
		return r;
	}
	
	public void deleteRating(long id) {
		
		Rating r = RatingQueries.getRating(em, id);
		
		if(r == null) {
			throw new AppException(ErrorMessage.rating_does_not_exists);
		}
		
		em.remove(r);
	}

}
