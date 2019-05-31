package pavle.stojanovic.blogger.jsf.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import pavle.stojanovic.blogger.domain.Article;
import pavle.stojanovic.blogger.domain.User;
import pavle.stojanovic.blogger.rest.app.ErrorMessage;
import pavle.stojanovic.blogger.service.AppException;
import pavle.stojanovic.blogger.service.UserService;

@Named
@RequestScoped
public class UserBean implements Serializable {
	
	private String username;
	private String password;
	private String email;
	private String name;
	private String surname;
	private String nickname;
	
	private UIData dataTable;
	
	@EJB
	private UserService userService;
	
	public String createUser() {
		
		User user = new User();
		
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setName(name);
		user.setSurname(surname);
		user.setNickname(nickname);
		
		try {
			userService.createUser(user);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ErrorMessage.ok.getMessage()));
			
			return "login?faces-redirect=true";
			
		} catch(PersistenceException pe) {
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ErrorMessage.db_problem.getMessage()));
			
			return "";
			
		} catch(AppException ae) {
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ae.getError().getMessage()));
	
			return "";
		}		
	}

	public List<User> getAllUsers() {
		
		return userService.getAll(null, false);
	}
	
	public User getUser() {
		
		String userId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userId");
		
		int id = Integer.parseInt(userId);
		
		return userService.getUser(id);
	}
	
	public List<String> getArticles() {
		
		List<String> articleTitle = new ArrayList<>();
		
		String userId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userId");
		long id = Long.parseLong(userId);
		User user = userService.getUser(id);
		
		for(Article a : user.getArticles()) {
			articleTitle.add(a.getTitle());
		}
		
		return articleTitle;
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

	public UIData getDataTable() {
		return dataTable;
	}
	
	public void setDataTable(UIData dataTable) {
		this.dataTable = dataTable;
	}
	
	private static final long serialVersionUID = 1L;

}
