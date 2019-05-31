package pavle.stojanovic.blogger.rest.dto;

import java.util.HashSet;
import java.util.Set;

import pavle.stojanovic.blogger.domain.Article;
import pavle.stojanovic.blogger.domain.Comment;
import pavle.stojanovic.blogger.domain.Rating;
import pavle.stojanovic.blogger.domain.User;

public class UserDTO {
	
	private long id;
	
	private String username;
	
	private String password;
	
	private String email;
	
	private String name;
	
	private String surname;
	
	private String nickname;

	private Set<ArticleDTO> articles;
	
	private Set<RatingDTO> ratings;
	
	private Set<CommentDTO> comments;
	
	public UserDTO() {}
	
	public UserDTO(User u) {
		this.id = u.getId();
		this.username = u.getUsername();
		this.password = u.getPassword();
		this.email = u.getEmail();
		this.name = u.getName();
		this.surname = u.getSurname();
		this.nickname = u.getNickname();
		
		this.articles = new HashSet<>();
		for(Article a : u.getArticles()) {
			this.articles.add(new ArticleDTO(a, true, true));
		}
		
		this.ratings = new HashSet<>();
		for(Rating r : u.getRatings()) {
			this.ratings.add(new RatingDTO(r, true));
		}
		
		this.comments = new HashSet<>();
		for(Comment c : u.getComments()) {
			this.comments.add(new CommentDTO(c, true));
		}
	}
	
	public UserDTO(User u, boolean get) {
		this.id = u.getId();
		this.username = u.getUsername();
		this.password = u.getPassword();
		this.email = u.getEmail();
		this.name = u.getName();
		this.surname = u.getSurname();
		this.nickname = u.getNickname();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Set<ArticleDTO> getArticles() {
		return articles;
	}

	public void setArticles(Set<ArticleDTO> articles) {
		this.articles = articles;
	}


}
