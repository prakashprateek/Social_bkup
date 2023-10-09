package com.berriesoft.social.user;

import java.time.LocalDate;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="app_user")
@SQLDelete(sql = "UPDATE app_user SET is_deleted = 1 WHERE id=?")
@Where(clause = "is_deleted=0")
public class AppUser
{
	@Id
	@GeneratedValue
	private int id;

	@Size(min = 2, max = 10)
	@NotNull
	private String userName;
	
	@Past
	private LocalDate dateOfBirth;
	
	@NotNull
	@Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
	private String firstName;
	
	@NotNull
	@Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
	private String lastName;
	
	@NotNull
	private int isDeleted;
	@NotNull
	private int isInactive;

	public AppUser(int id, String name, LocalDate dateOfBirth, String firstName, String lastName, int isDeleted, int isInactive)
	{
		super();
		this.id = id;
		this.userName = name;
		this.dateOfBirth = dateOfBirth;
		this.firstName = firstName;
		this.lastName = lastName;
		this.isDeleted = isDeleted;
		this.isInactive = isInactive;
	}

	public AppUser()
	{

	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public LocalDate getDateOfBirth()
	{
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth)
	{
		this.dateOfBirth = dateOfBirth;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public int getIsDeleted()
	{
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted)
	{
		this.isDeleted = isDeleted;
	}

	public int getIsInactive()
	{
		return isInactive;
	}

	public void setIsInactive(int isInactive)
	{
		this.isInactive = isInactive;
	}

	@Override
	public String toString()
	{
		return "AppUser [id=" + id + ", userName=" + userName + ", dateOfBirth=" + dateOfBirth + ", firstName="
				+ firstName + ", lastName=" + lastName + ", isDeleted=" + isDeleted + ", isInactive=" + isInactive
				+ "]";
	}



	

}
