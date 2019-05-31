package pavle.stojanovic.blogger.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import pavle.stojanovic.blogger.rest.dto.RatingDTO;

@Entity
@XmlRootElement
public class Rating {
	
	@Id
	@GeneratedValue
	private long id;
	
	private int rating;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Article article;
	
	public Rating() {}
	
	public Rating(RatingDTO rating) {
		this.id = rating.getId();
		this.rating = rating.getRating();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}
	
}
