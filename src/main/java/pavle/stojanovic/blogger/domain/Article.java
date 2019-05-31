package pavle.stojanovic.blogger.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import pavle.stojanovic.blogger.rest.dto.ArticleDTO;
import pavle.stojanovic.blogger.domain.utils.LocalDateTimeAdapter;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Article {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String title;
	
	private String content;
	
	@XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
	private LocalDateTime created;
	
	private int ratingCounter;
	
	@Column
	@ElementCollection(targetClass=String.class, fetch = FetchType.EAGER)
	private Set<String> keywords = new HashSet<>();
	
	private double averageCounter;
	
	@ManyToOne
	private User user;
	@XmlTransient
	@ManyToMany(mappedBy="articles", fetch = FetchType.EAGER)
	private Set<Tag> tags = new HashSet<>();
	@XmlTransient
	@OneToMany(mappedBy="article", fetch = FetchType.EAGER)
	private Set<Comment> comments;
	@XmlTransient
	@OneToMany(mappedBy="article", fetch = FetchType.EAGER)
	private Set<Rating> ratings;
	
	public Article() {}
	
	public Article(ArticleDTO article) {
		this.title = article.getTitle();
		this.content = article.getContent();
		this.created = article.getCreated();
		this.ratingCounter = article.getRatingCounter();
		this.keywords = article.getKeywords();
		this.averageCounter = article.getAverageCounter();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	
	public int getRatingCounter() {
		return ratingCounter;
	}

	public void setRatingCounter(int ratingCounter) {
		this.ratingCounter = ratingCounter;
	}
	
	public double getAverageCounter() {
		return averageCounter;
	}
	
	public Set<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<String> keywords) {
		this.keywords = keywords;
	}

	public void setAverageCounter(double averageCounter) {
		this.averageCounter = averageCounter;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}

}
