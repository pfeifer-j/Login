package de.uni_passau.fim.Login;

//Stores the user-data
public class User extends Login {

	private String name;
	private String birthday;
	private String address;

	public User(String username, String password, String name, String birthday, String address) {
		super(username, password);
		this.name = name;
		this.birthday = birthday;
		this.address = address;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
