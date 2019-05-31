package pavle.stojanovic.blogger.rest.app;

import pavle.stojanovic.blogger.rest.dto.RatingDTO;

public class RatingResponse extends RESTResponse {
	
	private RatingDTO rating;

	public RatingDTO getRating() {
		return rating;
	}

	public void setRating(RatingDTO rating) {
		this.rating = rating;
	}

}
