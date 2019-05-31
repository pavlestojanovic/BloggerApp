package pavle.stojanovic.blogger.rest.dto;

import java.time.LocalDateTime;

import pavle.stojanovic.blogger.domain.Comment;

public class CommentDTO {
	
	private long id;
	
	private LocalDateTime created;
	
	private String text;
	
	private int upVote;
	
	private int downVote;
	
	private UserDTO user;
	
	private ArticleDTO article;
	
	public CommentDTO() {}
	
	public CommentDTO(Comment comment) {
		this.id = comment.getId();
		this.created = comment.getCreated();
		this.text = comment.getText();
		this.upVote = comment.getUpVote();
		this.downVote = comment.getDownVote();
		
		this.user = new UserDTO(comment.getUser(), true);
		
		this.article = new ArticleDTO(comment.getArticle(), true);
	}
	
	public CommentDTO(Comment comment, boolean get) {
		this.id = comment.getId();
		this.created = comment.getCreated();
		this.text = comment.getText();
		this.upVote = comment.getUpVote();
		this.downVote = comment.getDownVote();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getUpVote() {
		return upVote;
	}

	public void setUpVote(int upVote) {
		this.upVote = upVote;
	}

	public int getDownVote() {
		return downVote;
	}

	public void setDownVote(int downVote) {
		this.downVote = downVote;
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
