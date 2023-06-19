package entities;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Represents a User entity.
 */

public class User implements Serializable{
	private int id;
	public String firstName, lastName, email, position, username;
	private boolean isLogged;
	private String pass;
	
	/**
	 * Constructs a User object with the specified user details.
	 *
	 * @param userHM the HashMap containing user details
	 */
	public User(HashMap<String, Object> userHM) {
	    this.id = (int) userHM.get("id");
	    this.firstName = (String) userHM.get("firstName");
	    this.lastName = (String) userHM.get("lastName");
	    this.email = (String) userHM.get("email");
	    this.position = (String) userHM.get("position");
	    this.pass = (String) userHM.get("pass");
	    this.username = (String) userHM.get("username");
	    this.isLogged = (boolean) userHM.get("isLogged");
	}

	/**
	 * Returns the ID of the User.
	 *
	 * @return the ID
	 */
	public int getId() {
	    return id;
	}

	/**
	 * Sets the ID of the User.
	 *
	 * @param id the ID to set
	 */
	public void setId(int id) {
	    this.id = id;
	}

	/**
	 * Returns the first name of the User.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
	    return firstName;
	}

	/**
	 * Sets the first name of the User.
	 *
	 * @param firstName the first name to set
	 */
	public void setFirstName(String firstName) {
	    this.firstName = firstName;
	}

	/**
	 * Returns the last name of the User.
	 *
	 * @return the last name
	 */
	public String getLastName() {
	    return lastName;
	}

	/**
	 * Sets the last name of the User.
	 *
	 * @param lastName the last name to set
	 */
	public void setLastName(String lastName) {
	    this.lastName = lastName;
	}

	/**
	 * Returns the email of the User.
	 *
	 * @return the email
	 */
	public String getEmail() {
	    return email;
	}

	/**
	 * Sets the email of the User.
	 *
	 * @param email the email to set
	 */
	public void setEmail(String email) {
	    this.email = email;
	}

	/**
	 * Returns the position of the User.
	 *
	 * @return the position
	 */
	public String getPosition() {
	    return position;
	}

	/**
	 * Sets the position of the User.
	 *
	 * @param position the position to set
	 */
	public void setPosition(String position) {
	    this.position = position;
	}

	/**
	 * Returns the username of the User.
	 *
	 * @return the username
	 */
	public String getUsername() {
	    return username;
	}

	/**
	 * Sets the username of the User.
	 *
	 * @param username the username to set
	 */
	public void setUsername(String username) {
	    this.username = username;
	}

	/**
	 * Returns the logged status of the User.
	 *
	 * @return true if the User is logged in, false otherwise
	 */
	public boolean getIsLogged() {
	    return isLogged;
	}

	/**
	 * Sets the logged status of the User.
	 *
	 * @param isLogged the logged status to set
	 */
	public void setIsLogged(boolean isLogged) {
	    this.isLogged = isLogged;
	}
}

	
	

