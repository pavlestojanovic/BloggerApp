package pavle.stojanovic.blogger.rest.app;

import pavle.stojanovic.blogger.rest.dto.CommentDTO;

public class CommentResponse extends RESTResponse {
	
	private CommentDTO comment;

	public CommentDTO getComment() {
		return comment;
	}

	public void setComment(CommentDTO comment) {
		this.comment = comment;
	}

}
