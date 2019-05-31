package pavle.stojanovic.blogger.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pavle.stojanovic.blogger.domain.Article;
import pavle.stojanovic.blogger.domain.User;
import pavle.stojanovic.blogger.domain.jpa.UserQueries;
import pavle.stojanovic.blogger.rest.app.ErrorMessage;
import pavle.stojanovic.blogger.service.utils.EmailRegEX;

@Stateless
public class UserService {
	
	@PersistenceContext
	private EntityManager em;
	
	public User createUser(User user) {
		
		if(user.getUsername().length() > 20) {
			throw new AppException(ErrorMessage.invalid_username);
		}
		
		if(user.getNickname().length() > 20) {
			throw new AppException(ErrorMessage.invalid_nickname);
		}
		
		if(user.getName().length() > 20) {
			throw new AppException(ErrorMessage.invalid_name);
		}
		
		if(user.getSurname().length() > 50) {
			throw new AppException(ErrorMessage.invalid_surname);
		}
		
		if(user.getPassword().length() > 10 || !Character.isUpperCase(user.getPassword().charAt(0))) {
			throw new AppException(ErrorMessage.invalid_password);
		}
		
		if(user.getEmail().length() > 70 || !EmailRegEX.validate(user.getEmail())) {
			throw new AppException(ErrorMessage.wrong_email_format);
		}
		
		if(UserQueries.nicknameExists(em, user.getNickname())) {
			throw new AppException(ErrorMessage.nickname_exists);
		}
		
		if(UserQueries.usernameExists(em, user.getUsername())) {
			throw new AppException(ErrorMessage.username_exists);
		}
		
		if(UserQueries.emailExists(em, user.getEmail())) {
			throw new AppException(ErrorMessage.email_already_exists);
		}
		
		em.persist(user);
		
		return user;	
	}
	
	public List<User> getAll(String user, boolean like) {
		
		return UserQueries.getAll(em, user, like);
	}
	
	public User getUser(long id) {
		
		User u = UserQueries.getUser(em, id);
		
		if(u == null) {
			throw new AppException(ErrorMessage.user_does_not_exists);
		}
		
		Set<Article> article = new HashSet<>();
		for(Article a : u.getArticles()) {
			article.add(a);
		}
		u.setArticles(article);
		
		return u;
	}
	
	public User updateUser(long id, User user) {
		
		if(user.getUsername() != null && user.getUsername().length() > 20) {
			throw new AppException(ErrorMessage.invalid_username);
		}
		
		if(user.getNickname() != null && user.getNickname().length() > 20) {
			throw new AppException(ErrorMessage.invalid_nickname);
		}
		
		if(user.getName() != null && user.getName().length() > 20) {
			throw new AppException(ErrorMessage.invalid_name);
		}
		
		if(user.getSurname() != null && user.getSurname().length() > 50) {
			throw new AppException(ErrorMessage.invalid_surname);
		}
		
		if(user.getPassword() != null && (user.getPassword().length() > 10 || !Character.isUpperCase(user.getPassword().charAt(0)))) {
			throw new AppException(ErrorMessage.invalid_password);
		}
		
		if(user.getEmail() != null && (user.getEmail().length() > 70 || !EmailRegEX.validate(user.getEmail()))) {
			throw new AppException(ErrorMessage.wrong_email_format);
		}
		
		User u = UserQueries.getUser(em, id);
		
//		if(u == null) {
//			return createUser(user);
//		}
		
		User u1 = UserQueries.findByNickname(em, user.getNickname());
		
		if(u1 != null && !(u1.getId() == u.getId())) {
			throw new AppException(ErrorMessage.nickname_exists);
		}
		
		if(user.getNickname() == null) {
			u.getNickname();
		} else { 
			u.setNickname(user.getNickname());
		}

		User u2 = UserQueries.findByUsername(em, user.getUsername());
		
		if(u2 != null && !(u2.getId() == u.getId())) {
			throw new AppException(ErrorMessage.username_exists);
		}
		
		if(user.getUsername() == null) {
			u.getUsername();
		} else { 
			u.setUsername(user.getUsername());
		}
		
		User u3 = UserQueries.findByEmail(em, user.getEmail());
		
		if(u3 != null && !(u3.getId() == u.getId())) {
			throw new AppException(ErrorMessage.email_already_exists);
		}
		
		if(user.getEmail() == null) {
			u.getEmail();
		} else { 
			u.setEmail(user.getEmail());
		}
		
		if(user.getName() == null) {
			u.getName();
		} else { 
			u.setName(user.getName());
		}
		
		if(user.getSurname() == null) {
			u.getSurname();
		} else { 
			u.setSurname(user.getSurname());
		}
		
		if(user.getPassword() == null) {
			u.getPassword();
		} else { 
			u.setPassword(user.getPassword());
		}
		
		return u;
	}
	
	public void deleteUser(long id) {
		
		User u = UserQueries.getUser(em, id);
		
		if(u == null) {
			throw new AppException(ErrorMessage.user_does_not_exists);
		}
		
		em.remove(u);
	}

}
