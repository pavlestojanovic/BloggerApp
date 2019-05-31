package pavle.stojanovic.blogger.rest.dto;

import java.util.HashSet;
import java.util.Set;

import pavle.stojanovic.blogger.domain.Article;
import pavle.stojanovic.blogger.domain.Tag;

public class TagDTO {
	
	private long id;
	
	private String value;
	
	private Set<ArticleDTO> articles;
	
	public TagDTO() {}
	
	public TagDTO(Tag tag) {
		this.id = tag.getId();
		this.value = tag.getValue();
		
		this.articles = new HashSet<>();
		for(Article a : tag.getArticles()) {
			this.articles.add(new ArticleDTO(a, true));
		}
	}
	
	public TagDTO(Tag tag, boolean get) {
		this.id = tag.getId();
		this.value = tag.getValue();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Set<ArticleDTO> getArticles() {
		return articles;
	}

	public void setArticles(Set<ArticleDTO> articles) {
		this.articles = articles;
	}

}
