package pavle.stojanovic.blogger.jsf.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import pavle.stojanovic.blogger.domain.User;
import pavle.stojanovic.blogger.service.UserService;

@Named
@SessionScoped
public class LoginBean implements Serializable {
	
	private String username;
	private String password;
    private String dbusername;
    private String dbpassword;
    private User loggedUser;
    
    @EJB
	private UserService userService;
    
    public String login() {
    	
    	List<User> u = new ArrayList<>();
    	
    	u = userService.getAll(username, true);
    	
    	if(u.isEmpty()) {
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User does not exist"));
    		return"";
    	} else {
			dbusername = u.iterator().next().getUsername();
			dbpassword = u.iterator().next().getPassword();
			loggedUser = u.iterator().next();
			if(username.equals(dbusername)) {
				if(password.equals(dbpassword)) {
					return "welcome?faces-redirect=true";
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid password"));
					return "";
					}
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid username"));
				return "";
				}
		}
    }
    
    public String logout() {
    	
    	loggedUser = null;
    	
    	return "login?faces-redirect=true";
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

	public String getDbusername() {
		return dbusername;
	}

	public void setDbusername(String dbusername) {
		this.dbusername = dbusername;
	}

	public String getDbpassword() {
		return dbpassword;
	}

	public void setDbpassword(String dbpassword) {
		this.dbpassword = dbpassword;
	}

	public User getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}
	
	private static final long serialVersionUID = 1L;
	
}
