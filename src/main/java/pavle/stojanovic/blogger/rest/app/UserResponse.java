package pavle.stojanovic.blogger.rest.app;

import pavle.stojanovic.blogger.rest.dto.UserDTO;

public class UserResponse extends RESTResponse {
	
	private UserDTO user;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

}
