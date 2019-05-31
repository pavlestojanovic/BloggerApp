package pavle.stojanovic.blogger.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pavle.stojanovic.blogger.domain.Article;
import pavle.stojanovic.blogger.domain.Comment;
import pavle.stojanovic.blogger.domain.User;
import pavle.stojanovic.blogger.domain.jpa.CommentQueries;
import pavle.stojanovic.blogger.rest.app.ErrorMessage;

@Stateless
public class CommentService {
	
	@PersistenceContext
	private EntityManager em;
	
	public Comment createComment(Comment comment, User user, Article article) {
		
		if(comment.getText().length() > 500) {
			throw new AppException(ErrorMessage.invalid_comment);
		}
		
		comment.setCreated(LocalDateTime.now());
		
		em.persist(comment);
		
		user = em.merge(user);
		article = em.merge(article);
		
		comment.setUser(user);
		comment.setArticle(article);
		user.getComments().add(comment);
		article.getComments().add(comment);
		
		return comment;
	}
	
	public List<Comment> getAll(String comment, boolean like) {
		
		return CommentQueries.getAll(em, comment, like);
	}
	
	public Comment getComment(long id) {
		
		Comment c = CommentQueries.getComment(em, id);
		
		if(c == null) {
			throw new AppException(ErrorMessage.comment_does_not_exists);
		}
		
		return c;
	}
	
	public Comment updateComment(long id, Comment comment) {
		
		if(comment.getText() != null && comment.getText().length() > 500) {
			throw new AppException(ErrorMessage.invalid_comment);
		}
		
		Comment c = CommentQueries.getComment(em, id);
		
//		if(c == null) {
//			return createComment(comment);
//		}
		
		if(comment.getText() == null) {
			c.getText();
		} else {
			c.setText(comment.getText());
		}
		
		if(comment.getUpVote() != 0) {
			c.setUpVote(c.getUpVote() + 1);
		} 
		
		if(comment.getDownVote() != 0) {
			c.setDownVote(c.getDownVote() - 1); 
		}
		
		return c;
	}
	
	public void deleteComment(long id) {
		
		Comment c = CommentQueries.getComment(em, id);
		
		if(c == null) {
			throw new AppException(ErrorMessage.comment_does_not_exists);
		}
		
		em.remove(c);
	}

}
