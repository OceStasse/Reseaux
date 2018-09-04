package database.entities;

import java.io.Serializable;
import java.sql.Date;

public class Passenger implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String lastName;
	private Date birthdate;
	private char gender;
	private String firstname;
	private String login;
	private String password;
	
	public Passenger(String id,String lastName, Date birthdate, char gender, String firstname, String login, String password) {
		super();
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.gender = gender;
		this.firstname = firstname;
		this.login = login;
		this.password = password;
	}

	@Override
	public String toString() {
		return "Passenger [id=" + id + ", lastName=" + lastName + ", birthdate=" + birthdate + ", gender=" + gender
				+ ", firstname=" + firstname + ", login=" + login + ", password=" + password + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Passenger() {
		
	}

}
