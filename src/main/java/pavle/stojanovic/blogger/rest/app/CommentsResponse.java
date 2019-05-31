package pavle.stojanovic.blogger.rest.app;

import java.util.List;

import pavle.stojanovic.blogger.domain.Comment;

public class CommentsResponse extends RESTResponse {
	
	private List<Comment> comments;

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}
