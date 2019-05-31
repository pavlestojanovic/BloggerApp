package pavle.stojanovic.blogger.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import pavle.stojanovic.blogger.domain.utils.LocalDateTimeAdapter;
import pavle.stojanovic.blogger.rest.dto.CommentDTO;

@Entity
@XmlRootElement
public class Comment {
	
	@Id
	@GeneratedValue
	private long id;
	
	@XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
	private LocalDateTime created;
	
	private String text;
	
	private int upVote;
	
	private int downVote;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Article article;
	
	public Comment() {}

	public Comment(CommentDTO comment) {
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
