package de.uni_passau.fim.Login;

public class Login {

	private String password;
	private String username;

	public Login(String name, String password) {
		this.username = name;
		this.password = password;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null)
			return false;
		Login login = (Login) object;
		return username.equals(login.username) && password.equals(login.password);
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
