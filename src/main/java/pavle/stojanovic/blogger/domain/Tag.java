package pavle.stojanovic.blogger.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;

import pavle.stojanovic.blogger.rest.dto.TagDTO;

@Entity
@XmlRootElement
public class Tag {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String value;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Article> articles = new HashSet<>();
	
	public Tag () {}
	
	public Tag(TagDTO tag) {
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

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

}
