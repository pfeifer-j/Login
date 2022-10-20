package de.uni_passau.fim.Login;

import java.io.Serial;
import java.io.Serializable;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named("editor")
@SessionScoped
public class Editor implements Serializable{
	
    @Serial
    private static final long serialVersionUID = 1L;
	
	User userAccount;

	public void setPassword(String password) {
		userAccount.setAddress(password);
	}

	public String getPassword() {
		return userAccount.getPassword();
	}

	public String getUsername() {
		return userAccount.getUsername();
	}

	public String getName() {
		return userAccount.getName();
	}

	public void setName(String name) {
		userAccount.setName(name);
	}

	public String getBirthday() {
		return userAccount.getBirthday();
	}

	public void setBirthday(String birthday) {
		userAccount.setBirthday(birthday);
	}

	public String getAddress() {
		return userAccount.getAddress();
	}

	public void setAddress(String address) {
		userAccount.setAddress(address);
	}

	public User getValidatedUser() {
		return userAccount;
	}

	public void setValidatedUser(User user) {
		userAccount = user;
	}

	public String save() {
		JdbcTest.updateInfo(userAccount);
		return "user";
	}

	public String logout() {
		return "login";
	}
}