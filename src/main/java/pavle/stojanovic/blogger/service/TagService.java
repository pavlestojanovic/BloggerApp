package pavle.stojanovic.blogger.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pavle.stojanovic.blogger.domain.Article;
import pavle.stojanovic.blogger.domain.Tag;
import pavle.stojanovic.blogger.domain.jpa.TagQueries;
import pavle.stojanovic.blogger.rest.app.ErrorMessage;

@Stateless
public class TagService {
	
	@PersistenceContext
	private EntityManager em;
	
	public Tag createTag(Tag tag, Article article) {
		
		if(tag.getValue().length() > 10) {
			throw new AppException(ErrorMessage.invalid_tag);
		}
		
		if(TagQueries.tagExists(em, tag.getValue())) {
			List<Tag> t = TagQueries.getAll(em, tag.getValue(), true);
			tag = t.get(0);
		} else {
			em.persist(tag); 
		}
		
		article = em.merge(article);
		
		tag.getArticles().add(article);
		article.getTags().add(tag);
		
		return tag;	
	}
	
	public List<Tag> getAll(String tag, boolean like) {
		
		return TagQueries.getAll(em, tag, like);
	}
	
	public Tag getTag(long id) {
		
		Tag t = TagQueries.getTag(em, id);
		
		if(t == null) {
			throw new AppException(ErrorMessage.tag_does_not_exists);
		}
		
		Set<Article> article = new HashSet<>();
		for(Article a : t.getArticles()) {
			article.add(a);
		}
		t.setArticles(article);
		
		return t;
	}
	
	public Tag updateTag(long id, Tag tag) {
		
		if(tag.getValue().length() > 10) {
			throw new AppException(ErrorMessage.invalid_tag);
		}
		
		Tag t = TagQueries.getTag(em, id);
		
//		if(t == null) {
//			return createTag(tag);
//		}
		
		t.setValue(tag.getValue());
		
		return t;
	}
	
	public void deleteTag(long id) {
		
		Tag t = TagQueries.getTag(em, id);
		
		if(t == null) {
			throw new AppException(ErrorMessage.tag_does_not_exists);
		}
		
		em.remove(t);
	}

}
