package pavle.stojanovic.blogger.service;

import javax.ejb.ApplicationException;

import pavle.stojanovic.blogger.rest.app.ErrorMessage;

@ApplicationException(rollback = true)
public class AppException extends RuntimeException {
	
	private ErrorMessage error;
	
	public AppException(ErrorMessage error) {
		this.error = error;
	}

	public ErrorMessage getError() {
		return error;
	}

	public void setError(ErrorMessage error) {
		this.error = error;
	}
	
	private static final long serialVersionUID = 1L;

}
