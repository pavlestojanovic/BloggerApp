package pavle.stojanovic.blogger.rest.app;

import java.util.List;

import pavle.stojanovic.blogger.domain.User;

public class UsersResponse extends RESTResponse {
	
	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
