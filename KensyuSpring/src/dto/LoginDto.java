package dto;

public class LoginDto {
	private String userName;
	private String pass;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public LoginDto(String userName, String pass) {
		super();
		this.userName = userName;
		this.pass = pass;
	}
	
}
