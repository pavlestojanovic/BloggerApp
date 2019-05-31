package pavle.stojanovic.blogger.domain.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import pavle.stojanovic.blogger.domain.Tag;
import pavle.stojanovic.blogger.rest.app.ErrorMessage;
import pavle.stojanovic.blogger.service.AppException;

public class TagQueries {
	
	public static boolean tagExists(EntityManager em, String value) {
		
		String q = "select t from Tag t where t.value = :value";
		
		TypedQuery<Tag> query = em.createQuery(q, Tag.class);
		query.setParameter("value", value);
		
		List<Tag> tags = query.getResultList();
		
		if(tags.size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public static List<Tag> getAll(EntityManager em, String tag, boolean like) {
		
		if(like == true && tag == null) {
			throw new AppException(ErrorMessage.invalid_query_string);
		}
		
		String q = "Select t from Tag t";
		
		if(tag != null && like == false) {
			q +=" where t.value = :value";
		} else if(tag != null && like == true) {
			q +=" where t.value like concat('%', :value, '%')";
		}
		
		TypedQuery<Tag> query = em.createQuery(q, Tag.class);
		
		if(tag != null) {
			query.setParameter("value", tag);
		}
		
		return query.getResultList();
	}
	
	public static Tag getTag(EntityManager em, long id) {
		
		return em.find(Tag.class, id);
	}

}
