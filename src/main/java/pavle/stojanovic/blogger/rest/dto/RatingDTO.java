package pavle.stojanovic.blogger.rest.dto;

import pavle.stojanovic.blogger.domain.Rating;

public class RatingDTO {
	
	private long id;
	
	private int rating;
	
	private UserDTO user;
	
	private ArticleDTO article;
	
	public RatingDTO() {}
	
	public RatingDTO(Rating rating) {
		this.id = rating.getId();
		this.rating = rating.getRating();
		
		this.user = new UserDTO(rating.getUser(), true);
		
		this.article = new ArticleDTO(rating.getArticle(), true);
	}
	
	public RatingDTO(Rating rating, boolean get) {
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

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public ArticleDTO getArticle() {
		return article;
	}

	public void setArticle(ArticleDTO article) {
		this.article = article;
	}
	
}
