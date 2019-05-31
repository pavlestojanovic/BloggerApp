package pavle.stojanovic.blogger.rest.app;

public class ErrorMessage {
	
	private int code;
	
	private String message;

	private ErrorMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public static final ErrorMessage ok = new ErrorMessage(0, "Operation successful!");
	public static final ErrorMessage db_problem = new ErrorMessage(100, "Database error!");
	
	public static final ErrorMessage invalid_username = new ErrorMessage(101, "The username is longer than 20 characters");
	public static final ErrorMessage invalid_password = new ErrorMessage(102, "The password is longer than 10 characters, or don`t have first upper case");
	public static final ErrorMessage invalid_nickname = new ErrorMessage(103, "The nickname is longer than 20 characters");
	public static final ErrorMessage invalid_name = new ErrorMessage(104, "The name is longer than 20 characters");
	public static final ErrorMessage invalid_surname = new ErrorMessage(105, "The surname is longer than 50 characters");
	public static final ErrorMessage wrong_email_format = new ErrorMessage(106, "String is not valid email address, or is longer than 70 characters");
	public static final ErrorMessage username_exists = new ErrorMessage(107, "The username already exists");
	public static final ErrorMessage nickname_exists = new ErrorMessage(108, "The nickname already exists");
	public static final ErrorMessage email_already_exists = new ErrorMessage(109, "Email in db exists.");
	public static final ErrorMessage user_does_not_exists = new ErrorMessage(110, "User with given id does not exist");
	
	public static final ErrorMessage invalid_title = new ErrorMessage(121, "The title is longer than 75 characters");
	public static final ErrorMessage invalid_content = new ErrorMessage(122, "The content is longer than 5000 characters");
	public static final ErrorMessage article_does_not_exists = new ErrorMessage(123, "Article with given id does not exist");
	
	public static final ErrorMessage invalid_comment = new ErrorMessage(131, "The comment is longer than 500 characters");
	public static final ErrorMessage comment_does_not_exists = new ErrorMessage(132, "Comment with given id does not exist");
	
	public static final ErrorMessage rating_does_not_exists = new ErrorMessage(141, "Rating with given id does not exist");
	public static final ErrorMessage invalid_rating = new ErrorMessage(142, "Rating must be between 0 and 5!");
	
	public static final ErrorMessage invalid_tag = new ErrorMessage(151, "The tag is longer than 10 characters");
	public static final ErrorMessage tag_does_not_exists = new ErrorMessage(152, "Tag with given id does not exist");
	
	public static final ErrorMessage invalid_query_string = new ErrorMessage(199, "Query string is not properly formed");
	
}
