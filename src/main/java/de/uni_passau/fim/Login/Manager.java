package de.uni_passau.fim.Login;

import java.io.Serial;
import java.io.Serializable;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("manager")
@SessionScoped
public class Manager implements Serializable{
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	@Inject
	JdbcTest store;
	@Inject
	Editor edit;
	@Inject
	FacesContext context;

	User user;
	private final Login login = new Login("", "");

	public String validate() {

		user = JdbcTest.getInfo(login);

		if (user == null) {
			FacesMessage fmsg = new FacesMessage("Incorrect login-data.");
			fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage("login:password", fmsg);
			return null;

		} else {
			edit.setValidatedUser(user);
			return "user";
		}
	}

	public String getPassword() {
		return login.getPassword();
	}

	public void setPassword(String newPassword) {
		this.login.setPassword(newPassword);
	}

	public String getUsername() {
		return login.getUsername();
	}

	public void setUsername(String newUsername) {
		this.login.setUsername(newUsername);
	}
}
