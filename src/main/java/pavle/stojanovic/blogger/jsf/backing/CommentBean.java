package pavle.stojanovic.blogger.jsf.backing;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import pavle.stojanovic.blogger.domain.Article;
import pavle.stojanovic.blogger.domain.Comment;
import pavle.stojanovic.blogger.domain.User;
import pavle.stojanovic.blogger.rest.app.ErrorMessage;
import pavle.stojanovic.blogger.service.AppException;
import pavle.stojanovic.blogger.service.ArticleService;
import pavle.stojanovic.blogger.service.CommentService;
import pavle.stojanovic.blogger.service.UserService;

@Named
@RequestScoped
public class CommentBean implements Serializable {
	
	private LocalDateTime created;
	private String text;
	private int upVote;
	private int downVote;

	@EJB
	private UserService userService;
	@EJB
	private ArticleService articleService;
	@EJB
	private CommentService commentService;
	
	@Inject
	private LoginBean loginBean;
	
	public String createComment() {
		
		Comment comment = new Comment();
		
		long userId = loginBean.getLoggedUser().getId();
		User user = userService.getUser(userId);
		
		String articleId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("articleId");
		long articleid = Long.parseLong(articleId);
		Article article = articleService.getArticle(articleid);
		
		comment.setText(text);
		comment.setCreated(created);
		
		try {
			commentService.createComment(comment, user, article);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ErrorMessage.ok.getMessage()));
			
			return "articleDetail";
			
		} catch(PersistenceException pe) {
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ErrorMessage.db_problem.getMessage()));
			
			return "";
			
		} catch(AppException ae) {
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ae.getError().getMessage()));
	
			return "";
		}			
	}
	
	public List<Comment> getAllComments() {
		
		String articleId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("articleId");
		long articleid = Long.parseLong(articleId);
		Article article = articleService.getArticle(articleid);
		
		List<Comment> comments = new ArrayList<>();
		for(Comment c : article.getComments()) {
			comments.add(c);
		}
		
		return comments;
	}
	
	public String upVoteComment() {
		
		String commentId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("commentId");
		long id = Long.parseLong(commentId);
		
		Comment comment = new Comment();
		
		comment.setUpVote(1);
		
		commentService.updateComment(id, comment);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Your vote is recorded!"));
		
		return "";
	}
	
	public String downVoteComment() {
		
		String commentId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("commentId");
		long id = Long.parseLong(commentId);
		
		Comment comment = new Comment();
		
		comment.setDownVote(1);
		
		commentService.updateComment(id, comment);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Your vote is recorded!"));
		
		return "";
	}
	
	public Comment getComment() {
		
		String commentId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("commentId");
		
		long id = Long.parseLong(commentId);
		
		return commentService.getComment(id);
	}
	
	public String deleteComment() {
		
		String commentId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("commentId");
		
		long id = Long.parseLong(commentId);
		
		commentService.deleteComment(id);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Comment deleted!"));
		
		return "";
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
	
	private static final long serialVersionUID = 1L;

}
