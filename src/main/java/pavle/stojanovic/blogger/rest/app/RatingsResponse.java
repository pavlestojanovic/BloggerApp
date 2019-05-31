package pavle.stojanovic.blogger.rest.app;

import java.util.List;

import pavle.stojanovic.blogger.domain.Rating;

public class RatingsResponse extends RESTResponse {
	
	private List<Rating> ratings;

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}
	
}
