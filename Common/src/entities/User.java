package entities;

import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable{
	private int id;
	private String firstName, lastName, email, position, pass, username;
	private boolean isLogged;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean getIsLogged() {
		return isLogged;
	}
	public void setIsLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}
	public User(HashMap<String,Object> userHM) {
		super();
		this.id = (int) userHM.get("id");
		this.firstName = (String) userHM.get("firstName");
		this.lastName = (String) userHM.get("lastName");
		this.email = (String) userHM.get("email");
		this.position = (String) userHM.get("position");
		this.pass = (String) userHM.get("pass");
		this.username = (String) userHM.get("username");
		this.isLogged = (boolean) userHM.get("isLogged");
	}

	
	
}
